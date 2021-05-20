import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JRadioButton;
import java.awt.Color;
import javax.swing.SwingConstants;

public class GUIAppOpen {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIAppOpen window = new GUIAppOpen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUIAppOpen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel playerNameLabel = new JLabel("Name:");
		playerNameLabel.setBounds(10, 11, 46, 14);
		frame.getContentPane().add(playerNameLabel);
		
		JLabel daysLabel = new JLabel("Number of days:");
		daysLabel.setBounds(10, 56, 114, 14);
		frame.getContentPane().add(daysLabel);
		
		textField = new JTextField();
		textField.setBounds(10, 25, 160, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JSlider slider = new JSlider();
		slider.setMajorTickSpacing(1);
		slider.setMaximum(50);
		slider.setMinimum(20);
		slider.setBounds(10, 70, 200, 20);
		frame.getContentPane().add(slider);
		
		JLabel shipLabel = new JLabel("Choose a Ship:");
		shipLabel.setBounds(10, 101, 86, 14);
		frame.getContentPane().add(shipLabel);
		
		JPanel shipsArrayPanel = new JPanel();
		shipsArrayPanel.setBounds(84, 126, 340, 99);
		frame.getContentPane().add(shipsArrayPanel);
		shipsArrayPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		
		// Ship Array Panel #################################################
		
		
		ButtonGroup shipSelectionButtons = new ButtonGroup();
		
		for (Ship ship: GameEnvironment.getShips()) {
			JPanel panel = new JPanel();
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
			
			shipSelectionButtons.add(rdbtnNewRadioButton);;
		}
		
//		JPanel panel = new JPanel();
//		shipsArrayPanel.add(panel);
//		panel.setLayout(new GridLayout(0, 1, 0, 0));
//		
//		JLabel shipNameLabel = new JLabel("Name");
//		panel.add(shipNameLabel);
//		
//		JLabel shipCargoLabel = new JLabel("Cargo Size:");
//		shipCargoLabel.setHorizontalAlignment(SwingConstants.TRAILING);
//		panel.add(shipCargoLabel);
//		
//		JLabel shipCrewLabel = new JLabel("Crew Size:");
//		shipCrewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
//		panel.add(shipCrewLabel);
//		
//		JLabel shipDurabilityLabel = new JLabel("Durability");
//		shipDurabilityLabel.setHorizontalAlignment(SwingConstants.TRAILING);
//		panel.add(shipDurabilityLabel);
//		
//		JLabel shipSpeedLabel = new JLabel("Speed:");
//		shipSpeedLabel.setHorizontalAlignment(SwingConstants.TRAILING);
//		panel.add(shipSpeedLabel);
//		
//		JRadioButton rdbtnNewRadioButton = new JRadioButton("");
//		rdbtnNewRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
//		panel.add(rdbtnNewRadioButton);
		
		
		// Ship Label Panel #################################################
		JPanel shipLabelPanel = new JPanel();
		shipLabelPanel.setBounds(10, 126, 64, 99);
		frame.getContentPane().add(shipLabelPanel);
		shipLabelPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel nameLabel = new JLabel("<html><b>Name:</b></html>");
		shipLabelPanel.add(nameLabel);
		
		JLabel cargoLabel = new JLabel("Cargo Size:");
		shipLabelPanel.add(cargoLabel);
		
		JLabel crewLabel = new JLabel("Crew Size:");
		shipLabelPanel.add(crewLabel);
		
		JLabel durabilityLabel = new JLabel("Durability");
		shipLabelPanel.add(durabilityLabel);
		
		JLabel speedLabel = new JLabel("Speed:");
		shipLabelPanel.add(speedLabel);
		
		JLabel spacingLabel = new JLabel("");
		shipLabelPanel.add(spacingLabel);
		
		JLabel errorDisplayLabel = new JLabel("temp error text");
		errorDisplayLabel.setForeground(Color.RED);
		errorDisplayLabel.setBounds(10, 236, 315, 14);
		frame.getContentPane().add(errorDisplayLabel);
		
		JButton startGameButton = new JButton("Start New Game");
		startGameButton.setBounds(289, 227, 135, 23);
		frame.getContentPane().add(startGameButton);
		
		
		
		this.frame.setVisible(true);
	}
}
