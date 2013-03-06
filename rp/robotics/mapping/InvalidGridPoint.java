package rp.robotics.mapping;

import java.awt.Point;

public class InvalidGridPoint extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Point m_gridPoint;

	public InvalidGridPoint(String _message, int _x, int _y) {
		super(_message);
		m_gridPoint = new Point(_x, _y);
	}

	public int getX() {
		return m_gridPoint.x;
	}

	public int getY() {
		return m_gridPoint.y;
	}

	public Point getPoint() {
		return m_gridPoint;
	}

}
