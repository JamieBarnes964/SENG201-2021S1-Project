package main;

import ui.*;
import ui.gui.*;

/**
 * Class where application execution begins.
 * If {@code cmd} is passed through as an argument the application will run as a command line app, otherwise the GUI will be used.
 */
public class Main {
	/**
	 * Initialises the application
	 * @param args Command Line Arguments. Accepts {@code cmd} for which the application will run as a command line app.
	 * 			   If no valid arguments are given, then the application will run as a GUI app.
	 */
	public static void main(String[] args) {
		GameEnvironment manager = new GameEnvironment();
		if (args.length > 0 && (args[0].equals("cmd"))) {
			CommandLineUI commandLineUI = new CommandLineUI(manager);
		} else {
			GUISetupScreen guiStart = new GUISetupScreen(manager);
		}
	}
}
