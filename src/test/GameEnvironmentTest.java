package test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import main.*;

class GameEnvironmentTest {
	
	@Test
	void addMoneyTest() {
		GameEnvironment.initialise();
		GameEnvironment.addMoney(600);
		assertEquals(2600, GameEnvironment.getPlayerMoney());
	}
	
	@Test
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
	void canContinueGameTest() {
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
		GameEnvironment.setGameDays(1);
		GameEnvironment.addMoney(-GameEnvironment.getPlayerMoney());
		assertEquals(false, GameEnvironment.canContinueGame()); // Test not enough Money
		GameEnvironment.addMoney(2000);
		assertEquals(true, GameEnvironment.canContinueGame());
		GameEnvironment.gameOver();
		assertEquals(false, GameEnvironment.canContinueGame());
	}
}
