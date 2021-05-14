import java.util.ArrayList;

public class Ship {
	private String name;
	private int capacity;
	private int crewSize;
	private int durability;
	private int maxDurability;
	private double speed;
	private ArrayList<Item> cargo;
	private int availableCargoSpace;
	private boolean repairs;
	
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
		this.cargo = new ArrayList<Item>();
		this.availableCargoSpace = capacity;
		this.repairs = false;
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
	public int getCrewSize() {
		return this.crewSize;
	}
	
	/**
	 * Returns the current durability of the Ship.
	 * @return the current durability of the Ship.
	 */
	public double getDurability() {
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
	
	/**
	 * Calculates the total cost of the items in cargo
	 * @return total cost of items
	 */
	public int getCargoCost() {
		int total = 0;
		for (Item item: this.cargo) {
			total += item.getPrice();
		}
		return total;
	}
	
	public boolean addItemCargo(Item item, int amount) {
		if (availableCargoSpace >= item.getWeight() * amount) {
			for (int i = 0; i < amount; i++) {
				this.cargo.add(item);
				availableCargoSpace -= item.getWeight();
			}
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Removes all cargo from the ship
	 */
	public void emptyCargo() {
		this.cargo = new ArrayList<Item>();
		System.out.println("All of your cargo has been taken by enemy pirates");
	}
	
	/**
	 * Repairs the ship
	 */
	public void repairShip() {
		System.out.println("The ship has been repaired");
		this.durability = this.maxDurability;
		this.repairs = false;
	}
	
	/**
	 * Returns the status of whether or not the ship needs repairs
	 * @return True or False depending on whether or not the ship needs repairs
	 */
	public boolean getRepairs() {
		return repairs;
	}
	
	/**
	 * Called if the ship gets damaged
	 */
	public void needRepairs() {
		System.out.println("Your ship is damaged. You will need to fix it before you leave the next port");
		this.repairs = true;
	}
	
	/**
	 * Damages the ship based on the amount supplied by damageTaken
	 * @param damageTaken a random double that determines how much damage the ship takes
	 */
	public void takeDamage(int damageTaken) {
		System.out.println("Your ship has taken " + damageTaken + " points of damage.");
		this.durability -= damageTaken;
		this.needRepairs();
	}
}
