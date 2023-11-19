package monopolySimulation;

import edu.princeton.cs.algs4.RedBlackBST;

/**
 * Represents the game Monopoly and the actions the player can take.
 * 
 * @author Josh Martin
 *
 */
public abstract class Game {
	
	private int playerLocation;
	private GameSpace[] gameBoard;
	private int doublesRolled;
	private int getOutOfJailCards;
	private static final RedBlackBST<SpaceNames, Integer> SPACES_BY_NAME;
	private boolean inJail;
	
	/**
	 * Constructor for {@code Game} class.
	 * 
	 * @param maxTurns The maximum number of turns that will be taken in this game.
	 */
	public Game(int maxTurns) {
		
		playerLocation = 0;
		doublesRolled = 0;
		getOutOfJailCards = 0;
		SPACES_BY_NAME = new RedBlackBST<>();
		inJail = false;
		
		// Initialize gmaeBoard
		SpaceNames[] spaceNamesValues = SpaceNames.values();
		
		for (int i = 0; i < spaceNamesValues.length; i++) {
			if (spaceNamesValues[i].toString().contains("COMMUNITY_CHEST")) {
				gameBoard[i] = new CommunityChest();
			}
			else if (spaceNamesValues[i].toString().contains("CHANCE")) {
				gameBoard[i] = new Chance();
			}
			else if (spaceNamesValues[i].toString().contains("GO_TO_JAIL")) {
				gameBoard[i] = new GoToJail();
			}
			else {
				gameBoard[i] = new GameSpace(spaceNamesValues[i], maxTurns);
			}
			
			SPACES_BY_NAME.put(spaceNamesValues[i], i);
		}
	}
	
	/**
	 * Rolls two 6-sided dice to determine how many spaces the player should move,
	 * then performs the action dictated by the space the player lands on. If the
	 * player rolls three doubles (two 1's, two 2's, etc.) and they are not in jail, the player is sent to
	 * {@link GameSpace.JAIL} instead of moving.
	 */
	abstract void roll();
	
	/**
	 * Moves the player by the specified number of spaces. If {@code spaces} is
	 * positive, moves the player forward in the board. If {@code spaces} is
	 * negative, moves the player backwards in the board.
	 * 
	 * @param spaces The number of spaces to move the player.
	 */
	private void move(int spaces) {
		playerLocation = (playerLocation + spaces) % gameBoard.length;
	}
	
	/**
	 * Moves the player to the specified location on the game board. If the player
	 * is moved to {@code JAIL}, the player is placed in jail (they are not
	 * visiting).
	 * 
	 * @param location The location the player should be moved to.
	 */
	private void move(SpaceNames location) {
		playerLocation = SPACES_BY_NAME.get(location);
	}
}