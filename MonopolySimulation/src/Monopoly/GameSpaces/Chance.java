package Monopoly.GameSpaces;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;
import monopolySimulation.SpaceNames;

/**
 * Represents a Chance space in Monopoly.
 * 
 * @author Zach Royer
 * @author Josh Martin
 *
 */
public class Chance extends GameSpace {

	private static Stack<ChanceCards> cardDeck;
	private static boolean isGetOutOfJailOwned = false;

	/**
	 * Cosntructor for the Chance class.
	 * 
	 * @param name     The name of this space.
	 * @param maxTurns The number of turns to base {@code percentLandedOn} on.
	 */
	public Chance(SpaceNames name, int maxTurns) {
		super(name, maxTurns);

		cardDeck = new Stack<>();
		isGetOutOfJailOwned = false;
		shuffleDeck();
	}

	/**
	 * Returns the effect of landing on this space: a random effect from a deck of
	 * {@link ChanceCards} and {@code null}s.
	 * 
	 * @return The effect of landing on this space: a random effect from a deck of
	 *         {@link ChanceCards} and {@code null}s.
	 */
	@Override
	public SpaceEffects effect() {

		if (cardDeck.isEmpty()) {
			shuffleDeck();
		}

		ChanceCards drawnEffect = cardDeck.pop();

		if (drawnEffect == ChanceCards.GET_OUT_OF_JAIL_FREE) {
			isGetOutOfJailOwned = true;
		}

		return SpaceEffects.valueOf(drawnEffect.toString());
	}

	/**
	 * Shuffles the deck of Chance cards.
	 */
	private static void shuffleDeck() {

		final int CARD_DECK_SIZE = 16;

		ChanceCards[] allCards = new ChanceCards[CARD_DECK_SIZE];
		ChanceCards[] implementedCards = ChanceCards.values();
		int addedCards = 0;

		// Add implemented cards
		for (int i = 0; i < implementedCards.length; i++) {
			
			ChanceCards currentCard = implementedCards[i];
			
			if ((currentCard == ChanceCards.GET_OUT_OF_JAIL_FREE) && (isGetOutOfJailOwned)) {
				continue;
			}
			
			allCards[i] = currentCard;
			addedCards++;
		}
		
		// Add non-implemented cards
		for (int i = addedCards; i < CARD_DECK_SIZE; i++) {
			allCards[i] = null;
		}

		StdRandom.shuffle(allCards);

		for (ChanceCards currentCard : allCards) {
			cardDeck.push(currentCard);
		}
	}

	/**
	 * Returns a Get Out of Jail Free (GOOJF) card to the discard pile. If a player
	 * already owns the GOOJF card from the Chance deck, it cannot be added
	 * to the discard pile unless they use it.
	 * 
	 * @return True if the card was successfully added to the discard pile.
	 */
	public static boolean discardGetOutOfJailCard() {

		if (isGetOutOfJailOwned) {

			isGetOutOfJailOwned = false;
			
			return true;
		}
		else {
			return false;
		}
	}
}
