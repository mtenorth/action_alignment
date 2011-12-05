package evaluation;

import java.util.ArrayList;

import alignmentAlgorithm.NeedlemanWunsch;

import sequence.ActionSequence;

public class ConfusionMatrix {

	private int number;
	private ArrayList<ActionSequence> seqList;
	
	public ConfusionMatrix(ArrayList<ActionSequence> seqList){
		number = seqList.size();
		this.seqList = seqList;
	}
	
	public void printConfusionMatrix(){
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
				NeedlemanWunsch ndl = new NeedlemanWunsch(seqList.get(i).getSequence(), seqList.get(k).getSequence());
				int score = ndl.getScore();
				for (int l = 0; l < 6 - String.valueOf(score).length(); l++){
					System.out.print(" ");
				}
				System.out.print(String.valueOf(score));
			}
			System.out.println();
		}
	}
	
}
