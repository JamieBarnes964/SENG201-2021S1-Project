import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RandomEvent {
	private static final int VALUENEEDED = 1000;
	
	private static double randomNumber() {
		Random rand = new Random();
		double randomDouble = rand.nextDouble();
		return randomDouble;
	}
	

	public static ArrayList<String> tryEvent(Ship ship, double eventChance) {
		ArrayList<String> notifyEventList = new ArrayList<String>();
		double chance = randomNumber();
		if (eventChance <= randomNumber()) {
			
			// Pirate Attack
			if (chance <= 0.33){
				notifyEventList.add("You have encountered pirates!");
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (0.4 + ((ship.getCapacity() * ship.getSpeed())/ 250) < randomNumber()){
					notifyEventList.add("You haven't managed to excape!");
					if (ship.getCargoValue() < VALUENEEDED) {
						notifyEventList.add("The pirates are unhappy with your cargo. You have been made to walk the plank.");
						GameEnvironment.gameOver();
						return notifyEventList;
					} else {
						ship.emptyCargo();
						notifyEventList.add("The pirates have taken all of your cargo and mercifilly let you and your crew live.");
						return notifyEventList;
					}
				} else {
					notifyEventList.add("You escaped the enemy pirates!");
					return notifyEventList;
				}
			}
			
			// Stormy Weather
			else if(chance <= 0.66) {
				int damageTaken = (int) (10 + (randomNumber() * 0.3 * 100));
				notifyEventList.add("Your ship has been caught in stormy weather.\nWatch out for your cargo!");
				if (damageTaken >= ship.getDurability()) {
					notifyEventList.add("The storm has completely destroyed your ship and all of its cargo.");
					GameEnvironment.gameOver();
					return notifyEventList;
				}
				else {
					ship.takeDamage(damageTaken);
					notifyEventList.add("Your ship has taken " + damageTaken + " points of damage!");
					return notifyEventList;
				}
			}
			
			// Rescue Sailors
			else {
				int goldAmount = (int) (200 * (randomNumber() + 0.5));
				notifyEventList.add("You spot some drowning sailors and decide to rescue them.\nThey give you some gold as thanks.");
				GameEnvironment.addMoney(goldAmount);
				notifyEventList.add("You recieve " + goldAmount + " gold.\nYou now have " + GameEnvironment.getPlayerMoney() + " gold.");
				return notifyEventList;
			}
		}
		// Nothing happens
		else {
			notifyEventList.add("The journey between the islands was uneventful");
			return notifyEventList;
		}
	}
}
