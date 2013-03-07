package rp.robotics.visualisation;

import javax.swing.JFrame;

import rp.robotics.localisation.GridPoseDistribution;
import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.Heading;
import rp.robotics.mapping.LocalisationUtils;

public class MapViewer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		JFrame frame = new JFrame("Map Viewer");

		GridMap gridMap = LocalisationUtils.createTrainingMap();

		GridPoseDistribution distribution = new GridPoseDistribution(gridMap);

		int x = 0;
		int y = 6;

		distribution.setProbability(x, y, 0.5f);
		distribution.normalise();

		System.out.println("distance down: "
				+ gridMap.rangeToObstacleFromGridPoint(x, y, Heading.PLUS_Y));

		System.out.println("distance right: "
				+ gridMap.rangeToObstacleFromGridPoint(x, y, Heading.PLUS_X));

		System.out.println("distance up: "
				+ gridMap.rangeToObstacleFromGridPoint(x, y, Heading.MINUS_Y));

		System.out.println("distance left: "
				+ gridMap.rangeToObstacleFromGridPoint(x, y, Heading.MINUS_X));

		System.out.println("isValidTransition: "
				+ gridMap.isValidTransition(x, y, x, y - 1));
		System.out.println("isValidTransition: "
				+ gridMap.isValidTransition(x, y, x, y + 1));

		// view the map with 2 pixels as 1 cm
		GridPoseDistributionVisualisation mapVis = new GridPoseDistributionVisualisation(
				distribution, 2);

		frame.add(mapVis);
		frame.pack();
		frame.setSize(1050, 600);

		frame.setVisible(true);

	}
}
