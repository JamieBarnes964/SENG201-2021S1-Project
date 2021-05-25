package test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import main.*;

class GameEnvironmentTest {
	
	@Test
	/**
	 * Tests the addMoney function
	 */
	void addMoneyTest() {
		GameEnvironment.initialise();
		GameEnvironment.addMoney(600);
		assertEquals(2600, GameEnvironment.getPlayerMoney());
		assertFalse(GameEnvironment.addMoney(-2601));
	}
	
	@Test
	/**
	 * Tests whether or not the game can continue based on the number of days left
	 */
	void canContinueGameDaysTest() {
		GameEnvironment.initialise();
		
		Ship testShip = new Ship("Test Ship", 30, 40, 50);
		for (Item item: GameEnvironment.getItems()) {
			testShip.initialiseCargo(item);
		}
		HashMap<Item, Double> tempTrades = new HashMap<Item, Double>();
		for (Item item: GameEnvironment.getItems()) {
			tempTrades.put(item, 0.0);
		}
		Island testIsland = new Island("Test Island", tempTrades);
		Island testIsland2 = new Island("Test Island 2", tempTrades);
		Route testRoute = new Route(1, 0, testIsland2);
		GameEnvironment.setGameDays(0);
		testIsland.addRoute(testRoute);
		
		GameEnvironment.setActiveIsland(testIsland);
		GameEnvironment.setActiveShip(testShip);
		
		assertEquals(false, GameEnvironment.canContinueGame()); // Test not enough Days
	}
	
	@Test
	/**
	 * Tests whether or not the game can continue based on the amount of money left
	 */
	void canContinueGameMoneyTest() {
		GameEnvironment.initialise();
		
		Ship testShip = new Ship("Test Ship", 30, 40, 50);
		for (Item item: GameEnvironment.getItems()) {
			testShip.initialiseCargo(item);
		}
		HashMap<Item, Double> tempTrades = new HashMap<Item, Double>();
		for (Item item: GameEnvironment.getItems()) {
			tempTrades.put(item, 0.0);
		}
		Island testIsland = new Island("Test Island", tempTrades);
		Island testIsland2 = new Island("Test Island 2", tempTrades);
		Route testRoute = new Route(1, 0, testIsland2);
		testIsland.addRoute(testRoute);
		
		GameEnvironment.setActiveIsland(testIsland);
		GameEnvironment.setActiveShip(testShip);
		GameEnvironment.setGameDays(1);
		
		GameEnvironment.addMoney(-GameEnvironment.getPlayerMoney());
		assertEquals(false, GameEnvironment.canContinueGame()); // Test not enough Money
	}
	
	@Test
	/**
	 * Tests whether or not the game can continue
	 */
	void canContinueGameTrueTest() {
		GameEnvironment.initialise();
		
		Ship testShip = new Ship("Test Ship", 30, 40, 50);
		for (Item item: GameEnvironment.getItems()) {
			testShip.initialiseCargo(item);
		}
		HashMap<Item, Double> tempTrades = new HashMap<Item, Double>();
		for (Item item: GameEnvironment.getItems()) {
			tempTrades.put(item, 0.0);
		}
		Island testIsland = new Island("Test Island", tempTrades);
		Island testIsland2 = new Island("Test Island 2", tempTrades);
		Route testRoute = new Route(1, 0, testIsland2);
		testIsland.addRoute(testRoute);
		
		GameEnvironment.setActiveIsland(testIsland);
		GameEnvironment.setActiveShip(testShip);
		GameEnvironment.setGameDays(1);
		GameEnvironment.addMoney(-GameEnvironment.getPlayerMoney());
		GameEnvironment.addMoney(2000);
		assertEquals(true, GameEnvironment.canContinueGame());
	}
	
	@Test
	/**
	 * Tests whether or not the gameOver function works
	 */
	void canContinueGameGameOverTest() {
		GameEnvironment.initialise();
		
		Ship testShip = new Ship("Test Ship", 30, 40, 50);
		for (Item item: GameEnvironment.getItems()) {
			testShip.initialiseCargo(item);
		}
		HashMap<Item, Double> tempTrades = new HashMap<Item, Double>();
		for (Item item: GameEnvironment.getItems()) {
			tempTrades.put(item, 0.0);
		}
		Island testIsland = new Island("Test Island", tempTrades);
		Island testIsland2 = new Island("Test Island 2", tempTrades);
		Route testRoute = new Route(1, 0, testIsland2);
		testIsland.addRoute(testRoute);
		
		GameEnvironment.setActiveIsland(testIsland);
		GameEnvironment.setActiveShip(testShip);
		GameEnvironment.setGameDays(1);
		GameEnvironment.addMoney(-GameEnvironment.getPlayerMoney());
		GameEnvironment.addMoney(2000);
		GameEnvironment.gameOver();
		assertEquals(false, GameEnvironment.canContinueGame()); // Test gameOver
	}
	
	@Test
	/**
	 * Tests whether or not the sail function works
	 */
	void sailTest() {
		GameEnvironment.initialise();
		Ship testShip = new Ship("Test Ship", 30, 40, 50);
		for (int i=0; i < 5; i++) {
			testShip.initialiseCargo(new Item("", 0, 0));
		}
		Island testIsland = new Island("Test Island", new HashMap<Item, Double>());
		Island testIsland2 = new Island("Test Island 2", new HashMap<Item, Double>());
		Route testRoute = new Route(0, 0, testIsland2);
		testIsland.addRoute(testRoute);
		GameEnvironment.setActiveIsland(testIsland);
		GameEnvironment.setActiveShip(testShip);
		GameEnvironment.sail(testRoute);
		assertEquals(testIsland2, GameEnvironment.getActiveIsland());
	}
	
	@Test
	/**
	 * Tests whether or not the InsufficientDaysException gets thrown properly
	 */
	void sailDaysTest() {
		GameEnvironment.initialise();
		Ship testShip = new Ship("Test Ship", 30, 40, 50);
		for (int i=0; i < 5; i++) {
			testShip.initialiseCargo(new Item("", 0, 0));
		}
		Island testIsland = new Island("Test Island", new HashMap<Item, Double>());
		Island testIsland2 = new Island("Test Island 2", new HashMap<Item, Double>());
		Route testRoute = new Route(1, 0, testIsland2);
		testIsland.addRoute(testRoute);
		GameEnvironment.setActiveIsland(testIsland);
		GameEnvironment.setActiveShip(testShip);
		GameEnvironment.setGameDays(0);
		
		assertThrows(InsufficientDaysException.class, () -> GameEnvironment.sail(testRoute));
	}
	
	@Test
	/**
	 * Tests whether or not the InsufficientFundsException gets thrown properly
	 */
	void sailFundsTest() {
		GameEnvironment.initialise();
		Ship testShip = new Ship("Test Ship", 30, 40, 50);
		for (int i=0; i < 5; i++) {
			testShip.initialiseCargo(new Item("", 0, 0));
		}
		Island testIsland = new Island("Test Island", new HashMap<Item, Double>());
		Island testIsland2 = new Island("Test Island 2", new HashMap<Item, Double>());
		Route testRoute = new Route(1, 0, testIsland2);
		testIsland.addRoute(testRoute);
		GameEnvironment.setActiveIsland(testIsland);
		GameEnvironment.setActiveShip(testShip);
		GameEnvironment.addMoney(-GameEnvironment.getPlayerMoney());
		GameEnvironment.setGameDays(1);
		
		assertThrows(InsufficientFundsException.class, () -> GameEnvironment.sail(testRoute));
	}
	
	@Test
	/**
	 *  Tests whether or not InsufficientRepairsException gets thrown properly
	 */
	void sailRepairsTest() {
		GameEnvironment.initialise();
		Ship testShip = new Ship("Test Ship", 30, 40, 50);
		for (int i=0; i < 5; i++) {
			testShip.initialiseCargo(new Item("", 0, 0));
		}
		Island testIsland = new Island("Test Island", new HashMap<Item, Double>());
		Island testIsland2 = new Island("Test Island 2", new HashMap<Item, Double>());
		Route testRoute = new Route(0, 0, testIsland2);
		testIsland.addRoute(testRoute);
		GameEnvironment.setActiveIsland(testIsland);
		GameEnvironment.setActiveShip(testShip);
		GameEnvironment.addMoney(2000);
		GameEnvironment.setGameDays(0);
		testShip.takeDamage(1);
		
		assertThrows(InsufficientRepairsException.class, () -> GameEnvironment.sail(testRoute));
	}
	
	
}
