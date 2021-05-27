package main;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class that handles random events.
 */
public class RandomEvent {
	// This is the value that your cargo must beat for the pirates to take your cargo and let you go
	private final int VALUENEEDED = 1000; 
	private ArrayList<String> notifyEventList;
	
	/**
	 * Initialises and runs a Random Event
	 * @param manager A {@link GameEnvironment} object that is actively running the game
	 * @param eventChance the chance of an event happening on the chosen route
	 */
	public RandomEvent(GameEnvironment manager, double eventChance) {
		tryEvent(manager, eventChance, randomNumber(), randomNumber());
	}
	
	/**
	 * Initialises and runs a Random Event with the given variables (Used for Testing)
	 * @param manager A {@link GameEnvironment} object that is actively running the game
	 * @param eventChance the chance of an event happening on the chosen route
	 * @param eventCheck is a random number generated in GameEnvironment that determines whether or not the event happens
	 * @param eventTypeCheck is a random number that determines what event happens
	 */
	public RandomEvent(GameEnvironment manager, double eventChance, double eventCheck, double eventTypeCheck) {
		tryEvent(manager, eventChance, eventCheck, eventTypeCheck);
	}
	
	/**
	 * Returns a random double between 0 and 1
	 * @return a random double between 0 and 1
	 */
	private double randomNumber() {
		Random rand = new Random();
		double randomDouble = rand.nextDouble();
		return randomDouble;
	}
	
	/**
	 * Returns the set event strings list
	 * @return the set event strings list
	 */
	public ArrayList<String> getEventStrings() {
		return notifyEventList;
	}
	
	/**
	 * 
	 * @param manager A {@link GameEnvironment} object that is actively running the game
	 * @param eventChance is the chance of an event happening on the chosen route
	 * @param eventCheck is a random number generated in GameEnvironment that determines whether or not the event happens
	 * @param eventTypeCheck is a random number that determines what event happens
	 */
	private void tryEvent(GameEnvironment manager, double eventChance, double eventCheck, double eventTypeCheck) {
		Random rand = new Random();
		double randomDouble = rand.nextDouble();
		
		notifyEventList = new ArrayList<String>();
		if (eventChance > eventCheck) {
			// Pirate Attack
			if (eventTypeCheck <= 0.33){
				notifyEventList.add("You have encountered pirates!");
				if (0.4 + ((manager.getActiveShip().getCapacity() * manager.getActiveShip().getSpeed())/ 250) < randomDouble){
					notifyEventList.add("You haven't managed to excape!");
					if (manager.getActiveShip().getCargoValue() < VALUENEEDED) {
						notifyEventList.add("The pirates are unhappy with your cargo. You have been made to walk the plank.");
						manager.gameOver();
					} else {
						manager.getActiveShip().emptyCargo();
						notifyEventList.add("The pirates have taken all of your cargo and mercifully let you and your crew live.");
					}
				} else {
					notifyEventList.add("You escaped the enemy pirates!");
				}
			}
			
			// Stormy Weather
			else if(eventTypeCheck <= 0.66) {
				int damageTaken = (int) (10 + (randomDouble * 0.3 * 100));
				notifyEventList.add("Your ship has been caught in stormy weather.\nWatch out for your cargo!");
				if (damageTaken >= manager.getActiveShip().getDurability()) { // If the damage taken is too much for the manager.getActiveShip()
					notifyEventList.add("The storm has completely destroyed your ship and all of its cargo.");
					manager.gameOver();
				} else {
					manager.getActiveShip().takeDamage(damageTaken);
					if (manager.getActiveShip().getCargo().get(manager.getItems().get(3)) > 0) { // if there is fine china: break it
						notifyEventList.add("In the rough seas all of you fine china has broken!");
						manager.getActiveShip().addItemCargo(manager.getItems().get(3), - manager.getActiveShip().getCargo().get(manager.getItems().get(3)));
					}
					notifyEventList.add("Your ship has taken " + damageTaken + " points of damage!");
				}
			}
			
			// Rescue Sailors
			else {
				int goldAmount = (int) (200 * (randomDouble + 0.5));
				notifyEventList.add("You spot some drowning sailors and decide to rescue them.\nThey give you some gold as thanks.");
				manager.addMoney(goldAmount);
				notifyEventList.add("You recieve " + goldAmount + " gold.\nYou now have " + manager.getPlayerMoney() + " gold.");
			}
		}
		// Nothing happens
		else {
			notifyEventList.add("The journey between the islands was uneventful");
		}
	}
}
