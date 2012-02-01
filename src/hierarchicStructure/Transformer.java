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
	
}
