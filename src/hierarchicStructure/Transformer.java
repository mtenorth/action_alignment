package hierarchicStructure;

import java.util.ArrayList;

import sequence.ActionSequence;
import sequenceElement.ActionElement;

/**
 * @author Johannes Ziegltrum
 *
 */
public class Transformer {

	HierarchicStructure hierarchy;
	
	/**
	 * @param hierarchy HierarchicStructure
	 */
	public Transformer(HierarchicStructure hierarchy) {
		this.hierarchy = hierarchy;
	}
	
	/**
	 * @param seq the sequence to be transformed
	 * @return the sequence which has been transformed to the lowest hierarchy level
	 */
	public ArrayList<ActionElement> transform(ArrayList<ActionElement> seq) {
		ArrayList<ActionElement> newSeq = new ArrayList<ActionElement>();
		boolean change = false;
		for (ActionElement a : seq) {
			String name = a.getName();
			if (hierarchy.getSubEventsMap().containsKey(name)) {
				String[] subEvents = hierarchy.getSubEventsMap().get(name);
				for (int i = 0; i < subEvents.length; i++) {
					String newName = subEvents[i];
					ActionElement newElement = new ActionElement(newName);
					newElement.setHashMap(a.getHashMap());
					newElement.put(newName, name);
					newSeq.add(newElement);
					change = true;
				}
			} else {
				newSeq.add(a);
			}
		}
		if (change) {
			return transform(newSeq);
		} else {
			return newSeq;
		}
	}
	
	/**
	 * @param alignments array that contains the aligned low-level-sequences
	 * @return array that contains the aligned sequences in their original form
	 */
	public ActionElement[][] retransform(ActionElement[][] alignments) {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < alignments[i].length; j++) {
				ActionElement element = alignments[i][j];
				if (element != null) {
					while (element.getHashMap().containsKey(element.getName())) {
						String name = element.getName();
						String newName = element.getHashMap().get(name);
						element.setName(newName);
						element.getHashMap().remove(name);
					}
				}
			}
		}
		
		int steps = 0;
		for (int i = 0; i < 2; i++) {
			for (int j = alignments[i].length - 1; j >= 0; j--) {
				ActionElement element = alignments[i][j];
				if (steps == 0) {
					if (element != null) {
						if (hierarchy.getSubEventsMap().containsKey(element.getName())) {
							steps = getNumberOfSubEvents(element.getName()) - 1;
						}
					}
				} else {
					if (!element.getName().equals("|")) {
						element.setName("()");
						steps--;
					}
				}
			}
		}
		
		return alignments;
	}
	
	/**
	 * @param seq the sequence to be abstracted to the highest level
	 * @return the abstract sequence
	 */
	public ArrayList<ActionElement> toplevel(ArrayList<ActionElement> seq) {
		boolean change = false;
		ArrayList<ActionElement> newSeq = new ArrayList<ActionElement>();
		for (int i = 0; i < seq.size(); i++) {
			if (hierarchy.getSuperEventsMap().containsKey(seq.get(i).getName())) {
				String subEventName = seq.get(i).getName();
				String superEventName = hierarchy.getSuperEventsMap().get(subEventName);
				String[] subEvents = hierarchy.getSubEventsMap().get(superEventName);
				if (subEventName.equals(subEvents[0])) {
					boolean equal = true;
					if (seq.size() < i + (subEvents.length)) {
						equal = false;
					} else {
						for (int j = 0; j < subEvents.length; j++) {
							if (!subEvents[j].equals(seq.get(i + j).getName())) {
								equal = false;
							}
						}
					}
					if (equal) {
						change = true;
						ActionElement newElement = new ActionElement(superEventName);
						newSeq.add(newElement);
						i += subEvents.length - 1;
					} else {
						newSeq.add(seq.get(i));
					}
				} else {
					newSeq.add(seq.get(i));
				}
			} else {
				newSeq.add(seq.get(i));
			}
		}
		if (change) {
			return this.toplevel(newSeq);
		} else {
			return newSeq;
		}
	}

	private int getNumberOfSubEvents(String name) {
		int number = 0;
		String[] subEvents = hierarchy.getSubEventsMap().get(name);
		for (String subEvent : subEvents) {
			if (hierarchy.getSubEventsMap().containsKey(subEvent)) {
				number += getNumberOfSubEvents(subEvent);
			} else {
				number++;
			}
		}
		return number;
	}
	
}
