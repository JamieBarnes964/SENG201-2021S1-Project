
public class Item {
	private String name;
	private int weight;
	
	
	/**
	 * Constructs an Item with the given Name and Weight.
	 * @param name the name of the Item
	 * @param weight the weight of the Item
	 */
	public Item(String name, int weight) {
		this.name = name;
		this.weight = weight;
	}
	
	/**
	 * Returns the name of the Item
	 * @return the name of the Item
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the weight of the Item
	 * @return the weight of the Item
	 */
	public int getWeight() {
		return this.weight;
	}
}
