import java.util.ArrayList;

public class Island {
	private String name;
	private ArrayList<Item> trades;
	private ArrayList<Route> routes;
	
	public Island(String name, ArrayList<Item> trades) {
		this.name = name;
		this.trades = trades;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<Item> getTrades() {
		return this.trades;
	}
	
	public ArrayList<Route> getRoutes() {
		return this.routes;
	}
}
