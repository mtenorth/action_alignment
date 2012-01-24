package alignmentAlgorithm;

import java.util.ArrayList;

import ontology.Ontology;

import sequenceElement.ActionElement;

public class NeedlemanWunsch {
	
	private double match = 1.0;
	private double mismatch = -1.0;
	private double gap = 0;
	
	private ArrayList<ActionElement> seq1;
	private ArrayList<ActionElement> seq2;
	
	private Ontology ontology;
	
	private int m;
	private int n;
	
	private double[][] matrix;
	private String[][] traceback;
	private String[][] alignments;
	
	private int pointer = 0;
	private int nothingCount = 0;
	
	public NeedlemanWunsch(ArrayList<ActionElement> seq1, ArrayList<ActionElement> seq2, int function, Ontology ontology){
		this.seq1 = seq1;
		this.seq2 = seq2;
		this.ontology = ontology;
		m = seq1.size();
		n = seq2.size();
		matrix = new double[m+1][n+1];
		traceback = new String[m+1][n+1];
		alignments = new String[2][m+n];
		//initialisation of the matrix + traceback
		matrix[0][0] = 0;
		traceback[0][0] = "done";
		for (int j = 1; j <= n; j++){
			matrix[0][j] = j * gap;
			traceback[0][j] = "left";
		}
		for (int i = 1; i <= m; i++){
			matrix[i][0] = i * gap;
			traceback[i][0] = "up";
		}
		//recursive calculation of the remaining alignment-scores + traceback
		for (int i = 1; i <= m; i++){
			for (int j = 1; j <= n; j++){
				double score1;
				double d;
				if (function == 1) {
					d = Compare1(seq1.get(i - 1), seq2.get(j - 1));
					score1 = matrix[i - 1][j - 1] + d;
				} 
				else {
					d = Compare2(seq1.get(i - 1), seq2.get(j - 1));
					score1 = matrix[i - 1][j - 1] + d;
				}
				
				if (d == 4.0 * match) {
					String s = seq1.get(i - 1).getHashMap().get("verb");
					if (s.equals("Nothing")) {
						score1 -= 2.0;
					}
				}
				
				double score2 = matrix[i-1][j] + gap;
				double score3 = matrix[i][j-1] + gap;
				
				//System.out.println(seq1.get(i - 1).getName() + " - " + seq2.get(j - 1).getName());
				//System.out.println(d);
				
				if (score1 > score2 && score1 > score3){
					matrix[i][j] = score1;
					traceback[i][j] = "diag";
				} else if (score2 > score3){
						matrix[i][j] = score2;
						traceback[i][j] = "up";
				} else {
						matrix[i][j] = score3;
						traceback[i][j] = "left";
				}
			}
		}
		this.countNothing(m, n);
	}
	
	private double Compare1(ActionElement a1, ActionElement a2){
		if (a1.getName().equals(a2.getName())) {
			return 4.0 * match;
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
			
			if (!verb1.equals(verb2)) {
				compare += mismatch;
			} else if (verb1.equals(verb2) && !verb1.isEmpty() && !verb2.isEmpty()) {
				compare += match;
			}
			
			if (!firstObject1.equals(firstObject2)) {
				compare += mismatch;
			} else if (firstObject1.equals(firstObject2) && !firstObject1.isEmpty() && !firstObject2.isEmpty()) {
				compare += match;
			}
			
			if (!preposition1.equals(preposition2)) {
				compare += mismatch;
			} else if (preposition1.equals(preposition2) && !preposition1.isEmpty() && !preposition2.isEmpty()) {
				compare += match;
			}
			
			if (!secondObject1.equals(secondObject2)) {
				compare += mismatch;
			} else if (secondObject1.equals(secondObject2) && !secondObject1.isEmpty() && !secondObject2.isEmpty()) {
				compare += match;
			}
			
			return compare;
		}
	}
	
	private double Compare2(ActionElement a1, ActionElement a2){
		if (a1.getName().equals(a2.getName())) {
			return 4.0 * match;
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
			
			if (!verb1.equals(verb2)) {
				if (verb1.isEmpty() || verb2.isEmpty()) {
					compare += mismatch;
				} else {
					compare += mismatch + 2.0 * ontology.getWupSimilarity(verb1, verb2);
				}
			} else if (verb1.equals(verb2) && !verb1.isEmpty() && !verb2.isEmpty()) {
				compare += match;
			}
			
			if (!firstObject1.equals(firstObject2)) {
				if (firstObject1.isEmpty() || firstObject2.isEmpty()) {
					compare += mismatch;
				} else {
					compare += mismatch + 2.0 * ontology.getWupSimilarity(firstObject1, firstObject2);
				}
			} else if (firstObject1.equals(firstObject2) && !firstObject1.isEmpty() && !firstObject2.isEmpty()) {
				compare += match;
			}
			
			if (!preposition1.equals(preposition2)) {
				if (preposition1.isEmpty() || preposition2.isEmpty()) {
					compare += mismatch;
				} else {
					compare += mismatch + 2.0 * ontology.getWupSimilarity(preposition1, preposition2);
				}
			} else if (preposition1.equals(preposition2) && !preposition1.isEmpty() && !preposition2.isEmpty()) {
				compare += match;
			}
			
			if (!secondObject1.equals(secondObject2)) {
				if (secondObject1.isEmpty() || secondObject2.isEmpty()) {
					compare += mismatch;
				} else {
					compare += mismatch + 2.0 * ontology.getWupSimilarity(secondObject1, secondObject2);
				}
			} else if (secondObject1.equals(secondObject2) && !secondObject1.isEmpty() && !secondObject2.isEmpty()) {
				compare += match;
			}
			
			return compare;
		}
	}
	
	public void printMatrix(){
		for (int i = 0; i <= m; i++){
			for (int j = 0; j <= n; j++){
				double x = Math.round(matrix[i][j] * 100.0) / 100.0;
				String s = String.valueOf(x);
				//maximum of 8 characters
				for (int k = 8 - s.length(); k > 0; k--){
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
				//maximum of 8 characters
				for (int k = 8 - s.length(); k > 0; k--){
					System.out.print(" ");
				}
				System.out.print(s);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void printAlignment(){
		if (pointer == 0) {
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
		System.out.println("Länge seq1 = " + m + "; Länge seq2 = " + n);
		double score = this.getScore();
		System.out.println("Alignment-Score: " + score);
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
	
	private void countNothing(int m, int n) {
		String s = traceback[m][n];
		if (s.equals("diag")){
			String s1 = seq1.get(m - 1).getHashMap().get("verb");
			String s2 = seq2.get(n - 1).getHashMap().get("verb");
			if (s1.equals("Nothing") && s2.equals("Nothing")) {
				//System.out.println("++");
				nothingCount++;
			}
			countNothing(m - 1, n - 1);
		} else if (s.equals("left")){
			countNothing(m, n - 1);
		} else if (s.equals("up")){
			countNothing(m - 1, n);
		}
	}
	
	public double getScore(){
		//System.out.println(matrix[m][n] + " + 2 * " + nothingCount + " / 2 * (" + m + " + " + n + ")");
		double score = (matrix[m][n] + 2.0 * nothingCount) / (2.0 * (m + n));
		//score = (score + 0.5) / 1.5;
		return Math.round(score * 100.0) / 100.0;
	}
	
}
