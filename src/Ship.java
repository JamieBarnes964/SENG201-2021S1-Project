import java.util.ArrayList;

public class Ship {
	private String name;
	private int capacity;
	private int crewSize;
	private int durability;
	private double speed;
	private ArrayList<Item> cargo;
	
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
		this.speed = 1 + ((crewSize - capacity) / 50);
		this.cargo = new ArrayList<Item>();
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
	
	/**
	 * Returns the size of the Ship.
	 * @return the size of the Ship.
	 */
	public int getcrewSize() {
		return this.crewSize;
	}
	
	/**
	 * Returns the current durability of the Ship.
	 * @return the current durability of the Ship.
	 */
	public int getDurability() {
		return this.durability;
	}
	
	/**
	 * Returns the speed of the Ship.
	 * @return the current speed of the Ship.
	 */
	public double getSpeed() {
		return this.speed;
	}
	
	/**
	 * Returns a list of the items stored in the cargo.
	 * @return a list of the items stored in the cargo.
	 */
	public ArrayList<Item> getCargo() {

		return this.cargo;
	}
	
}
