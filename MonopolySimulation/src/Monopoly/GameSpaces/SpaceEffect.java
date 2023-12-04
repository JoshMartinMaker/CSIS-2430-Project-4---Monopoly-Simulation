package Monopoly.GameSpaces;

/**
 * Allows the object to have an effect when landed on in a game of Monopoly.
 * 
 * @author Zach Royer
 *
 */
public interface SpaceEffect {
	
	/**
	 * Returns the effect this space has when landed on.
	 * 
	 * @return The effect this space has when landed on.
	 */
	public SpaceEffects effect();
}