import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RandomEvent {
	private static final int VALUENEEDED = 1000;
	
	private static double randomNumber() {
		Random rand = new Random();
		double randomDouble = rand.nextDouble();
		return randomDouble;
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
		if (eventChance <= randomNumber()) {
			// Pirate Attack
			if (chance <= 0.33){
				System.out.println("Prepare to be boarded");
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (0.4 + ((ship.getCapacity() * ship.getSpeed())/ 250) < randomNumber()){
					if (ship.getCargoCost() < VALUENEEDED) {
						walkPlank();
						return false;
					} else {
						ship.emptyCargo();
						return true;
					}
				
				}
				System.out.println("You escaped the enemy pirates.");
				return true;
			}
			
			// Stormy Weather
			else if(chance <= 0.66) {
				int damageTaken = (int) (10 + (randomNumber() * 0.3 * 100));
				System.out.println("Your ship has been caught in stormy weather.");
				System.out.println("Watch out for your cargo!");
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
			else {
				int goldAmount = (int) (200 * (randomNumber() + 0.5));
				System.out.println("You spot some drowning sailors and decide to rescue them.");
				System.out.println("They give you some gold as thanks.");
				System.out.println("You recieve " + goldAmount + " gold.");
				GameEnvironment.addMoney(goldAmount);
				System.out.println("You now have " + GameEnvironment.getMoney() + " gold.");
				return true;
			}
		}
		// Nothing happens
		else {
			uneventfulJourney();
			return true;
		}
	}
}
