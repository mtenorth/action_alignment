package main;

import java.util.ArrayList;

import fileReader.SequenceFileReader;
import alignmentAlgorithm.NeedlemanWunsch;
import alignmentAlgorithm.SmithWaterman;
import sequenceElement.ActionElement;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*
		ActionElement reach = new ActionElement("Reaching");
		ActionElement take = new ActionElement("TakingSomething");
		ActionElement move = new ActionElement("Moving");
		ActionElement put = new ActionElement("PuttingDown");
		ActionElement release = new ActionElement("Releasing");
		ActionElement wait = new ActionElement("Waiting");
		
		ArrayList<ActionElement> seq1 = new ArrayList<ActionElement>();
		seq1.add(reach);
		seq1.add(take);
		seq1.add(move);
		seq1.add(put);
		seq1.add(release);
		seq1.add(wait);
		
		ArrayList<ActionElement> seq2 = new ArrayList<ActionElement>();
		seq2.add(reach);
		seq2.add(take);
		seq2.add(move);
		seq2.add(wait);
		seq2.add(wait);
		seq2.add(put);
		seq2.add(release);
		seq2.add(wait);
		*/
		
		SequenceFileReader reader1 = new SequenceFileReader();
		SequenceFileReader reader2 = new SequenceFileReader();
		
		ArrayList<ActionElement> seq1 = reader1.getSequence("/usr/stud/ziegltru/sequences-johannes/lefthand-0-2-condensed.csv");
		ArrayList<ActionElement> seq2 = reader2.getSequence("/usr/stud/ziegltru/sequences-johannes/lefthand-0-11-condensed.csv");
		
		NeedlemanWunsch ndl = new NeedlemanWunsch(seq1, seq2);
		
		ndl.printMatrix();
		ndl.printTraceback();
		ndl.printAlignment();
			
		SmithWaterman smt = new SmithWaterman(seq1, seq2);
		
		smt.printMatrix();
		smt.printTraceback();
		smt.printAlignment();
		
	}

}
