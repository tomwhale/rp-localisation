package rp.robotics.localisation;

import rp.robotics.mapping.Heading;

/**
 * An example of an interface an action model might provide.
 * 
 * Note: you do not have to use this if you don't want to.
 * 
 * @author nah
 * 
 */
public interface ActionModel {

	public GridPoseDistribution updateAfterMove(GridPoseDistribution _dist,
			Heading _heading);

}
