package main;

public class Item {
	private String name;
	private int weight;
	private int price;
	private int id;
	
	private static int nextID = 0;
	
	
	/**
	 * Constructs an Item with the given Name and Weight.
	 * @param name the name of the Item
	 * @param weight the weight of the Item
	 * @param price the default price of the Item
	 */
	public Item(String name, int weight, int price) {
		this.name = name;
		this.weight = weight;
		this.price = price;
		this.id = nextID;
		nextID += 1;
	}
	
	/**
	 * Returns the id of the Item
	 * @return the id of the Item
	 */
	public int getID() {
		return this.id;
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
	
	/**
	 * Returns the default price of the Item
	 * @return the default price of the Item
	 */
	public int getPrice() {
		return this.price;
	}
}