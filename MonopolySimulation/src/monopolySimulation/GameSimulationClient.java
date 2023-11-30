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
	
    private RedBlackBST<Integer, GameSpace[]> strategyAResults;
    private RedBlackBST<Integer, GameSpace[]> strategyBResults;
    
	public static void main(String[] args) {

    }

    private void simulateGame(int maxTurns, String startegy) {

    }
    
    /**
     * Writes simulation results to a .csv file.
     */
    private void writeResults() {
    	
    	String file = "src/monopolySimulation/Resources/SimulationResults.csv";
    	
    	try (PrintWriter writer = new PrintWriter(file)) {
			
    		writeTrials(writer);
    		
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }

	/**
	 * 
	 * 
	 * @param writer
	 */
	private void writeTrials(PrintWriter writer) {
		
		StringBuilder[] trialResults = new StringBuilder[strategyAResults.size()];
		
		for (Integer trial : strategyAResults.keys()) {
			
			StringBuilder currentTrialResults = new StringBuilder(trial + ";");
			GameSpace[] currentBoard = strategyAResults.get(trial);
			
			currentTrialResults.append("Space Name,Times Landed On,Percent Landed On;");
			
			for (GameSpace space : currentBoard) {
				currentTrialResults.append(space.getName() + "," + space.getTimesLandedOn() + "," + space.getPercentLandedOn() + ";");
			}
			
			trialResults[trial] = currentTrialResults;
		}
	}
}