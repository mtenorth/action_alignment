package sequence;

import java.util.ArrayList;

import sequenceElement.ActionElement;

public class ActionSequence {

	private String identifier = "";
	private ArrayList<ActionElement> sequence = new ArrayList<ActionElement>();
	
	public ActionSequence(String identifier, ArrayList<ActionElement> sequence){
		this.identifier = identifier;
		this.sequence = sequence;
	}
	
	public String getIdentifier(){
		return identifier;
	}
	
	public ArrayList<ActionElement> getSequence(){
		return sequence;
	}
	
}
