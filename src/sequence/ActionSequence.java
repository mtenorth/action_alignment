package sequence;

import java.util.ArrayList;

import sequenceElement.ActionElement;

/**
 * @author Johannes Ziegltrum
 *
 */
public class ActionSequence {
	
	private String identifier = "";
	private ArrayList<ActionElement> sequence = new ArrayList<ActionElement>();
	
	/**
	 * @param identifier
	 * @param sequence
	 */
	public ActionSequence(String identifier, ArrayList<ActionElement> sequence){
		this.identifier = identifier;
		this.sequence = sequence;
	}
	
	/**
	 * @return identifier
	 */
	public String getIdentifier(){
		return identifier;
	}
	
	/**
	 * @return sequence
	 */
	public ArrayList<ActionElement> getSequence(){
		return sequence;
	}
	
}
