package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import alignmentAlgorithm.NeedlemanWunsch;
import alignmentAlgorithm.SmithWaterman;

import fileReader.LabelsDATFileReader;
import ontology.Ontology;
import sequence.ActionSequence;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		LabelsCSVFileReader r1 = new LabelsCSVFileReader();
//		LabelsCSVFileReader r2 = new LabelsCSVFileReader();
//		
//		ArrayList<ActionElement> seqLeft00 = r1.getSequence("Data/SequencesHands/lefthand-0-0-condensed.csv");
//		ArrayList<ActionElement> seqRight00 = r2.getSequence("Data/SequencesHands/righthand-0-0-condensed.csv");
//		
//		ActionSequence aLeft00 = new ActionSequence("lefthand-0-0", seqLeft00);
//		ActionSequence aRight00 = new ActionSequence("righthand-0-0", seqRight00);
//		
//		NeedlemanWunsch ndl = new NeedlemanWunsch(aLeft00, aRight00);
//		ndl.printMatrix();
//		ndl.printTraceback();
//		ndl.printAlignment();

		
		
		String url = "http://ias.cs.tum.edu/kb/knowrob.owl";
		Ontology ontology = new Ontology(url);
		LabelsDATFileReader reader = new LabelsDATFileReader();
		
		
		// read labels for making eggs
		
		HashMap<String, ActionSequence> eggs = new LinkedHashMap<String, ActionSequence>();
		for (String id : new String[]{"S06", "S07", "S08", "S09", "S11", "S12", "S13", 
				"S14", "S15", "S16", "S17", "S19", "S20", "S25", "S53", "S54", "S55"}) {
			eggs.put("Egg_"+id, new ActionSequence("Bro_"+id, 
					reader.getSequence("Data/MakingEggs/"+id+"/labels.dat")));
		}
		
		
		// read labels for making brownies
		HashMap<String, ActionSequence> brownies = new LinkedHashMap<String, ActionSequence>();
		for (String id : new String[]{"S06", "S07", "S08", "S09", "S10", "S12", "S13",
					"S14", "S16", "S17", "S18", "S19", "S20", "S22", "S23", "S24"}) {
			brownies.put("Bro_"+id, new ActionSequence("Bro_"+id, 
					reader.getSequence("Data/MakingBrownie/"+id+"/labels.dat")));
		}
		
		
		// // // // // // // // // // // // // // // // // // // // // // 
		// GLOBAL ALIGNMENT		
		
		// comparison without WUP
//		computeAndPrintGlobalAlignment(brownies, null);
//		computeAndPrintGlobalAlignment(eggs, null);

		// comparison with WUP
//		computeAndPrintGlobalAlignment(brownies, ontology);
		computeAndPrintGlobalAlignment(eggs, ontology);


		// // // // // // // // // // // // // // // // // // // // // // 
		// LOCAL ALIGNMENT
		
		// comparison without WUP
//		computeAndPrintLocalAlignment(brownies, null);
//		computeAndPrintLocalAlignment(eggs, null);

		// comparison with WUP
//		computeAndPrintLocalAlignment(brownies, ontology);
//		computeAndPrintLocalAlignment(eggs, ontology);
		

		// // // // // // // // // // // // // // // // // // // // // // 
		// SIMILARITY MATRIX EGGS/BROWNIES
		
//		ArrayList<ActionSequence> aList = new ArrayList<ActionSequence>();
//		aList.addAll(brownies.values());
//		aList.addAll(eggs.values());
//		
//		System.out.println("\n\n\n\n\n\n= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
//		System.out.println("SIMILARITY MATRIX WITHOUT WUP");
//		
//		ConfusionMatrix cm_nowup = new ConfusionMatrix(aList);
//		cm_nowup.printConfusionMatrix();
//
//		System.out.println("\n\n\n\n\n\n= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
//		System.out.println("SIMILARITY MATRIX WITH WUP");
//		
//		ConfusionMatrix cm_wup = new ConfusionMatrix(aList, ontology);
//		cm_wup.printConfusionMatrix();
		
	}
	
	
	@SuppressWarnings("unused")
	private static void computeAndPrintLocalAlignment(HashMap<String, ActionSequence> experiment, Ontology ontology) {

		System.out.println("\n\n\n\n\n\n= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
		System.out.println("SMITH-WATERMAN PAIRWISE LOCAL ALIGNMENTS");
		
		ArrayList<ActionSequence> seqs = new ArrayList<ActionSequence>(experiment.values());
		for(int i=0;i<seqs.size();i++) {
			
			System.out.println("\n\n\n= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
			System.out.println("COMPARING " + seqs.get(i).getIdentifier() + " WITH " + seqs.get((i+1)%seqs.size()).getIdentifier());
			
			SmithWaterman ndl1;
			if(ontology!=null)
				ndl1 = new SmithWaterman(seqs.get(i), seqs.get((i+1)%seqs.size()), ontology);
			else 
				ndl1 = new SmithWaterman(seqs.get(i), seqs.get((i+1)%seqs.size()));
			
			//ndl1.printMatrix();
			//ndl1.printTraceback();
			ndl1.printAlignment();
		}
		
	}

	
	@SuppressWarnings("unused")
	public static void computeAndPrintGlobalAlignment(HashMap<String, ActionSequence> experiment, Ontology ontology) {
		
		System.out.println("\n\n\n\n\n\n= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
		System.out.println("NEEDLEMAN-WUNSCH PAIRWISE GLOBAL ALIGNMENTS");
		
		ArrayList<ActionSequence> seqs = new ArrayList<ActionSequence>(experiment.values());
		for(int i=0;i<seqs.size();i++) {
			
			System.out.println("\n\n\n= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
			System.out.println("COMPARING " + seqs.get(i).getIdentifier() + " WITH " + seqs.get((i+1)%seqs.size()).getIdentifier());
			
			NeedlemanWunsch ndl1;
			if(ontology!=null)
				ndl1 = new NeedlemanWunsch(seqs.get(i), seqs.get((i+1)%seqs.size()), ontology);
			else 
				ndl1 = new NeedlemanWunsch(seqs.get(i), seqs.get((i+1)%seqs.size()));
			
			//ndl1.printMatrix();
			//ndl1.printTraceback();
			ndl1.printAlignment();
		}
	}

}
