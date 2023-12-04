package Monopoly.GameSpaces;

import monopolySimulation.SpaceNames;

/**
 * Represents the Go To Jail space in Monopoly.
 * 
 * @author Zach Royer
 * @author Josh Martin
 *
 */
public class GoToJail extends GameSpace {

	/**
	 * Cosntructor for the GoToJail class.
	 * 
	 * @param name The name of this space.
	 * @param maxTurns The number of turns to base {@code percentLandedOn} on.
	 */
	public GoToJail(SpaceNames name, int maxTurns) {
		super(name, maxTurns);
	}
	
	/**
	 * Returns the effect of landing on this space: {@link SpaceEffects#JAIL}.
	 * 
	 * @return The effect of landing on this space: {@link SpaceEffects#JAIL}.
	 */
	@Override
	public SpaceEffects effect() {
		return SpaceEffects.JAIL;
	}
}
