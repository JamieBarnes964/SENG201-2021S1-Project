package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import main.RandomEvent;
import main.Ship;


class RandomEventTest {
	RandomEvent testEvent = new RandomEvent();
	Ship testShip = new Ship("Test Ship", 80, 60, 40);
	@Test
	void pirateAttackTest() {
		assertEquals("You have encountered pirates!", testEvent.tryEvent(testShip, 0.4, 1.0, 0.2).get(0));
	}
	
	@Test
	void stormTest() {
		assertEquals("Your ship has been caught in stormy weather.\nWatch out for your cargo!", testEvent.tryEvent(testShip, 0.4, 1, 0.5).get(0));
	}
	
	@Test
	void rescueTest() {
		assertEquals("You spot some drowning sailors and decide to rescue them.\nThey give you some gold as thanks.", testEvent.tryEvent(testShip, 0.4, 1, 1).get(0));
	}
	
	@Test
	void nothingTest() {
		assertEquals("The journey between the islands was uneventful", testEvent.tryEvent(testShip, 0.4, 0, 0).get(0));
	}
}
