package main;

/**
 * Thrown to indicate that a method has been passed an insufficient number of days.
 */
public class InsufficientDaysException extends IllegalArgumentException {
	
	/**
	 * Constructs a {@code InsufficientDaysException} with no detail message.
	 */
	public InsufficientDaysException() {
		super();
	}
	
	/**
	 * Constructs a {@code InsufficientDaysException} with the specified detail message.
	 * @param message the detail message
	 */
	public InsufficientDaysException(String message) {
		super(message);
	}
}
