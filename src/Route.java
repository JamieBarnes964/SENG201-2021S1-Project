
public class Route {
	private int days;
	private double eventChance;
	private Island destinationIsland;
	
	/**
	 * @param days			Days needed to travel to the destination island.
	 * @param eventChance	The likelihood of an event happening on a given route.
	 * @param island		The destination island.
	 */
	public Route(int days, double eventChance, Island destinationIsland) {
		this.days = days;
		this.eventChance = eventChance;
		this.destinationIsland = destinationIsland;
	}
	
	/**
	 * Returns the amount of days to travel to the destination island.
	 * @return the amount of days to travel to the destination island.
	 */
	public int getDays() {
		return this.days;
	}
	
	/**
	 * Returns the chance of an event happening on a given route.
	 * @return the chance of an event happening on a given route.
	 */
	public double getEventChance() {
		return this.eventChance;
	}
	
	
	/**
	 * Returns the destination island.
	 * @return the destination island.
	 */
	public Island getDestinationIsland() {
		return this.destinationIsland;
	}
}
