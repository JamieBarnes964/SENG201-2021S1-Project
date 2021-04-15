
public class Route {
	private int days;
	private double eventChance;
	private Island destinationIsland;
	
	public Route(int days, double eventChance, Island destinationIsland) {
		this.days = days;
		this.eventChance = eventChance;
		this.destinationIsland = destinationIsland;
	}
	
	public int getDays() {
		return this.days;
	}
	
	public double getEventChance() {
		return this.eventChance;
	}
	
	public Island getDestinationIsland() {
		return this.destinationIsland;
	}
}
