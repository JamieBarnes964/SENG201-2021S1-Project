package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import main.*;

class ShipTest {
	
	Ship testShip = new Ship("Test Ship", 30, 40, 50);
	
	@Test
	/**
	 * Tests whether or not the ship takes damage properly
	 */
	void takeDamageTest() {
		testShip.takeDamage(20);
		assertEquals(30, testShip.getDurability());
	}
	
	@Test
	/**
	 * Tests whether or not the getNeedRepairs function works properly
	 */
	void getNeedRepairsTest() {
		testShip.takeDamage(20);
		assertEquals(true, testShip.getNeedRepairs());
	}
	
	@Test
	/**
	 * Tests whether or not the repairShip function works properly
	 */
	void repairShipTest() {
		testShip.takeDamage(20);
		testShip.repairShip();
		assertEquals(false, testShip.getNeedRepairs());
	}
	
	@Test
	/**
	 * Tests whether or not the getCargoValue function works
	 */
	void getCargoValueTest() {
		Item testItem = new Item("", 0, 2);
		testShip.initialiseCargo(testItem);
		testShip.addItemCargo(testItem, 5);
		assertEquals(10, testShip.getCargoValue());
	}
	
	@Test
	/**
	 * Tests if you can add items to the cargo properly
	 */
	void addItemCargoTest() {
		Item testItem = new Item("", 1, 1);
		testShip.initialiseCargo(testItem);
		assertEquals(true, testShip.addItemCargo(testItem, 1));
		assertEquals(1, testShip.getCargo().get(testItem));
		assertEquals(true, testShip.addItemCargo(testItem, 29));
		assertEquals(30, testShip.getCargo().get(testItem));
		assertEquals(false, testShip.addItemCargo(testItem, 1));
		assertEquals(30, testShip.getCargo().get(testItem));
	}
	
	@Test
	/**
	 * Tests whether or not the emptyCargo function works
	 */
	void emptyCargoTest() {
		Item testItem = new Item("", 1, 2);
		testShip.initialiseCargo(testItem);
		testShip.addItemCargo(testItem, 5);
		assertEquals(10, testShip.getCargoValue());
		assertEquals(25, testShip.getAvailableCargoSpace());
		testShip.emptyCargo();
		assertEquals(0, testShip.getCargoValue());
		assertEquals(30, testShip.getAvailableCargoSpace());
		
	}
}