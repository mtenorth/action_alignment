package sequenceElement;

import java.util.HashMap;

public class ActionElement {

	private String name = "";
	private HashMap<String, String> hashMap = new HashMap<String, String>();
	
	public ActionElement(String name) {
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

	public HashMap<String, String> getHashMap(){
		return hashMap;
	}
	
	public void put(String key, String value){
		hashMap.put(key, value);
	}
	
}
