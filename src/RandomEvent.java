import java.util.Random;

public class RandomEvent {
	private int valueNeeded = 1000;
	
	public String escape() {
		return("You manage to slip past the enemy pirates");
	}
	
	public void walkPlank() {
		System.out.println("The pirates are unhappy with your cargo. You have been forced to walk the ship.");
		System.out.println("Game Over");
		GameEnvironment.gameOver();
	}
	
	public void tryEvent(Ship ship, double eventChance) {
		Random rand = new Random();
		double randomDouble = rand.nextDouble();
		if (randomDouble < (0.4 + (ship.getCrewSize() * ship.getSpeed()) / 250)) {
			escape();
		} else {
			if (ship.getCargoCost() < valueNeeded) {
				walkPlank();
			} else {
				ship.emptyCargo();
			}
		}
	}
}
