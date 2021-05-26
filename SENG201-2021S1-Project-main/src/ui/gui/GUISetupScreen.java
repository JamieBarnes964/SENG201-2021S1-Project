package ui.gui;
import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JRadioButton;
import java.awt.Color;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;

import main.*;

import javax.swing.event.ChangeEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class GUISetupScreen {

	private JFrame window;
	private GameEnvironment manager;
	
	/**
	 * Create and open the application.
	 */
	public GUISetupScreen(GameEnvironment manager) {
		this.manager = manager;
		initialize();
		this.window.setVisible(true);
	}
	
	public void closeWindow() {
		window.dispose();
	}
	
	public void finishedWindow() {
		manager.closeGUISetupScreen(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		window = new JFrame();
		window.setTitle("Island Trader Setup");
		window.setResizable(false);
		window.setBounds(100, 100, 470, 360);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Island Trader");
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 444, 41);
		window.getContentPane().add(lblNewLabel);
		
		JLabel playerNameLabel = new JLabel("Name:");
		playerNameLabel.setBounds(10, 44, 86, 14);
		window.getContentPane().add(playerNameLabel);

		JTextField nameTextField = new JTextField();
		nameTextField.setBounds(10, 58, 160, 20);
		window.getContentPane().add(nameTextField);
		nameTextField.setColumns(10);
		
		JLabel daysLabel = new JLabel("Number of days: 0");
		daysLabel.setBounds(10, 89, 160, 14);
		window.getContentPane().add(daysLabel);
		
		JSlider daysSlider = new JSlider();
		daysSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				daysLabel.setText("Number of days: " + daysSlider.getValue());
			}
		});
		daysSlider.setValue(20);
		daysSlider.setMajorTickSpacing(1);
		daysSlider.setMaximum(50);
		daysSlider.setMinimum(20);
		daysSlider.setBounds(10, 103, 200, 20);
		window.getContentPane().add(daysSlider);
		
		JLabel shipLabel = new JLabel("Choose a Ship:");
		shipLabel.setBounds(10, 134, 200, 14);
		window.getContentPane().add(shipLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 149, 444, 145);
		window.getContentPane().add(scrollPane);
		
		// Ship Array Panel #################################################
		
		JPanel shipsArrayPanel = new JPanel();
		scrollPane.setViewportView(shipsArrayPanel);
		shipsArrayPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		ButtonGroup shipSelectionButtons = new ButtonGroup();
		ArrayList<JRadioButton> radioButtons = new ArrayList<JRadioButton>();
		
		for (Ship ship: manager.getShips()) {
			JPanel panel = new JPanel();
//			panel.setBorder(new LineBorder(new Color(0, 0, 0)));
			shipsArrayPanel.add(panel);
			panel.setLayout(new GridLayout(0, 1, 0, 0));
			
			JLabel shipNameLabel = new JLabel(ship.getName());
			panel.add(shipNameLabel);
			shipNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			
			JLabel shipCargoLabel = new JLabel("" + ship.getCapacity());
			shipCargoLabel.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(shipCargoLabel);
			
			JLabel shipCrewLabel = new JLabel("" + ship.getCrewSize());
			shipCrewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(shipCrewLabel);
			
			JLabel shipDurabilityLabel = new JLabel("" + ship.getDurability());
			shipDurabilityLabel.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(shipDurabilityLabel);
			
			JLabel shipSpeedLabel = new JLabel("" + ship.getSpeed());
			shipSpeedLabel.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(shipSpeedLabel);
			
			JRadioButton rdbtnNewRadioButton = new JRadioButton("");
			rdbtnNewRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(rdbtnNewRadioButton);
			
			radioButtons.add(rdbtnNewRadioButton);
			shipSelectionButtons.add(rdbtnNewRadioButton);
		}
		
		
		
		
		
		JLabel errorDisplayLabel = new JLabel("");
		errorDisplayLabel.setForeground(Color.RED);
		errorDisplayLabel.setBounds(10, 297, 293, 23);
		window.getContentPane().add(errorDisplayLabel);
		
		
		
		
		
		JButton startGameButton = new JButton("Start New Game");
		startGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					int chosenShipIndex = -1;
					for (int i = 0; i < radioButtons.size(); i++) {
						if (radioButtons.get(i).isSelected()) {
							chosenShipIndex = i;
						}
					}
					if (chosenShipIndex == -1) {
						throw new IllegalArgumentException("Please choose a ship using the radio buttons!");
					}
					
					if (!nameTextField.getText().matches("^([a-zA-Z]| )*$")) {
						throw new IllegalArgumentException("Please refrain from using numbers or special characters in your name.");
					} else if (nameTextField.getText().length() > 15 || nameTextField.getText().length() < 3) {
						throw new IllegalArgumentException("Please enter a name between 3 and 15 characters");
					}
					errorDisplayLabel.setText("");
					
					manager.setPlayerName(nameTextField.getText());
					manager.setStartGameDays(daysSlider.getValue());
					manager.setActiveShip(manager.getShips().get(chosenShipIndex));
					
					// Initialises the chosen ship's cargo
					for (Item item: manager.getItems()) {
						manager.getActiveShip().initialiseCargo(item);
					}
					
					finishedWindow();
					
				} catch (Exception excptn) {
					errorDisplayLabel.setText(excptn.getMessage());
				}
				
				
			}
		});
		startGameButton.setBounds(266, 297, 188, 23);
		window.getContentPane().add(startGameButton);
		
		// Ship Label Panel #################################################
		JPanel shipLabelPanel = new JPanel();
		scrollPane.setRowHeaderView(shipLabelPanel);
		shipLabelPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		shipLabelPanel.add(nameLabel);
		
		JLabel cargoLabel = new JLabel("Cargo Size:");
		cargoLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		shipLabelPanel.add(cargoLabel);
		
		JLabel crewLabel = new JLabel("Crew Size:");
		crewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		shipLabelPanel.add(crewLabel);
		
		JLabel durabilityLabel = new JLabel("Durability");
		durabilityLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		shipLabelPanel.add(durabilityLabel);
		
		JLabel speedLabel = new JLabel("Speed:");
		speedLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		shipLabelPanel.add(speedLabel);
		
		
		
		
	}
}
