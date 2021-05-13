import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class GameEnvironment {
	private final static int STARTINGMONEY = 100000;
	private final static int DAILYPAYPERHEAD = 2;
	
	private static ArrayList<Island> islands;
	private static ArrayList<Ship> ships;
	private static ArrayList<Item> items;
	
	private static int gameDays;
	private static String playerName;
	
	private static Ship activeShip;
	private static Island activeIsland;
	private static int totalMoney = STARTINGMONEY;
	
	static private Scanner consoleInput = new Scanner(System.in);
	
	public static int getMoney() {
		return totalMoney;
	}
	
	public static boolean addMoney(int change) {
		if (totalMoney + change < 0) {
			return false;
		} else {
			totalMoney = totalMoney + change;
			return true;
		}
	}
	
	public static void initialise() {
		islands = new ArrayList<Island>();
		ships = new ArrayList<Ship>();
		items = new ArrayList<Item>();
		
		// Initialise Ships 
		ships.add(new Ship("Saint Mary", 30, 40, 90));
		ships.add(new Ship("Perla Betty III", 80, 60, 40));
		ships.add(new Ship("The Golden Hand", 60, 55, 60));
		ships.add(new Ship("Artemis", 40, 70, 30));
		
		// Initialise Items
//		items.add(new Item("Cask of Rum", 8, 300));
//		items.add(new Item("Barrel of Fish", 8, 600));
//		items.add(new Item("Logs of Wood", 14, 450));
//		items.add(new Item("Roll of Cloth", 4, 1500));
//		items.add(new Item("Copper Plates", 8, 300));
		items.add(new Item("Bushels of Bananas", 2, 1500));
		items.add(new Item("Iron Rods", 8, 750));
		items.add(new Item("Gold Bars", 14, 1200));
		items.add(new Item("Fine China", 2, 2000));
		items.add(new Item("Barrels of Gunpowder", 8, 750));
		
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
		islands.add(new Island("Mahkarn", tempTrades));
		
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
		islands.add(new Island("Tolset Reef", tempTrades));
		
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
		islands.add(new Island("Alegate", tempTrades));
		
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
		islands.add(new Island("Pardea", tempTrades));
		
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
		islands.add(new Island("Erbest", tempTrades));
		
		// Initialise Mahkarn Routes
		Route mahkarnToPardea = new Route(2, 0.2, islands.get(3));
		Route mahkarnToAlegate = new Route(3, 0.4, islands.get(2));
		Route mahkarnToErberst = new Route(4, 0.3, islands.get(4));
		islands.get(0).addRoute(mahkarnToPardea);
		islands.get(0).addRoute(mahkarnToAlegate);
		islands.get(0).addRoute(mahkarnToErberst);
		
		// Initialise Tolset Reef Routes
		Route tolsetToPardea = new Route(8, 0.2, islands.get(3));
		islands.get(1).addRoute(tolsetToPardea);
		
		// Initialise Alegate Routes
		Route alegateToPardea = new Route(2, 0.3, islands.get(3));
		Route alegateToMahkarn = new Route(3, 0.4, islands.get(0));
		Route alegateToErberst = new Route(3, 0.2, islands.get(4));
		islands.get(2).addRoute(alegateToPardea);
		islands.get(2).addRoute(alegateToMahkarn);
		islands.get(2).addRoute(alegateToErberst);
		
		// Initialise Pardea Routes
		Route pardeaToAlegate = new Route(2, 0.3, islands.get(2));
		Route pardeaToTolset = new Route(8, 0.2, islands.get(1));
		Route pardeaToMahkarn = new Route(2, 0.2, islands.get(0));
		islands.get(3).addRoute(pardeaToAlegate);
		islands.get(3).addRoute(pardeaToTolset);
		islands.get(3).addRoute(pardeaToMahkarn);
		
		// Initialise Erberst Routes
		Route erberstToAlegate = new Route(3, 0.2, islands.get(2));
		Route erberstToMahkarn = new Route(3, 0.3, islands.get(0));
		islands.get(4).addRoute(erberstToAlegate);
		islands.get(4).addRoute(erberstToMahkarn);
		
		activeIsland = islands.get(0);
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
	
	
	/**
	 * 
	 */
	public static void consoleSail() {
		ArrayList<String> routeChoices = new ArrayList<String>();
		for (Route route: activeIsland.getRoutes()) {
			routeChoices.add(route.getDestinationIsland().getName() + " in " + route.getDays() + " days (" + route.getEventChance() + " Risk)");
		}
		String query = "Select the route you would like to take:";
		Route chosenRoute = activeIsland.getRoutes().get(getPlayerDecision(query, routeChoices));
		System.out.println("You have selected the route to " + chosenRoute.getDestinationIsland().getName() + "!");
		try {
			sail(chosenRoute);
			System.out.println("You have made it to " + activeIsland.getName() + "!");
		} catch (IllegalArgumentException e) {
			System.out.println("You cannot pay your crew for the journey. Choose another route, or sell some cargo to afford the journey.");
		}
	}
	
	
	/**
	 * 
	 * @param route
	 * @throws IllegalArgumentException
	 */
	public static void sail(Route route) throws IllegalArgumentException {
		if (RandomEvent.tryEvent(activeShip, route.getEventChance())) {
			
			 if (addMoney(-DAILYPAYPERHEAD * activeShip.getCrewSize())) {
				 activeIsland = route.getDestinationIsland();
			 } else {
				 throw new IllegalArgumentException("Not enough money to pay crew for the trip!");
			 }
		} else {
			gameOver();
		}
	}
	
	
	/**
	 * 
	 */
	public void trade() {
		
	}
	
	
	/**
	 * 
	 */
	public static void initialisePlayerValues() {
		boolean nameDecided = false;
		boolean daysDecided = false;
		boolean shipDecided = false;
		
		while (nameDecided == false) {
			try {
				System.out.println("######################################");
				System.out.println("Please enter your name: ");
				// gets the user's input
				playerName = (consoleInput.nextLine());
				
				boolean isAlphaNumeric = playerName.matches("^([a-zA-Z]| )*$");
				nameDecided = true;
				// checks if the input string is in the range of choices
				if (playerName.length() < 3|| playerName.length() > 15) {
					nameDecided = false;
					throw new Exception("Please enter a name that is between 3 and 15 characters.");
				}
				if (isAlphaNumeric == false) {
					nameDecided = false;
					throw new Exception("please enter a name that does not contain non alphanumeric characters.");
				}
			}  catch (Exception e) {
				nameDecided = false;
				System.out.println(e.getMessage());
			}
		}
		
		while (daysDecided == false) {
			try {
				// prints the query and possible choices to the console
				System.out.println("######################################");
				System.out.println("Please choose the number of days that you wish the game to take place over:");
				// gets the user's input
				gameDays = Integer.parseInt(consoleInput.nextLine());
				daysDecided = true;
				
				// checks if the input number is in the range of choices
				if (gameDays < 20 || gameDays > 50) {
					daysDecided = false;
					throw new Exception("Please enter a number between 20 and 50.");
				}
			} catch (IllegalArgumentException e) {
				daysDecided = false;
				System.out.println("Please enter a NUMBER between 20 and 50.");
			} catch (Exception e) {
				daysDecided = false;
				System.out.println(e.getMessage());
			}
		}
		ArrayList<String> choices = new ArrayList<String>();
		String query = "Choose a Ship:";
		for (Ship ship: ships) {
			choices.add("Ship Name: " + ship.getName() + 
					"\n   Durability: " + ship.getDurability() + 
					"\n   Cargo Capacity: " + ship.getCapacity() + 
					"\n   Crew Size: " + ship.getCrewSize() + 
					"\n   Speed: " + ship.getSpeed() + "\n");
		}
		activeShip = ships.get(getPlayerDecision(query, choices));
		System.out.println("You have selected " + activeShip.getName() + "!");
	}
	
	public static void gameOver() {
		
	}
	
	
	public static void mainConsoleGameplayLoop() {
		
	}
	
	
	public static void main(String[] args) {
		initialise();
		initialisePlayerValues();
		consoleSail();
		System.out.println(totalMoney);
		
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
