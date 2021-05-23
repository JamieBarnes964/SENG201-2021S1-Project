package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import main.*;

class ShipTest {
	
	Ship testShip = new Ship("Test Ship", 30, 40, 50);
	@Test
	void takeDamageTest() {
		testShip.takeDamage(20);
		assertEquals(30, testShip.getDurability());
	}
	
	@Test
	void getNeedRepairsTest() {
		testShip.takeDamage(20);
		assertEquals(true, testShip.getNeedRepairs());
	}
	
	@Test
	void repairShipTest() {
		testShip.takeDamage(20);
		testShip.repairShip();
		assertEquals(false, testShip.getNeedRepairs());
	}
	
	@Test
	void getCargoValueTest() {
		Item testItem = new Item("", 0, 2);
		testShip.initialiseCargo(testItem);
		testShip.addItemCargo(testItem, 5);
		assertEquals(10, testShip.getCargoValue());
	}
	
	@Test
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