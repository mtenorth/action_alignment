package sequenceElement;

import java.util.HashMap;

/**
 * 
 * @author Johannes Ziegltrum
 *
 */
public class ActionElement {

	private String name = "";
	//properties of the action e.g. manipulated objects
	private HashMap<String, String> hashMap = new HashMap<String, String>();
	
	/**
	 * 
	 * @param name
	 */
	public ActionElement(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return hashMap
	 */
	public HashMap<String, String> getHashMap(){
		return hashMap;
	}
	
	/**
	 * 
	 * @param newHashMap
	 */
	public void setHashMap(HashMap<String, String> newHashMap) {
		hashMap = new HashMap<String, String>(newHashMap);
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, String value){
		hashMap.put(key, value);
	}
	
}
