package main;

/**
 * Thrown to indicate that a method has been passed an insufficient repairs.
 */
public class InsufficientRepairsException extends IllegalArgumentException {
	
	/**
	 * Constructs a {@code InsufficientRepairsException} with no detail message.
	 */
	public InsufficientRepairsException() {
		super();
	}
	
	/**
	 * Constructs a {@code InsufficientRepairsException} with the specified detail message.
	 * @param message the detail message
	 */
	public InsufficientRepairsException(String message) {
		super(message);
	}
}
