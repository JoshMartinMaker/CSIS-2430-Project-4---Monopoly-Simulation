/**
 * 
 */
package Monopoly.GameSpaces;

/**
 * @author Zach Royer
 */
public class GameSpace implements SpaceEffect {

	private spaceNames name;
	private int timeLandedOn;
	private double percentLandedOn;
	private static int maxTurns;

	public GameSpace(spaceNames name, int maxTurns) {
		this.name = name;
		this.maxTurns = maxTurns;
	}

	@Override
	public spaceEffects effect() {
		return null;
	}

	public spaceNames getName() {
		return this.name;
	}

	public int getTimesLandedOn() {
		return this.timeLandedOn;
	}

	public double getPercentLandedOn() {
		return this.percentLandedOn;
	}
}
