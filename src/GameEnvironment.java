import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GameEnvironment {
	private ArrayList<Island> islands;
	private ArrayList<Ship> ships;
	private ArrayList<Item> items;
	
	private Ship activeShip;
	private Island activeIsland;
	private int totalMoney;
	
	private final int DAILYPAYPERHEAD = 2;
	
	static private Scanner consoleInput = new Scanner(System.in);
	
	public int getMoney() {
		return this.totalMoney;
	}
	
	public boolean addMoney(int change) {
		if (this.totalMoney + change < 0) {
			return false;
		} else {
			this.totalMoney = this.totalMoney + change;
			return true;
		}
	}
	
	public void initialise() {
		// Initialise Ships 
		this.ships.add(new Ship("Saint Mary", 30, 40, 90));
		this.ships.add(new Ship("Perla Betty III", 80, 60, 40));
		this.ships.add(new Ship("The Golden Hand", 60, 55, 60));
		this.ships.add(new Ship("Artemis", 40, 70, 30));
		
		// Initialise Items
//		this.items.add(new Item("Cask of Rum", 8, 300));
//		this.items.add(new Item("Barrel of Fish", 8, 600));
//		this.items.add(new Item("Logs of Wood", 14, 450));
//		this.items.add(new Item("Roll of Cloth", 4, 1500));
//		this.items.add(new Item("Copper Plates", 8, 300));
		this.items.add(new Item("Bushels of Bananas", 2, 1500));
		this.items.add(new Item("Iron Rods", 8, 750));
		this.items.add(new Item("Gold Bars", 14, 1200));
		this.items.add(new Item("Fine China", 2, 2000));
		this.items.add(new Item("Barrels of Gunpowder", 8, 750));
		
		// Initialise Islands
		HashMap<Item, Double> tempTrades = new HashMap<Item, Double>();
		ArrayList<Double> tempTradeMultiplier = new ArrayList<Double>();
		tempTradeMultiplier.add(0.0);
		tempTradeMultiplier.add(1.0);
		tempTradeMultiplier.add(0.0);
		tempTradeMultiplier.add(1.0);
		tempTradeMultiplier.add(1.0);
		for (int i = 0; i < items.size(); i++) {
			tempTrades.put(items.get(i), tempTradeMultiplier.get(i));
		}
		this.islands.add(new Island("Mahkarn", tempTrades));
		
		tempTrades = new HashMap<Item, Double>();
		tempTradeMultiplier.clear();
		tempTradeMultiplier.add(0.0);
		tempTradeMultiplier.add(1.0);
		tempTradeMultiplier.add(0.0);
		tempTradeMultiplier.add(1.0);
		tempTradeMultiplier.add(1.0);
		for (int i = 0; i < items.size(); i++) {
			tempTrades.put(items.get(i), tempTradeMultiplier.get(i));
		}
		this.islands.add(new Island("Tolset Reef", tempTrades));
		
		tempTrades = new HashMap<Item, Double>();
		tempTradeMultiplier.clear();
		tempTradeMultiplier.add(0.0);
		tempTradeMultiplier.add(1.0);
		tempTradeMultiplier.add(0.0);
		tempTradeMultiplier.add(1.0);
		tempTradeMultiplier.add(1.0);
		for (int i = 0; i < items.size(); i++) {
			tempTrades.put(items.get(i), tempTradeMultiplier.get(i));
		}
		this.islands.add(new Island("Alegate", tempTrades));
		
		tempTrades = new HashMap<Item, Double>();
		tempTradeMultiplier.clear();
		tempTradeMultiplier.add(0.0);
		tempTradeMultiplier.add(1.0);
		tempTradeMultiplier.add(0.0);
		tempTradeMultiplier.add(1.0);
		tempTradeMultiplier.add(1.0);
		for (int i = 0; i < items.size(); i++) {
			tempTrades.put(items.get(i), tempTradeMultiplier.get(i));
		}
		this.islands.add(new Island("Pardea", tempTrades));
		
		tempTrades = new HashMap<Item, Double>();
		tempTradeMultiplier.clear();
		tempTradeMultiplier.add(0.0);
		tempTradeMultiplier.add(1.0);
		tempTradeMultiplier.add(0.0);
		tempTradeMultiplier.add(1.0);
		tempTradeMultiplier.add(1.0);
		for (int i = 0; i < items.size(); i++) {
			tempTrades.put(items.get(i), tempTradeMultiplier.get(i));
		}
		this.islands.add(new Island("Erbest", tempTrades));
		
		// Initialise Routes
		Route tempRoute = new Route(3, 0.2, islands.get(1));
		islands.get(1).addRoute(tempRoute);
	}
	
	/**
	 * A console based user decision making system.
	 * Displays the given query and choices that the user can make.
	 * Returns the index of the user's choice in the choices array.
	 * @param stringQuery	the string query that will be displayed to the user
	 * @param choices		the string choices that the user can choose
	 * @return the index of the user's choice in the choices array.
	 */
	static public int getPlayerDecision(String stringQuery, ArrayList<String> choices) {
		int userChoice = 0;
		// repeat until a recognised choice is made
		while (userChoice < 1 || userChoice > choices.size()) {
			try {
				
				// prints the query and possible choices to the console
				System.out.println("######################################");
				System.out.println(stringQuery);
				for (int i = 0; i < choices.size(); i++) {
					System.out.println(Integer.toString(i + 1) + ". " + choices.get(i));
				}
				System.out.println("\nEnter a number from the range 1-" + Integer.toString(choices.size()) + " to select the corresponding option: \n");
				
				// gets the user's input
				userChoice = Integer.parseInt(consoleInput.nextLine());
				
				// checks if the input number is in the range of choices
				if (userChoice < 1 || userChoice > choices.size()) {
					throw new Exception("Please enter a number between 1 and " + Integer.toString(choices.size()));
				}
				
			} catch (IllegalArgumentException e) {
				System.out.println("Please enter a NUMBER between 1 and " + Integer.toString(choices.size()));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return userChoice - 1;
	}
	
	public void consoleSail() {
		ArrayList<String> routeChoices = new ArrayList<String>();
		for (Route route: activeIsland.getRoutes()) {
			routeChoices.add(""); // not finished
		}
	}
	
	public void sail(Route route) {
		if (RandomEvent.tryEvent(activeShip, route.getEventChance())) {
			activeIsland = route.getDestinationIsland();
			 if (!addMoney(DAILYPAYPERHEAD * activeShip.getCrewSize())) {
				 //not finished
			 }
		} else {
			gameOver();
		}
	}
	
	public void trade() {
		
	}
	
	public void initialisePlayerValues() {
		
	}
	
	public static void gameOver() {
		
	}
	
	public static void main(String[] args) {
		
		// Example of getPlayerDecision
//		ArrayList<String> choices = new ArrayList<String>();
//		String query = "Choose a letter:";
//		choices.add("a");
//		choices.add("b");
//		choices.add("c");
//		choices.add("d");
//		choices.add("e");
//		String choice = choices.get(getPlayerDecision(query, choices));
//		System.out.println(choice);
		
		
	}
}
