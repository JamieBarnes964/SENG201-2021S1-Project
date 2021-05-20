package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import main.Ship;

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

}
