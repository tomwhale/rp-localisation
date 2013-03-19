package rp.robotics.mapping;

import java.util.ArrayList;

import lejos.geom.Line;
import lejos.geom.Rectangle;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.Move;
import lejos.robotics.navigation.Pose;

/**
 * 
 * @author nah
 */
public class LocalisationUtils {

	private static final float BOARD_WIDTH = 1.7f;

	/**
	 * Create a rectangular map with 0,0 at the top left and the given width and
	 * height
	 * 
	 * @param _width
	 * @param _height
	 * @return
	 */
	public static LineMap createRectangularMap(float _width, float _height) {

		// these are the walls for the world outline
		Line[] lines = new Line[] { new Line(0f, 0f, _width, 0f),
				new Line(_width, 0f, _width, _height),
				new Line(_width, _height, 0f, _height),
				new Line(0f, _height, 0f, 0f) };

		LineMap map = new LineMap(lines, new Rectangle(0, 0, _width, _height));

		return map;
	}

	
	public static GridMap createRectangularGridMap(int _xJunctions,
			int _yJunctions, float _pointSeparation) {

	
		int xInset = (int) (_pointSeparation / 2);
		int yInset = (int) (_pointSeparation / 2);

		float _width = _xJunctions * _pointSeparation;
		float _height = _yJunctions * _pointSeparation;

		ArrayList<Line> lines = new ArrayList<Line>();

		// these are the walls for the world outline
		lines.add(new Line(0f, 0f, _width, 0f));
		lines.add(new Line(_width, 0f, _width, _height));
		lines.add(new Line(_width, _height, 0f, _height));
		lines.add(new Line(0f, _height, 0f, 0f));

		Line[] lineArray = new Line[lines.size()];
		lines.toArray(lineArray);

		return new GridMap(_xJunctions, _yJunctions, xInset, yInset,
				_pointSeparation, lineArray, new Rectangle(0, 0, _width,
						_height));

	}

	/**
	 * Turns a straight line into a box with 4 walls around the line at the
	 * given width
	 * 
	 * @return
	 */
	public static ArrayList<Line> lineToBox(float _x1, float _y1, float _x2,
			float _y2) {

		ArrayList<Line> box = new ArrayList<Line>(4);

		float expand = Math.round(BOARD_WIDTH / 2f);

		// extend in x direction
		if (_x1 == _x2) {
			box.add(new Line(_x1 - expand, _y1, _x1 + expand, _y1));
			box.add(new Line(_x1 + expand, _y1, _x2 + expand, _y2));
			box.add(new Line(_x2 + expand, _y2, _x2 - expand, _y2));
			box.add(new Line(_x2 - expand, _y2, _x1 - expand, _y1));
		} else if (_y1 == _y2) {
			box.add(new Line(_x1, _y1 + expand, _x2, _y2 + expand));
			box.add(new Line(_x2, _y2 - expand, _x2, _y2 + expand));
			box.add(new Line(_x1, _y1 - expand, _x2, _y2 - expand));
			box.add(new Line(_x1, _y1 - expand, _x1, _y1 + expand));

		} else {
			throw new RuntimeException(
					"can only use this with axis-aligned lines");
		}

		return box;
	}

	/**
	 * Creates a grid map to match the training map as of 6/3/2013. Note that
	 * this map uses
	 * 
	 * @return
	 */
	public static GridMap createTrainingMap() {
		float height = 238;
		float width = 366;

		// junction numbers
		int xJunctions = 11;
		int yJunctions = 7;

		// position of 0,0 junction wrt to top left of map
		int xInset = 24;
		int yInset = 24;

		// length of edges between junctions.
		int junctionSeparation = 30;

		ArrayList<Line> lines = new ArrayList<Line>();

		// these are the walls for the world outline
		lines.add(new Line(0f, 0f, width, 0f));
		lines.add(new Line(width, 0f, width, height));
		lines.add(new Line(width, height, 0f, height));
		lines.add(new Line(0f, height, 0f, 0f));

		lines.add(new Line(75.0f, 133f, 100f, 133f));
		lines.add(new Line(75.0f, 193.0f, 100f, 193.0f));
		lines.add(new Line(100f, 133f, 100f, 193.0f));
		lines.add(new Line(75.0f, 133f, 75.0f, 193.0f));

		lines.addAll(lineToBox(42f, 67f, 287f, 67f));
		lines.add(new Line(287f, 0, 287f, 67f));
		lines.add(new Line(257f, 0, 257f, 67f));

		lines.addAll(lineToBox(135f, 129f, 255f, 129f));
		lines.addAll(lineToBox(135f, 129f, 135f, height));

		lines.addAll(lineToBox(194f, 191f, 254f, 191f));
		lines.addAll(lineToBox(194f, 191f, 194f, height));

		lines.add(new Line(width - 42f, 99f, width, 99f));
		lines.add(new Line(width - 42f, 159f, width, 159f));
		lines.add(new Line(width - 42f, 99f, width - 42f, 159f));
		Line[] lineArray = new Line[lines.size()];
		lines.toArray(lineArray);

		return new GridMap(xJunctions, yJunctions, xInset, yInset,
				junctionSeparation, lineArray, new Rectangle(0, 0, width,
						height));
	}

	/**
	 * Creates a grid map to match the training map as of 4/3/2013. Note that
	 * this map uses
	 * 
	 * @return
	 */
	public static GridMap createKenMap() {
		float height = 238;
		float width = 366;

		// junction numbers
		int xJunctions = 11;
		int yJunctions = 7;

		// position of 0,0 junction wrt to top left of map
		int xInset = 24;
		int yInset = 24;

		// length of edges between junctions.
		int junctionSeparation = 30;

		ArrayList<Line> lines = new ArrayList<Line>();

		// these are the walls for the world outline
		lines.add(new Line(0f, 0f, width, 0f));
		lines.add(new Line(width, 0f, width, height));
		lines.add(new Line(width, height, 0f, height));
		lines.add(new Line(0f, height, 0f, 0f));
		// // 1
		lines.add(new Line(77, height - 41 - 6, 77, height - 101 - 6));
		lines.add(new Line(77, height - 101 - 6, 91, height - 101 - 6));
		lines.add(new Line(91, height - 101 - 6, 91, height - 41 - 6));
		lines.add(new Line(91, height - 41 - 6, 77, height - 41 - 6));

		// 2 - fix, 1.7 wide boards
		// new Line(40, height - 145, 284, height - 145),
		lines.addAll(lineToBox(40, height - 145, 284, height - 145));
		// new Line(255, height - 238, 255, height - 145),
		lines.addAll(lineToBox(255, height - 238, 255, height - 145));

		//
		// 3 - fix, 1.7 wide boards
		lines.addAll(lineToBox(366, height - 141, 324, height - 141));
		lines.addAll(lineToBox(324, height - 141, 324, height - 82));
		// 4
		lines.addAll(lineToBox(256, height - 110, 136, height - 110));
		lines.addAll(lineToBox(136, height - 110, 136, height - 0));
		lines.addAll(lineToBox(194, height - 110, 194, height - 77));
		lines.addAll(lineToBox(194, height - 77, 254, height - 77));
		lines.addAll(lineToBox(254, height - 50, 194, height - 50));
		lines.addAll(lineToBox(194, height - 50, 194, height - 0));

		Line[] lineArray = new Line[lines.size()];
		lines.toArray(lineArray);

		return new GridMap(xJunctions, yJunctions, xInset, yInset,
				junctionSeparation, lineArray, new Rectangle(0, 0, width,
						height));
	}

	public static String toString(Pose _pose) {
		StringBuilder sb = new StringBuilder("Pose: ");
		sb.append(_pose.getX());
		sb.append(", ");
		sb.append(_pose.getY());
		sb.append(", ");
		sb.append(_pose.getHeading());
		return sb.toString();
	}

	/**
	 * Calculate the change in X coordinate to pose from move.
	 * 
	 * @param _previousPose
	 * @param _move
	 * @return
	 */
	public static float changeInX(Pose _previousPose, Move _move) {
		return (_move.getDistanceTraveled() * ((float) Math.cos(Math
				.toRadians(_previousPose.getHeading()))));
	}

	/**
	 * Calculate the change in Y coordinate to pose from move.
	 * 
	 * @param _previousPose
	 * @param _move
	 * @return
	 */
	public static float changeInY(Pose _previousPose, Move _move) {
		return (_move.getDistanceTraveled() * ((float) Math.sin(Math
				.toRadians(_previousPose.getHeading()))));
	}
}
