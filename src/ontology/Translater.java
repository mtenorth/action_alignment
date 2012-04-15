package ontology;

import java.util.HashMap;

/**
 * @author Johannes Ziegltrum
 *
 */
public class Translater {

	private HashMap<String, String> translateMap = new HashMap<String, String>();
	
	/**
	 * transforms the label into the corresponding entities of the ontology
	 */
	public Translater() {
		
		translateMap.put("none", "Nothing");
		
		translateMap.put("take", "TakingSomething");
		translateMap.put("put", "PuttingSomethingSomewhere");
		translateMap.put("open", "OpeningSomething");
		translateMap.put("fill", "FillingProcess");
		translateMap.put("crack", "Cracking");
		translateMap.put("beat", "Stirring-Beating");
		translateMap.put("stir", "Stirring");
		translateMap.put("pour", "Pouring-PourFromOneContainerIntoAnother");
		translateMap.put("clean", "Cleaning");
		translateMap.put("switch_on", "TurningOnPoweredDevice");
		translateMap.put("switch_off", "TurningOffPoweredDevice");
		translateMap.put("read", "Reading");
		translateMap.put("spray", "Spraying");
		translateMap.put("close", "ClosingSomething");
		translateMap.put("walk", "Walking-Generic");
		translateMap.put("twist_on", "ClosingABottle");
		translateMap.put("twist_off", "OpeningABottle");
		
		translateMap.put("brownie_box", "Box-Container");
		translateMap.put("brownie_bag", "Bag");
		translateMap.put("egg_box", "Box-Container");
		translateMap.put("egg", "Egg-Chickens");
		translateMap.put("egg_shell", "Eggshell");
		translateMap.put("salt", "TableSalt");
		translateMap.put("pepper", "Pepper-TheSpice");
		translateMap.put("water", "Water");
		translateMap.put("oil", "VegetableOil");
		translateMap.put("pam", "VegetableOil");
		translateMap.put("cap", "ContainerLid");
		translateMap.put("knife", "Knife");
		translateMap.put("fork", "Fork-SilverwarePiece");
		translateMap.put("fork2", "Fork-SilverwarePiece");
		translateMap.put("spoon", "Spoon");
		translateMap.put("scissors", "Scissors");
		translateMap.put("cupboard_top_right", "Cupboard");
		translateMap.put("cupboard_top_left", "Cupboard");
		translateMap.put("cupboard_bottom_right", "Cupboard");
		translateMap.put("cupboard_bottom_left", "Cupboard");
		translateMap.put("drawer", "Drawer");
		translateMap.put("fridge", "Refrigerator");
		translateMap.put("stove", "StoveTop");
		translateMap.put("oven", "Oven");
		translateMap.put("counter", "CounterTop");
		translateMap.put("sink", "Sink");
		translateMap.put("baking_pan", "CakePan");
		translateMap.put("frying_pan", "Skillet");
		translateMap.put("measuring_cup_big", "MeasuringCup-Big");
		translateMap.put("measuring_cup_small", "MeasuringCup-Small");
		translateMap.put("big_bowl", "Bowl-Mixing");
		translateMap.put("small_bowl", "Bowl-Mixing");
		translateMap.put("paper_towel", "PaperTowel");
		translateMap.put("spatula", "Spatula");
		translateMap.put("plate", "DinnerPlate");
		translateMap.put("whisk", "WireWhisk");
		
		translateMap.put("into", "toLocation");
		translateMap.put("from", "fromLocation");
		translateMap.put("with", "deviceUsed");
		translateMap.put("on", "on-Physical");
		translateMap.put("to", "toLocation");
		
	}
	
	/**
	 * @return translateMap
	 */
	public HashMap<String, String> getTranslateMap() {
		return translateMap;
	}
	
}
