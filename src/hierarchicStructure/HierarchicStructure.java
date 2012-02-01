package hierarchicStructure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class HierarchicStructure {

	private HashMap<String, String[]> hierarchyMap = new HashMap<String, String[]>();
	
	public HierarchicStructure(String file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String regex = ": ";
				String[] tokens = line.split(regex);
				regex = "-";
				String[] subEvents = tokens[1].split(regex);
				hierarchyMap.put(tokens[0], subEvents);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public HashMap<String, String[]> getHierarchyMap() {
		return hierarchyMap;
	}
	
}
