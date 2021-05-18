import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;

public class GUIApplication {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIApplication window = new GUIApplication();
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
	public GUIApplication() {
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
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBounds(333, 11, 241, 409);
		frame.getContentPane().add(panel);
		panel.setLayout(new CardLayout(0, 0));
		
		JLayeredPane tradingPane = new JLayeredPane();
		panel.add(tradingPane, "name_285505433482700");
		tradingPane.setLayout(null);
		
		JPanel itemsPane = new JPanel();
		itemsPane.setBounds(10, 11, 221, 353);
		tradingPane.add(itemsPane);
		itemsPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		
		
		for (int i = 0; i < 2; i++) {
			JPanel singleItemPane = new JPanel();
			itemsPane.add(singleItemPane);
			singleItemPane.setLayout(null);
			
			JLabel itemName = new JLabel("Item Name");
			itemName.setBounds(0, 0, 221, 15);
			singleItemPane.add(itemName);
			
			JLabel itemValue = new JLabel("Item Value");
			itemValue.setBounds(0, 15, 221, 15);
			singleItemPane.add(itemValue);
			
			JLabel itemWeight = new JLabel("Item Weight");
			itemWeight.setBounds(0, 30, 221, 15);
			singleItemPane.add(itemWeight);
			
			JSlider slider = new JSlider();
			slider.setBounds(10, 45, 200, 20);
			singleItemPane.add(slider);
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
		
		JPanel mapPanel = new JPanel();
		mapPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		mapPanel.setBounds(10, 11, 313, 249);
		frame.getContentPane().add(mapPanel);
		mapPanel.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_2.setBounds(10, 271, 313, 149);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("New label");
		panel_2.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		panel_2.add(lblNewLabel_1);

	}

}
