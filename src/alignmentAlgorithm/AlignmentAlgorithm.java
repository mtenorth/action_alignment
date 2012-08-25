package alignmentAlgorithm;

import java.util.ArrayList;

import ontology.Ontology;
import ontology.Translator;
import sequence.ActionSequence;
import sequenceElement.ActionElement;

public abstract class AlignmentAlgorithm {


	protected double match = 1;
	protected double mismatch = -1;
	protected double gap = 0;
	
	//the sum of alpha, beta, gamma and delta must be 1
	protected double weight_act = 0.3;
	protected double weight_obj1 = 0.3;
	protected double weight_prep = 0.1;
	protected double weight_obj2 = 0.3;
	
	protected double exponent = 3;

	protected Ontology ontology;

	protected ArrayList<ActionElement> seq1;
	protected ArrayList<ActionElement> seq2;
	
	protected int m;
	protected int n;
	protected int pointer = 0;
	
	protected double[][] matrix;
	protected String[][] traceback;
	
	
	abstract void calculate(ActionSequence aSeq1, ActionSequence aSeq2, Ontology ontology);
	
	
	
	//simple match-mismatch-comparison
	protected double compareNoWUP(ActionElement a1, ActionElement a2){
		if (a1.getName().equals(a2.getName())) {
			return match;
		} else {
			return mismatch;
		}
	}
	
	//comparison with WUP-similarity
	protected double compareWUP(ActionElement a1, ActionElement a2){
		if (a1.getName().equals(a2.getName())) {
			return match;
		} else {
			double compare = 0;
			Translator translator = new Translator();
			String act_1 = translator.getTranslateMap().get(a1.getHashMap().get("verb"));
			String act_2 = translator.getTranslateMap().get(a2.getHashMap().get("verb"));
			String obj1_1 = translator.getTranslateMap().get(a1.getHashMap().get("object1"));
			String obj1_2 = translator.getTranslateMap().get(a2.getHashMap().get("object1"));
			String prep_1 = translator.getTranslateMap().get(a1.getHashMap().get("preposition"));
			String prep_2 = translator.getTranslateMap().get(a2.getHashMap().get("preposition"));
			String obj2_1 = translator.getTranslateMap().get(a1.getHashMap().get("object2"));
			String obj2_2 = translator.getTranslateMap().get(a2.getHashMap().get("object2"));

			
			double sim_act = 0.5;
			
			if (!act_1.equals(act_2)) {
				if (act_1.isEmpty() || act_2.isEmpty()) {
					sim_act = 0;
				} else {
					sim_act = Math.pow(ontology.getWupSimilarity(act_1, act_2), exponent);
				}
			} else if (act_1.equals(act_2) && !act_1.isEmpty() && !act_2.isEmpty()) {
				sim_act = match;
			}
			
			double sim_obj1 = 0.5;
			if (!obj1_1.equals(obj1_2)) {
				if (obj1_1.isEmpty() || obj1_2.isEmpty()) {
					sim_obj1 = 0;
				} else {
					sim_obj1 = Math.pow(ontology.getWupSimilarity(obj1_1, obj1_2), exponent);
				}
			} else if (obj1_1.equals(obj1_2) && !obj1_1.isEmpty() && !obj1_2.isEmpty()) {
				sim_obj1 = match;
			}
			
			double sim_prep = 0.5;
			if (!prep_1.equals(prep_2)) {
				if (prep_1.isEmpty() || prep_2.isEmpty()) {
					sim_prep = 0;
				} else {
					sim_prep = Math.pow(ontology.getWupSimilarity(prep_1, prep_2), exponent);
				}
			} else if (prep_1.equals(prep_2) && !prep_1.isEmpty() && !prep_2.isEmpty()) {
				sim_prep = match;
			}
			
			double sim_obj2 = 0.5;
			if (!obj2_1.equals(obj2_2)) {
				if (obj2_1.isEmpty() || obj2_2.isEmpty()) {
					sim_obj2 = 0;
				} else {
					sim_obj2 = Math.pow(ontology.getWupSimilarity(obj2_1, obj2_2), exponent);
				}
			} else if (obj2_1.equals(obj2_2) && !obj2_1.isEmpty() && !obj2_2.isEmpty()) {
				sim_obj2 = match;
			}
			
			compare = weight_act * sim_act + weight_obj1 * sim_obj1 + weight_prep * sim_prep + weight_obj2 * sim_obj2;
			
			//standardize on [-1;1]
			compare = -1.0 + 2.0 * compare;
			return compare;
		}
	}
	
}
