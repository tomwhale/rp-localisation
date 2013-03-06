package rp.robotics.localisation;

import rp.robotics.mapping.Heading;

/**
 * Example structure for an action model that should move the probabilities 1
 * cell in the requested direction. In the case where the move would take the
 * robot into an obstacle or off the map, this model assumes the robot stayed in
 * one place. This is the same as the model presented in Robot Programming
 * Lecture 14.
 * 
 * Note that this class doesn't actually do this, instead it shows you a
 * <b>possible</b> structure for your action model.
 * 
 * @author nah
 * 
 */
public class PerfectActionModel implements ActionModel {

	@Override
	public GridPoseDistribution updateAfterMove(GridPoseDistribution _from,
			Heading _heading) {

		// Create the new distribution that will result from applying the action
		// model
		GridPoseDistribution to = new GridPoseDistribution(_from);

		// Move the probability in the correct direction for the action
		if (_heading == Heading.PLUS_X) {
			movePlusX(_from, to);
		} else if (_heading == Heading.PLUS_Y) {

		} else if (_heading == Heading.MINUS_X) {
		
		} else if (_heading == Heading.MINUS_Y) {
		
		}

		return to;
	}


	/**
	 * Move probabilities from _from one cell in the plus x direction into _to
	 * 
	 * @param _from
	 * @param _to
	 */
	private void movePlusX(GridPoseDistribution _from, GridPoseDistribution _to) {

		// iterate through points updating as appropriate
		for (int y = 0; y < _to.getGridHeight(); y++) {
			for (int x = 0; x < _to.getGridWidth(); x++) {
				// make sure to respect obstructed grid points
				if (!_to.isObstructed(x, y)) {

					// calculate the new probability for x,y based on the _from
					// grid and the move made

					// this just copies the new value for the given point
					// straight from the _from grid...
					float newProb = _from.getProbability(x, y);

					// ... and uses the result to update the SAME point in the
					// _to distribution
					_to.setProbability(x, y, newProb);

					// as you can see this, doesn't actually move any
					// probabilities. That is for you to do.
				}
			}
		}
	}

}
