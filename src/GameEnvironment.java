import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
	private static int playerMoney = STARTINGMONEY;
	
	private static boolean gameOver = false;
	
	// Statistics
	private static int statSailed = 0;	// Stores the number of times the player sailed between islands
	private static int statTraded = 0;	// Stores the number of items the player traded
	
	private static  Scanner consoleInput = new Scanner(System.in);
	
	public static int getMoney() {
		return playerMoney;
	}
	
	/**
	 * Adds the given integer to the playerMoney. Negative integers are allowed to subtract.
	 * @param change the integer amount to change playerMoney by. Negative integers are allowed.
	 * @return true if the combination of playerMoney and the given integer change is > 0, false otherwise
	 */
	public static boolean addMoney(int change) {
		if (playerMoney + change < 0) {
			return false;
		} else {
			playerMoney = playerMoney + change;
			return true;
		}
	}
	
	
	/**
	 * Need to implement variable prices
	 */
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
	 * A console based user number input system.
	 * Displays the given query and valid range that the user can input.
	 * Returns the user's input as an integer.
	 * @param prompt	the string query that will be displayed to the user
	 * @param min		the minimum number that the user can input (inclusive)
	 * @param max		the maximum number that the user can input (inclusive)
	 * @return the user's input as an integer.
	 */
	static public int getUserIntInRange(String prompt, int min, int max) {
		boolean acceptedInput = false;
		int userInput = 0;
		while (!acceptedInput) {
			System.out.println(prompt); // Prints prompt to console
			System.out.println("Please enter a number between " + min + " and " + max); 
			try {
				userInput = Integer.parseInt(consoleInput.nextLine()); // Gets the user's input and attempts to convert it to an Integer
				// Checks if the input number is within the range
				if (userInput < min || userInput > max) {
					System.out.println("That is not a number between " + min + " and " + max); // Informs the user that the input is outside the range
				} else {
					acceptedInput = true; // Breaks the loop
				}
			} catch (IllegalArgumentException e) { // Catches an error thrown from converting the input to an integer
				System.out.println("That is not a number. Please enter a NUMBER between " + min + " and " + max);
			}
		}
		return userInput;
	}
	
	/**
	 * A console based user decision making system.
	 * Displays the given query and choices that the user can make.
	 * Returns the index of the user's choice in the choices array.
	 * @param prompt		the string query that will be displayed to the user
	 * @param choices		the string choices that the user can choose
	 * @return the index of the user's choice in the choices array.
	 */
	static public int getPlayerDecision(String prompt, ArrayList<String> choices) {
		int userChoice = 0;
		// repeat until a recognised choice is made
		while (userChoice < 1 || userChoice > choices.size()) {
			try {
				
				// prints the query and possible choices to the console
				System.out.println("\n######################################");
				System.out.println(prompt);
				for (int i = 0; i < choices.size(); i++) {
					System.out.println(Integer.toString(i + 1) + ". " + choices.get(i));
				}
				System.out.println("\nEnter a number from the range 1-" + Integer.toString(choices.size()) + " to select the corresponding option: ");
				
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
	 * Console environment representation of the Sailing Menu
	 */
	public static void consoleSail() {
		// Establish the route choices the player can make
		ArrayList<String> routeChoices = new ArrayList<String>();
		for (Route route: activeIsland.getRoutes()) {
			routeChoices.add(route.getDestinationIsland().getName() + " in " + route.getDays() + " days (" + route.getEventChance() + " Risk)");
		}
		routeChoices.add("Cancel");
		
		int chosenRouteIndex = getPlayerDecision("Select the route you would like to take:", routeChoices); // Get the user to choose the route index
		
		if (chosenRouteIndex != activeIsland.getRoutes().size()) { // if not cancelled
			Route chosenRoute = activeIsland.getRoutes().get(chosenRouteIndex); // Get the chosen route as a Route
			
			System.out.println("You have selected the route to " + chosenRoute.getDestinationIsland().getName() + "!");
			
			try {
				ArrayList<String> notifyEventStrings = sail(chosenRoute); // Sail using the chosen route and receive the Random Event strings
				System.out.println("\n######################################\n");
				for (String notification: notifyEventStrings) { // Print the returned random event strings
					System.out.println(notification);
				}
				if (!gameOver) { // If the player has not died while sailing, prints out the items that the Island sells
					System.out.println("\n######################################\nYou have made it to " + activeIsland.getName() + "!\n");
					System.out.println(activeIsland.getName() + " buys and sells: ");
					for (Item item: items) {
						if (activeIsland.getTrades().get(item) != 0) {
							System.out.println("- " + item.getName() + " for " + item.getPrice() * activeIsland.getTrades().get(item) + " gold.");
						}
					}
				}
			} catch (InsufficientFundsException e) {
				System.out.println("You cannot pay your crew for the journey. Choose another route, or sell some cargo to afford the journey.");
			} catch (InsufficientDaysException e) {
				System.out.println("You do not have enough days left to make that journey.");
			}
		}
	}
	
	
	/**
	 * Generalised Sail function for use by console and gui environments
	 * @param route	the route that will be sailed. Type: Route
	 * @return the array list of strings given by the tryEvent method in RandomEvent
	 * @throws InsufficientDaysException	throws if there are not enough days left to take the route
	 * @throws InsufficientFundsException	throws if the player does not have enough money to pay their sailors for the route
	 */
	public static ArrayList<String> sail(Route route) throws InsufficientDaysException, InsufficientFundsException {
		if (route.getDays() > gameDays) {
			throw new InsufficientDaysException();
		} else if (addMoney(-DAILYPAYPERHEAD * activeShip.getCrewSize())) {
			ArrayList<String> notifyEventStrings = RandomEvent.tryEvent(activeShip, route.getEventChance());
			statSailed += 1;
			gameDays -= route.getDays();
			activeIsland = route.getDestinationIsland();
			return notifyEventStrings;
		} else {
			throw new InsufficientFundsException();
		}
		
	}
	
	/**
	 * Console environment representation of the Trading Menu
	 */
	public static void consoleTrade() {
		
		// Determine whether to Buy or Sell
		ArrayList<String> bsOptions = new ArrayList<String>();
		bsOptions.add("Buy");
		bsOptions.add("Sell");
		int bsDecision = getPlayerDecision("Select an option:", bsOptions);
		
		// Determine which item to trade, or cancel the 
		ArrayList<String> itemOptions = new ArrayList<String>();
		ArrayList<Item> tradableItems = new ArrayList<Item>();
		for (Item item: items) {
			if (activeIsland.getTrades().get(item) != 0) {
				tradableItems.add(item);
				if (bsDecision == 0) {
					itemOptions.add(item.getName() + 
							"\n   Value:  " + (item.getPrice() * activeIsland.getTrades().get(item)) +
							"\n   Weight: " + item.getWeight() + "\n");
				} else {
					itemOptions.add(item.getName() + 
							"\n   Value:    " + (item.getPrice() * activeIsland.getTrades().get(item)) +
							"\n   Quantity: " + activeShip.getCargo().get(item) + "\n");
				}
			}
		}
		itemOptions.add("Cancel");
		int chosenItemIndex = getPlayerDecision("Select an item to " + bsOptions.get(bsDecision) + ": ", itemOptions);
		
		
		if (chosenItemIndex != tradableItems.size()) { // If not cancel ask for a quantity to buy/sell
			Item chosenItem = tradableItems.get(chosenItemIndex);
			// Determine the maximum amount to buy or sell
			int maxBuySell = 0;
			if (bsDecision == 0) {
				// Get the minimum between:	the max number of the chosen item that can fit in the remaining space
				//							the max number of the chosen item that the player can afford
				maxBuySell = Math.min(Math.floorDiv(activeShip.getAvailableCargoSpace(), chosenItem.getWeight()),
									  Math.floorDiv(playerMoney, (int) (chosenItem.getPrice() * activeIsland.getTrades().get(chosenItem))));
			} else {
				// Get the 
				maxBuySell = activeShip.getCargo().get(chosenItem);
			}
			
			// Get the quantity to buy/sell
			int quantity = getUserIntInRange("Please enter the quantity you would like to " + bsOptions.get(bsDecision) + " (0 to cancel): ", 0, maxBuySell);
			
			if (quantity != 0) { // If not 0 (to cancel)
				if (bsDecision == 0) { // If Buying
					statTraded += quantity;
					addMoney((int) -(quantity * chosenItem.getPrice() * activeIsland.getTrades().get(chosenItem))); 				// Remove Money
					activeShip.addItemCargo(chosenItem, quantity);																	// Add Item * Quantity to Cargo
					System.out.println("You have bought " + quantity + " " + chosenItem.getName() + " for " 
										+ (int) (quantity * chosenItem.getPrice() * activeIsland.getTrades().get(chosenItem)) 
										+ " gold.\nYou now have " + playerMoney + " gold.");
				} else { // If Selling
					statTraded += quantity;
					addMoney((int) (quantity * chosenItem.getPrice() * activeIsland.getTrades().get(chosenItem)));					// Add Money
					activeShip.addItemCargo(chosenItem, -quantity);																	// Remove Item * Quantity from Cargo
					System.out.println("You have sold " + quantity + " " + chosenItem.getName() + " for " 
							+ (int) (quantity * chosenItem.getPrice() * activeIsland.getTrades().get(chosenItem)) 
							+ " gold.\nYou now have " + playerMoney + " gold.");
				}
			}
		}
	}
	
	
	
	/**
	 * Initialises the player name, the game days they wish to play for and which ship they would like to use
	 */
	public static void initialisePlayerValues() {
		
		boolean nameDecided = false;
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
		
		// Gets the number of game days the game will take place over
		gameDays = getUserIntInRange("Please choose the number of days that you wish the game to take place over: ", 20, 50);
		
		// Gets the ship that the user chooses
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
		
		// Initialises the chosen ship's cargo
		for (Item item: items) {
			activeShip.initialiseCargo(item);
		}
	}
	
	public static void gameOver() {
		gameOver = true;
	}
	
	/**
	 * Returns true if the player can continue the game, false otherwise.
	 * @return true if the player can continue the game, false otherwise.
	 */
	public static boolean canContinueGame(){
		double minRouteDays = Double.POSITIVE_INFINITY;
		for (Route route: activeIsland.getRoutes()) {
			if (route.getDays() < minRouteDays) {minRouteDays = route.getDays();}
		}
		int totalCargoValueAtIsland = 0;
		for (Item item: activeShip.getCargo().keySet()) {
			totalCargoValueAtIsland += item.getPrice() * activeIsland.getTrades().get(item) * activeShip.getCargo().get(item);
		}
		if (playerMoney + totalCargoValueAtIsland - (minRouteDays * DAILYPAYPERHEAD * activeShip.getCrewSize()) >= 0 
				&& minRouteDays <= gameDays && !gameOver) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Runs the main gameplay loop in the console.
	 */
	public static void mainConsoleGameplayLoop() {
		// Define the main options
		ArrayList<String> mainGameOptionsStrings = new ArrayList<String>();
		mainGameOptionsStrings.add("Trade");
		mainGameOptionsStrings.add("Sail");
		mainGameOptionsStrings.add("View Ship Stats");
		mainGameOptionsStrings.add("View Cargo");
		mainGameOptionsStrings.add("Exit");
		
		// While the game can continue, run the main gameplay loop
		while (canContinueGame()) {
			int selectedGameOption = getPlayerDecision("Select an option: ", mainGameOptionsStrings);
			if (selectedGameOption == 0) {
				consoleTrade();
			}
			else if (selectedGameOption == 1) {
				consoleSail();
			}
			else if (selectedGameOption == 2) {
				System.out.println("Ship Name: " + activeShip.getName() + 
						"\n   Durability: " + activeShip.getDurability() + 
						"\n   Cargo Capacity: " + activeShip.getCapacity() + 
						"\n   Crew Size: " + activeShip.getCrewSize() + 
						"\n   Speed: " + activeShip.getSpeed() + "\n");
			}
			else if (selectedGameOption == 3) {
				int total = 0;
				for (Item item: activeShip.getCargo().keySet()) {
					
					if (activeShip.getCargo().get(item) != 0) {
						System.out.println((total + 1) + ". " + item.getName() + ": " + activeShip.getCargo().get(item));
						total += 1;
					}
				}
				if (total == 0) {
					System.out.println("\nYou have no items in your hold!\n");
				}
			}
			else if (selectedGameOption == 4) {
				gameOver();
			}
		}
		
		System.out.println("Game Over!");
		System.out.println("You made " + (playerMoney - STARTINGMONEY) + " gold!\n"
				+ "You sailed " + statSailed + " times.\n"
				+ "You traded " + statTraded + " items.");
	}
	
	
	public static void main(String[] args) {
		initialise();
		initialisePlayerValues();
		
		mainConsoleGameplayLoop();
		
//		while (true) {
//			consoleTrade();
//			for (Item item: activeShip.getCargo().keySet()) {
//				System.out.println(item.getName() + " : " + activeShip.getCargo().get(item));
//			}
//			System.out.println(activeShip.getAvailableCargoSpace());
//		}
		
//		activeIsland = islands.get(0);
//		activeShip = ships.get(0);
		
		
//		System.out.println("you have $" + totalMoney);
//		
//		if (!activeShip.addItemCargo(items.get(0), 15)) {
//			System.out.println("You do not have enough space to load this item");
//		}
//		System.out.println(canContinueGame());
		
		
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
