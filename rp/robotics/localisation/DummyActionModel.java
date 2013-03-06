package rp.robotics.localisation;

import java.util.Random;

import rp.robotics.mapping.Heading;

/**
 * An example of how you could start writing an action model given the available
 * classes.
 * 
 * @author nah
 * 
 */
public class DummyActionModel implements ActionModel {

	public GridPoseDistribution updateAfterMove(GridPoseDistribution _from,
			Heading _heading) {

		// Create a new distribution from the input distribution.
		// NB It might prove more efficient to update the distribution directly,
		// rather than create a new one each time.
		GridPoseDistribution afterAction = new GridPoseDistribution(_from);

		Random rand = new Random();

		// iterate through points updating as appropriate
		// for (int x = 0; x < afterAction.getGridWidth(); x++) {
		// for (int y = 0; y < afterAction.getGridHeight(); y++) {
		// // make sure to respect obstructed grid points
		// if (!afterAction.isObstructed(x, y)) {
		// // updating with random value
		// afterAction.setProbability(x, y, rand.nextFloat());
		// }
		// }
		// }

		// 5 points set to random values
		for (int i = 0; i < 5; i++) {
			int x = rand.nextInt(afterAction.getGridWidth());
			int y = rand.nextInt(afterAction.getGridHeight());
			while (afterAction.isObstructed(x, y)) {
				// keep looping until unobstructed point is found
				x = rand.nextInt(afterAction.getGridWidth());
				y = rand.nextInt(afterAction.getGridHeight());
			}
			afterAction.setProbability(x, y, rand.nextFloat());
		}

		afterAction.normalise();

		return afterAction;

	}
}
