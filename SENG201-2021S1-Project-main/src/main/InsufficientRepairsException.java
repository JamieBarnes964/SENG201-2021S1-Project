package main;

public class InsufficientRepairsException extends IllegalArgumentException {
	public InsufficientRepairsException() {
		super();
	}
	
	public InsufficientRepairsException(String message) {
		super(message);
	}
}
