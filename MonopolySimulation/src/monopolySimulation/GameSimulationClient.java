package monopolySimulation;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import Monopoly.GameSpaces.GameSpace;
import edu.princeton.cs.algs4.RedBlackBST;

/**
 * Runs simulations of Monopoly and writes the results to a .csv file.
 * 
 * @author Cristian Morales
 * @author Josh Martin
 *
 */
public class GameSimulationClient {
	
    private static RedBlackBST<Integer, GameSpace[]> strategyAResults = new RedBlackBST<>();
    private static RedBlackBST<Integer, GameSpace[]> strategyBResults = new RedBlackBST<>();
    
	public static void main(String[] args) {
<<<<<<< HEAD
		for (int n = 0; n < 1_000_000; n = game.roll()){

		}
		simulateGame(game.roll(), Strategy.values());
=======
		
>>>>>>> 2455a8fca714db6a52dfec6decb4d3af9a3f4e61
    }

    private void simulateGame(int maxTurns, Strategy startegy) {

    }
    
    /**
     * Writes simulation results to a .csv file.
     * 
     * @param n The number of turns taken in each simulation.
     * @param strategy The strategy whose results to be printed.
     */
    private static void writeResults(int n, Strategy strategy) {
    	
    	String file = "src/monopolySimulation/Results/SimulationResults (n = " + n + ").csv";
    	RedBlackBST<Integer, GameSpace[]> strategyResults = null;
    	
    	try (PrintWriter writer = new PrintWriter(file)) {
			
			switch (strategy) {
			case A:
				strategyResults = strategyAResults;
				break;
			case B:
				strategyResults = strategyBResults;
				break;
			}
    		
    		writer.println("Strategy " + strategy + ",n = " + n);
    		writer.println();
    		writer.println();
    		writeTrials(writer, strategyResults);
    		
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }

	/**
	 * Writes the results of all trials in {@code results} with {@code writer}.
	 * 
	 * @param writer The object that will write {@code results} to file.
	 * @param results The simulation results that should be printed.
	 */
	private static void writeTrials(PrintWriter writer, RedBlackBST<Integer, GameSpace[]> results) {
		
		String[] allTrialResults = new String[results.size()];
		
		for (Integer trial : results.keys()) {
			
			StringBuilder currentTrialResults = new StringBuilder("Trial " + trial + "\n\n");
			GameSpace[] currentGameBoard = results.get(trial);
			
			currentTrialResults.append("Space Name,Times Landed On,Percent Landed On\n");
			
			for (GameSpace space : currentGameBoard) {
				currentTrialResults.append(space.getName() + "," + space.getTimesLandedOn() + "," + space.getPercentLandedOn() + "\n");
			}
			
			allTrialResults[trial - 1] = currentTrialResults.toString();
		}
		
		for (String trialResult : allTrialResults) {
			writer.println(trialResult);
			writer.println();
		}
		
	}
	
	/**
	 * Test method for writeResults and writeTrials methods.
	 */
	private static void testWriteResults() {
		strategyAResults = new RedBlackBST<>();
		GameSpace[] results = new GameSpace[3];
		
		SpaceNames[] allSpaceNames = SpaceNames.values();
		
		for (int i = 0; i < results.length; i++) {
			results[i] = new GameSpace(allSpaceNames[i], 1000);
		}

		strategyAResults.put(1, results);
		strategyAResults.put(2, results);
		
		writeResults(1000, Strategy.A);
	}
}