package ui;
import java.util.ArrayList;
import java.util.Scanner;

import main.GameEnvironment;
import main.InsufficientDaysException;
import main.InsufficientFundsException;
import main.Item;
import main.Route;
import main.Ship;

public class CommandLineUI {

	private Scanner consoleInput = new Scanner(System.in);
	private GameEnvironment manager;
	
	/**
	 * Creates and runs the command line user interface app
	 * @param manager A GameEnvironment object that is actively running the game
	 */
	public CommandLineUI(GameEnvironment manager) {
		this.manager = manager;
		initialisePlayerValues();
		mainGameplayLoop();
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
	private int getUserIntInRange(String prompt, int min, int max) {
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
	private int getPlayerDecision(String prompt, ArrayList<String> choices) {
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
	private void consoleSail() {
		// Establish the route choices the player can make
		ArrayList<String> routeChoices = new ArrayList<String>();
		for (Route route: manager.getActiveIsland().getRoutes()) {
			routeChoices.add(route.getDestinationIsland().getName() + " in " + route.getDays() + " days (" + route.getEventChance() + " Risk)");
		}
		routeChoices.add("Cancel");
		
		int chosenRouteIndex = getPlayerDecision("Select the route you would like to take:", routeChoices); // Get the user to choose the route index
		
		if (chosenRouteIndex != manager.getActiveIsland().getRoutes().size()) { // if not cancelled
			Route chosenRoute = manager.getActiveIsland().getRoutes().get(chosenRouteIndex); // Get the chosen route as a Route
			
			System.out.println("You have selected the route to " + chosenRoute.getDestinationIsland().getName() + "!");
			
			try {
				ArrayList<String> notifyEventStrings = manager.sail(chosenRoute); // Sail using the chosen route and receive the Random Event strings
				System.out.println("\n######################################\n");
				for (String notification: notifyEventStrings) { // Print the returned random event strings
					System.out.println(notification);
				}
				if (!manager.isGameOver()) { // If the player has not died while sailing, prints out the items that the Island sells
					System.out.println("\n######################################\nYou have made it to " + manager.getActiveIsland().getName() + "!\n");
					System.out.println(manager.getActiveIsland().getName() + " buys and sells: ");
					for (Item item: manager.getItems()) {
						if (manager.getActiveIsland().getTrades().get(item) != 0) {
							System.out.println("- " + item.getName() + " for " + item.getPrice() * manager.getActiveIsland().getTrades().get(item) + " gold.");
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
	 * Console environment representation of the Trading Menu
	 */
	private void consoleTrade() {
		
		// Determine whether to Buy or Sell
		ArrayList<String> bsOptions = new ArrayList<String>();
		bsOptions.add("Buy");
		bsOptions.add("Sell");
		int bsDecision = getPlayerDecision("Select an option:", bsOptions);
		
		// Determine which item to trade, or cancel the 
		ArrayList<String> itemOptions = new ArrayList<String>();
		ArrayList<Item> tradableItems = new ArrayList<Item>();
		for (Item item: manager.getItems()) {
			if (manager.getActiveIsland().getTrades().get(item) != 0) {
				tradableItems.add(item);
				if (bsDecision == 0) {
					itemOptions.add(item.getName() + 
							"\n   Value:  " + (item.getPrice() * manager.getActiveIsland().getTrades().get(item)) +
							"\n   Weight: " + item.getWeight() + "\n");
				} else {
					itemOptions.add(item.getName() + 
							"\n   Value:    " + (item.getPrice() * manager.getActiveIsland().getTrades().get(item)) +
							"\n   Quantity: " + manager.getActiveShip().getCargo().get(item) + "\n");
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
				maxBuySell = Math.min(Math.floorDiv(manager.getActiveShip().getAvailableCargoSpace(), chosenItem.getWeight()),
									  Math.floorDiv(manager.getPlayerMoney(), (int) (chosenItem.getPrice() * manager.getActiveIsland().getTrades().get(chosenItem))));
			} else {
				// Get the 
				maxBuySell = manager.getActiveShip().getCargo().get(chosenItem);
			}
			
			// Get the quantity to buy/sell
			int quantity = getUserIntInRange("Please enter the quantity you would like to " + bsOptions.get(bsDecision) + " (0 to cancel): ", 0, maxBuySell);
			
			if (quantity != 0) { // If not 0 (to cancel)
				if (bsDecision == 0) { // If Buying
					manager.addStatTraded(quantity);
					manager.addMoney((int) -(quantity * chosenItem.getPrice() * manager.getActiveIsland().getTrades().get(chosenItem))); 				// Remove Money
					manager.getActiveShip().addItemCargo(chosenItem, quantity);																	// Add Item * Quantity to Cargo
					System.out.println("You have bought " + quantity + " " + chosenItem.getName() + " for " 
										+ (int) (quantity * chosenItem.getPrice() * manager.getActiveIsland().getTrades().get(chosenItem)) 
										+ " gold.\nYou now have " + manager.getPlayerMoney() + " gold.");
				} else { // If Selling
					manager.addStatTraded(quantity);
					manager.addMoney((int) (quantity * chosenItem.getPrice() * manager.getActiveIsland().getTrades().get(chosenItem)));					// Add Money
					manager.getActiveShip().addItemCargo(chosenItem, -quantity);																	// Remove Item * Quantity from Cargo
					System.out.println("You have sold " + quantity + " " + chosenItem.getName() + " for " 
							+ (int) (quantity * chosenItem.getPrice() * manager.getActiveIsland().getTrades().get(chosenItem)) 
							+ " gold.\nYou now have " + manager.getPlayerMoney() + " gold.");
				}
			}
		}
	}
	
	
	
	/**
	 * Initialises the player name, the game days they wish to play for and which ship they would like to use
	 */
	private void initialisePlayerValues() {
		
		boolean nameDecided = false;
		while (nameDecided == false) {
			try {
				System.out.println("######################################");
				System.out.println("Please enter your name: ");
				// gets the user's input
				manager.setPlayerName((consoleInput.nextLine()));
				
				boolean isAlphaNumeric = manager.getPlayerName().matches("^([a-zA-Z]| )*$");
				nameDecided = true;
				// checks if the input string is in the range of choices
				if (manager.getPlayerName().length() < 3|| manager.getPlayerName().length() > 15) {
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
		manager.setStartGameDays(getUserIntInRange("Please choose the number of days that you wish the game to take place over: ", 20, 50));
		
		// Gets the ship that the user chooses
		ArrayList<String> choices = new ArrayList<String>();
		String query = "Choose a Ship:";
		for (Ship ship: manager.getShips()) {
			choices.add("Ship Name: " + ship.getName() + 
					"\n   Durability: " + ship.getDurability() + 
					"\n   Cargo Capacity: " + ship.getCapacity() + 
					"\n   Crew Size: " + ship.getCrewSize() + 
					"\n   Speed: " + ship.getSpeed() + "\n");
		}
		manager.setActiveShip(manager.getShips().get(getPlayerDecision(query, choices)));
		System.out.println("You have selected " + manager.getActiveShip().getName() + "!");
		
		// Initialises the chosen ship's cargo
		for (Item item: manager.getItems()) {
			manager.getActiveShip().initialiseCargo(item);
		}
	}
	
	
	/**
	 * Runs the main gameplay loop
	 */
	private void mainGameplayLoop() {
		// Define the main options
		ArrayList<String> mainGameOptionsStrings = new ArrayList<String>();
		mainGameOptionsStrings.add("Trade");
		mainGameOptionsStrings.add("Sail");
		mainGameOptionsStrings.add("View Ship Stats");
		mainGameOptionsStrings.add("View Cargo");
		if (manager.getActiveShip().getNeedRepairs()) {
			mainGameOptionsStrings.add("Repair Ship");
		}
		mainGameOptionsStrings.add("Exit");
		
		// While the game can continue, run the main gameplay loop
		while (manager.canContinueGame()) {
			int selectedGameOption = getPlayerDecision("Select an option: ", mainGameOptionsStrings);
			if (selectedGameOption == 0) {
				consoleTrade();
			}
			else if (selectedGameOption == 1) {
				consoleSail();
			}
			else if (selectedGameOption == 2) {
				System.out.println("Ship Name: " + manager.getActiveShip().getName() + 
						"\n   Durability: " + manager.getActiveShip().getDurability() + 
						"\n   Cargo Capacity: " + manager.getActiveShip().getCapacity() + 
						"\n   Crew Size: " + manager.getActiveShip().getCrewSize() + 
						"\n   Speed: " + manager.getActiveShip().getSpeed() + "\n");
			}
			else if (selectedGameOption == 3) {
				int total = 0;
				for (Item item: manager.getActiveShip().getCargo().keySet()) {
					
					if (manager.getActiveShip().getCargo().get(item) != 0) {
						System.out.println((total + 1) + ". " + item.getName() + ": " + manager.getActiveShip().getCargo().get(item));
						total += 1;
					}
				}
				if (total == 0) {
					System.out.println("\nYou have no items in your hold!\n");
				}
			}
			else if (selectedGameOption >= 4) {
				if (mainGameOptionsStrings.size() == 6) {
					if (manager.getPlayerMoney() >= manager.getRepairCost()) {
						System.out.println("You have repaired your ship for $" + manager.getRepairCost());
					} else {
						System.out.println("You don't have enough money to pay for Repairs!");
					}
				} else {
					manager.gameOver();
				}
			}
		}
		
		System.out.println("Game Over!");
		System.out.println("You made " + (manager.getPlayerMoney() - manager.getStartingmoney()) + " gold!\n"
				+ "You sailed " + manager.getStatSailed() + " times.\n"
				+ "You traded " + manager.getStatTraded() + " items.");
	}
}
