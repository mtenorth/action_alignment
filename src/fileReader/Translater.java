package fileReader;

import java.util.HashMap;

public class Translater {

	private HashMap<String, String> translateMap = new HashMap<String, String>();
	
	public Translater() {
		
		translateMap.put("beat", "Stirring-Beating");
		translateMap.put("close", "ClosingSomething");
		translateMap.put("crack", "Cracking");
		translateMap.put("open", "OpeningSomething");
		translateMap.put("pour", "Pouring-MakingSomethingAvailable");
		translateMap.put("put", "PuttingSomethingSomewhere");
		translateMap.put("stir", "Stirring");
		translateMap.put("take", "TakingSomething");
		translateMap.put("walk", "Walking-Generic");
		translateMap.put("switch_on", "");
		
		translateMap.put("counter", "CounterTop");
		translateMap.put("cupboard_bottom_left", "Cupboard");
		translateMap.put("cupboard_bottom_right", "Cupboard");
		translateMap.put("cupboard_top_left", "Cupboard");
		translateMap.put("cupboard_top_right", "Cupboard");
		translateMap.put("drawer", "Drawer");
		translateMap.put("egg", "Egg-Chickens");
		translateMap.put("egg_shell", "Eggshell");
		translateMap.put("fork", "Fork-SilverwarePiece");
		translateMap.put("fridge", "Refrigerator");
		translateMap.put("oil", "VegetableOil");
		translateMap.put("sink", "Sink");
		translateMap.put("small_bowl", "Bowl-Mixing");
		translateMap.put("stove", "StoveTop");
		translateMap.put("frying_pan", "");
		translateMap.put("pepper", "");
		translateMap.put("salt", "");
		
		translateMap.put("from", "fromLocation");
		translateMap.put("on", "on-Physical");
		translateMap.put("to", "toLocation");
		translateMap.put("into", "");
		translateMap.put("with", "");
		
	}
	
	public HashMap<String, String> getTranslateMap() {
		return translateMap;
	}
	
}
