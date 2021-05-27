package main;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that models an Island.
 */
public class Island {
	private String name;
	private HashMap<Item, Double> trades;
	private ArrayList<Route> routes;
	
	/**
	 * Constructs an Island with the given name and trades.
	 * @param name		the name of the Island
	 * @param trades	the trades in a HashMap with the Item as the key, and Item's trade value as the HashMap value
	 */
	public Island(String name, HashMap<Item, Double> trades) {
		this.name = name;
		this.trades = trades;
		this.routes = new ArrayList<Route>();
	}
	
	/**
	 * Returns the name of the Island.
	 * @return the name of the Island
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the Island's trades.
	 * @return the Island's trades
	 */
	public HashMap<Item, Double> getTrades() {
		return this.trades;
	}
	
	/**
	 * Returns the Island's routes.
	 * @return the Island's routes
	 */
	public ArrayList<Route> getRoutes() {
		return this.routes;
	}
	
	/**
	 * Adds a Route to the Island's routes ArrayList.
	 * @param route a Route
	 */
	public void addRoute(Route route) {
		this.routes.add(route);
	}
	

	
}
