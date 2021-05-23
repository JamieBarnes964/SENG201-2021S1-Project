package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import main.*;

class ShipTest {
	
	//Need to implement cargo tests but idk how
	
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
		
	}
	
	@Test
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
	void emptyCargoTest() {
		
	}

}