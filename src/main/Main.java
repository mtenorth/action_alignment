package main;

import java.util.ArrayList;

import org.semanticweb.HermiT.debugger.Debugger.WaitOption;

import alignmentAlgorithm.NeedlemanWunsch;

import evaluation.ConfusionMatrix;
import fileReader.DataFileReader;
import ontology.Ontology;
import sequence.ActionSequence;
import sequenceElement.ActionElement;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		String url = "http://ias.cs.tum.edu/kb/knowrob.owl";
		Ontology ontology = new Ontology(url);
		
		/*
		String object1 = "ClearGlass";
		String object2 = "Water";
		String object3 = "Event";
		String object4 = "ClosingADoor";
		String object5 = "B21";
		String object6 = "Box";
		String object7 = "ObjectType";
		String object8 = "Color";
		String object9 = "ColoredThing";
		String object10 = "Nothing";
		String object11 = "none";
		String object12 = "";
		
		ontology.getWupSimilarity(object1, object10);
		ontology.getWupSimilarity(object10, object11);
		ontology.getWupSimilarity(object1, object2);
		ontology.getWupSimilarity(object11, object12);
		ontology.getWupSimilarity(object5, object6);
		ontology.getWupSimilarity(object6, object7);
		ontology.getWupSimilarity(object7, object8);
		ontology.getWupSimilarity(object8, object9);
		*/
		
		
		DataFileReader reader1 = new DataFileReader();
		DataFileReader reader2 = new DataFileReader();
		DataFileReader reader3 = new DataFileReader();
		DataFileReader reader4 = new DataFileReader();
		DataFileReader reader5 = new DataFileReader();
		DataFileReader reader6 = new DataFileReader();
		DataFileReader reader7 = new DataFileReader();
		DataFileReader reader8 = new DataFileReader();
		DataFileReader reader9 = new DataFileReader();
		DataFileReader reader10 = new DataFileReader();
		DataFileReader reader11 = new DataFileReader();
		DataFileReader reader12 = new DataFileReader();
		DataFileReader reader13 = new DataFileReader();
		DataFileReader reader14 = new DataFileReader();
		DataFileReader reader15 = new DataFileReader();
		DataFileReader reader16 = new DataFileReader();
		DataFileReader reader17 = new DataFileReader();
		
		ArrayList<ActionElement> seq06 = reader1.getSequence("C:/Users/Administrator/Desktop/Data/MakingEggs/S06/labels.dat");
		ArrayList<ActionElement> seq07 = reader2.getSequence("C:/Users/Administrator/Desktop/Data/MakingEggs/S07/labels.dat");
		ArrayList<ActionElement> seq08 = reader3.getSequence("C:/Users/Administrator/Desktop/Data/MakingEggs/S08/labels.dat");
		ArrayList<ActionElement> seq09 = reader4.getSequence("C:/Users/Administrator/Desktop/Data/MakingEggs/S09/labels.dat");
		ArrayList<ActionElement> seq11 = reader5.getSequence("C:/Users/Administrator/Desktop/Data/MakingEggs/S11/labels.dat");
		ArrayList<ActionElement> seq12 = reader6.getSequence("C:/Users/Administrator/Desktop/Data/MakingEggs/S12/labels.dat");
		ArrayList<ActionElement> seq13 = reader7.getSequence("C:/Users/Administrator/Desktop/Data/MakingEggs/S13/labels.dat");
		ArrayList<ActionElement> seq14 = reader8.getSequence("C:/Users/Administrator/Desktop/Data/MakingEggs/S14/labels.dat");
		ArrayList<ActionElement> seq15 = reader9.getSequence("C:/Users/Administrator/Desktop/Data/MakingEggs/S15/labels.dat");
		ArrayList<ActionElement> seq16 = reader10.getSequence("C:/Users/Administrator/Desktop/Data/MakingEggs/S16/labels.dat");
		ArrayList<ActionElement> seq17 = reader11.getSequence("C:/Users/Administrator/Desktop/Data/MakingEggs/S17/labels.dat");
		ArrayList<ActionElement> seq19 = reader12.getSequence("C:/Users/Administrator/Desktop/Data/MakingEggs/S19/labels.dat");
		ArrayList<ActionElement> seq20 = reader13.getSequence("C:/Users/Administrator/Desktop/Data/MakingEggs/S20/labels.dat");
		ArrayList<ActionElement> seq25 = reader14.getSequence("C:/Users/Administrator/Desktop/Data/MakingEggs/S25/labels.dat");
		ArrayList<ActionElement> seq53 = reader15.getSequence("C:/Users/Administrator/Desktop/Data/MakingEggs/S53/labels.dat");
		ArrayList<ActionElement> seq54 = reader16.getSequence("C:/Users/Administrator/Desktop/Data/MakingEggs/S54/labels.dat");
		ArrayList<ActionElement> seq55 = reader17.getSequence("C:/Users/Administrator/Desktop/Data/MakingEggs/S55/labels.dat");
		
		ActionSequence a06 = new ActionSequence("S06", seq06);
		ActionSequence a07 = new ActionSequence("S07", seq07);
		ActionSequence a08 = new ActionSequence("S08", seq08);
		ActionSequence a09 = new ActionSequence("S09", seq09);
		ActionSequence a11 = new ActionSequence("S11", seq11);
		ActionSequence a12 = new ActionSequence("S12", seq12);
		ActionSequence a13 = new ActionSequence("S13", seq13);
		ActionSequence a14 = new ActionSequence("S14", seq14);
		ActionSequence a15 = new ActionSequence("S15", seq15);
		ActionSequence a16 = new ActionSequence("S16", seq16);
		ActionSequence a17 = new ActionSequence("S17", seq17);
		ActionSequence a19 = new ActionSequence("S19", seq19);
		ActionSequence a20 = new ActionSequence("S20", seq20);
		ActionSequence a25 = new ActionSequence("S25", seq25);
		ActionSequence a53 = new ActionSequence("S53", seq53);
		ActionSequence a54 = new ActionSequence("S54", seq54);
		ActionSequence a55 = new ActionSequence("S55", seq55);
		
		ArrayList<ActionSequence> aList = new ArrayList<ActionSequence>();
		/*
		aList.add(a06);
		aList.add(a07);
		aList.add(a08);
		aList.add(a09);
		aList.add(a11);
		aList.add(a12);
		aList.add(a13);
		aList.add(a14);
		aList.add(a15);
		aList.add(a16);
		aList.add(a17);
		aList.add(a19);
		aList.add(a20);
		aList.add(a25);
		aList.add(a53);
		aList.add(a54);
		aList.add(a55);
		
		ConfusionMatrix cm1 = new ConfusionMatrix(aList, 1, ontology);
		cm1.printConfusionMatrix();
		
		ConfusionMatrix cm2 = new ConfusionMatrix(aList, 2, ontology);
		cm2.printConfusionMatrix();
		*/
		
		/*
		NeedlemanWunsch ndl1 = new NeedlemanWunsch(seq, seq, 1, ontology);
		System.out.println();
		ndl1.printAlignment();
		System.out.println();
		System.out.println(ndl1.getScore());
		*/
		/*
		NeedlemanWunsch ndl2 = new NeedlemanWunsch(seq, seq, 2, ontology);
		System.out.println();
		ndl2.printAlignment();
		System.out.println();
		System.out.println(ndl2.getScore());
		*/
		
	}

}
