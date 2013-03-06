package rp.robotics.visualisation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import rp.robotics.mapping.GridMap;

import lejos.geom.Point;



public class GridMapVisualisation extends LineMapVisualisation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected GridMap m_gridMap;

	public GridMapVisualisation(GridMap _gridMap, float _scaleFactor) {
		super(_gridMap, _scaleFactor);
		m_gridMap = _gridMap;
	}

	@Override
	protected void renderMap(Graphics2D _g2) {
		// render lines first
		super.renderMap(_g2);

		_g2.setStroke(new BasicStroke(1));
		_g2.setPaint(Color.BLUE);
		
		// then add grid
		for (int x = 0; x < m_gridMap.getGridWidth(); x++) {
			for (int y = 0; y < m_gridMap.getGridHeight(); y++) {
				if (!m_gridMap.isObstructed(x, y)) {
					Point gridPoint = m_gridMap.getCoordinateOfGridPoint(x, y);
					renderPoint(gridPoint, _g2);
				}
			}
		}

	}

}
