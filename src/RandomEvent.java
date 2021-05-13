import java.util.Random;

public class RandomEvent {
	private static final int VALUENEEDED = 1000;
	
	private static int escapeChance(Ship ship) {
		int chance = (int) (ship.getCrewSize() * ship.getSpeed());
		return chance;
	}
	
	private static int randomNumber() {
		Random rand = new Random();
		double randomDouble = rand.nextDouble();
		return (int) randomDouble;
	}
	
	public String escape() {
		return("You manage to slip past the enemy pirates");
	}
	
	public static String uneventfulJourney() {
		return("The journey between the islands was uneventful");
	}
	
	public static void walkPlank() {
		System.out.println("The pirates are unhappy with your cargo. You have been forced to walk the plank.");
	}
	
	public static boolean tryEvent(Ship ship, double eventChance) {
		double chance = randomNumber();
		
		// Pirate Attack
		if (eventChance < ((0.8 + escapeChance(ship))/ 250) && eventChance > (0.6 + escapeChance(ship)) / 250) {
			if (ship.getCargoCost() < VALUENEEDED) {
				walkPlank();
				return false;
			} else {
				ship.emptyCargo();
				return true;
			}
		}
		
		// Stormy Weather
		else if(eventChance < ((0.9 + escapeChance(ship))/ 250) && eventChance > (0.8 + escapeChance(ship)) / 250) {
			double damageTaken = (randomNumber() * 0.3 * 100);
			if (damageTaken >= ship.getDurability()) {
				System.out.println("The storm has completely destroyed your ship and all of its cargo.");
				return false;
			}
			else {
				ship.takeDamage(damageTaken);
				return true;
				
			}
		}
		
		// Rescue Sailors
		else if(eventChance < ((1.0 + escapeChance(ship))/ 250) && eventChance > (0.9 + escapeChance(ship)) / 250) {
			System.out.println("You have rescued some stranded sailors");
			return true;
			// Need to add gold gift thingy
		}
		
		// Nothing happens
		else {
			uneventfulJourney();
			return true;
		}
	}
}
