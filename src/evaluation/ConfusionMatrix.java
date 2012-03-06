package evaluation;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ontology.Ontology;

import alignmentAlgorithm.NeedlemanWunsch;

import sequence.ActionSequence;

public class ConfusionMatrix {

	private ArrayList<ActionSequence> seqList;
	private int compare;
	private Ontology ontology;
	private int number;
	private double[][] confusionMatrix;
	
	public ConfusionMatrix(ArrayList<ActionSequence> seqList, int compare, Ontology ontology){
		this.seqList = seqList;
		this.compare = compare;
		this.ontology = ontology;
		number = seqList.size();
	}
	
	private void calculateConfusionMatrix(){
		confusionMatrix = new double[number][number];
		for (int i = 0; i < number; i++) {
			for (int j = 0; j < number; j++) {
				if (i <= j) {
					//System.out.println("i = " + i + "; j = " + j + ";");
					//long start = System.currentTimeMillis();
					NeedlemanWunsch ndl = new NeedlemanWunsch(seqList.get(i).getSequence(), seqList.get(j).getSequence(), compare, ontology);
					confusionMatrix[i][j] = ndl.getScore();
					//long ende = System.currentTimeMillis();
					//System.out.println("Berechnungszeit: " + ((ende - start)/1000.0) + " Sek.");
				} else {
					confusionMatrix[i][j] = confusionMatrix[j][i];
				}
			}
		}
	}
	
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
