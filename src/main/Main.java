package main;

import ui.*;
import ui.gui.*;

public class Main {
	public static void main(String[] args) {
		GameEnvironment manager = new GameEnvironment();
		if (args.length > 0 && (args[0].equals("cmd"))) {
			CommandLineUI commandLineUI = new CommandLineUI(manager);
		} else {
			GUISetupScreen guiStart = new GUISetupScreen(manager);
		}
	}
}
