/**
 * 
 */
package Monopoly;

/**
 * @author Zach Royer
 */
public class GameSpace implements SpaceEffect {

	private SpaceNames name;
	private int timeLandedOn;
	private double percentLandedOn;
	private static int maxTurns;
	
	public GameSpace(SpaceNames name, int maxTurns) {
		this.name = name;
		this.maxTurns = maxTurns;
	}
	@Override
	public spaceEffects effect() {
		// TODO Auto-generated method stub
		return null;
	}

	public SpaceNames getName() {
		return this.name;
	}
	
	public int getTimesLandedOn() {
		return this.timeLandedOn;
	}
	
	public double getPercentLandedOn() {
		return this.percentLandedOn;
	}
}
