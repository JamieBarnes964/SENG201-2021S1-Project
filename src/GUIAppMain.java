import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSeparator;
import javax.swing.JTable;

public class GUIAppMain {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIAppMain window = new GUIAppMain();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public GUIAppMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Starmans Cool App");
		frame.setBounds(100, 100, 600, 470);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel interactionPanel = new JPanel();
		interactionPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		interactionPanel.setBounds(333, 11, 241, 409);
		frame.getContentPane().add(interactionPanel);
		interactionPanel.setLayout(new CardLayout(0, 0));
		
		JLayeredPane tradingPane = new JLayeredPane();
		interactionPanel.add(tradingPane, "name_285505433482700");
		tradingPane.setLayout(null);
		
		JPanel itemsPane = new JPanel();
		itemsPane.setBounds(10, 11, 221, 353);
		tradingPane.add(itemsPane);
		itemsPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		
		ArrayList<JSlider> sliderList = new ArrayList<JSlider>();
		for (Item item: GameEnvironment.getActiveIsland().getTrades().keySet()) {
			if (GameEnvironment.getActiveIsland().getTrades().get(item) != 0) {
				JPanel singleItemPane = new JPanel();
				itemsPane.add(singleItemPane);
				singleItemPane.setLayout(null);
				
				
				JLabel itemName = new JLabel(item.getName());
				itemName.setBounds(0, 0, 221, 15);
				singleItemPane.add(itemName);
				
				JLabel itemValue = new JLabel("Price: " + (int) (item.getPrice() * GameEnvironment.getActiveIsland().getTrades().get(item)));
				itemValue.setBounds(0, 15, 221, 15);
				singleItemPane.add(itemValue);
				
				JLabel itemWeight = new JLabel("Weight: " + item.getWeight());
				itemWeight.setBounds(0, 30, 221, 15);
				singleItemPane.add(itemWeight);
				
				
				JSlider slider = new JSlider();
				slider.setBounds(10, 45, 200, 20);
				singleItemPane.add(slider);
				slider.addChangeListener(new ChangeListener() {
					
			        public void stateChanged(ChangeEvent ce) {
			        	if (!slider.getValueIsAdjusting()) {
			        		System.out.println(((JSlider) ce.getSource()).getValue());
						}
			            
			        }
				});
				sliderList.add(slider);
				
			}
		}
		
		
		
		
		JButton buySellButton = new JButton("Buy / Sell");
		buySellButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(buySellButton, "Are you sure?", "Confim", 2);
				System.out.println(choice);
				
			}
		});
		buySellButton.setBounds(10, 375, 89, 23);
		tradingPane.add(buySellButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(142, 375, 89, 23);
		tradingPane.add(cancelButton);
		
		JLayeredPane mainOptionsPane = new JLayeredPane();
		interactionPanel.add(mainOptionsPane, "name_371154961016800");
		mainOptionsPane.setLayout(new GridLayout(0, 1, 0, 40));
		
		JSeparator separator = new JSeparator();
		mainOptionsPane.add(separator);
		
		JButton btnNewButton = new JButton("New button");
		mainOptionsPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		mainOptionsPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("New button");
		mainOptionsPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("New button");
		mainOptionsPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("New button");
		mainOptionsPane.add(btnNewButton_4);
		
		JSeparator separator_1 = new JSeparator();
		mainOptionsPane.add(separator_1);
		
		JPanel mapPanel = new JPanel();
		mapPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		mapPanel.setBounds(10, 11, 313, 249);
		frame.getContentPane().add(mapPanel);
		mapPanel.setLayout(null);
		
		JPanel statsPanel = new JPanel();
		statsPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		statsPanel.setBounds(10, 271, 313, 149);
		frame.getContentPane().add(statsPanel);
		statsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("New label");
		statsPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		statsPanel.add(lblNewLabel_1);
		
		this.frame.setVisible(true);
	}
}
