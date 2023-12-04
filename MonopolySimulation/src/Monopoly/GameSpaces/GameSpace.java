package Monopoly.GameSpaces;

import monopolySimulation.SpaceNames;

/**
 * Represents a space on a Monopoly board.
 * 
 * @author Zach Royer
 * @author Josh Martin
 */
public class GameSpace implements SpaceEffect {

	private final SpaceNames name;
	private int timesLandedOn = 0;
	private double percentLandedOn = 0;
	private final int maxTurns;

	/**
	 * Cosntructor for GameSpace class.
	 * 
	 * @param name The name of this space.
	 * @param maxTurns The number of turns to base {@code percentLandedOn} on.
	 */
	public GameSpace(SpaceNames name, int maxTurns) {
		this.name = name;
		this.maxTurns = maxTurns;
	}

	/**
	 * Returns the effect when landing on this space.
	 * 
	 * @return The effect when landing on this space.
	 */
	@Override
	public SpaceEffects effect() {
		return null;
	}

	/**
	 * Returns the name of this space.
	 * 
	 * @return The name of this space.
	 */
	public SpaceNames getName() {
		return this.name;
	}

	/**
	 * Returns the number of times this space has been landed on.
	 * 
	 * @return The number of times this space has been landed on.
	 */
	public int getTimesLandedOn() {
		return this.timesLandedOn;
	}

	/**
	 * Returns the percentage of times this space has been landed on over an entire game.
	 * 
	 * @return The percentage of times this space has been landed on over an entire game.
	 */
	public double getPercentLandedOn() {
		
		this.percentLandedOn = ((double) this.timesLandedOn) / this.maxTurns;
		
		return this.percentLandedOn;
	}
	
	/**
	 * Increments the number of times this space has been landed on.
	 */
	public void incrementTimesLandedOn() {
		this.timesLandedOn++;
	}
}
