package ui.gui;

import main.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUIGameOverScreen {

	private JFrame window;
	private GameEnvironment manager;


	/**
	 * Create the application.
	 */
	public GUIGameOverScreen(GameEnvironment manager) {
		this.manager = manager;
		initialize();
		this.window.setVisible(true);
	}
	
	/**
	 * Closes the window
	 */
	public void closeWindow() {
		window.dispose();
	}
	
	
	/**
	 * initialises the closing of this window
	 */
	private void finishedWindow() {
		manager.closeGUIEndGameScreen(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		window = new JFrame();
		window.setResizable(false);
		window.setBounds(100, 100, 450, 300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel gameOverLabel = new JLabel("Game Over!");
		gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gameOverLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		window.getContentPane().add(gameOverLabel, BorderLayout.NORTH);
		
		JPanel finalStatsPanel = new JPanel();
		window.getContentPane().add(finalStatsPanel, BorderLayout.CENTER);
		finalStatsPanel.setLayout(new BorderLayout(0, 0));
		
		// Show all stats
		JLabel allStatsLabel = new JLabel();
		allStatsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		finalStatsPanel.add(allStatsLabel);
			
			int totalCargoValueAtIsland = 0;
			for (Item item: manager.getActiveShip().getCargo().keySet()) {
				totalCargoValueAtIsland += item.getPrice() * manager.getActiveIsland().getTrades().get(item) * manager.getActiveShip().getCargo().get(item);
			}
			int finalScore;
			try {
				finalScore = Math.floorDiv(manager.getPlayerMoney() + totalCargoValueAtIsland - manager.getStartingmoney(), manager.getStartGameDays() - manager.getGameDays());
			} catch (ArithmeticException e) {
				finalScore = 0;
			}
			String statsString = "<html>" + "Score: " + finalScore;
			statsString += "<br>Played " + (manager.getStartGameDays() - manager.getGameDays()) + " of " + manager.getStartGameDays() + " days" + 
								 	"<br>"+ manager.getPlayerName() + " made: $" + (manager.getPlayerMoney() + totalCargoValueAtIsland - manager.getStartingmoney()) + 
								 	"<br>" + manager.getPlayerName() + " traded items " + manager.getStatTraded() + " times" +
								 	"<br>" + manager.getPlayerName() + " sailed " + manager.getStatSailed() + " times</html>";
			
			allStatsLabel.setText(statsString);
		
		JButton finishButton = new JButton("Finish");
		finishButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finishedWindow();
			}
		});
		finalStatsPanel.add(finishButton, BorderLayout.SOUTH);
	}

}
