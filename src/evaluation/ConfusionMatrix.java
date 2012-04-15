package evaluation;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ontology.Ontology;

import alignmentAlgorithm.NeedlemanWunsch;

import sequence.ActionSequence;

/**
 * 
 * @author Johannes Ziegltrum
 *
 */
public class ConfusionMatrix {

	private ArrayList<ActionSequence> seqList;
	private int compare;
	private Ontology ontology;
	private int number;
	private double[][] confusionMatrix;
	
	/**
	 * 
	 * @param seqList a list of ActionSequences
	 */
	public ConfusionMatrix(ArrayList<ActionSequence> seqList) {
		calculate(seqList, 1, null);
	}
	
	/**
	 * 
	 * @param seqList a list of ActionSequences
	 * @param ontology the used Ontology for the WUP-similarity-calculation
	 */
	public ConfusionMatrix(ArrayList<ActionSequence> seqList, Ontology ontology) {
		calculate(seqList, 2, ontology);
	}
	
	private void calculate(ArrayList<ActionSequence> seqList, int compare, Ontology ontology){
		this.seqList = seqList;
		this.compare = compare;
		this.ontology = ontology;
		number = seqList.size();
	}
	
	//pairwise calculation of the similarity-score of all sequences in the list
	private void calculateConfusionMatrix(){
		confusionMatrix = new double[number][number];
		for (int i = 0; i < number; i++) {
			for (int j = 0; j < number; j++) {
				if (i <= j) {
					NeedlemanWunsch ndl;
					if (compare == 1) {
						ndl = new NeedlemanWunsch(seqList.get(i), seqList.get(j));
					} else {
						ndl = new NeedlemanWunsch(seqList.get(i), seqList.get(j), ontology);
					}
					confusionMatrix[i][j] = ndl.getScore();
				} else {
					confusionMatrix[i][j] = confusionMatrix[j][i];
				}
			}
		}
	}
	
	/**
	 * prints the confusion-matrix
	 */
	public void printConfusionMatrix(){
		long start = System.currentTimeMillis();
		this.calculateConfusionMatrix();
		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 12; j++){
				System.out.print(" ");
			}
			for (int k = 0; k < number; k++){
				String c;
				String identifier = seqList.get(k).getIdentifier();
				int length = identifier.length();
				if (i < 10 - length){
					c = " ";
				} else {
					c = String.valueOf(identifier.charAt(i - (10 - length)));
				}
				for (int l = 0; l < 5; l++){
					System.out.print(" ");
				}
				System.out.print(c);
			}
			System.out.println();
		}
		System.out.println();
		for (int i = 0; i < number; i++){
			String identifier = seqList.get(i).getIdentifier();
			int length = identifier.length();
			for (int j = 0; j < 10 - length; j++){
				System.out.print(" ");
			}
			System.out.print(identifier + "  ");
			for (int k = 0; k < number; k++){
				double score = confusionMatrix[i][k];
				DecimalFormat df = new DecimalFormat("0.00");
				String s = df. format(score);
				for (int l = 0; l < 6 - s.length(); l++) {
					System.out.print(" ");
				}
				System.out.print(s);
			}
			System.out.println();
		}
		long ende = System.currentTimeMillis();
		System.out.println();
		System.out.println("Berechnungszeit: " + ((ende - start)/1000.0) + " Sek.");
		System.out.println();
	}
	
}
