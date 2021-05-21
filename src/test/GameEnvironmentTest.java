package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import main.GameEnvironment;

class GameEnvironmentTest {

	@Test
	public void addMoneyTest() {
		GameEnvironment testGameEnvironment = new GameEnvironment();
		testGameEnvironment.addMoney(600);
		assertEquals(1600, testGameEnvironment.getPlayerMoney());
	}

}
