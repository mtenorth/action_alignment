package alignmentAlgorithm;

import hierarchicStructure.HierarchicalStructure;
import hierarchicStructure.Transformer;

import java.text.DecimalFormat;
import ontology.Ontology;
import sequence.ActionSequence;
import sequenceElement.ActionElement;

/**
 * 
 * @author Johannes Ziegltrum
 *
 */
public class NeedlemanWunsch extends AlignmentAlgorithm {
	
	
	HierarchicalStructure hierarchy = new HierarchicalStructure("Data/HierarchicStructure.txt");
	Transformer transformer = new Transformer(hierarchy);
	
	protected ActionElement[][] alignments;
	
	/**
	 * @param aSeq1 first ActionSequence
	 * @param aSeq2 second ActionSequence
	 */
	public NeedlemanWunsch(ActionSequence aSeq1, ActionSequence aSeq2) {
		this(aSeq1, aSeq2, null);
	}
	
	/**
	 * @param aSeq1 first ActionSequence
	 * @param aSeq2 second ActionSequence
	 * @param ontology the used Ontology for the WUP-similarity-calculation
	 */
	public NeedlemanWunsch(ActionSequence aSeq1, ActionSequence aSeq2, Ontology ontology) {
		
		match = 1;
		mismatch = -1;
		gap = 0.0;
				
		calculate(aSeq1, aSeq2, ontology);
	}
	
	//implementation of the needleman-wunsch-algorithm
	protected void calculate(ActionSequence aSeq1, ActionSequence aSeq2, Ontology ontology){
		seq1 = aSeq1.getSequence();
		seq2 = aSeq2.getSequence();
		seq1 = transformer.transform(aSeq1.getSequence());
		seq2 = transformer.transform(aSeq2.getSequence());
		this.ontology = ontology;
		m = seq1.size();
		n = seq2.size();
		matrix = new double[m+1][n+1];
		traceback = new String[m+1][n+1];
		alignments = new ActionElement[2][m+n];
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
				if (ontology == null) {
					d = compareNoWUP(seq1.get(i - 1), seq2.get(j - 1));
					score1 = matrix[i - 1][j - 1] + d;
				} 
				else {
					d = compareWUP(seq1.get(i - 1), seq2.get(j - 1));
					score1 = matrix[i - 1][j - 1] + d;
				}
				
//				if (d == match) {
//					String s1 = seq1.get(i - 1).getHashMap().get("verb");
//					String s2 = seq2.get(j - 1).getHashMap().get("verb");
//					if (s1 != null && s2 != null) {
//						if (s1.equals("none") && s2.equals("none")) {
//							score1 -= 0.5;
//						}
//					}
//				}
				
				double score2 = matrix[i-1][j] + gap;
				double score3 = matrix[i][j-1] + gap;
				
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
		
		//this.countNoneMatchings(m, n);
		
	}
	
	
	/**
	 * prints the calculation-matrix
	 */
	public String printMatrix(){
		
		String res = "";
		
		for (int i = 0; i <= m; i++){
			for (int j = 0; j <= n; j++){
				double d = matrix[i][j];
				DecimalFormat df = new DecimalFormat("0.00");
				String s = df. format(d);
				//maximum of 8 characters
				for (int k = 8 - s.length(); k > 0; k--){
					res += " ";
				}
				res += s;
			}
			res += "\n";
		}
		res += "\n";

		System.out.println(res);
		return res;
	}
	
	/**
	 * prints the traceback-matrix
	 */
	public String printTraceback(){

		String res = "";
		
		for (int i = 0; i <= m; i++){
			for (int j = 0; j <= n; j++){
				String s = traceback[i][j];
				//maximum of 8 characters
				for (int k = 8 - s.length(); k > 0; k--){
					res += " ";
				}
				res += s;
			}
			res += "\n";
		}
		res += "\n";
		
		System.out.println(res);
		return res;
	}
	
	/**
	 * prints the alignment of the two sequences
	 */
	public String printAlignment(){
		
		String res = "";
		
		if (pointer == 0) {
			calculateAlignmentRecursive(m, n);
			alignments = transformer.retransform(alignments);
		}
		for (int i = pointer - 1; i >= 0; i--){
			String s1 = alignments[0][i].getName();
			String s2 = alignments[1][i].getName();
			//maximum of 50 characters
			for (int k = 50 - s1.length(); k > 0; k--){
				res += " ";
			}
			res += s1 + " - " + s2 + "\n";
		}
		
		res += "\nLength seq1 = " + m + "; length seq2 = " + n + "\n";
		double score = this.getScore();
		res += "alignment score: " + score + "\n\n";
		
		System.out.println(res);
		return res;
	}
	
	private void calculateAlignmentRecursive(int m, int n){
		String s = traceback[m][n];
		if (s.equals("diag")) {
			alignments[0][pointer] = seq1.get(m - 1);
			alignments[1][pointer] = seq2.get(n - 1);
			pointer++;
			calculateAlignmentRecursive(m - 1, n - 1);
		} else if (s.equals("left")) {
			alignments[0][pointer] = new ActionElement("|");
			alignments[1][pointer] = seq2.get(n - 1);
			pointer++;
			calculateAlignmentRecursive(m, n - 1);
		} else if (s.equals("up")) {
			alignments[0][pointer] = seq1.get(m - 1);
			alignments[1][pointer] = new ActionElement("|");
			pointer++;
			calculateAlignmentRecursive(m - 1, n);
		}
	}
	
//	private void countNoneMatchings(int m, int n) {
//		String s = traceback[m][n];
//		if (s.equals("diag")){
//			String s1 = seq1.get(m - 1).getHashMap().get("verb");
//			String s2 = seq2.get(n - 1).getHashMap().get("verb");
//			if (s1 != null && s2 != null) {
//				if (s1.equals("none") && s2.equals("none")) {
//					noneCount++;
//				}
//			}
//			countNoneMatchings(m - 1, n - 1);
//		} else if (s.equals("left")){
//			countNoneMatchings(m, n - 1);
//		} else if (s.equals("up")){
//			countNoneMatchings(m - 1, n);
//		}
//	}
	
	/**
	 * @return the similarity-score of the two sequences
	 */
	public double getScore(){
		//double score = (matrix[m][n] + 0.5 * noneCount) / ((m + n) / 2.0);
		double score = (matrix[m][n]) / ((m + n) / 2.0);
		return Math.round(score * 100.0) / 100.0;
	}
	
}
