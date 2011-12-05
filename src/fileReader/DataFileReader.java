package fileReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import sequenceElement.ActionElement;

public class DataFileReader {

	private ArrayList<ActionElement> seq = new ArrayList<ActionElement>();
	
	public ArrayList<ActionElement> getSequence(String file){
		seq.clear();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String regex = "[ -]";
				String[] tokens = line.split(regex, -1);
				String name = tokens[2] + "-" + tokens[3] + "-" + tokens[4] + "-" + tokens[5];
				ActionElement element = new ActionElement(name);
				element.put("verb", tokens[2]);
				element.put("object1", tokens[3]);
				element.put("preposition", tokens[4]);
				element.put("object2", tokens[5]);
				seq.add(element);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return seq;
	}
	
}
