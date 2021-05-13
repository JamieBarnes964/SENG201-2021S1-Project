import java.util.Random;

public class RandomEvent {
	private int valueNeeded = 1000;
	
	private int escapeChance(Ship ship) {
		int chance = (int) (ship.getCrewSize() * ship.getSpeed());
		return chance;
	}
	
	private int eventChance() {
		Random rand = new Random();
		double randomDouble = rand.nextDouble();
		return (int) randomDouble;
	}
	
	public String escape() {
		return("You manage to slip past the enemy pirates");
	}
	
	public String uneventfulJourney() {
		return("The journey between the islands was uneventful");
	}
	
	public void walkPlank() {
		System.out.println("The pirates are unhappy with your cargo. You have been forced to walk the plank.");
		System.out.println("Game Over");
		GameEnvironment.gameOver();
	}
	
	public void tryEvent(Ship ship, double eventChance) {
		int chance = eventChance();
		
		// Pirate Attack
		if (chance < ((0.8 + escapeChance(ship))/ 250) && chance > (0.6 + escapeChance(ship)) / 250) {
			if (ship.getCargoCost() < valueNeeded) {
				walkPlank();
			} else {
				ship.emptyCargo();
			}
		}
		
		// Stormy Weather
		else if(chance < ((0.9 + escapeChance(ship))/ 250) && chance > (0.8 + escapeChance(ship)) / 250) {
			double damageTaken = (eventChance() * 0.3 * 100);
			if (damageTaken >= ship.getDurability()) {
				System.out.println("The storm has completely destroyed your ship and all of its cargo.");
				GameEnvironment.gameOver();
			}
			else {
				ship.takeDamage(damageTaken);
			}
		}
		
		// Rescue Sailors
		else if(chance < ((1.0 + escapeChance(ship))/ 250) && chance > (0.9 + escapeChance(ship)) / 250) {
			System.out.println("You have rescued some stranded sailors");
			// Need to add gold gift thingy
		}
		
		// Nothing happens
		else {
			this.uneventfulJourney();
		}
	}
}
