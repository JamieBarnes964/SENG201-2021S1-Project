package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import main.*;

class GameEnvironmentTest {
	
	@Test
	void addMoneyTest() {
		GameEnvironment.addMoney(600);
		assertEquals(2600, GameEnvironment.getPlayerMoney());
	}
	
	@Test
	void sailTest() {
		Ship testShip = new Ship("Test Ship", 30, 40, 50);
		Island testIsland = new Island("Test Island", new HashMap<Item, Double>());
		Island testIsland2 = new Island("Test Island 2", new HashMap<Item, Double>());
		Route testRoute = new Route(0, 0, testIsland2);
		testIsland.addRoute(testRoute);
		GameEnvironment.setActiveIsland(testIsland);
		GameEnvironment.setActiveShip(testShip);
		GameEnvironment.initialise();
		GameEnvironment.sail(testRoute);
		assertEquals("Test Island 2", GameEnvironment.getActiveIsland().getName());
		
	}
	
	@Test
	void canContinueGameTest() {
		
	}
}
