package fileReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import sequenceElement.ActionElement;

public class LabelsCSVFileReader {
	
	private ArrayList<ActionElement> seq = new ArrayList<ActionElement>();
	
	public ArrayList<ActionElement> getSequence(String file){
		seq.clear();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				ActionElement element = new ActionElement(line);
				seq.add(element);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return seq;
	}
	
}
