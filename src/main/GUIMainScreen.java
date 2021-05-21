package main;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class GUIMainScreen {

	private JFrame window;
	private ArrayList<JRadioButton> routeRadioButtons;

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
	
	
	
	private void switchCard(JPanel panel, JLayeredPane card) {
		panel.removeAll();
		panel.add(card);
		panel.repaint();
		panel.revalidate();
	}
	
	
	
	/**
	 * Updates all sliders in the given slider list to their maximum values that can be bought
	 * @param slider
	 * @param sliderList
	 * @param buyableItems
	 */
	private void buySlidersMax(JSlider slider, ArrayList<JSlider> sliderList, ArrayList<Item> buyableItems) {
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
			if (GameEnvironment.getActiveIsland().getTrades().get(item) != 0) {
				tradableItems.add(item);
				
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
				        		buySlidersMax(slider, sliderList, tradableItems);
							}
				        }
					});
					sliderList.add(slider);
					for (JSlider tempSlider: sliderList) {
						buySlidersMax(tempSlider, sliderList, tradableItems);
					}
				} else { // else Selling
					JSlider slider = new JSlider(0, GameEnvironment.getActiveShip().getCargo().get(item), 0);
					slider.setBounds(10, 45, 175, 20);
					singleItemPane.add(slider);
					slider.addChangeListener(new ChangeListener() {
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
		if (buySell == 0) { // if buying then button is buy
			buySellButton = new JButton("Buy");
		} else { // else selling and button is sell
			buySellButton = new JButton("Sell");
		}
		buySellButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(buySellButton, "Are you sure you want to " + buySellButton.getText() + " these items?", "Confim", 0);
				if (choice == 0) { // if confirmed
					for (int i = 0; i < sliderList.size(); i++) {
						int quantity = sliderList.get(i).getValue();
						Item sliderItem = tradableItems.get(i);
						GameEnvironment.addStatTraded(quantity);
						if (buySell == 0) { // if buying
							GameEnvironment.addMoney((int) -(quantity * sliderItem.getPrice() * GameEnvironment.getActiveIsland().getTrades().get(sliderItem))); // Remove Money
							GameEnvironment.getActiveShip().addItemCargo(sliderItem, quantity);	// Add Item * Quantity to Cargo
						} else { // if selling
							GameEnvironment.addMoney((int) (quantity * sliderItem.getPrice() * GameEnvironment.getActiveIsland().getTrades().get(sliderItem))); // Add Money
							GameEnvironment.getActiveShip().addItemCargo(sliderItem, -quantity); // Remove Item * Quantity from Cargo
						}
					}
				
					switchCard(parentPanel, returnToCard);
				}
			}
		});
		buySellButton.setBounds(10, 375, 89, 23);
		addToCard.add(buySellButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(142, 375, 89, 23);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchCard(parentPanel, returnToCard);
			}
		});
		addToCard.add(cancelButton);
		
		switchCard(parentPanel, addToCard);
	}
	
	
	
	/**
	 * Updates the Ship Cargo Card to display the current cargo
	 * @param itemListPanel
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
	 * 
	 * @param routeListPanel
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
	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		window = new JFrame();
		window.setResizable(false);
		window.setTitle("Island Trader - " + GameEnvironment.getPlayerName());
		window.setBounds(100, 100, 600, 470);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setLayout(null);
		
		JPanel interactionPanel = new JPanel();
		interactionPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		interactionPanel.setBounds(333, 11, 241, 409);
		window.getContentPane().add(interactionPanel);
		interactionPanel.setLayout(new CardLayout(0, 0));
		
		JLayeredPane mainOptionsCard = new JLayeredPane();
		interactionPanel.add(mainOptionsCard, "name_371154961016800");
		mainOptionsCard.setLayout(null);
		
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
		quitGameButton.setBounds(40, 344, 159, 23);
		mainOptionsCard.add(quitGameButton);
		
		JLayeredPane buySellCard = new JLayeredPane();
		interactionPanel.add(buySellCard, "name_476595512279500");
		
		JButton buyButton = new JButton("Buy");
		buyButton.setBounds(40, 125, 159, 23);
		buySellCard.add(buyButton);
		
		JButton sellButton = new JButton("Sell");
		sellButton.setBounds(40, 226, 159, 23);
		buySellCard.add(sellButton);
		
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
		
		
		
		JPanel mapPanel = new JPanel();
		mapPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		mapPanel.setBounds(10, 11, 313, 249);
		window.getContentPane().add(mapPanel);
		mapPanel.setLayout(null);
		
		JPanel statsPanel = new JPanel();
		statsPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		statsPanel.setBounds(10, 271, 313, 149);
		window.getContentPane().add(statsPanel);
		statsPanel.setLayout(null);
		
		JLabel playerMoneyLabel = new JLabel("Money: $" + GameEnvironment.STARTINGMONEY);
		playerMoneyLabel.setBounds(10, 6, 94, 14);
		statsPanel.add(playerMoneyLabel);
		
		JLabel daysRemainingLabel = new JLabel("Days Remaining: " + GameEnvironment.getGameDays());
		daysRemainingLabel.setBounds(196, 6, 116, 14);
		statsPanel.add(daysRemainingLabel);
		
		
		
		JPanel statsSubPanel = new JPanel();
		statsSubPanel.setBounds(1, 23, 311, 125);
		statsPanel.add(statsSubPanel);
		statsSubPanel.setLayout(new CardLayout(0, 0));
		
		// Ship Stats Card
		JLayeredPane shipStatsCard = new JLayeredPane();
		statsSubPanel.add(shipStatsCard, "name_476153800316500");
		shipStatsCard.setLayout(null);
		
			JLabel shipNameLabel = new JLabel(GameEnvironment.getActiveShip().getName());
			shipNameLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
			shipNameLabel.setBounds(10, 11, 119, 14);
			shipStatsCard.add(shipNameLabel);
			
			JLabel crewSizeLabel = new JLabel("Crew: " + GameEnvironment.getActiveShip().getCrewSize());
			crewSizeLabel.setBounds(10, 36, 94, 14);
			shipStatsCard.add(crewSizeLabel);
			
			JLabel cargoCapacityLabel = new JLabel("Cargo: " + (GameEnvironment.getActiveShip().getCapacity() - GameEnvironment.getActiveShip().getAvailableCargoSpace()) + "/" + GameEnvironment.getActiveShip().getCapacity());
			cargoCapacityLabel.setBounds(10, 61, 94, 14);
			shipStatsCard.add(cargoCapacityLabel);
			
			JLabel lblDurabilitycurrentmax = new JLabel("Durability: " + (int) GameEnvironment.getActiveShip().getDurability() + "/" + GameEnvironment.getActiveShip().getMaxDurability());
			lblDurabilitycurrentmax.setBounds(10, 86, 94, 14);
			shipStatsCard.add(lblDurabilitycurrentmax);
			
			JLabel shipSpeedLabel = new JLabel("Speed: " + GameEnvironment.getActiveShip().getSpeed());
			shipSpeedLabel.setBounds(166, 36, 94, 14);
			shipStatsCard.add(shipSpeedLabel);
			
			JLabel sailingCost = new JLabel("Cost to Sail / Day: $" + GameEnvironment.getActiveShip().getCrewSize() * GameEnvironment.DAILYPAYPERHEAD);
			sailingCost.setBounds(166, 61, 135, 14);
			shipStatsCard.add(sailingCost);
		
		
		// Ship Cargo Card
		JLayeredPane shipCargoCard = new JLayeredPane();
		statsSubPanel.add(shipCargoCard, "name_541647390610900");
			
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
					
					JLabel baseValueLabel = new JLabel("Base Value: ");
					labelPanel.add(baseValueLabel);
					
					JLabel cargoTitleLabel = new JLabel("Cargo");
					cargoTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
					cargoTitleLabel.setBounds(10, 0, 46, 14);
					shipCargoCard.add(cargoTitleLabel);
		
		
		
		// Switch Card Button Actions
		
			// Main Options Card Buttons
		
			shipStatsButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					shipNameLabel.setText(GameEnvironment.getActiveShip().getName());
					crewSizeLabel.setText("Crew: " + GameEnvironment.getActiveShip().getCrewSize());
					cargoCapacityLabel.setText("Cargo: " + (GameEnvironment.getActiveShip().getCapacity() - GameEnvironment.getActiveShip().getAvailableCargoSpace()) + "/" + GameEnvironment.getActiveShip().getCapacity());
					lblDurabilitycurrentmax.setText("Durability: " + (int) GameEnvironment.getActiveShip().getDurability() + "/" + GameEnvironment.getActiveShip().getMaxDurability());
					shipSpeedLabel.setText("Speed: " + GameEnvironment.getActiveShip().getSpeed());
					sailingCost.setText("Cost to Sail / Day: $" + GameEnvironment.getActiveShip().getCrewSize() * GameEnvironment.DAILYPAYPERHEAD);
					switchCard(statsSubPanel, shipStatsCard);
				}
			});
			
			tradeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateCargoCard(cargoItemsViewportPanel);
					switchCard(statsSubPanel, shipCargoCard);
					switchCard(interactionPanel, buySellCard);
				}
			});
				
				// Buy Sell Card Buttons
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

			viewCargoButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateCargoCard(cargoItemsViewportPanel);
					switchCard(statsSubPanel, shipCargoCard);
				}
			});
			
		
		sailButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				routeRadioButtons = updateSailRoutesCard(routeScrollPaneViewport);
				switchCard(interactionPanel, sailingCard);
			}
		});
			
			// Sail Card Buttons
			sailCardSailButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (JRadioButton radButton: routeRadioButtons) {
						if (radButton.isSelected()) {
							try {
								GameEnvironment.sail(GameEnvironment.getActiveIsland().getRoutes().get(routeRadioButtons.indexOf(radButton)));
								// TODO
							} catch (InsufficientDaysException ex) {
								JOptionPane.showMessageDialog(sailCardSailButton, "There are not enough days remaining to take this Route!");
							} catch (InsufficientFundsException ex) {
								JOptionPane.showMessageDialog(sailCardSailButton, "<html>You do not have enough money to pay your crew for this Route!<br>" + 
																				  "Sell some cargo to be able to afford this route, or take another.</html>");
							} catch (InsufficientRepairsException ex) {
								JOptionPane.showMessageDialog(sailCardSailButton, "Your ship needs to be repaired before sailing!");
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
		
	}
}
