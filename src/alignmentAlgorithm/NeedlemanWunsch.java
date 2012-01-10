package alignmentAlgorithm;

import java.util.ArrayList;

import ontology.Ontology;

import sequenceElement.ActionElement;

public class NeedlemanWunsch {

	private double match = 2;
	private double mismatch = -2;
	private double gap = -1;
	
	private ArrayList<ActionElement> seq1;
	private ArrayList<ActionElement> seq2;
	
	private int m;
	private int n;
	
	private double[][] matrix;
	private String[][] traceback;
	private String[][] alignments;
	
	private int pointer = 0;
	
	private Ontology ontology;
	
	public NeedlemanWunsch(ArrayList<ActionElement> seq1, ArrayList<ActionElement> seq2, int function, Ontology ontology){
		this.ontology = ontology;
		this.seq1 = seq1;
		this.seq2 = seq2;
		m = seq1.size();
		n = seq2.size();
		matrix = new double[m+1][n+1];
		traceback = new String[m+1][n+1];
		alignments = new String[2][m+n];
		//initialisation of the matrix + traceback
		matrix[0][0] = 0;
		traceback[0][0] = "done";
		for (int j = 1; j <= n; j++){
			matrix[0][j] = j*gap;
			traceback[0][j] = "left";
		}
		for (int i = 1; i <= m; i++){
			matrix[i][0] = i*gap;
			traceback[i][0] = "up";
		}
		//recursive calculation of the remaining alignment-scores + traceback
		for (int i = 1; i <= m; i++){
			for (int j = 1; j <= n; j++){
				double score1;
				if (function == 1) {
					score1 = matrix[i - 1][j - 1] + Compare1(seq1.get(i - 1), seq2.get(j - 1));
				} 
				else {
					score1 = matrix[i - 1][j - 1] + Compare2(seq1.get(i - 1), seq2.get(j - 1));
				}
				double score2 = matrix[i-1][j] + gap;
				double score3 = matrix[i][j-1] + gap;
				if (score1 >= score2){
					if (score1 >= score3){
						matrix[i][j] = score1;
						traceback[i][j] = "diag";
					} else {
						matrix[i][j] = score3;
						traceback[i][j] = "left";
					}
				} else if (score2 >= score3){
						matrix[i][j] = score2;
						traceback[i][j] = "up";
				} else {
						matrix[i][j] = score3;
						traceback[i][j] = "left";
				}
			}
		}
	}
	
	private double Compare1(ActionElement a1, ActionElement a2){
		if (a1.getName().equals(a2.getName())){
			return match;
		} else {
			return mismatch;
		}
	}
	
	
	private double Compare2(ActionElement a1, ActionElement a2){
		//System.out.println();
		//System.out.println(a1.getName() + " - " + a2.getName());
		if (a1.getName().equals(a2.getName())) {
			//System.out.println("+2");
			return match;
		} else {
			double compare = 0;
			String verb1 = a1.getHashMap().get("verb");
			String verb2 = a2.getHashMap().get("verb");
			String firstObject1 = a1.getHashMap().get("object1");
			String firstObject2 = a2.getHashMap().get("object1");
			String preposition1 = a1.getHashMap().get("preposition");
			String preposition2 = a2.getHashMap().get("preposition");
			String secondObject1 = a1.getHashMap().get("object2");
			String secondObject2 = a2.getHashMap().get("object2");
			
			if (verb1.isEmpty() && verb2.isEmpty()) {
				compare -= 1;
			} else if (!verb1.equals(verb2)) {
				double x = ontology.getWupSimilarity(verb1, verb2);
				compare = compare - 1.0 + x;
				//System.out.println("--> " + verb1 + " - " + verb2);
				//System.out.println("--> " + x);
			}
			if (firstObject1.isEmpty() && firstObject2.isEmpty()) {
				compare -= 1;
			} else if (!firstObject1.equals(firstObject2)) {
				double x = ontology.getWupSimilarity(firstObject1, firstObject2);
				compare = compare - 1.0 + x;
				//System.out.println("--> " + firstObject1 + " - " + firstObject2);
				//System.out.println("--> " + x);
			}
			if (preposition1.isEmpty() && preposition2.isEmpty()) {
				compare -= 1;
			} else if (!preposition1.equals(preposition2)) {
				double x = ontology.getWupSimilarity(preposition1, preposition2);
				compare = compare - 1.0 + x;
				//System.out.println("--> " + preposition1 + " - " + preposition2);
				//System.out.println("--> " + x);
			}
			if (secondObject1.isEmpty() && secondObject2.isEmpty()) {
				compare -= 1;
			} else if (!secondObject1.equals(secondObject2)) {
				double x = ontology.getWupSimilarity(secondObject1, secondObject2);
				compare = compare - 1.0 + x;
				//System.out.println("--> " + secondObject1 + " - " + secondObject2);
				//System.out.println("--> " + x);
			}
			
			//double x = 2 + compare;
			//System.out.println(x + " !");
			return match + compare;
		}
	}
	
	
	public void printMatrix(){
		for (int i = 0; i <= m; i++){
			for (int j = 0; j <= n; j++){
				double x = matrix[i][j];
				String s = String.valueOf(x);
				//maximum of 6 characters
				for (int k = 6 - s.length(); k > 0; k--){
					System.out.print(" ");
				}
				System.out.print(s);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void printTraceback(){
		for (int i = 0; i <= m; i++){
			for (int j = 0; j <= n; j++){
				String s = traceback[i][j];
				//maximum of 6 characters
				for (int k = 6 - s.length(); k > 0; k--){
					System.out.print(" ");
				}
				System.out.print(s);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void printAlignment(){
		if (pointer == 0){
			calculateAlignmentRecursive(m, n);
		}
		for (int i = pointer - 1; i >= 0; i--){
			String s1 = alignments[1][i];
			String s2 = alignments[0][i];
			//maximum of 50 characters
			for (int k = 50 - s1.length(); k > 0; k--){
				System.out.print(" ");
			}
			System.out.println(s1 + " - " + s2);
		}
		System.out.println();
	}
	
	private void calculateAlignmentRecursive(int m, int n){
		String s = traceback[m][n];
		if (s.equals("diag")){
			alignments[0][pointer] = seq1.get(m-1).getName();
			alignments[1][pointer] = seq2.get(n-1).getName();
			pointer++;
			calculateAlignmentRecursive(m - 1, n - 1);
		} else if (s.equals("left")){
			alignments[0][pointer] = "|";
			alignments[1][pointer] = seq2.get(n-1).getName();
			pointer++;
			calculateAlignmentRecursive(m, n - 1);
		} else if (s.equals("up")){
			alignments[0][pointer] = seq1.get(m-1).getName();
			alignments[1][pointer] = "|";
			pointer++;
			calculateAlignmentRecursive(m - 1, n);
		}
	}
	
	public double getScore(){
		double score = matrix[m][n] / (m + n);
		score = (score + 1.0) / 2.0;
		return Math.round(score * 100.0) / 100.0;
	}
	
}
