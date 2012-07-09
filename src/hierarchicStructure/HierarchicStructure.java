package hierarchicStructure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Johannes Ziegltrum
 *
 */
public class HierarchicStructure {

	private HashMap<String, String[]> subEventsMap = new HashMap<String, String[]>();
	private HashMap<String, String> superEventMap = new HashMap<String, String>();
	
	/**
	 * @param file path to the file that contains the information for the hierarchy
	 */
	public HierarchicStructure(String file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String regex = ": ";
				String[] tokens = line.split(regex);
				regex = "-";
				String[] subEvents = tokens[1].split(regex);
				subEventsMap.put(tokens[0], subEvents);
				for (String subEvent : subEvents) {
					superEventMap.put(subEvent, tokens[0]);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return subEventsMap
	 */
	public HashMap<String, String[]> getSubEventsMap() {
		return subEventsMap;
	}
	
	/**
	 * @return superEventMap
	 */
	public HashMap<String, String> getSuperEventsMap() {
		return superEventMap;
	}
	
}
