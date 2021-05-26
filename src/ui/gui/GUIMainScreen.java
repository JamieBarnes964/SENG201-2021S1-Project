package ui.gui;

import main.*;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
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

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ImageIcon;

public class GUIMainScreen {

	private JFrame window;
	private ArrayList<JRadioButton> routeRadioButtons;
	private JLabel playerMoneyLabel;
	private JLabel daysRemainingLabel;
	private JLabel activeIslandNameLabel;
	private JPanel statsSubPanel;


	/**
	 * Create the application.
	 */
	public GUIMainScreen() {
		initialize();
		this.window.setVisible(true);
	}
	
	public void closeWindow() {
		window.dispose();
	}
	
	public void finishedWindow() {
		GameEnvironment.closeGUIMainScreen(this);
	}
	
	
	/**
	 * A tool for switching cards in a JPanel with a card layout
	 * @param panel	a panel with a card layout which will change to the given card
	 * @param card	a JLayeredPane card which will be displayed on the given panel
	 */
	private void switchCard(JPanel panel, JLayeredPane card) {
		panel.removeAll();
		panel.add(card);
		panel.repaint();
		panel.revalidate();
	}
	
	
	/**
	 * Updates the main game values of playerMoney and daysRemaining within the GUI
	 */
	private void updateGameValues() {
		playerMoneyLabel.setText("Money: $" + GameEnvironment.getPlayerMoney());
		daysRemainingLabel.setText("Days Remaining: " + GameEnvironment.getGameDays());
		activeIslandNameLabel.setText(GameEnvironment.getActiveIsland().getName());
	}
	
	
	
	/**
	 * Updates all sliders in the given slider list to their maximum values that can be bought of their respective item
	 * @param slider		the slider that is being 
	 * @param sliderList
	 * @param buyableItems
	 */
	private void buySlidersMax(ArrayList<JSlider> sliderList, ArrayList<Item> buyableItems) {
		int tempAvailableSpace = GameEnvironment.getActiveShip().getAvailableCargoSpace();
		int tempAvailableMoney = GameEnvironment.getPlayerMoney();
		for (int i = 0; i < sliderList.size(); i++) {
			tempAvailableSpace -= sliderList.get(i).getValue() * buyableItems.get(i).getWeight();
			tempAvailableMoney -= sliderList.get(i).getValue() * buyableItems.get(i).getPrice() * GameEnvironment.getActiveIsland().getTrades().get(buyableItems.get(i));
		}
		for (int i = 0; i < sliderList.size(); i++) {
			int ithItemValue = (int) (buyableItems.get(i).getPrice() * GameEnvironment.getActiveIsland().getTrades().get(buyableItems.get(i)));
			sliderList.get(i).setMaximum(Math.min(Math.floorDiv(tempAvailableSpace + sliderList.get(i).getValue() * buyableItems.get(i).getWeight(), buyableItems.get(i).getWeight()),
  				   						 		  Math.floorDiv(tempAvailableMoney + sliderList.get(i).getValue() * ithItemValue, ithItemValue)));
		}
	}
	
	
	
	/**
	 * 
	 * @param buySell
	 * @param parentPanel
	 * @param addToCard
	 * @param returnToCard
	 */
	private void tradingCardBuyOrSell(int buySell, JPanel parentPanel, JLayeredPane addToCard, JLayeredPane returnToCard) {
		addToCard.removeAll();
		
		JPanel itemsPane = new JPanel();
		itemsPane.setBounds(10, 11, 221, 353);
		addToCard.add(itemsPane);
		itemsPane.setLayout(new GridLayout(0, 1, 0, 0));

		ArrayList<JSlider> sliderList = new ArrayList<JSlider>();
		ArrayList<Item> tradableItems = new ArrayList<Item>();
		
		for (Item item: GameEnvironment.getActiveIsland().getTrades().keySet()) {
			if (GameEnvironment.getActiveIsland().getTrades().get(item) != 0) { // If the item is traded by the island
				tradableItems.add(item);
				
				JPanel singleItemPane = new JPanel();
				itemsPane.add(singleItemPane);
				singleItemPane.setLayout(null);
				
				JLabel itemName = new JLabel(item.getName());
				itemName.setBounds(0, 0, 221, 15);
				singleItemPane.add(itemName);
				
				// Display price and difference from the default value
				DecimalFormat numberFormat = new DecimalFormat("#");
				String itemValueString = "Price: $" + (int) (item.getPrice() * GameEnvironment.getActiveIsland().getTrades().get(item));
				if (GameEnvironment.getActiveIsland().getTrades().get(item) > 1) {
					itemValueString += " (+" + numberFormat.format((GameEnvironment.getActiveIsland().getTrades().get(item) - 1) * 100) + "%)";
				} else {
					itemValueString += " (" + numberFormat.format((GameEnvironment.getActiveIsland().getTrades().get(item) - 1) * 100) + "%)";
				}
				JLabel itemValue = new JLabel(itemValueString);
				itemValue.setBounds(0, 15, 221, 15);
				singleItemPane.add(itemValue);
				
				JLabel itemWeight = new JLabel("Weight: " + item.getWeight());
				itemWeight.setBounds(0, 30, 221, 15);
				singleItemPane.add(itemWeight);
				
				JLabel quantityLabel = new JLabel("0");
				quantityLabel.setBounds(195, 48, 46, 14);
				singleItemPane.add(quantityLabel);
				
				if (buySell == 0) { // If Buying
					JSlider slider = new JSlider(0, 0, 0);
					slider.setBounds(10, 45, 175, 20);
					singleItemPane.add(slider);
					slider.addChangeListener(new ChangeListener() {
				        public void stateChanged(ChangeEvent ce) {
				        	quantityLabel.setText("" + slider.getValue());
				        	if (!slider.getValueIsAdjusting()) {
				        		buySlidersMax(sliderList, tradableItems);
							}
				        }
					});
					sliderList.add(slider);
					buySlidersMax(sliderList, tradableItems);
				} else { // else Selling
					JSlider slider = new JSlider(0, GameEnvironment.getActiveShip().getCargo().get(item), 0); // set slider max to the number of items 
					slider.setBounds(10, 45, 175, 20);
					singleItemPane.add(slider);
					slider.addChangeListener(new ChangeListener() { // Simple display of the slider value
				        public void stateChanged(ChangeEvent ce) {
				        	quantityLabel.setText("" + slider.getValue());
				        }
					});
					sliderList.add(slider);
				}
			}
		}
		// Buy or Sell button
		JButton buySellButton;
		if (buySell == 0) { // if buying then button is "Buy"
			buySellButton = new JButton("Buy");
		} else { // else selling and button is "Sell"
			buySellButton = new JButton("Sell");
		}
		buySellButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(window, "Are you sure you want to " + buySellButton.getText() + " these items?", "Confim", 0); // Show confirm Dialog
				if (choice == 0) { // if confirmed
					for (int i = 0; i < sliderList.size(); i++) { // For each index in sliderList
						int quantity = sliderList.get(i).getValue(); // Get the value of the slider at that index
						Item sliderItem = tradableItems.get(i); // Get the item that slider represents
						GameEnvironment.addStatTraded(quantity); // Handle statistics
						if (buySell == 0) { // if buying
							GameEnvironment.addMoney((int) -(quantity * sliderItem.getPrice() * GameEnvironment.getActiveIsland().getTrades().get(sliderItem))); // Remove Money
							GameEnvironment.getActiveShip().addItemCargo(sliderItem, quantity);	// Add Item * Quantity to Cargo
						} else { // if selling
							GameEnvironment.addMoney((int) (quantity * sliderItem.getPrice() * GameEnvironment.getActiveIsland().getTrades().get(sliderItem))); // Add Money
							GameEnvironment.getActiveShip().addItemCargo(sliderItem, -quantity); // Remove Item * Quantity from Cargo
						}
					}
					updateGameValues();
					switchCard(parentPanel, returnToCard);
					switchCard(statsSubPanel, new JLayeredPane());
				}
			}
		});
		buySellButton.setBounds(10, 375, 89, 23);
		addToCard.add(buySellButton);
		
		// Cancel Button
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(142, 375, 89, 23);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchCard(parentPanel, returnToCard);
				switchCard(statsSubPanel, new JLayeredPane());
			}
		});
		addToCard.add(cancelButton);
		
		switchCard(parentPanel, addToCard);
	}
	
	
	
	/**
	 * Updates the Ship Cargo Card to display the list of current cargo
	 * @param itemListPanel	the JPanel which the cargo list of cargo will be added to
	 */
	private void updateCargoCard(JPanel itemListPanel) {
		itemListPanel.removeAll();
		for (Item item: GameEnvironment.getActiveShip().getCargo().keySet()) {
			if (GameEnvironment.getActiveShip().getCargo().get(item) != 0) {
				JPanel singleItemPane = new JPanel();
				itemListPanel.add(singleItemPane);
				singleItemPane.setLayout(new GridLayout(0, 1, 0, 0));
				
					JLabel itemName = new JLabel(item.getName());
					itemName.setHorizontalAlignment(SwingConstants.CENTER);
					singleItemPane.add(itemName);
					
					JLabel itemTotalWeight = new JLabel("" + item.getWeight() * GameEnvironment.getActiveShip().getCargo().get(item));
					itemTotalWeight.setHorizontalAlignment(SwingConstants.CENTER);
					singleItemPane.add(itemTotalWeight);
					
					JLabel itemQuantity = new JLabel("" + GameEnvironment.getActiveShip().getCargo().get(item));
					itemQuantity.setHorizontalAlignment(SwingConstants.CENTER);
					singleItemPane.add(itemQuantity);
					
					JLabel itemBaseValue = new JLabel("" + item.getPrice());
					itemBaseValue.setHorizontalAlignment(SwingConstants.CENTER);
					singleItemPane.add(itemBaseValue);
					
			}
		}
	}
	
	
	/**
	 * Handles the Sailing Routes List panel, adds the list of routes to the given JPanel
	 * @param routeListPanel	the panel of which routes will be listed on (usually routeScrollPaneViewport)
	 * @return 
	 */
	private ArrayList<JRadioButton> updateSailRoutesCard(JPanel routeListPanel) {
		routeListPanel.removeAll();
		
		ArrayList<JRadioButton> routeRadioButtons = new ArrayList<JRadioButton>();
		ButtonGroup routeButtonGroup = new ButtonGroup();
		
		for (Route route: GameEnvironment.getActiveIsland().getRoutes()) {
			JPanel singleRoutePane = new JPanel();
			routeListPanel.add(singleRoutePane);
			singleRoutePane.setLayout(null);
			
			JLabel routeIslandNameLabel = new JLabel("To " + route.getDestinationIsland().getName());
			routeIslandNameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			routeIslandNameLabel.setBounds(0, 0, 142, 14);
			singleRoutePane.add(routeIslandNameLabel);
			
			JLabel routeDangerLabel;
			if (route.getEventChance() >= 0.4) {
				routeDangerLabel = new JLabel("Dangerous");
				routeDangerLabel.setForeground(Color.RED);
			} else if (route.getEventChance() >= 0.3) {
				routeDangerLabel = new JLabel("Risky");
				routeDangerLabel.setForeground(Color.ORANGE);
			} else {
				routeDangerLabel = new JLabel("Relatively Safe");
				routeDangerLabel.setForeground(new Color(0, 153, 51));
			}
			routeDangerLabel.setBounds(0, 18, 114, 14);
			singleRoutePane.add(routeDangerLabel);
			
			JLabel routeDaysLabel = new JLabel("Days: " + route.getDays());
			routeDaysLabel.setBounds(0, 33, 114, 14);
			singleRoutePane.add(routeDaysLabel);
			
			JLabel routeCostLabel = new JLabel("Cost: $" + route.getDays() * GameEnvironment.getActiveShip().getCrewSize() * GameEnvironment.DAILYPAYPERHEAD);
			routeCostLabel.setBounds(0, 48, 114, 14);
			singleRoutePane.add(routeCostLabel);
			
			JRadioButton routeRadioButton = new JRadioButton();
			routeRadioButton.setBounds(190, 21, 21, 23);
			singleRoutePane.add(routeRadioButton);
			routeRadioButtons.add(routeRadioButton);
			routeButtonGroup.add(routeRadioButton);
		}
		return routeRadioButtons;
	}
	
	
	
	private void updateMapBoatLocation(ArrayList<JLabel> islandIconList, JLabel boat) {
		JLabel activeIslandIcon = islandIconList.get(GameEnvironment.getIslands().indexOf(GameEnvironment.getActiveIsland()));
		boat.setBounds(activeIslandIcon.getX() - 20, activeIslandIcon.getY(), 50, 50);
	}
	
	
	/**
	 * Updates the ShipStatsCard to most recent values
	 * @param parentPanel	the parent panel which will be updated (usually shipStatsCard)
	 */
	private void updateShipStatsCard(JLayeredPane parentPanel) {
		parentPanel.removeAll();
		
		JLabel shipNameLabel = new JLabel(GameEnvironment.getActiveShip().getName());
		shipNameLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		shipNameLabel.setBounds(10, 11, 135, 14);
		parentPanel.add(shipNameLabel);
		
		JLabel crewSizeLabel = new JLabel("Crew: " + GameEnvironment.getActiveShip().getCrewSize());
		crewSizeLabel.setBounds(10, 36, 124, 14);
		parentPanel.add(crewSizeLabel);
		
		JLabel cargoCapacityLabel = new JLabel("Cargo: " + (GameEnvironment.getActiveShip().getCapacity() - GameEnvironment.getActiveShip().getAvailableCargoSpace()) + "/" + GameEnvironment.getActiveShip().getCapacity());
		cargoCapacityLabel.setBounds(10, 61, 124, 14);
		parentPanel.add(cargoCapacityLabel);
		
		JLabel lblDurabilitycurrentmax = new JLabel("Durability: " + (int) GameEnvironment.getActiveShip().getDurability() + "/" + GameEnvironment.getActiveShip().getMaxDurability());
		lblDurabilitycurrentmax.setBounds(10, 86, 135, 14);
		parentPanel.add(lblDurabilitycurrentmax);
		
		JLabel shipSpeedLabel = new JLabel("Speed: " + GameEnvironment.getActiveShip().getSpeed());
		shipSpeedLabel.setBounds(144, 36, 105, 14);
		parentPanel.add(shipSpeedLabel);
		
		JLabel sailingCost = new JLabel("Cost to Sail / Day: $" + GameEnvironment.getActiveShip().getCrewSize() * GameEnvironment.DAILYPAYPERHEAD);
		sailingCost.setBounds(144, 61, 167, 14);
		parentPanel.add(sailingCost);
		
		JButton repairButton = new JButton("Repair");
		if (GameEnvironment.getActiveShip().getNeedRepairs()) {
			repairButton.setEnabled(true);
		} else {
			repairButton.setEnabled(false);
		}
		repairButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(window, "The repairs will cost $" + GameEnvironment.getRepairCost());
				if (GameEnvironment.addMoney(-GameEnvironment.getRepairCost())) {
					GameEnvironment.getActiveShip().repairShip();
					updateShipStatsCard(parentPanel);
					updateGameValues();
				} else {
					JOptionPane.showMessageDialog(window, "<html>You do not have enough money to pay for repairs!<br>" + 
							  							  "Sell some cargo to be able to afford the repairs.</html>");
				}
			}
		});
		repairButton.setBounds(155, 82, 89, 23);
		parentPanel.add(repairButton);
	}
	
	
	
	/**
	 * Initialize the contents of the window.
	 */
	private void initialize() {
		window = new JFrame();
		window.setResizable(false);
		window.setTitle("Island Trader - " + GameEnvironment.getPlayerName());
		window.setBounds(100, 100, 600, 470);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setLayout(null);
		
		
		
		// Map Panel
		JLayeredPane mapPanel = new JLayeredPane();
		mapPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		mapPanel.setBounds(261, 11, 313, 249);
		window.getContentPane().add(mapPanel);
		mapPanel.setLayout(null);
			
			ArrayList<JLabel> islandIconList = new ArrayList<JLabel>();
			
			JLabel islandMahkarnIcon = new JLabel("");
			mapPanel.setLayer(islandMahkarnIcon, 1);
			islandMahkarnIcon.setIcon(new ImageIcon(GUIMainScreen.class.getResource("/img/Island.png")));
			islandMahkarnIcon.setToolTipText("Mahkarn");
			islandMahkarnIcon.setBounds(223, 182, 50, 50);
			mapPanel.add(islandMahkarnIcon);
			islandIconList.add(islandMahkarnIcon);
			
			JLabel islandTolsetReefIcon = new JLabel("");
			mapPanel.setLayer(islandTolsetReefIcon, 1);
			islandTolsetReefIcon.setToolTipText("<html>Tolset Reef<br>obama<html>");
			islandTolsetReefIcon.setIcon(new ImageIcon(GUIMainScreen.class.getResource("/img/Island.png")));
			islandTolsetReefIcon.setBounds(235, 11, 57, 50);
			mapPanel.add(islandTolsetReefIcon);
			islandIconList.add(islandTolsetReefIcon);
			
			JLabel islandAlegateIcon = new JLabel("");
			mapPanel.setLayer(islandAlegateIcon, 1);
			islandAlegateIcon.setIcon(new ImageIcon(GUIMainScreen.class.getResource("/img/Island.png")));
			islandAlegateIcon.setToolTipText("Alegate");
			islandAlegateIcon.setBounds(22, 70, 50, 50);
			mapPanel.add(islandAlegateIcon);
			islandIconList.add(islandAlegateIcon);
			
			JLabel islandPardeaIcon = new JLabel("");
			mapPanel.setLayer(islandPardeaIcon, 1);
			islandPardeaIcon.setIcon(new ImageIcon(GUIMainScreen.class.getResource("/img/Island.png")));
			islandPardeaIcon.setToolTipText("Pardea");
			islandPardeaIcon.setBounds(136, 89, 50, 50);
			mapPanel.add(islandPardeaIcon);
			islandIconList.add(islandPardeaIcon);
			
			JLabel islandErbestIcon = new JLabel("");
			mapPanel.setLayer(islandErbestIcon, 1);
			islandErbestIcon.setIcon(new ImageIcon(GUIMainScreen.class.getResource("/img/Island.png")));
			islandErbestIcon.setToolTipText("Erberst");
			islandErbestIcon.setBounds(56, 189, 50, 50);
			mapPanel.add(islandErbestIcon);
			islandIconList.add(islandErbestIcon);
			
			DecimalFormat numberFormat = new DecimalFormat("#");
			for (int i = 0; i < islandIconList.size(); i++) {
				Island tempIsland = GameEnvironment.getIslands().get(i);
				String toolTipText = "<html>" + tempIsland.getName() + "<br>Trades:<br>";
				for (Item item: tempIsland.getTrades().keySet()) {
					if (tempIsland.getTrades().get(item) != 0) {
						toolTipText += item.getName() + "<br>$" + (int) (item.getPrice() * tempIsland.getTrades().get(item));
						if (tempIsland.getTrades().get(item) > 1) {
							toolTipText += " (+" + numberFormat.format((tempIsland.getTrades().get(item) - 1) * 100) + "%)<br>";
						} else {
							toolTipText += " (" + numberFormat.format((tempIsland.getTrades().get(item) - 1) * 100) + "%)<br>";
						}
					}
				}
				islandIconList.get(i).setToolTipText(toolTipText);
			}
			
			JLabel islandTolsetReefLabel = new JLabel("Tolset Reef");
			islandTolsetReefLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			mapPanel.setLayer(islandTolsetReefLabel, 1);
			islandTolsetReefLabel.setBounds(136, 21, 101, 20);
			mapPanel.add(islandTolsetReefLabel);
			
			JLabel islandAlegateLabel = new JLabel("Alegate");
			mapPanel.setLayer(islandAlegateLabel, 1);
			islandAlegateLabel.setBounds(71, 79, 73, 20);
			mapPanel.add(islandAlegateLabel);
			
			JLabel islandPardeaLabel = new JLabel("Pardea");
			mapPanel.setLayer(islandPardeaLabel, 1);
			islandPardeaLabel.setBounds(189, 111, 73, 20);
			mapPanel.add(islandPardeaLabel);
			
			JLabel islandErbestLabel = new JLabel("Erberst");
			islandErbestLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			mapPanel.setLayer(islandErbestLabel, 1);
			islandErbestLabel.setBounds(0, 223, 60, 20);
			mapPanel.add(islandErbestLabel);
			
			JLabel islandMahkarnLabel = new JLabel("Mahkarn");
			mapPanel.setLayer(islandMahkarnLabel, 1);
			islandMahkarnLabel.setBounds(253, 225, 60, 20);
			mapPanel.add(islandMahkarnLabel);
			
			JLabel mapBoatIcon = new JLabel();
			mapBoatIcon.setIcon(new ImageIcon(GUIMainScreen.class.getResource("/img/ship.png")));
			mapPanel.setLayer(mapBoatIcon, 2);
			mapBoatIcon.setBounds(0, 0, 50, 50);
			mapPanel.add(mapBoatIcon);
			
			JPanel seaColourPanel = new JPanel();
			seaColourPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
			mapPanel.setLayer(seaColourPanel, -1);
			seaColourPanel.setBackground(new Color(176, 224, 230));
			seaColourPanel.setBounds(0, 0, 313, 249);
			mapPanel.add(seaColourPanel);
			
			JLabel islandRoutesMapImg = new JLabel();
			mapPanel.setLayer(islandRoutesMapImg, 0);
			islandRoutesMapImg.setIcon(new ImageIcon(GUIMainScreen.class.getResource("/img/routeMapImg.png")));
			islandRoutesMapImg.setBounds(0, 0, 313, 249);
			mapPanel.add(islandRoutesMapImg);
			
			updateMapBoatLocation(islandIconList, mapBoatIcon);
		
		
		
		
		// Iteraction Panel
		JPanel interactionPanel = new JPanel();
		interactionPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		interactionPanel.setBounds(10, 11, 241, 409);
		window.getContentPane().add(interactionPanel);
		interactionPanel.setLayout(new CardLayout(0, 0));
		
			JLayeredPane mainOptionsCard = new JLayeredPane();
			interactionPanel.add(mainOptionsCard, "name_371154961016800");
			mainOptionsCard.setLayout(null);
			
			activeIslandNameLabel = new JLabel(GameEnvironment.getActiveIsland().getName());
			activeIslandNameLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
			activeIslandNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			activeIslandNameLabel.setBounds(40, 11, 159, 23);
			mainOptionsCard.add(activeIslandNameLabel);
			
			JButton shipStatsButton = new JButton("View Ship Stats");
			shipStatsButton.setBounds(40, 66, 159, 23);
			mainOptionsCard.add(shipStatsButton);
			
			JButton viewCargoButton = new JButton("View Cargo");
			viewCargoButton.setBounds(40, 129, 159, 23);
			mainOptionsCard.add(viewCargoButton);
			
			JButton tradeButton = new JButton("Trade");
			tradeButton.setBounds(40, 192, 159, 23);
			mainOptionsCard.add(tradeButton);
			
			JButton sailButton = new JButton("Sail");
			sailButton.setBounds(40, 255, 159, 23);
			mainOptionsCard.add(sailButton);
			
			JButton quitGameButton = new JButton("Quit Game");
			quitGameButton.setBounds(40, 373, 159, 23);
			mainOptionsCard.add(quitGameButton);
		
			
			
		// Buy Sell Choice Card
		JLayeredPane buySellCard = new JLayeredPane();
		interactionPanel.add(buySellCard, "name_476595512279500");
		
			JButton buyButton = new JButton("Buy");
			buyButton.setBounds(40, 125, 159, 23);
			buySellCard.add(buyButton);
			
			JButton sellButton = new JButton("Sell");
			sellButton.setBounds(40, 226, 159, 23);
			buySellCard.add(sellButton);
		
		// Trading Card
		JLayeredPane tradingCard = new JLayeredPane();
		interactionPanel.add(tradingCard, "name_285505433482700");
		tradingCard.setLayout(null);
		
		
		
		// Sailing Interaction Card
		JLayeredPane sailingCard = new JLayeredPane();
		interactionPanel.add(sailingCard, "name_454086212272400");
		
			JScrollPane routeScrollPane = new JScrollPane();
			routeScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			routeScrollPane.setViewportBorder(null);
			routeScrollPane.setBounds(10, 11, 219, 324);
			sailingCard.add(routeScrollPane);
			
				JPanel routeScrollPaneViewport = new JPanel();
				routeScrollPane.setViewportView(routeScrollPaneViewport);
				routeScrollPaneViewport.setLayout(new GridLayout(0, 1, 0, 0));
			
			JButton sailCardSailButton = new JButton("Sail");
			sailCardSailButton.setBounds(10, 373, 89, 23);
			sailingCard.add(sailCardSailButton);
			
			JButton sailCardCancelButton = new JButton("Cancel");
			sailCardCancelButton.setBounds(140, 373, 89, 23);
			sailingCard.add(sailCardCancelButton);
		
		
		// Stats Panel
		
		JPanel statsPanel = new JPanel();
		statsPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		statsPanel.setBounds(261, 271, 313, 149);
		window.getContentPane().add(statsPanel);
		statsPanel.setLayout(null);
		
			playerMoneyLabel = new JLabel("Money: $" + GameEnvironment.STARTINGMONEY);
			playerMoneyLabel.setBounds(10, 6, 135, 14);
			statsPanel.add(playerMoneyLabel);
			
			daysRemainingLabel = new JLabel("Days Remaining: " + GameEnvironment.getGameDays());
			daysRemainingLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			daysRemainingLabel.setBounds(155, 6, 148, 14);
			statsPanel.add(daysRemainingLabel);
		
		
			// Stats Sub Panel
			statsSubPanel = new JPanel();
			statsSubPanel.setBounds(1, 23, 311, 125);
			statsPanel.add(statsSubPanel);
			statsSubPanel.setLayout(new CardLayout(0, 0));
			
			
				// Ship Stats Card
				JLayeredPane shipStatsCard = new JLayeredPane();
				statsSubPanel.add(shipStatsCard, "name_476153800316500");
				shipStatsCard.setLayout(null);
				
					updateShipStatsCard(shipStatsCard);
				
	
				// Ship Cargo Card
				JLayeredPane shipCargoCard = new JLayeredPane();
				statsSubPanel.add(shipCargoCard, "name_541647390610900");
					
					JLabel cargoTitleLabel = new JLabel("Cargo");
					cargoTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
					cargoTitleLabel.setBounds(10, 0, 46, 14);
					shipCargoCard.add(cargoTitleLabel);
					
					JScrollPane scrollPane = new JScrollPane();
					scrollPane.setViewportBorder(null);
					scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
					scrollPane.setBounds(10, 15, 291, 99);
					shipCargoCard.add(scrollPane);
					
						JPanel cargoItemsViewportPanel = new JPanel();
						scrollPane.setViewportView(cargoItemsViewportPanel);
						cargoItemsViewportPanel.setLayout(new GridLayout(1, 0, 0, 0));
						
							updateCargoCard(cargoItemsViewportPanel);
							
							JPanel labelPanel = new JPanel();
							scrollPane.setRowHeaderView(labelPanel);
							labelPanel.setLayout(new GridLayout(0, 1, 0, 0));
							
							JLabel itemNameLabel = new JLabel("Item:");
							labelPanel.add(itemNameLabel);
							
							JLabel totalWeightLabel = new JLabel("Total Weight:");
							labelPanel.add(totalWeightLabel);
							
							JLabel quantityLabel = new JLabel("Quantity:");
							labelPanel.add(quantityLabel);
							
							JLabel baseValueLabel = new JLabel("Base Value:");
							labelPanel.add(baseValueLabel);
		
		
		
		// Switch Card Button Actions
		
			// Main Options Card Buttons
			
			// Shows ship statistics
			shipStatsButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateShipStatsCard(shipStatsCard);
					switchCard(statsSubPanel, shipStatsCard);
				}
			});
			
			// Trade Option Button
			tradeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateCargoCard(cargoItemsViewportPanel);
					switchCard(statsSubPanel, shipCargoCard);
					switchCard(interactionPanel, buySellCard);
				}
			});
				
				// Buy Sell Card Buttons in Trading Card
				buyButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						tradingCardBuyOrSell(0, interactionPanel, tradingCard, mainOptionsCard);
					}
				});
				
				sellButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						tradingCardBuyOrSell(1, interactionPanel, tradingCard, mainOptionsCard);
					}
				});
			
			// View Cargo Option button
			viewCargoButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateCargoCard(cargoItemsViewportPanel);
					switchCard(statsSubPanel, shipCargoCard);
				}
			});
			
		// Sail Button on Main Options Card takes player to sailing card
		sailButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				routeRadioButtons = updateSailRoutesCard(routeScrollPaneViewport);
				switchCard(interactionPanel, sailingCard);
			}
		});
			
			// Sail Card Buttons
			sailCardSailButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (JRadioButton radButton: routeRadioButtons) { // finds the selected route
						if (radButton.isSelected()) {
							try {
								ArrayList<String> eventStrings = GameEnvironment.sail(GameEnvironment.getActiveIsland().getRoutes().get(routeRadioButtons.indexOf(radButton)));
								for (String string: eventStrings) {
									JOptionPane.showMessageDialog(window, string); // Display given eventStrings from sail method
								}
								updateGameValues();
								switchCard(interactionPanel, mainOptionsCard);
								updateMapBoatLocation(islandIconList, mapBoatIcon);
								switchCard(statsSubPanel, new JLayeredPane());
								if (!GameEnvironment.canContinueGame()) { // If the game cannot continue -> finishedWindow()
									finishedWindow();
								}
							} catch (InsufficientDaysException ex) {
								JOptionPane.showMessageDialog(window, "There are not enough days remaining to take this Route!");
							} catch (InsufficientFundsException ex) {
								JOptionPane.showMessageDialog(window, "<html>You do not have enough money to pay your crew for this Route!<br>" + 
																				  "Sell some cargo to be able to afford this route, or take another.</html>");
							} catch (InsufficientRepairsException ex) {
								JOptionPane.showMessageDialog(window, "Your ship needs to be repaired before sailing!");
							}
						}
					}
				}
			});
			
			sailCardCancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					switchCard(interactionPanel, mainOptionsCard);
				}
			});
		
		
		// Quit game button takes user to game over screen
		quitGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(window, "Are you sure you want to quit the game?", "Quit Game", 0);
				if (choice == 0) {
					finishedWindow();
				}
			}
		});
		
	}
}
