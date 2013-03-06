package rp.robotics.localisation;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import lejos.util.Delay;
import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.Heading;
import rp.robotics.mapping.LocalisationUtils;
import rp.robotics.visualisation.GridPoseDistributionVisualisation;

public class MarkovLocalisationSkeleton {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		JFrame frame = new JFrame("Map Viewer");
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent _arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent _arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent _arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent _arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent _arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent _arg0) {

				System.exit(0);

			}

			@Override
			public void windowActivated(WindowEvent _arg0) {
				// TODO Auto-generated method stub

			}
		});

		GridMap gridMap = LocalisationUtils.createTrainingMap();

		GridPoseDistribution distribution = new GridPoseDistribution(gridMap);

		// view the map with 2 pixels as 1 cm
		GridPoseDistributionVisualisation mapVis = new GridPoseDistributionVisualisation(
				distribution, 2);

		frame.add(mapVis);
		frame.pack();
		frame.setSize(1050, 600);
		frame.setVisible(true);

		ActionModel actionModel = new DummyActionModel();
		// ActionModel actionModel = new ActualPerfectActionModel();
		DummySensorModel sensorModel = new DummySensorModel();

		while (true) {
			// Do some action
			// ...
			// I'm faking an action by waiting for some time
			Delay.msDelay(1000);

			// Once completed, apply action model as appropriate
			distribution = actionModel.updateAfterMove(distribution,
					Heading.PLUS_X);

			// Update visualisation. Only necessary if you want to visualise a
			// new object
			mapVis.setDistribution(distribution);

			System.out.println("map sum: " + distribution.sumProbabilities());

			// Do some sensing
			// ...
			// I'm faking sensing by waiting for some time
			Delay.msDelay(1000);

			// Once completed apply sensor model as appropriate
			sensorModel.updateDistributionAfterSensing(distribution);

			// Note, as the sensor model changes the distribution directly, the
			// visualisation will update automatically

		}

	}
}
