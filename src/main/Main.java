package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import alignmentAlgorithm.AlignmentAlgorithm;
import alignmentAlgorithm.NeedlemanWunsch;
import alignmentAlgorithm.SmithWaterman;

import evaluation.ConfusionMatrix;
import fileReader.LabelsDATFileReader;
import ontology.Ontology;
import sequence.ActionSequence;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {


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
				eggs.put("Egg_"+id, new ActionSequence("Egg_"+id, 
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

			String outpath = "results/weights_"+AlignmentAlgorithm.weight_act+"_"+
												AlignmentAlgorithm.weight_obj1+"_"+
												AlignmentAlgorithm.weight_prep+"_"+
												AlignmentAlgorithm.weight_obj2+"/";

			// comparison without WUP
			computeAndPrintGlobalAlignment(brownies, null, outpath + "pairwise-global-alignment-brownies-nowup");
			computeAndPrintGlobalAlignment(eggs, null, outpath + "pairwise-global-alignment-eggs-nowup");

			// comparison with WUP
			computeAndPrintGlobalAlignment(brownies, ontology, outpath + "pairwise-global-alignment-brownies-wup");
			computeAndPrintGlobalAlignment(eggs, ontology, outpath + "pairwise-global-alignment-eggs-wup");


			// // // // // // // // // // // // // // // // // // // // // // 
			// LOCAL ALIGNMENT

			// comparison without WUP
			computeAndPrintLocalAlignment(brownies, null, outpath + "pairwise-local-alignment-brownies-nowup");
			computeAndPrintLocalAlignment(eggs, null, outpath + "pairwise-local-alignment-eggs-nowup");

			// comparison with WUP
			computeAndPrintLocalAlignment(brownies, ontology, outpath + "pairwise-local-alignment-brownies-wup");
			computeAndPrintLocalAlignment(eggs, ontology, outpath + "pairwise-local-alignment-eggs-wup");


			// // // // // // // // // // // // // // // // // // // // // // 
			// SIMILARITY MATRIX EGGS/BROWNIES

			Writer out = new OutputStreamWriter(new FileOutputStream(new File(outpath + "confusion-matrix-nowup")));
			
			ArrayList<ActionSequence> aList = new ArrayList<ActionSequence>();
			aList.addAll(brownies.values());
			aList.addAll(eggs.values());
			
			out.write("\n\n\n\n\n\n= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =\n");
			out.write("SIMILARITY MATRIX WITHOUT WUP\n");
			out.close();
			
			
			out = new OutputStreamWriter(new FileOutputStream(new File(outpath + "confusion-matrix-wup")));
			
			ConfusionMatrix cm_nowup = new ConfusionMatrix(aList);
			out.write(cm_nowup.printConfusionMatrix());
	
			out.write("\n\n\n\n\n\n= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =\n");
			out.write("SIMILARITY MATRIX WITH WUP\n");
			
			ConfusionMatrix cm_wup = new ConfusionMatrix(aList, ontology);
			out.write(cm_wup.printConfusionMatrix());
			out.close();
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private static void computeAndPrintLocalAlignment(HashMap<String, ActionSequence> experiment, Ontology ontology, String outfile) throws IOException {

		Writer out = new OutputStreamWriter(new FileOutputStream(outfile));
		try {

			out.write("\n\n\n\n\n\n= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =\n");
			out.write("SMITH-WATERMAN PAIRWISE LOCAL ALIGNMENTS\n");

			ArrayList<ActionSequence> seqs = new ArrayList<ActionSequence>(experiment.values());
			for(int i=0;i<seqs.size();i++) {

				out.write("\n\n\n= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =\n");
				out.write("COMPARING " + seqs.get(i).getIdentifier() + " WITH " + seqs.get((i+1)%seqs.size()).getIdentifier()+"\n\n");

				SmithWaterman smw;
				if(ontology!=null)
					smw = new SmithWaterman(seqs.get(i), seqs.get((i+1)%seqs.size()), ontology);
				else 
					smw = new SmithWaterman(seqs.get(i), seqs.get((i+1)%seqs.size()));

				//ndl1.printMatrix();
				//ndl1.printTraceback();
				out.write(smw.printAlignment());
			}
		} finally {
			out.close();
		}

	}


	public static void computeAndPrintGlobalAlignment(HashMap<String, ActionSequence> experiment, Ontology ontology, String outfile) throws IOException {

		Writer out = new OutputStreamWriter(new FileOutputStream(outfile));
		try {

			out.write("\n\n\n\n\n\n= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =\n");
			out.write("NEEDLEMAN-WUNSCH PAIRWISE GLOBAL ALIGNMENTS\n");

			ArrayList<ActionSequence> seqs = new ArrayList<ActionSequence>(experiment.values());
			for(int i=0;i<seqs.size();i++) {

				out.write("\n\n\n= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =\n");
				out.write("COMPARING " + seqs.get(i).getIdentifier() + " WITH " + seqs.get((i+1)%seqs.size()).getIdentifier() + "\n\n");

				NeedlemanWunsch ndl;
				if(ontology!=null)
					ndl = new NeedlemanWunsch(seqs.get(i), seqs.get((i+1)%seqs.size()), ontology);
				else 
					ndl = new NeedlemanWunsch(seqs.get(i), seqs.get((i+1)%seqs.size()));

				//ndl1.printMatrix();
				//ndl1.printTraceback();
				out.write(ndl.printAlignment());
			}

		} finally {
			out.close();
		}
	}
}
