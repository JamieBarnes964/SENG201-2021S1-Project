package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameEnvironment {
	public final static int STARTINGMONEY = 2000;
	public final static int DAILYPAYPERHEAD = 2;
	public final static int REPAIRCOSTPERUNIT = 2;
	
	
	private static ArrayList<Island> islands;
	private static ArrayList<Ship> ships;
	private static ArrayList<Item> items;
	
	private static int gameDays;
	private static String playerName;
	
	private static Ship activeShip;
	private static Island activeIsland;
	private static int playerMoney;
	
	private static boolean gameOver = false;
	
	// Statistics
	private static int statSailed = 0;	// Stores the number of times the player sailed between islands
	private static int statTraded = 0;	// Stores the number of items the player traded
	
	public static boolean isGameOver() {
		return gameOver;
	}

	public static int getStartingmoney() {
		return STARTINGMONEY;
	}
	
	public static ArrayList<Island> getIslands() {
		return islands;
	}
	
	public static ArrayList<Ship> getShips() {
		return ships;
	}

	public static ArrayList<Item> getItems() {
		return items;
	}
	
	public static void setActiveIsland(Island activeIsland) {
		GameEnvironment.activeIsland = activeIsland;
	}
	
	public static Island getActiveIsland() {
		return activeIsland;
	}
	
	public static void setActiveShip(Ship activeShip) {
		GameEnvironment.activeShip = activeShip;
	}
	
	public static Ship getActiveShip() {
		return activeShip;
	}
	
	public static int getPlayerMoney() {
		return playerMoney;
	}
	
	public static int getGameDays() {
		return gameDays;
	}

	public static void setGameDays(int gameDays) {
		GameEnvironment.gameDays = gameDays;
	}

	public static String getPlayerName() {
		return playerName;
	}

	public static void setPlayerName(String playerName) {
		GameEnvironment.playerName = playerName;
	}

	public static int getStatSailed() {
		return statSailed;
	}

	public static void addStatSailed(int statSailed) {
		GameEnvironment.statSailed += statSailed;
	}

	public static int getStatTraded() {
		return statTraded;
	}

	public static void addStatTraded(int statTraded) {
		GameEnvironment.statTraded += statTraded;
	}

	public static int getRepairCost() {
		return (activeShip.getMaxDurability() - activeShip.getDurability()) * REPAIRCOSTPERUNIT;
	}
	
	public static double randomNumber() {
		Random rand = new Random();
		double randomDouble = rand.nextDouble();
		return randomDouble;
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
		items.add(new Item("Bushels of Bananas", 2, 75));
		items.add(new Item("Iron Rods", 6, 300));
		items.add(new Item("Gold Bars", 8, 500));
		items.add(new Item("Fine China", 2, 450));
		items.add(new Item("Barrels of Gunpowder", 4, 175));
		
		// Initialise Islands
		HashMap<Item, Double> tempTrades = new HashMap<Item, Double>();
		ArrayList<Double> tempTradeMultiplier = new ArrayList<Double>();
		tempTradeMultiplier.add(1.1); //Bushels of Bananas
		tempTradeMultiplier.add(0.0); //Iron Rods
		tempTradeMultiplier.add(1.1); //Gold Bars
		tempTradeMultiplier.add(0.0); //Fine China
		tempTradeMultiplier.add(1.1); //Barrels of Gunpowder
		for (int i = 0; i < items.size(); i++) {
			tempTrades.put(items.get(i), tempTradeMultiplier.get(i));
		}
		islands.add(new Island("Mahkarn", tempTrades));
		
		tempTrades = new HashMap<Item, Double>();
		tempTradeMultiplier.clear();
		tempTradeMultiplier.add(0.7); //Bushels of Bananas
		tempTradeMultiplier.add(1.2); //Iron Rods
		tempTradeMultiplier.add(0.0); //Gold Bars
		tempTradeMultiplier.add(0.6); //Fine China
		tempTradeMultiplier.add(0.0); //Barrels of Gunpowder
		for (int i = 0; i < items.size(); i++) {
			tempTrades.put(items.get(i), tempTradeMultiplier.get(i));
		}
		islands.add(new Island("Tolset Reef", tempTrades));
		
		tempTrades = new HashMap<Item, Double>();
		tempTradeMultiplier.clear();
		tempTradeMultiplier.add(1.4); //Bushels of Bananas
		tempTradeMultiplier.add(0.0); //Iron Rods
		tempTradeMultiplier.add(0.8); //Gold Bars
		tempTradeMultiplier.add(1.3); //Fine China
		tempTradeMultiplier.add(0.0); //Barrels of Gunpowder
		for (int i = 0; i < items.size(); i++) {
			tempTrades.put(items.get(i), tempTradeMultiplier.get(i));
		}
		islands.add(new Island("Alegate", tempTrades));
		
		tempTrades = new HashMap<Item, Double>();
		tempTradeMultiplier.clear();
		tempTradeMultiplier.add(0.0); //Bushels of Bananas
		tempTradeMultiplier.add(1.1); //Iron Rods
		tempTradeMultiplier.add(1.0); //Gold Bars
		tempTradeMultiplier.add(0.0); //Fine China
		tempTradeMultiplier.add(1.2); //Barrels of Gunpowder
		for (int i = 0; i < items.size(); i++) {
			tempTrades.put(items.get(i), tempTradeMultiplier.get(i));
		}
		islands.add(new Island("Pardea", tempTrades));
		
		tempTrades = new HashMap<Item, Double>();
		tempTradeMultiplier.clear();
		tempTradeMultiplier.add(0.0); //Bushels of Bananas
		tempTradeMultiplier.add(0.7); //Iron Rods
		tempTradeMultiplier.add(0.0); //Gold Bars
		tempTradeMultiplier.add(1.6); //Fine China
		tempTradeMultiplier.add(0.9); //Barrels of Gunpowder
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
		
		playerMoney = STARTINGMONEY;
		setActiveIsland(islands.get(3));
	}
	

	

	
	

	
	
	/**
	 * Generalised Sail function for use by command line and graphical UI environments
	 * @param route	the route that will be sailed. Type: Route
	 * @return the array list of strings given by the tryEvent method in RandomEvent
	 * @throws InsufficientDaysException	throws if there are not enough days left to take the route
	 * @throws InsufficientFundsException	throws if the player does not have enough money to pay their sailors for the route
	 */
	public static ArrayList<String> sail(Route route) throws InsufficientDaysException, InsufficientFundsException {
		if (route.getDays() > gameDays) {
			throw new InsufficientDaysException();
		} else if (getPlayerMoney() <= DAILYPAYPERHEAD * activeShip.getCrewSize()) {
			throw new InsufficientFundsException();
		} else if (activeShip.getNeedRepairs()) {
			throw new InsufficientRepairsException();
		} else {
			addMoney(-DAILYPAYPERHEAD * activeShip.getCrewSize());
			ArrayList<String> notifyEventStrings = RandomEvent.tryEvent(activeShip, route.getEventChance(), randomNumber(), randomNumber());
			statSailed += 1;
			gameDays -= route.getDays();
			setActiveIsland(route.getDestinationIsland());
			return notifyEventStrings;
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
		if (playerMoney + totalCargoValueAtIsland - (minRouteDays * DAILYPAYPERHEAD * activeShip.getCrewSize() + getRepairCost()) >= 0 
				&& minRouteDays <= gameDays && !gameOver) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public static void launchGUIMainScreen() {
		GUIMainScreen mainWindow = new GUIMainScreen();
	}
	
	public static void closeGUIMainScreen(GUIMainScreen mainWindow) {
		mainWindow.closeWindow();
		lauchGUIEndGameScreen();
	}
	
	public static void launchGUISetupScreen() {
		GUISetupScreen setupWindow = new GUISetupScreen();
	}
	
	public static void closeGUISetupScreen(GUISetupScreen setupWindow) {
		setupWindow.closeWindow();
		launchGUIMainScreen();
	}
	
	public static void lauchGUIEndGameScreen() {
		GUIGameOverScreen endGameWindow = new GUIGameOverScreen();
	}
	
	public static void closeGUIEndGameScreen(GUIGameOverScreen endGameWindow) {
		endGameWindow.closeWindow();
	}
	
	public static void main(String[] args) {
		initialise();
		
//		new CommandLineUI();
		launchGUISetupScreen();
		
//		GUIAppMain guiApp = new GUIAppMain();
	}
}
