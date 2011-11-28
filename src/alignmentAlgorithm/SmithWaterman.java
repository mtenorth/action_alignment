package alignmentAlgorithm;

import java.util.ArrayList;

import sequenceElement.ActionElement;

public class SmithWaterman {

	int match = 2;
	int mismatch = -2;
	int gap = -1;
	
	private ArrayList<ActionElement> seq1;
	private ArrayList<ActionElement> seq2;
	
	private int m;
	private int n;
	
	private int[][] matrix;
	private String[][] traceback;
	private String[][] alignments;
	
	private int pointer = 0;
	
	private int maxScore = 0;
	private ArrayList<Integer> maxScoreIndices = new ArrayList<Integer>();
	
	public SmithWaterman(ArrayList<ActionElement> seq1, ArrayList<ActionElement> seq2){
		this.seq1 = seq1;
		this.seq2 = seq2;
		m = seq1.size();
		n = seq2.size();
		matrix = new int[m+1][n+1];
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
				int score1 = matrix[i-1][j-1] + Compare(seq1.get(i-1), seq2.get(j-1));
				int score2 = matrix[i-1][j] + gap;
				int score3 = matrix[i][j-1] + gap;
				if (score1 > 0){
					if (score1 >= score2){
						if (score1 >= score3){
							matrix[i][j] = score1;
							traceback[i][j] = "diag";
							if (score1 > maxScore){
								maxScore = score1;
							}
						} else {
							matrix[i][j] = score3;
							traceback[i][j] = "left";
							if (score3 > maxScore){
								maxScore = score3;
							}
						}
					} else if (score2 >= score3){
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
	
	private int Compare(ActionElement a1, ActionElement a2){
		if (a1.getName().equals(a2.getName())){
			return match;
		} else {
			return mismatch;
		}
	}
	
	public void printMatrix(){
		for (int i = 0; i <= m; i++){
			for (int j = 0; j <= n; j++){
				int x = matrix[i][j];
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
		if (pointer == 0) {
			for (int i = 1; i <= m; i++) {
				for (int j = 1; j <= n; j++) {
					int score = matrix[i][j];
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
		for (int i = pointer - 1; i >= 0; i--){
			if (!alignments[0][i].equals("ln")) {
				String s1 = alignments[1][i];
				String s2 = alignments[0][i];
				//maximum of 30 characters
				for (int k = 30 - s1.length(); k > 0; k--) {
					System.out.print(" ");
				}
				System.out.println(s1 + " - " + s2);
			} else {
				System.out.println();
			}
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
		} else {
			alignments[0][pointer] = "ln";
			pointer++;
		}
	}
	
}
