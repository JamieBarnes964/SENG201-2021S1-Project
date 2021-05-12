import java.util.ArrayList;
import java.util.Scanner;

public class GameEnvironment {
	private ArrayList<Island> islands;
	private ArrayList<Ship> ships;
	private ArrayList<Item> items;
	
	private Ship activeShip;
	private Island activeIsland;
	
	public void initialise() {
		// Initialise Ships 
		this.ships.add(new Ship("Saint Mary", 30, 40, 90));
		this.ships.add(new Ship("Perla Betty III", 80, 60, 40));
		this.ships.add(new Ship("The Golden Hand", 60, 55, 60));
		this.ships.add(new Ship("Artemis", 40, 70, 30));
		
		// Initialise Items
		this.items.add(new Item("Cask of Rum", 8, 300));
		this.items.add(new Item("Barrel of Fish", 8, 600));
		this.items.add(new Item("Logs of Wood", 14, 450));
		this.items.add(new Item("Roll of Cloth", 4, 1500));
		this.items.add(new Item("Bushels of Bananas", 2, 1500));
		this.items.add(new Item("Iron Rods", 8, 750));
		this.items.add(new Item("Gold Bars", 14, 1200));
		this.items.add(new Item("Copper Plates", 8, 300));
		this.items.add(new Item("Fine China", 2, 2000));
		this.items.add(new Item("Barrels of Gunpowder", 8, 750));
		
		// Initialise Islands
		this.islands.add(new Island("Mahkarn", ));
		this.islands.add(new Island("Tolset Reef", ));
		this.islands.add(new Island("Alegate", ));
		this.islands.add(new Island("Pardea", ));
		this.islands.add(new Island("Erbest", ));
	}
	
	public void sail() {
		
	}
	
	public void initialisePlayerValues() {
		
	}
	
	public static void gameOver() {
		
	}
	
	public static void main(String[] args) {
		
	}
}
