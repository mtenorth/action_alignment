package alignmentAlgorithm;

import java.util.ArrayList;

import sequenceElement.ActionElement;

public class NeedlemanWunsch {

	int match = 10;
	int mismatch = -5;
	int gap = -3;
	
	private ArrayList<ActionElement> seq1;
	private ArrayList<ActionElement> seq2;
	
	private int m;
	private int n;
	
	private int[][] matrix;
	private String[][] traceback;
	private String[][] alignments;
	
	private int pointer = 0;
	
	public NeedlemanWunsch(ArrayList<ActionElement> seq1, ArrayList<ActionElement> seq2){
		this.seq1 = seq1;
		this.seq2 = seq2;
		m = seq1.size();
		n = seq2.size();
		matrix = new int[m+1][n+1];
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
				//int score1 = matrix[i-1][j-1] + Compare(seq1.get(i-1), seq2.get(j-1));
				int score1 = matrix[i-1][j-1] + Compare2(seq1.get(i-1), seq2.get(j-1));
				int score2 = matrix[i-1][j] + gap;
				int score3 = matrix[i][j-1] + gap;
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
	
	/*private int Compare(ActionElement a1, ActionElement a2){
		if (a1.getName().equals(a2.getName())){
			return match;
		} else {
			return mismatch;
		}
	}*/
	
	private int Compare2(ActionElement a1, ActionElement a2){
		if (a1.getName().equals(a2.getName())){
			return match;
		} else {
			if (a1.getHashMap().get("verb").equals(a2.getHashMap().get("verb"))){
				if (a1.getHashMap().get("object1").equals(a2.getHashMap().get("object1"))){
					if (a1.getHashMap().get("preposition").equals(a2.getHashMap().get("preposition"))){
						return match - 3;
					} else {
						return match - 6;
					}
				} else {
					return match - 9;
				}
			} else {
				return mismatch;
			}
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
	
	public int getScore(){
		return matrix[m][n];
	}
	
}
