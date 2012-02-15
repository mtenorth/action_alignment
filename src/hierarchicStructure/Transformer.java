package hierarchicStructure;

import java.util.ArrayList;

import sequenceElement.ActionElement;

public class Transformer {

	HierarchicStructure hierarchy;
	
	public Transformer(HierarchicStructure hierarchy) {
		this.hierarchy = hierarchy;
	}
	
	public ArrayList<ActionElement> transform(ArrayList<ActionElement> seq) {
		ArrayList<ActionElement> newSeq = new ArrayList<ActionElement>();
		boolean change = false;
		for (ActionElement a : seq) {
			String name = a.getName();
			if (hierarchy.getHierarchyMap().containsKey(name)) {
				String[] subEvents = hierarchy.getHierarchyMap().get(name);
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
						if (hierarchy.getHierarchyMap().containsKey(element.getName())) {
							steps = getNumberOfSubEvents(element.getName()) - 1;
						}
					}
				} else {
					if (!element.getName().equals("$|$")) {
						element.setName("()");
						steps--;
					}
				}
				/*else {
					if (element.getName().equals("$|$")) {
						element.setName("");
					} else {
						element.setName("()");
						steps--;
					}
				}*/
			}
		}
		
		return alignments;
	}

	private int getNumberOfSubEvents(String name) {
		int number = 0;
		String[] subEvents = hierarchy.getHierarchyMap().get(name);
		for (String subEvent : subEvents) {
			if (hierarchy.getHierarchyMap().containsKey(subEvent)) {
				number += getNumberOfSubEvents(subEvent);
			} else {
				number++;
			}
		}
		return number;
	}
	
}
