package fileReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import sequenceElement.ActionElement;

public class LabelsDATFileReader {
	
	public ArrayList<ActionElement> getSequence(String file){
		ArrayList<ActionElement> seq = new ArrayList<ActionElement>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String regex = "[ -]";
				String[] tokens = line.split(regex, -1);
				for (int i = 2; i < tokens.length; i++) {
					tokens[i] = tokens[i].replaceAll("_", "-");
				}
				//String name = tokens[2] + "-" + tokens[3] + "-" + tokens[4] + "-" + tokens[5];
				String name = tokens[2] + " " + tokens[3] + " " + tokens[4] + " " + tokens[5];
				/*Translater translater = new Translater();
				for (int i = 2; i < 6; i++) {
					String s = translater.getTranslateMap().get(tokens[i]);
					if (s != "" && s != null) {
						tokens[i] = s;
					}
				}*/
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
