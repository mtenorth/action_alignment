package alignmentAlgorithm;

import java.util.ArrayList;


import ontology.Ontology;

import sequence.ActionSequence;

/**
 * @author Johannes Ziegltrum
 *
 */
public class SmithWaterman extends AlignmentAlgorithm{

	
	private double maxScore = 0;
	private ArrayList<Integer> maxScoreIndices = new ArrayList<Integer>();
	private String[][] alignments;
	
	
	/**
	 * @param aSeq1 first ActionSequence
	 * @param aSeq2 second ActionSequence
	 */
	public SmithWaterman(ActionSequence aSeq1, ActionSequence aSeq2) {
		
		match = 1.0;
		mismatch = -1.5;
		gap = -0.75;
		
		calculate(aSeq1, aSeq2, null);
	}
	
	/**
	 * 
	 * @param aSeq1 first ActionSequence
	 * @param aSeq2 second ActionSequence
	 * @param ontology the used Ontology for the WUP-similarity-calculation
	 */
	public SmithWaterman(ActionSequence aSeq1, ActionSequence aSeq2, Ontology ontology) {
		
		match = 1.0;
		mismatch = -1.5;
		gap = -0.75;
		
		calculate(aSeq1, aSeq2, ontology);
	}
	
	
	
	//implementation of the smith-waterman-algorithm
	protected void calculate(ActionSequence aSeq1, ActionSequence aSeq2, Ontology ontology){
		this.seq1 = aSeq1.getSequence();
		this.seq2 = aSeq2.getSequence();
		this.ontology = ontology;
		m = seq1.size();
		n = seq2.size();
		matrix = new double[m+1][n+1];
		traceback = new String[m+1][n+1];
		alignments = new String[2][m*n];
		//initialisation of the matrix + traceback
		for (int i = 0; i <= m; i++){
			for (int j = 0; j <= n; j++){
				matrix[i][j] = 0;
				traceback[i][j] = "done";
			}
		}
		//recursive calculation of the remaining alignment-scores + traceback
		for (int i = 1; i <= m; i++){
			for (int j = 1; j <= n; j++){
				double score1;
				double d;
				if (ontology == null) {
					d = compareNoWUP(seq1.get(i - 1), seq2.get(j - 1));
					score1 = matrix[i - 1][j - 1] + d;
				} else {
					d = compareWUP(seq1.get(i - 1), seq2.get(j - 1));
					score1 = matrix[i - 1][j - 1] + d;
				}
				
				if (d == match) {
					String s = seq1.get(i - 1).getHashMap().get("verb");
					if (s.equals("none")) {
						score1 -= 0.5;
					}
				}
				
				double score2 = matrix[i-1][j] + gap;
				double score3 = matrix[i][j-1] + gap;
				
				if (score1 > 0 || score2 > 0 || score3 > 0){
					if (score1 > score2 && score1 > score3){
						matrix[i][j] = score1;
						traceback[i][j] = "diag";
						if (score1 > maxScore){
							maxScore = score1;
						}
					} else if (score2 > score3){
						matrix[i][j] = score2;
						traceback[i][j] = "up";
						if (score2 > maxScore){
							maxScore = score2;
						}
					} else {
						matrix[i][j] = score3;
						traceback[i][j] = "left";
						if (score3 > maxScore){
							maxScore = score3;
						}
					}
				} else {
					matrix[i][j] = 0;
					traceback[i][j] = "done";
				}
			}
		}
		
	}

	
	/**
	 * prints the calculation-matrix
	 */
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
	
	/**
	 * prints the traceback-matrix
	 */
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
	
	/**
	 * prints the local alignments of the two sequences
	 */
	public void printAlignment(){
		if (pointer == 0) {
			for (int i = 1; i <= m; i++) {
				for (int j = 1; j <= n; j++) {
					double score = matrix[i][j];
					if (score == maxScore) {
						maxScoreIndices.add(i);
						maxScoreIndices.add(j);
					}
				}
			}
			while (!maxScoreIndices.isEmpty()) {
				int m = maxScoreIndices.remove(0);
				int n = maxScoreIndices.remove(0);
				calculateAlignmentRecursive(m, n);
			}
		}
		System.out.println("global alignments:");
		for (int i = pointer - 1; i >= 0; i--){
			// if "ln" println for a space between the next local alignment
			if (!alignments[0][i].equals("ln")) {
				String s1 = alignments[0][i];
				String s2 = alignments[1][i];
				//maximum of 50 characters
				for (int k = 50 - s1.length(); k > 0; k--) {
					System.out.print(" ");
				}
				System.out.println(s1 + " , " + s2);
			} else {
				System.out.println();
			}
		}
		System.out.println();
	}
	
	private void calculateAlignmentRecursive(int m, int n){
		String s = traceback[m][n];
		if (s.equals("diag")){
			String name1 = seq1.get(m - 1).getName();
			String name2 = seq2.get(n - 1).getName();
			alignments[0][pointer] = name1;
			alignments[1][pointer] = name2;
			pointer++;
			calculateAlignmentRecursive(m - 1, n - 1);
		} else if (s.equals("left")){
			String name2 = seq2.get(n - 1).getName();
			alignments[0][pointer] = "|";
			alignments[1][pointer] = name2;
			pointer++;
			calculateAlignmentRecursive(m, n - 1);
		} else if (s.equals("up")){
			String name1 = seq1.get(m - 1).getName();
			alignments[0][pointer] = name1;
			alignments[1][pointer] = "|";
			pointer++;
			calculateAlignmentRecursive(m - 1, n);
		} else {
			alignments[0][pointer] = "ln";
			pointer++;
		}
	}
	
	/**
	 * 
	 * @return the best local score in the matrix
	 */
	public double getScore() {
		return maxScore;
	}
	
}
