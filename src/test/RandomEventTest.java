package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.*;


class RandomEventTest {
	GameEnvironment manager;
	RandomEvent randomEvent;
	
	@BeforeEach
	void initialise() {
		manager = new GameEnvironment();
		manager.addMoney(-manager.getPlayerMoney() + 2000);
	}
	
	
	
	
	@Test
	/**
	 * Tests whether or not the player can encounter pirates
	 */
	void pirateAttackTest() {
		manager.setActiveShip(new Ship("Test Ship", 80, 60, 40));
		randomEvent = new RandomEvent(manager, 1.0, 0, 0.2);
		assertEquals("You have encountered pirates!", randomEvent.getEventStrings().get(0));
		
	}
	
	@Test
	/**
	 * Tests whether or not the player can escape pirates
	 */
	void pirateEscapeTest() {
		manager.setActiveShip(new Ship("Test Ship", 80, 6000, 40));
		randomEvent = new RandomEvent(manager, 1.0, 0, 0.2);
		assertEquals("You escaped the enemy pirates!", randomEvent.getEventStrings().get(1));
	}
	
	@Test
	/**
	 * Tests whether or not the player can walk the plank
	 */
	void pirateDeathTest() {
		manager.setActiveShip(new Ship("Test Ship", 800, 60, 40));
		randomEvent = new RandomEvent(manager, 1.0, 0, 0.2);
		assertEquals("The pirates are unhappy with your cargo. You have been made to walk the plank.", randomEvent.getEventStrings().get(2));
	}
	
	@Test
	/**
	 * Tests whether or not the player can encounter pirates and have them take their cargo
	 */
	void pirateTakeCargoTest() {
		Ship testShip = new Ship("Test Ship", 800, 60, 40);
		Item testItem = new Item("", 0, 1000);
		testShip.initialiseCargo(testItem);
		testShip.addItemCargo(testItem, 5);
		manager.setActiveShip(testShip);
		randomEvent = new RandomEvent(manager, 1.0, 0, 0.2);
		assertEquals("The pirates have taken all of your cargo and mercifully let you and your crew live.", randomEvent.getEventStrings().get(2));
		
	}
	
	@Test
	/**
	 * Tests whether or not the player can encounter a storm
	 */
	void stormTest() {
		Ship testShip = new Ship("Test Ship", 80, 60, 40);
		for (Item item: manager.getItems()) {
			testShip.initialiseCargo(item);
		}
		manager.setActiveShip(testShip);
		randomEvent = new RandomEvent(manager, 1.0, 0, 0.5);
		assertEquals("Your ship has been caught in stormy weather.\nWatch out for your cargo!", randomEvent.getEventStrings().get(0));
	}
	
	@Test
	/**
	 * Tests whether or not the player can lose their ship to the storm
	 */
	void shipDeathTest() {
		Ship testShip = new Ship("Test Ship", 80, 60, 5);
		for (Item item: manager.getItems()) {
			testShip.initialiseCargo(item);
		}
		manager.setActiveShip(testShip);
		randomEvent = new RandomEvent(manager, 1, 0, 0.5);
		assertEquals("The storm has completely destroyed your ship and all of its cargo.", randomEvent.getEventStrings().get(1));

	}
	
	@Test
	/**
	 * Tests whether or not the player can rescue stranded sailors
	 */
	void rescueTest() {
		manager.setActiveShip(new Ship("Test Ship", 800, 60, 40));
		randomEvent = new RandomEvent(manager, 1.0, 0, 1);
		assertEquals("You spot some drowning sailors and decide to rescue them.\nThey give you some gold as thanks.", randomEvent.getEventStrings().get(0));
	}
	
	@Test
	/**
	 * Tests whether or not the player can bypass all encounters
	 */
	void nothingTest() {
		manager.setActiveShip(new Ship("Test Ship", 800, 60, 40));
		randomEvent = new RandomEvent(manager, 0, 1, 0);
		assertEquals("The journey between the islands was uneventful", randomEvent.getEventStrings().get(0));
	}
	
	@Test
	/**
	 * Tests whether or not the player can lose their fine china to the storm
	 */
	void brokenChinaTest() {
		manager.initialise();
		Ship testShip = new Ship("Test Ship", 80, 60, 40);
		for (Item item: manager.getItems()) {
			testShip.initialiseCargo(item);
		}
		Item fineChina = manager.getItems().get(3);
		manager.setActiveShip(testShip);
		testShip.addItemCargo(fineChina, 2);
		assertEquals(2, testShip.getCargo().get(fineChina));
		randomEvent = new RandomEvent(manager, 1.0, 0, 0.5);
		assertEquals(0, testShip.getCargo().get(fineChina));
	}
}
