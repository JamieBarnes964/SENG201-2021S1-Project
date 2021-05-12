
public class Item {
	private String name;
	private int weight;
	private int price;
	
	
	/**
	 * Constructs an Item with the given Name and Weight.
	 * @param name the name of the Item
	 * @param weight the weight of the Item
	 */
	public Item(String name, int weight, int price) {
		this.name = name;
		this.weight = weight;
		this.price = price;
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
	
	public int getPrice() {
		return this.price;
	}
}
