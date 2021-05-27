package main;

/**
 * Thrown to indicate that a method has been passed an insufficient amount of money.
 */
public class InsufficientFundsException extends IllegalArgumentException{
	
	/**
	 * Constructs a {@code InsufficientFundsException} with no detail message.
	 */
	public InsufficientFundsException() {
		super();
	}
	
	/**
	 * Constructs a {@code InsufficientFundsException} with the specified detail message.
	 * @param message the detail message
	 */
	public InsufficientFundsException(String message) {
		super(message);
	}
}
