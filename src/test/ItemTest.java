package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import main.Item;


class ItemTest {
	
	Item testItem = new Item("Test Item", 100, 1000);
	Item testItem2 = new Item("Test Item Two", 100, 1000);
	@Test
	void getIDTest() {
		assertEquals(2, testItem.getID());
		assertEquals(3, testItem2.getID());
	}
	
	@Test
	void getParamsTest(){
		assertEquals("Test Item", testItem.getName());
		assertEquals(100, testItem.getWeight());
		assertEquals(1000, testItem.getPrice());
	}

}
