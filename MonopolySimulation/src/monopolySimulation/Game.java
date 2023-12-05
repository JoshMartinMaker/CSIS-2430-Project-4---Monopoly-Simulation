package monopolySimulation;

import Monopoly.GameSpaces.*;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Represents the game Monopoly and the actions the player can take.
 * 
 * @author Josh Martin
 *
 */
public class Game {

	private int playerLocation;
	private GameSpace[] gameBoard;
	private int doublesRolled;
	private int turnsInJail;
	private int getOutOfJailCards;
	private static final RedBlackBST<SpaceNames, Integer> SPACES_BY_NAME = initializeSpacesByName();
	private boolean inJail;
	private final Strategy strategy;

	/**
	 * Constructor for {@code Game} class.
	 * 
	 * @param maxTurns       The maximum number of turns that will be taken in this
	 *                       game.
	 * @param playerStrategy The strategy used by the player while in jail.
	 */
	public Game(int maxTurns, Strategy playerStrategy) {

		playerLocation = 0;
		doublesRolled = 0;
		turnsInJail = 0;
		getOutOfJailCards = 0;
		inJail = false;
		strategy = playerStrategy;

		// Initialize gameBoard
		SpaceNames[] spaceNamesValues = SpaceNames.values();
		gameBoard = new GameSpace[spaceNamesValues.length];

		for (int i = 0; i < spaceNamesValues.length; i++) {

			if (spaceNamesValues[i].toString().contains("COMMUNITY_CHEST")) {
				gameBoard[i] = new CommunityChest(spaceNamesValues[i], maxTurns);
			}
			else if (spaceNamesValues[i].toString().contains("CHANCE")) {
				gameBoard[i] = new Chance(spaceNamesValues[i], maxTurns);
			}
			else if (spaceNamesValues[i].toString().contains("GO_TO_JAIL")) {
				gameBoard[i] = new GoToJail(spaceNamesValues[i], maxTurns);
			}
			else {
				gameBoard[i] = new GameSpace(spaceNamesValues[i], maxTurns);
			}
		}
	}

	/**
	 * Rolls two 6-sided dice to determine how many spaces the player should move,
	 * then performs the action dictated by the space the player lands on. While the
	 * player is in jail, the player will follow the {@link Strategy} given to this
	 * object.
	 * <p>
	 * If the player rolls three doubles (two 1's, two 2's, etc.) in a row, the
	 * player is sent to {@code SpaceNames.JAIL} instead of moving. If the player
	 * rolls doubles while in jail, they are no longer in jail and they move as
	 * normal. If the player spends three turns in jail without rolling doubles,
	 * they must pay the $50 fine to leave jail, then they will move as normal.
	 */
	public void roll() {

		int die1 = 0;
		int die2 = 0;

		if (inJail) {

			turnsInJail++;

			// Execute in jail strategy
			switch (strategy) {
			case A:
				strategyA();
				break;
			case B:
				strategyB();
				break;
			}
		}

		// Roll Dice
		die1 = StdRandom.uniformInt(6) + 1; // An integer in the interval [1, 6]
		die2 = StdRandom.uniformInt(6) + 1;

		// If the player rolled doubles
		if (die1 == die2) {
			leaveJail();
			doublesRolled++;
		}
		else {
			doublesRolled = 0;
		}

		// If the player rolled three doubles in a row
		if (doublesRolled == 3) {
			goToJail();
			return;
		}

		// If the player has spent the maximum number of turns in jail
		if (turnsInJail == 3) {
			leaveJail();
		}

		if (!inJail) {
			move(die1 + die2);
		}
	}

	/**
	 * Executes strategy A for rolling while the player is in jail. If the player
	 * has a "Get Out Of Jail Free" card, they will use it. Otherwise, the player
	 * will pay the $50 fine to get out of jail.
	 * 
	 * @param diceTotal
	 */
	private void strategyA() {

		if (getOutOfJailCards >= 1) {
			useGetOutOfJailCard();
		}
		else {
			leaveJail();
		}
	}

	/**
	 * Executes strategy B for rolling while the player is in jail. If the player
	 * has a "Get Out Of Jail Free" card, they will use it. Otherwise, they will
	 * stay in jail until they roll doubles or they are forced to pay the $50 fine
	 * to get out of jail.
	 */
	private void strategyB() {

		if (getOutOfJailCards >= 1) {
			useGetOutOfJailCard();
		}
	}

	/**
	 * Moves the player by the specified number of spaces. If {@code spaces} is
	 * positive, moves the player forward in the board. If {@code spaces} is
	 * negative, moves the player backwards in the board. This counts as "landing
	 * on" the space and increments the times the space has been landed on.
	 * 
	 * @param spaces The number of spaces to move the player.
	 */
	public void move(int spaces) {
		
		playerLocation = (playerLocation + spaces) % gameBoard.length;
		gameBoard[playerLocation].incrementTimesLandedOn();
		
		if (spaces <= 0) {
			return;
		}
		
		SpaceEffects spaceEffect = gameBoard[playerLocation].effect();
		
		if (spaceEffect == null) {
			return;
		}
		
		switch (spaceEffect) {
		case NEXT_RAILROAD:
			
			int readingRailroad = SPACES_BY_NAME.get(SpaceNames.READING_RAILROAD);
			int pennsylvaniaRailroad = SPACES_BY_NAME.get(SpaceNames.PENNSYLVANIA_RAILROAD);
			int boRailroad = SPACES_BY_NAME.get(SpaceNames.BO_RAILROAD);
			int shortLine = SPACES_BY_NAME.get(SpaceNames.SHORT_LINE);
			
			if (playerLocation < readingRailroad || playerLocation > shortLine) {
				move(SpaceNames.READING_RAILROAD);
			}
			else if (playerLocation < pennsylvaniaRailroad) {
				move(SpaceNames.PENNSYLVANIA_RAILROAD);
			}
			else if (playerLocation < boRailroad) {
				move(SpaceNames.BO_RAILROAD);
			}
			else {
				move(SpaceNames.SHORT_LINE);
			}
			
			break;
		case NEXT_UTILITY:
			
			int electricCompany = SPACES_BY_NAME.get(SpaceNames.ELECTRIC_COMPANY);
			int waterWorks = SPACES_BY_NAME.get(SpaceNames.WATER_WORKS);
			
			if (playerLocation < electricCompany || playerLocation > waterWorks) {
				move(SpaceNames.ELECTRIC_COMPANY);
			}
			else {
				move(SpaceNames.WATER_WORKS);
			}
			
			break;
		case GET_OUT_OF_JAIL_FREE:
			
			getOutOfJailCards++;
			break;
		case BACK_THREE_SPACES:
			
			move(-3);
			break;
		default:
			
			SpaceNames locationName = SpaceNames.valueOf(spaceEffect.toString());
			move(locationName);
		}
	}

	/**
	 * Moves the player to the specified location on the game board. If the player
	 * is moved to {@code JAIL}, the player is placed in jail (they are not
	 * visiting).
	 * 
	 * @param location The location the player should be moved to.
	 */
	public void move(SpaceNames location) {
		playerLocation = SPACES_BY_NAME.get(location);
		gameBoard[playerLocation].incrementTimesLandedOn();
	}

	/**
	 * Moves the player to {@code SpaceNames.JAIL} and sets {@code inJail} to
	 * {@code true}.
	 */
	private void goToJail() {
		move(SpaceNames.JAIL);
		inJail = true;
		doublesRolled = 0;
		gameBoard[SPACES_BY_NAME.get(SpaceNames.JAIL)].incrementTimesLandedOn();
	}

	/**
	 * Sets {@code inJail} to false and resets {@code turnsInJail}.
	 */
	private void leaveJail() {
		inJail = false;
		turnsInJail = 0;
	}

	/**
	 * Uses a "Get Out Of Jail Free" card. On use, the player's
	 * {@code getOutOfJailCards} is decremented and {@code inJail} is set to
	 * {@code false}. The "Get Out Of Jail Free" card used is discarded after use.
	 * If the player owns both cards, the card from the {@link Chance} deck is used
	 * first.
	 */
	private void useGetOutOfJailCard() {
		getOutOfJailCards--;
		leaveJail();

		if (!Chance.discardGetOutOfJailCard()) {
			CommunityChest.discardGetOutOfJailCard();
		}
	}

	/**
	 * Initializes {@code SPACES_BY_NAME} with each space in {@link SpaceNames}
	 * keyed to integers in the order they are declared in the enum.
	 * 
	 * @return A symbol table with each {@link SpaceNames} value keyed to integers
	 *         in the order they are declared in the enum.
	 */
	private static RedBlackBST<SpaceNames, Integer> initializeSpacesByName() {

		RedBlackBST<SpaceNames, Integer> result = new RedBlackBST<>();
		SpaceNames[] spaceNamesValues = SpaceNames.values();

		for (int i = 0; i < spaceNamesValues.length; i++) {
			result.put(spaceNamesValues[i], i);
		}

		return result;
	}
	
	/**
	 * Returns the game board for this simulation.
	 * 
	 * @return The game board for this simulation.
	 */
	public GameSpace[] getGameBoard() {
		return gameBoard;
	}
}