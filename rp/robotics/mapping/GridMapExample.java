package rp.robotics.mapping;

import javax.swing.JFrame;

import rp.robotics.visualisation.GridMapVisualisation;

public class GridMapExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		JFrame frame = new JFrame("Map Viewer");

	

		GridMap gridMap = LocalisationUtils.createTrainingMap();

		// view the map with 2 pixels as 1 cm
		GridMapVisualisation mapVis = new GridMapVisualisation(gridMap, 2);

		frame.add(mapVis);
		frame.pack();
		frame.setSize(1050, 600);
		frame.setVisible(true);

		int x = 0;
		int y = 1;

		System.out.println(x + "," + y + " PLUS_X "
				+ gridMap.rangeToObstacleFromGridPoint(x, y, Heading.PLUS_X));

		System.out.println(x + "," + y + " MINUS_Y "
				+ gridMap.rangeToObstacleFromGridPoint(x, y, Heading.MINUS_Y));

		
	}
}
