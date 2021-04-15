import java.util.Random;

public class RandomEvent {
	public void tryEvent(Ship ship, double eventChance) {
		Random rand = new Random();
		double randomDouble = rand.nextDouble();
		if (randomDouble < (0.4 + (ship.getcrewSize() * ship.getSpeed()) / 250)) {
			beFree();
		} else {
			if (ship.getCargoValue() < someInt) {
				walkPlank();
			} else {
				ship.emptyCargo();
			}
		}
	}
}
