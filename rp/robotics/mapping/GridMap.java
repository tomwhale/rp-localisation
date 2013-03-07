package rp.robotics.mapping;

import lejos.geom.Line;
import lejos.geom.Point;
import lejos.geom.Rectangle;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.Pose;

/**
 * Creates a regular grid of points on top of a regular leJOS LineMap. The grid
 * is defined by the grid width and height parameters in the constructor. and
 * the grid is placed at the given offset from the origin.
 * 
 * @author nah
 * 
 */
public class GridMap extends LineMap {

	private float m_cellSize;
	private float m_yStart;
	private float m_xStart;
	private int m_gridHeight;
	private int m_gridWidth;
	private float m_largestDimension;

	/**
	 * 
	 * Overall grid width is (_gridWidth - 1) * _cellSize.
	 * 
	 * @param _gridWidth
	 *            The number of points the grid is wide.
	 * @param _gridHeight
	 * @param _xStart
	 * @param _yStart
	 * @param _cellSize
	 * @param _lines
	 * @param _boundingRect
	 */
	public GridMap(int _gridWidth, int _gridHeight, float _xStart,
			float _yStart, float _cellSize, Line[] _lines,
			Rectangle _boundingRect) {
		super(_lines, _boundingRect);

		// Sanity check, test whether the given grid fits within the bounds of
		// the map
		float gridMetricWidth = (_gridWidth - 1) * _cellSize;
		assert gridMetricWidth < _boundingRect.getWidth() : "Given grid is wider than bounding rectangle: "
				+ gridMetricWidth + " " + _boundingRect.getWidth();

		float gridMetricHeight = (_gridHeight - 1) * _cellSize;
		assert gridMetricHeight < _boundingRect.getHeight() : "Given grid is taller than bounding rectangle: "
				+ gridMetricHeight + " " + _boundingRect.getHeight();

		m_gridWidth = _gridWidth;
		m_gridHeight = _gridHeight;
		m_xStart = _xStart;
		m_yStart = _yStart;
		m_cellSize = _cellSize;

		m_largestDimension = Math
				.max(_boundingRect.width, _boundingRect.height);
	}

	/**
	 * 
	 * Create a grid starting from 0,0 on the LineMap.
	 * 
	 * @param _gridWidth
	 *            The number of points the grid is wide.
	 * @param _gridHeight
	 * @param _cellSize
	 * @param _lines
	 * @param _boundingRect
	 */
	public GridMap(int _gridWidth, int _gridHeight, float _cellSize,
			Line[] _lines, Rectangle _boundingRect) {
		this(_gridWidth, _gridHeight, 0, 0, _cellSize, _lines, _boundingRect);
	}

	/**
	 * Tests whether the input point is a point on the grid.
	 * 
	 * @param _x
	 * @param _y
	 * @return
	 */
	public boolean isValidGridPoint(int _x, int _y) {
		return _x >= 0 && _x < m_gridWidth && _y >= 0 && _y < m_gridHeight;
	}

	/**
	 * Tests whether the input point is inside the mapped area.
	 * 
	 * @param _x
	 * @param _y
	 * @return
	 */
	public boolean isObstructed(int _x, int _y) {
		return !inside(getCoordinateOfGridPoint(_x, _y));
	}

	/**
	 * Get the coordinate of the given grid point.
	 * 
	 * @param _x
	 * @param _y
	 * @return
	 */
	public final Point getCoordinateOfGridPoint(int _x, int _y) {
		assert (isValidGridPoint(_x, _y));
		return new Point(getXCoordinateOfGridPoint(_x),
				getYCoordinateOfGridPoint(_y));
	}

	/**
	 * @param _x
	 * @return
	 */
	public float getYCoordinateOfGridPoint(int _y) {
		return m_yStart + (_y * m_cellSize);
	}

	/**
	 * @param _x
	 * @return
	 */
	public float getXCoordinateOfGridPoint(int _x) {
		return m_xStart + (_x * m_cellSize);
	}

	/**
	 * Calculate the range of a robot to the nearest wall. Edited version from
	 * LineMap as it doesn't handle maps over a certain size.
	 * 
	 * @param pose
	 *            the pose of the robot
	 * @return the range or -1 if not in range
	 */
	@Override
	public float range(Pose pose) {
		Line l = new Line(pose.getX(), pose.getY(), pose.getX()
				+ m_largestDimension
				* (float) Math.cos(Math.toRadians(pose.getHeading())),
				pose.getY() + m_largestDimension
						* (float) Math.sin(Math.toRadians(pose.getHeading())));
		Line rl = null;

//		System.out.println("target: " + l.x1 + " " + l.y1 + ", " + l.x2 + " "
//				+ l.y2);

		Line[] lines = getLines();
		for (int i = 0; i < lines.length; i++) {

//			System.out.println(i + " checking against: " + lines[i].x1 + " "
//					+ lines[i].y1 + ", " + lines[i].x2 + " " + lines[i].y2);

			Point p = intersectsAt(lines[i], l);

			if (p == null) {
				// Does not intersect
//				System.out.println("does not intersect");
				continue;
			}

			Line tl = new Line(pose.getX(), pose.getY(), p.x, p.y);

//			System.out.println("does intersect: " + tl.length());

			// If the range line intersects more than one map line
			// then take the shortest distance.
			if (rl == null || tl.length() < rl.length()) {
				rl = tl;
			}
		}
		return (rl == null ? -1 : rl.length());
	}

	/**
	 * Can the robot move from grid point x1,y1 to x2,y2 without passing through
	 * an obstacle.
	 * 
	 * @param _x1
	 * @param _y1
	 * @param _x2
	 * @param _y2
	 * @return
	 */
	public boolean isValidTransition(int _x1, int _y1, int _x2, int _y2) {
		

		
		if (!isValidGridPoint(_x1, _y1) || !isValidGridPoint(_x2, _y2)) {
			return false;
		}

		if (isObstructed(_x1, _y1) || isObstructed(_x2, _y2)) {
			return false;
		}

		Line transition = createLineBetweenGridPoints(_x1, _y1, _x2, _y2);
		Line[] lines = getLines();
		for (int i = 0; i < lines.length; i++) {

			// System.out.println(i+ " checking against: " + lines[i].x1 + " "
			// + lines[i].y1 + ", " + lines[i].x2 + " " + lines[i].y2);

			Point p = intersectsAt(lines[i], transition);

			if (p != null) {
				return false;
			}

		}
		return true;
	}

	/**
	 * @param _x1
	 * @param _y1
	 * @param _x2
	 * @param _y2
	 * @return
	 */
	public Line createLineBetweenGridPoints(int _x1, int _y1, int _x2, int _y2) {
		Line transition = new Line(getXCoordinateOfGridPoint(_x1),
				getYCoordinateOfGridPoint(_y1), getXCoordinateOfGridPoint(_x2),
				getYCoordinateOfGridPoint(_y2));
		return transition;
	}

	/**
	 * 
	 * 
	 * Calculate the point of intersection of two lines. * Copied from Line in
	 * leJOS.
	 * 
	 * @param l
	 *            the second line
	 * 
	 * @return the point of intersection or null if the lines do not intercept
	 *         or are coincident
	 */
	public Point intersectsAt(Line l1, Line l) {
		float x, y, a1, a2, b1, b2;

		if (l1.y2 == l1.y1 && l.y2 == l.y1) {
			return null; // horizontal parallel
		}

		if (l1.x2 == l1.x1 && l.x2 == l.x1) {
			return null; // vertical parallel
		}

		// Find the point of intersection of the lines extended to infinity
		if (l1.x1 == l1.x2 && l.y1 == l.y2) { // perpendicular
			x = l1.x1;
			y = l.y1;
		} else if (l1.y1 == l1.y2 && l.x1 == l.x2) { // perpendicular
			x = l.x1;
			y = l1.y1;
		} else if (l1.y2 == l1.y1 || l.y2 == l.y1) { // one line is horizontal
			a1 = (l1.y2 - l1.y1) / (l1.x2 - l1.x1);
			b1 = l1.y1 - a1 * l1.x1;
			a2 = (l.y2 - l.y1) / (l.x2 - l.x1);
			b2 = l.y1 - a2 * l.x1;

			if (a1 == a2) {
				return null; // parallel
			}
			x = (b2 - b1) / (a1 - a2);
			y = a1 * x + b1;
		} else {
			a1 = (l1.x2 - l1.x1) / (l1.y2 - l1.y1);
			b1 = l1.x1 - a1 * l1.y1;
			a2 = (l.x2 - l.x1) / (l.y2 - l.y1);
			b2 = l.x1 - a2 * l.y1;

			if (a1 == a2) {
				return null; // parallel
			}
			y = (b2 - b1) / (a1 - a2);
			x = a1 * y + b1;
		}

		// FIX: The math above creates slightly odd results that are almost
		// correct and look fine after rounding to nearest int. This could add
		// inaccuracies later but nothing beyond what the robot is already
		// facing.
		x = Math.round(x);
		y = Math.round(y);

		// System.out.println("here: " + x + "," + y);

		// Check that the point of intersection is within both line segments
		if (!between(x, l1.x1, l1.x2)) {
			return null;
		}
		if (!between(y, l1.y1, l1.y2)) {
			return null;
		}
		if (!between(x, l.x1, l.x2)) {
			return null;
		}
		if (!between(y, l.y1, l.y2)) {
			return null;
		}
		return new Point(x, y);
	}

	/**
	 * 
	 * Copied from Line in leJOS.
	 * 
	 * Return true iff x is between x1 and x2
	 */
	private boolean between(float x, float x1, float x2) {
		if (x1 <= x2 && x >= x1 && x <= x2) {
			return true;
		}
		if (x2 < x1 && x >= x2 && x <= x1) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the distance to the nearest obstacle at a given heading from the
	 * grid point.
	 * 
	 * @param _x
	 * @param _y
	 * @param _heading
	 * @return
	 */
	public float rangeToObstacleFromGridPoint(int _x, int _y, float _heading) {
		return range(new Pose(getXCoordinateOfGridPoint(_x),
				getYCoordinateOfGridPoint(_y), _heading));
	}

	/**
	 * Gets the distance to the nearest obstacle at a given heading from the
	 * grid point.
	 * 
	 * @param _x
	 * @param _y
	 * @param _heading
	 * @return
	 */
	public float rangeToObstacleFromGridPoint(int _x, int _y, Heading _heading) {
		return rangeToObstacleFromGridPoint(_x, _y, Heading.toDegrees(_heading));
	}

	public float getCellSize() {
		return m_cellSize;
	}

	public float getYStart() {
		return m_yStart;
	}

	public float getXStart() {
		return m_xStart;
	}

	public int getGridHeight() {
		return m_gridHeight;
	}

	public int getGridWidth() {
		return m_gridWidth;
	}

	public static void main(String[] args) {
		GridMap map = LocalisationUtils.createTrainingMap();
		System.out.println(map.rangeToObstacleFromGridPoint(3, 1,
				Heading.PLUS_Y));

	}

}
