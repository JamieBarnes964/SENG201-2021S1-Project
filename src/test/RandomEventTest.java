package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import main.GameEnvironment;
import main.Item;
import main.RandomEvent;
import main.Ship;


class RandomEventTest {
	Ship testShip = new Ship("Test Ship", 80, 60, 40);
	@Test
	void pirateAttackTest() {
		assertEquals("You have encountered pirates!", RandomEvent.tryEvent(testShip, 1.0, 0, 0.2).get(0));
		
	}
	
	@Test
	void pirateEscapeTest() {
		Ship testShip = new Ship("Test Ship", 80, 6000, 40);
		assertEquals("You escaped the enemy pirates!", RandomEvent.tryEvent(testShip, 1.0, 0, 0.2).get(1));
	}
	
	@Test
	void pirateDeathTest() {
		Ship testShip = new Ship("Test Ship", 800, 60, 40);
		assertEquals("The pirates are unhappy with your cargo. You have been made to walk the plank.", RandomEvent.tryEvent(testShip, 1.0, 0, 0.2).get(2));
	}
	
	@Test
	void pirateTakeCargoTest() {
		Ship testShip = new Ship("Test Ship", 800, 60, 40);
		Item testItem = new Item("", 0, 1000);
		testShip.initialiseCargo(testItem);
		testShip.addItemCargo(testItem, 5);
		assertEquals("The pirates have taken all of your cargo and mercifully let you and your crew live.", RandomEvent.tryEvent(testShip, 1.0, 0, 0.2).get(2));
		
	}
	
	@Test
	void stormTest() {
		GameEnvironment.initialise();
		testShip = new Ship("Test Ship", 80, 60, 40);
		for (Item item: GameEnvironment.getItems()) {
			testShip.initialiseCargo(item);
		}
		assertEquals("Your ship has been caught in stormy weather.\nWatch out for your cargo!", RandomEvent.tryEvent(testShip, 1.0, 0, 0.5).get(0));
	}
	
	@Test
	void shipDeathTest() {
		Ship testShip = new Ship("Test Ship", 80, 60, 5);
		for (Item item: GameEnvironment.getItems()) {
			testShip.initialiseCargo(item);
		}
		assertEquals("The storm has completely destroyed your ship and all of its cargo.", RandomEvent.tryEvent(testShip, 1, 0, 0.5).get(1));

	}
	
	@Test
	void rescueTest() {
		assertEquals("You spot some drowning sailors and decide to rescue them.\nThey give you some gold as thanks.", RandomEvent.tryEvent(testShip, 1.0, 0, 1).get(0));
	}
	
	@Test
	void nothingTest() {
		assertEquals("The journey between the islands was uneventful", RandomEvent.tryEvent(testShip, 0, 1, 0).get(0));
	}
}
