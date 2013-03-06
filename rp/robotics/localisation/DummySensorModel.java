package rp.robotics.localisation;


/**
 * An example of how you could start writing an action model given the available
 * classes.
 * 
 * @author nah
 * 
 */
public class DummySensorModel {

	public void updateDistributionAfterSensing(GridPoseDistribution _dist/*
																		 * ,
																		 * SomeSensingResult
																		 * _data
																		 */) {

		// Commented out the random code to stop people using it without looking

		// Random rand = new Random();
		//
		// float prob;
		// // iterate through points updating as appropriate
		// for (int x = 0; x < _dist.getGridWidth(); x++) {
		// for (int y = 0; y < _dist.getGridHeight(); y++) {
		// // make sure to respect obstructed grid points
		// if (!_dist.isObstructed(x, y)) {
		// prob = _dist.getProbability(x, y);
		// // randomly set some decent values to 0
		// if (prob > 0.1) {
		// if (rand.nextBoolean()) {
		// _dist.setProbability(x, y, 0);
		// }
		// }
		// }
		// }
		// }
		//
		// _dist.normalise();
	}
}
