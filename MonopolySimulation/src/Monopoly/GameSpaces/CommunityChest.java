package Monopoly.GameSpaces;

import java.util.Collections;
import java.util.Stack;

import monopolySimulation.SpaceNames;

/*
 * Represents the Community Chest Game Spaces on the Monopoly board
 * 
 */
public class CommunityChest extends GameSpace {

	private static Stack<CommunityChestCards> cardDeck;
	private static boolean isGetOutOfJailInDiscard = false;
	
	public CommunityChest(SpaceNames name, int maxTurns) {
		super(name, maxTurns);
		// TODO Auto-generated constructor stub
	}

	private void shuffleDeck() {
		Collections.shuffle(cardDeck);
	}
	
	@Override
	public SpaceEffects effect() {
		return null;
	}
	public boolean discardGetOutOfJail() {
		if isGetOutOfJailInDiscard = !isGetOutOfJailInDiscard;
		
	}
}
