import java.util.ArrayList;

public class Ship {
	private String name;
	private int capacity;
	private int crewSize;
	private int durability;
	private double speed;
	private ArrayList<Item> cargo;
	
	public Ship(String name, int capacity, int crewSize, int durability) {
		this.name = name;
		this.capacity = capacity;
		this.crewSize = crewSize;
		this.durability = durability;
		this.speed = 1 + ((crewSize - capacity) / 50);
		this.cargo = new ArrayList<Item>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getCapacity() {
		return this.capacity;
	}
	
	public int getcrewSize() {
		return this.crewSize;
	}
	
	public int getDurability() {
		return this.durability;
	}
	
	public double getSpeed() {
		return this.speed;
	}
	
	public ArrayList<Item> getCargo() {
		return this.cargo;
	}
	
}
