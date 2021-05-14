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
	
	static private Scanner consoleInput = new Scanner(System.in);
	
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
	 * 
	 * @param prompt
	 * @param min
	 * @param max
	 * @return
	 */
	static public int getUserIntInRange(String prompt, int min, int max) {
		boolean acceptedInput = false;
		int userInput = 0;
		while (!acceptedInput) {
			System.out.println(prompt);
			System.out.println("Please enter a number between " + min + " and " + max);
			try {
				userInput = Integer.parseInt(consoleInput.nextLine());
				if (userInput < min || userInput > max) {
					throw new Exception("That is not a number between " + min + " and " + max);
				}
				acceptedInput = true;
			} catch (IllegalArgumentException e) {
				System.out.println("That is not a number. Please enter a NUMBER between " + min + " and " + max);
			} catch (Exception e) {
				System.out.println(e.getMessage());
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
	 * Sails to the island the main purpose of the game
	 * What did you expect to be here?
	 * An essay on what sailing is?
	 * Read this if you want to know what sailing is:
	 * https://en.wikipedia.org/wiki/Sailing (Press CTRL when hovering over the link)
	 * 
	 * Remember to remove the sarcasm!
	 * 
	 * Need to remove days after making a trip and to then end the game when the amount of days
	 * 		is less than the amount of days to make any trip
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
			ArrayList<String> notifyEventStrings = sail(chosenRoute);
			System.out.println("\n######################################\n");
			for (String notification: notifyEventStrings) {
				System.out.println(notification);
			}
			if (!gameOver) {
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
	
	
	/**
	 * 
	 * @param route
	 * @return
	 * @throws InsufficientDaysException
	 * @throws InsufficientFundsException
	 */
	public static ArrayList<String> sail(Route route) throws InsufficientDaysException, InsufficientFundsException {
		if (route.getDays() > gameDays) {
			throw new InsufficientDaysException();
		} else if (addMoney(-DAILYPAYPERHEAD * activeShip.getCrewSize())) {
			ArrayList<String> notifyEventStrings = RandomEvent.tryEvent(activeShip, route.getEventChance());
			gameDays -= route.getDays();
			activeIsland = route.getDestinationIsland();
			return notifyEventStrings;
		} else {
			throw new InsufficientFundsException();
		}
		
	}
	
	/**
	 * 
	 */
	public static void consoleTrade() {
		ArrayList<String> bsOptions = new ArrayList<String>();
		bsOptions.add("Buy");
		bsOptions.add("Sell");
		int bsDecision = getPlayerDecision("Select an option:", bsOptions);
		
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
		
		if (chosenItemIndex != tradableItems.size()) {
			
			Item chosenItem = tradableItems.get(chosenItemIndex);
			int maxBuySell = 0;
			
			if (bsDecision == 0) {
				maxBuySell = Math.min(Math.floorDiv(activeShip.getAvailableCargoSpace(), chosenItem.getWeight()),
									  Math.floorDiv(playerMoney, (int) (chosenItem.getPrice() * activeIsland.getTrades().get(chosenItem))));
			} else {
				maxBuySell = activeShip.getCargo().get(chosenItem);
			}
			
			int quantity = getUserIntInRange("Please enter the quantity you would like to " + bsOptions.get(bsDecision) + " (0 to cancel): ", 0, maxBuySell);
			
			if (quantity != 0) {
				if (bsDecision == 0) {
					addMoney((int) -(quantity * chosenItem.getPrice() * activeIsland.getTrades().get(chosenItem)));
					activeShip.addItemCargo(chosenItem, quantity);
					System.out.println("You have bought " + quantity + " " + chosenItem.getName() + " for " 
										+ (int) (quantity * chosenItem.getPrice() * activeIsland.getTrades().get(chosenItem)) 
										+ " gold.\nYou now have " + playerMoney + " gold.");
				} else {
					addMoney((int) (quantity * chosenItem.getPrice() * activeIsland.getTrades().get(chosenItem)));
					activeShip.addItemCargo(chosenItem, -quantity);
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
		boolean daysDecided = false;
		
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
	
	public static void mainConsoleGameplayLoop() {
		ArrayList<String> mainGameOptionsStrings = new ArrayList<String>();
		mainGameOptionsStrings.add("Trade");
		mainGameOptionsStrings.add("Sail");
		mainGameOptionsStrings.add("View Ship Stats");
		mainGameOptionsStrings.add("View Cargo");
		mainGameOptionsStrings.add("Exit");
		while (canContinueGame()) {
			int selectedGameOption = getPlayerDecision("Select an option: ", mainGameOptionsStrings);
			if (selectedGameOption == 0) {
				consoleTrade();
			}
			else if (selectedGameOption == 1) {
				consoleSail();
			}
		}
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
