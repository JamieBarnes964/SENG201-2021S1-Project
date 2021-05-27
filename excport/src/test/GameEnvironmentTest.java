package test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.*;

class managerTest {
	GameEnvironment manager;
	
	@BeforeEach
	void initialise() {
		manager = new GameEnvironment();
		manager.addMoney(-manager.getPlayerMoney() + 2000);
	}
	
	@Test
	/**
	 * Tests the addMoney function
	 */
	void addMoneyTest() {
		manager.addMoney(600);
		assertEquals(2600, manager.getPlayerMoney());
		assertFalse(manager.addMoney(-2601));
	}
	
	@Test
	/**
	 * Tests whether or not the game can continue based on the number of days left
	 */
	void canContinueGameDaysTest() {
		Ship testShip = new Ship("Test Ship", 30, 40, 50);
		for (Item item: manager.getItems()) {
			testShip.initialiseCargo(item);
		}
		HashMap<Item, Double> tempTrades = new HashMap<Item, Double>();
		for (Item item: manager.getItems()) {
			tempTrades.put(item, 0.0);
		}
		Island testIsland = new Island("Test Island", tempTrades);
		Island testIsland2 = new Island("Test Island 2", tempTrades);
		Route testRoute = new Route(1, 0, testIsland2);
		manager.setGameDays(0);
		testIsland.addRoute(testRoute);
		
		manager.setActiveIsland(testIsland);
		manager.setActiveShip(testShip);
		
		assertEquals(false, manager.canContinueGame()); // Test not enough Days
	}
	
	@Test
	/**
	 * Tests whether or not the game can continue based on the amount of money left
	 */
	void canContinueGameMoneyTest() {
		Ship testShip = new Ship("Test Ship", 30, 40, 50);
		for (Item item: manager.getItems()) {
			testShip.initialiseCargo(item);
		}
		HashMap<Item, Double> tempTrades = new HashMap<Item, Double>();
		for (Item item: manager.getItems()) {
			tempTrades.put(item, 0.0);
		}
		Island testIsland = new Island("Test Island", tempTrades);
		Island testIsland2 = new Island("Test Island 2", tempTrades);
		Route testRoute = new Route(1, 0, testIsland2);
		testIsland.addRoute(testRoute);
		
		manager.setActiveIsland(testIsland);
		manager.setActiveShip(testShip);
		manager.setGameDays(1);
		
		manager.addMoney(-manager.getPlayerMoney());
		assertEquals(false, manager.canContinueGame()); // Test not enough Money
	}
	
	@Test
	/**
	 * Tests whether or not the game can continue
	 */
	void canContinueGameTrueTest() {
		Ship testShip = new Ship("Test Ship", 30, 40, 50);
		for (Item item: manager.getItems()) {
			testShip.initialiseCargo(item);
		}
		HashMap<Item, Double> tempTrades = new HashMap<Item, Double>();
		for (Item item: manager.getItems()) {
			tempTrades.put(item, 0.0);
		}
		Island testIsland = new Island("Test Island", tempTrades);
		Island testIsland2 = new Island("Test Island 2", tempTrades);
		Route testRoute = new Route(1, 0, testIsland2);
		testIsland.addRoute(testRoute);
		
		manager.setActiveIsland(testIsland);
		manager.setActiveShip(testShip);
		manager.setGameDays(1);
		manager.addMoney(-manager.getPlayerMoney());
		manager.addMoney(2000);
		assertEquals(true, manager.canContinueGame());
	}
	
	@Test
	/**
	 * Tests whether or not the gameOver function works
	 */
	void canContinueGameGameOverTest() {
		Ship testShip = new Ship("Test Ship", 30, 40, 50);
		for (Item item: manager.getItems()) {
			testShip.initialiseCargo(item);
		}
		HashMap<Item, Double> tempTrades = new HashMap<Item, Double>();
		for (Item item: manager.getItems()) {
			tempTrades.put(item, 0.0);
		}
		Island testIsland = new Island("Test Island", tempTrades);
		Island testIsland2 = new Island("Test Island 2", tempTrades);
		Route testRoute = new Route(1, 0, testIsland2);
		testIsland.addRoute(testRoute);
		
		manager.setActiveIsland(testIsland);
		manager.setActiveShip(testShip);
		manager.setGameDays(1);
		manager.addMoney(-manager.getPlayerMoney());
		manager.addMoney(2000);
		manager.gameOver();
		assertEquals(false, manager.canContinueGame()); // Test gameOver
	}
	
	@Test
	/**
	 * Tests whether or not the sail function works
	 */
	void sailTest() {
		Ship testShip = new Ship("Test Ship", 30, 40, 50);
		for (int i=0; i < 5; i++) {
			testShip.initialiseCargo(new Item("", 0, 0));
		}
		Island testIsland = new Island("Test Island", new HashMap<Item, Double>());
		Island testIsland2 = new Island("Test Island 2", new HashMap<Item, Double>());
		Route testRoute = new Route(0, 0, testIsland2);
		testIsland.addRoute(testRoute);
		manager.setActiveIsland(testIsland);
		manager.setActiveShip(testShip);
		manager.sail(testRoute);
		assertEquals(testIsland2, manager.getActiveIsland());
	}
	
	@Test
	/**
	 * Tests whether or not the InsufficientDaysException gets thrown properly
	 */
	void sailDaysTest() {
		Ship testShip = new Ship("Test Ship", 30, 40, 50);
		for (int i=0; i < 5; i++) {
			testShip.initialiseCargo(new Item("", 0, 0));
		}
		Island testIsland = new Island("Test Island", new HashMap<Item, Double>());
		Island testIsland2 = new Island("Test Island 2", new HashMap<Item, Double>());
		Route testRoute = new Route(1, 0, testIsland2);
		testIsland.addRoute(testRoute);
		manager.setActiveIsland(testIsland);
		manager.setActiveShip(testShip);
		manager.setGameDays(0);
		
		assertThrows(InsufficientDaysException.class, () -> manager.sail(testRoute));
	}
	
	@Test
	/**
	 * Tests whether or not the InsufficientFundsException gets thrown properly
	 */
	void sailFundsTest() {
		Ship testShip = new Ship("Test Ship", 30, 40, 50);
		for (int i=0; i < 5; i++) {
			testShip.initialiseCargo(new Item("", 0, 0));
		}
		Island testIsland = new Island("Test Island", new HashMap<Item, Double>());
		Island testIsland2 = new Island("Test Island 2", new HashMap<Item, Double>());
		Route testRoute = new Route(1, 0, testIsland2);
		testIsland.addRoute(testRoute);
		manager.setActiveIsland(testIsland);
		manager.setActiveShip(testShip);
		manager.addMoney(-manager.getPlayerMoney());
		manager.setGameDays(1);
		
		assertThrows(InsufficientFundsException.class, () -> manager.sail(testRoute));
	}
	
	@Test
	/**
	 *  Tests whether or not InsufficientRepairsException gets thrown properly
	 */
	void sailRepairsTest() {
		Ship testShip = new Ship("Test Ship", 30, 40, 50);
		for (int i=0; i < 5; i++) {
			testShip.initialiseCargo(new Item("", 0, 0));
		}
		Island testIsland = new Island("Test Island", new HashMap<Item, Double>());
		Island testIsland2 = new Island("Test Island 2", new HashMap<Item, Double>());
		Route testRoute = new Route(0, 0, testIsland2);
		testIsland.addRoute(testRoute);
		manager.setActiveIsland(testIsland);
		manager.setActiveShip(testShip);
		manager.addMoney(2000);
		manager.setGameDays(0);
		testShip.takeDamage(1);
		
		assertThrows(InsufficientRepairsException.class, () -> manager.sail(testRoute));
	}
	
	
}
