package main;
import java.util.HashMap;

public class Ship {
	private String name;
	private int capacity;
	private int crewSize;
	private int durability;
	private int maxDurability;
	private double speed;
	private HashMap<Item, Integer> cargo;
	private int availableCargoSpace;
	
	/**
	 * Creates the Ship object with the given variables.
	 * @param name			The name of the Ship.
	 * @param capacity		The capacity of the Ship.
	 * @param crewSize		The size of the crew on the Ship.
	 * @param durability	The durability of the Ship.
	 * @param speed			The speed of the Ship.
	 * @param cargo			The cargo on the Ship.
	 */
	public Ship(String name, int capacity, int crewSize, int durability) {
		this.name = name;
		this.capacity = capacity;
		this.crewSize = crewSize;
		this.durability = durability;
		this.maxDurability = durability;
		this.speed = 1.0 + ((crewSize - capacity) / 50.0);
		this.cargo = new HashMap<Item, Integer>();
		this.availableCargoSpace = capacity;
	}
	
	/**
	 * Returns the name of the Ship.
	 * @return the name of the Ship.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the capacity of the Ship.
	 * @return the capacity of the Ship.
	 */
	public int getCapacity() {
		return this.capacity;
	}
	
	public int getAvailableCargoSpace() {
		return this.availableCargoSpace;
	}
	
	/**
	 * Returns the size of the Ship.
	 * @return the size of the Ship.
	 */
	public int getCrewSize() {
		return this.crewSize;
	}
	
	/**
	 * Returns the current durability of the Ship.
	 * @return the current durability of the Ship.
	 */
	public int getDurability() {
		return this.durability;
	}
	
	public int getMaxDurability() {
		return maxDurability;
	}

	/**
	 * Returns the speed of the Ship.
	 * @return the current speed of the Ship.
	 */
	public double getSpeed() {
		return this.speed;
	}
	
	public void initialiseCargo(Item item) {
		this.cargo.put(item, 0);
	}
	
	/**
	 * Returns a list of the items stored in the cargo.
	 * @return a list of the items stored in the cargo.
	 */
	public HashMap<Item, Integer> getCargo() {
		return this.cargo;
	}
	
	/**
	 * Calculates the total value of the items in cargo.
	 * @return total value of items
	 */
	public int getCargoValue() {
		int total = 0;
		for (Item item: this.cargo.keySet()) {
			total += item.getPrice() * this.cargo.get(item);
		}
		return total;
	}
	
	/**
	 * 
	 * @param item
	 * @param amount
	 * @return
	 */
	public boolean addItemCargo(Item item, int amount) {
		if (availableCargoSpace >= item.getWeight() * amount) {
			this.cargo.put(item, this.cargo.get(item) + amount);
			availableCargoSpace -= item.getWeight() * amount;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Removes all cargo from the ship.
	 */
	public void emptyCargo() {
		for (Item item: this.cargo.keySet()) {
			this.cargo.put(item, 0);
		}
		this.availableCargoSpace = 0;
	}

	/**
	 * Returns the status of whether or not the ship needs repairs.
	 * @return True or False depending on whether or not the ship needs repairs
	 */
	public boolean getNeedRepairs() {
		return maxDurability > durability;
	}
	
	/**
	 * Repairs the ship
	 */
	public void repairShip() {
		this.durability = this.maxDurability;
	}
	
	/**
	 * Damages the ship based on the amount supplied by the integer damageTaken.
	 * @param damageTaken an integer that determines how much damage the ship takes
	 */
	public void takeDamage(int damageTaken) {
		this.durability -= damageTaken;
	}
}
