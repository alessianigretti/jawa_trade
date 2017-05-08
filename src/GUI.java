import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.opencsv.CSVReader;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;

/**
 * The Class GUI is used to visually represent the software.
 * 
 * @author Alessia Nigretti
 */
public class GUI extends Application {
	// declaring objects from external classes that will be referenced by the UI
	private TradingExchange exchange = new TradingExchange();
	private Trader selectedTrader = exchange.getSmartTrader();
	private Client selectedClient = new Client(null, 0);
	private Company selectedCompany = null;
	private XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
	private final ObservableList<Order> orders = FXCollections.observableArrayList();

	// declaring labels to update in real-time
	private Label traderLabel = new Label("Trader: " + selectedTrader);
	private Label clientLabel = new Label("Client: " + selectedClient);
	private Label netWorthLabel = new Label("Net Worth: " + selectedClient.getNetWorth());
	private Label marketStatusLabel = new Label("Market Status: " + exchange.marketStatus());
	private Label currentDateTimeLabel = new Label("Day/Time: " + exchange.getDate() + ", " + exchange.getTime());
	private Label currentTradingMode = new Label("Mode: BALANCED");

	// hard-coded ideal window sizes
	private double width = Screen.getPrimary().getBounds().getWidth() / 1.5;
	private double height = Screen.getPrimary().getBounds().getHeight() / 1.3;
	private double scaleHeight = (832 / height) * 1.1;
	private double scaleWidth = (1200 / width) * 1.1;

	// elements shared within class methods
	private ScrollPane commoditiesScroll;
	private ScrollPane newsfeedScroll;
	private double totalSimIterations = 2880.0;
	private double currentSimIterations = 0;
	private ProgressBar progressBar = new ProgressBar(0);
	private Menu traderMenu;

	/*
	 * Sets up the stage.
	 * 
	 * @param stage the stage
	 */
	@Override
	public void start(Stage stage) {
		// creating chart/orders pane (centre of root)
		BorderPane centre = createCentrePane();

		// creating newsfeed pane (right of root)
		ScrollPane rightPane = createRightPane();

		// creating commodities pane (left of root)
		ScrollPane leftPane = createLeftPane();

		// creating toolbar pane (top of root)
		BorderPane topPane = createTopPane();

		// creating root to put together all elements
		BorderPane root = new BorderPane();
		root.setCenter(centre);
		root.setTop(topPane);
		root.setLeft(leftPane);
		root.setRight(rightPane);

		// real-time updates
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws Exception {
				while (true) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							traderLabel.setText("Trader: " + selectedTrader.getTraderName());
							clientLabel.setText("Client: " + selectedClient.getName());
						}
					});
					Thread.sleep(500);
				}
			}
		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();

		// setting up scene and stylesheets
		Scene scene = new Scene(root);
		scene.getStylesheets().add("/resources/com/guigarage/flatterfx/flatterfx.css");

		// setting up stage
		stage.setResizable(false);
		stage.sizeToScene();
		stage.setTitle("Stock Market Simulation by JAWA Trade");
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Creates the centre pane containing chart and orders table.
	 *
	 * @return the border pane to be placed as centre pane
	 */
	private BorderPane createCentrePane() {
		// defining the axes
		final NumberAxis xAxis = new NumberAxis("Time (Minutes)", 1, 2880, 30);
		final NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Value");
		// creating and setting up chart (centre of BorderPane centre)
		final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		lineChart.setMaxHeight(550 / scaleHeight);
		lineChart.setMinHeight(550 / scaleHeight);
		lineChart.setTitle("Stock Monitoring");
		// defining name of series
		series.setName("My portfolio");
		// adding data to chart
		lineChart.getData().add(series);
		// orders panel (bottom of BorderPane centre)
		TabPane bottomPane = createBottomPane();

		// centre panel (centre of root)
		BorderPane centre = new BorderPane();
		centre.setMaxWidth(650 / scaleWidth);
		centre.setMinWidth(650 / scaleWidth);
		centre.setTop(lineChart);
		centre.setBottom(bottomPane);

		return centre;
	}

	/**
	 * Creates the right pane containing the newsfeed.
	 *
	 * @return the scroll pane to be placed as right pane
	 */
	private ScrollPane createRightPane() {
		// right scrollpane (frame for gridpane)
		newsfeedScroll = new ScrollPane();
		newsfeedScroll.setMaxSize(250 / scaleWidth, 787 / scaleHeight);
		newsfeedScroll.setMinSize(250 / scaleWidth, 787 / scaleHeight);

		// gridpane containing all news
		GridPane allNews = displayAllNews();
		newsfeedScroll.setContent(allNews);

		return newsfeedScroll;
	}

	/**
	 * Creates the left pane containing the commodities.
	 *
	 * @return the scroll pane to be placed as left pane
	 */
	private ScrollPane createLeftPane() {
		// left scrollpane (frame for gridpane)
		commoditiesScroll = new ScrollPane();
		commoditiesScroll.setMaxSize(250 / scaleWidth, 787 / scaleHeight);
		commoditiesScroll.setMinSize(250 / scaleWidth, 787 / scaleHeight);

		// gridpane containing all commodities
		GridPane allCommodities = displayAllCommodities();
		commoditiesScroll.setContent(allCommodities);

		return commoditiesScroll;
	}

	/**
	 * Creates the top pane containing the menu bar and the toolbar.
	 *
	 * @return the border pane to be placed as top pane
	 */
	private BorderPane createTopPane() {
		// top borderpane (frame for menubar and toolbar)
		BorderPane topPane = new BorderPane();

		// top menu bar for traders and orders
		MenuBar menuBar = createMenu();
		topPane.setTop(menuBar);

		// bottom toolbar for info panel and simulation button
		BorderPane toolbar = new BorderPane();
		toolbar.setPadding(new Insets(15, 0, 15, 10));

		// gridpane for info panel
		GridPane info = new GridPane();
		info.add(traderLabel, 0, 0);
		info.add(clientLabel, 0, 1);
		info.add(new Label("     "), 1, 0);
		info.add(netWorthLabel, 2, 0);
		info.add(marketStatusLabel, 2, 1);
		info.add(new Label("     "), 3, 0);
		info.add(currentDateTimeLabel, 4, 0);
		info.add(currentTradingMode, 4, 1);
		info.add(new Label("     "), 5, 0);
		toolbar.setRight(info);

		// grid for two buttons for simulation
		GridPane simulationGrid = new GridPane();

		// button for starting simulation
		Button startSim = new Button("Start Simulation");
		startSim.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				orders.clear();
				try {
					if (selectedCompany == null) {
						throw new NullPointerException("Company");
					} else if (selectedClient.getName().equals("No client selected.")) {
						throw new NullPointerException("Client");
					}
					startSim.setDisable(true);
					Task<Void> task = new Task<Void>() {
						@Override
						public Void call() throws Exception {
							while (true) {
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										long start = System.currentTimeMillis();
										exchange.tradeSim();
										orders.clear();
										orders.addAll(selectedTrader.getOrderHistory(selectedClient));
										currentTradingMode.setText("Mode: " + (selectedTrader).getMode().toString());
										marketStatusLabel.setText("Market Status: " + exchange.marketStatus());
										netWorthLabel.setText("Net Worth: " + selectedClient.getNetWorth());
										currentDateTimeLabel.setText("Current: " + exchange.getDate() + ", " + exchange.getTime());
										for (int i = 0; i < selectedCompany.getShareValueList().size(); i++) {
											// updating chart depending on
											// selected commodity
											exchange.getXChart().get(i);
											series.getData().add(new XYChart.Data(exchange.getXChart().get(i),selectedCompany.getShareValueList().get(i)));
										}
										commoditiesScroll.setContent(displayAllCommodities());
										newsfeedScroll.setContent(displayAllNews());
										currentSimIterations++;
										double progress = currentSimIterations / totalSimIterations;
										progressBar.setProgress(progress);
										long end = System.currentTimeMillis();
										//System.out.println(end-start);
									}
								});
								Thread.sleep(500);
							}
						}
					};
					Thread th = new Thread(task);
					th.setDaemon(true);
					th.start();
				} catch (Exception e) {
					if (e.getMessage().equals("Company")) {
						throwErrorMessage(AlertType.ERROR, "Invalid Action", "You must select a Company.");
					} else if (e.getMessage().equals("Client")) {
						throwErrorMessage(AlertType.ERROR, "Invalid Action", "You must select a Client.");
					}
				}
			}
		});

		// button for setting up simulation
		Button setUpSim = new Button("Set Up Simulation");
		setUpSim.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setUpSimulation();
			}
		});

		simulationGrid.add(setUpSim, 0, 0);
		simulationGrid.add(startSim, 1, 0);
		toolbar.setLeft(simulationGrid);

		// setting up style and position for labels and toolbar
		traderLabel.setFont(new Font(16 / ((scaleHeight + scaleWidth) / 2)));
		clientLabel.setFont(new Font(16 / ((scaleHeight + scaleWidth) / 2)));
		netWorthLabel.setFont(new Font(16 / ((scaleHeight + scaleWidth) / 2)));
		marketStatusLabel.setFont(new Font(16 / ((scaleHeight + scaleWidth) / 2)));
		currentTradingMode.setFont(new Font(16 / ((scaleHeight + scaleWidth) / 2)));
		currentDateTimeLabel.setFont(new Font(16 / ((scaleHeight + scaleWidth) / 2)));
		toolbar.setStyle("-fx-border-color: #606060;" + "-fx-border-width: 3 3 3 3;" + "-fx-font-size: 16;");
		topPane.setCenter(toolbar);

		// setting up width for progress bar
		progressBar.setMinWidth(1150 / scaleWidth);
		progressBar.setMaxWidth(1150 / scaleWidth);
		topPane.setBottom(progressBar);

		return topPane;
	}

	/**
	 * Creates the bottom pane containing the orders table.
	 *
	 * @return the tab pane to be placed as bottom pane
	 */
	private TabPane createBottomPane() {
		// tab pane for orders table
		TabPane bottomPane = new TabPane();
		bottomPane.setMaxHeight(230 / scaleHeight);
		bottomPane.setMinHeight(230 / scaleHeight);

		// tab for orders
		Tab ordersTab = new Tab();
		ordersTab.setText("Orders");
		ordersTab.setClosable(false);

		bottomPane.getTabs().addAll(ordersTab);

		// table view for holding data in table
		TableView<Order> ordersTable = createTableView();
		ordersTab.setContent(ordersTable);

		return bottomPane;
	}

	/**
	 * Display all news.
	 *
	 * @return the grid pane containing one news per cell
	 */
	private GridPane displayAllNews() {
		// gridpane containing all news
		GridPane allNews = new GridPane();
		allNews.setMaxWidth(215 / scaleWidth);
		allNews.setMinWidth(215 / scaleWidth);
		allNews.setPadding(new Insets(20, 0, 0, 20));
		allNews.add(new Label("No events to display."), 0, 0);

		for (int i = 0; i < exchange.getEvents().size(); i++) {
			if (exchange.getEvents().get(i).isTrriggered()) {
				// creating new cells + filler cells and adding them to gridpane
				BorderPane news = createNewsCell(exchange.getEvents().get(i).getDate().toString() + " - ",exchange.getEvents().get(i).getTime().toString(), exchange.getEvents().get(i).getEventText());
				allNews.add(news, 0, (i * 2 + 1));
				BorderPane filler = createNewsCell(" ", " ", " ");
				allNews.add(filler, 0, (i * 2 + 2));
			}
		}

		return allNews;
	}

	/**
	 * Creates the news cell.
	 *
	 * @param newsDate
	 *            the date of the news to be displayed
	 * @param newsTime
	 *            the time of the news to be displayed
	 * @param newsContent
	 *            the content of the news
	 * @return the border pane to be placed in each cell
	 */
	private BorderPane createNewsCell(String newsDate, String newsTime, String newsContent) {
		// creating cell in news grid
		BorderPane news = new BorderPane();

		// creating label for name of news
		Label newsNameLabel = new Label(newsDate + newsTime);
		newsNameLabel.setFont(new Font(20 / ((scaleHeight + scaleWidth) / 2)));

		// creating label for content of news
		Label newsContentLabel = new Label(newsContent);
		newsContentLabel.setTextAlignment(TextAlignment.LEFT);
		newsContentLabel.setWrapText(true);

		news.setTop(newsNameLabel);
		news.setCenter(newsContentLabel);

		return news;
	}

	/**
	 * Display all commodities.
	 *
	 * @return the grid pane containing one commodity per cell
	 */
	private GridPane displayAllCommodities() {
		// gridpane containing all commodities
		GridPane allCommodities = new GridPane();
		allCommodities.setMinWidth(215 / scaleWidth);
		allCommodities.setMaxWidth(215 / scaleWidth);
		allCommodities.setPadding(new Insets(0, 20, 20, 20));

		for (int i = 0; i < exchange.getCompanies().size(); i++) {
			// creating new cells and adding them to main grid
			Button commodity = createCommodityCell(exchange.getCompanies().get(i),
					exchange.getCompanies().get(i).getCompanyTrend());
			allCommodities.add(commodity, 0, i * 2 + 1);
			allCommodities.add(new Label(), 0, i * 2);
		}
		return allCommodities;
	}

	/**
	 * Creates the commodity cell.
	 *
	 * @param company
	 *            the company to be displayed
	 * @param trend
	 *            the trend of the company
	 * @return the button to be placed as a cell of the commodities pane
	 */
	private Button createCommodityCell(Company company, String trend) {
		// creating cell in commodities grid
		BorderPane commodity = new BorderPane();
		commodity.setMinWidth(165 / scaleWidth);
		commodity.setMaxWidth(165 / scaleWidth);
		double shareValue = company.getCurrentShareValue();

		// creating button for cell
		Button commodityButton = new Button(null, commodity);
		commodityButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				series.getData().setAll(new XYChart.Data(0, 0));
				for (int i = 0; i < company.getShareValueList().size(); i++) {
					// updating chart depending on selected commodity
					try {
						selectedCompany = exchange.getCompanies().get(i);
						exchange.getXChart().get(i);
						series.getData().add(new XYChart.Data(exchange.getXChart().get(i), company.getShareValueList().get(i)));
					} catch (Exception e) {
						// ignoring error on timing when selecting different
						// company too fast for the threads
					}
				}
			}
		});

		// creating label for name of commodity
		Label commodityNameLabel = new Label(company.getName());
		commodityNameLabel.setFont(new Font(20 / ((scaleHeight + scaleWidth) / 2)));

		// creating button for new order
		Button newOrderButton = new Button("New Order");
		newOrderButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				createNewOrder(company);
			}
		});

		// creating label for share value
		Label shareValueLabel = new Label(String.valueOf(shareValue));

		// creating label for trend of commodity
		Label trendLabel = new Label(trend);
		trendLabel.setFont(new Font(20 / ((scaleHeight + scaleWidth) / 2)));

		// separating each commodity cell in left and right side
		BorderPane leftPaneCommodity = new BorderPane();
		leftPaneCommodity.setTop(newOrderButton);
		leftPaneCommodity.setBottom(shareValueLabel);

		commodity.setTop(commodityNameLabel);
		commodity.setRight(trendLabel);
		commodity.setCenter(leftPaneCommodity);

		return commodityButton;
	}

	/**
	 * Creates the stage to allow the user to make a new order.
	 *
	 * @param company
	 *            the company for which the user is making a new order
	 */
	private void createNewOrder(Company company) {
		// creating new window for new order
		Stage makeNewOrder = new Stage();

		// borderpane containing options to make new order
		BorderPane orderPane = new BorderPane();
		orderPane.setPadding(new Insets(10, 30, 10, 30));

		// label describing following combobox
		Label quantityLabel = new Label("Quantity: ");
		quantityLabel.setFont(new Font(20 / ((scaleHeight + scaleWidth) / 2)));

		// combobox allowing to choose quantities
		ComboBox quantities = new ComboBox();
		for (int i = 0; i < 6; i++) {
			if (i == 0) {
				quantities.getItems().add(50);
			} else {
				quantities.getItems().add(100 * i);
			}
		}

		// borderpane for buy and sell buttons
		BorderPane buyOrSell = new BorderPane();
		buyOrSell.setPadding(new Insets(20, 0, 0, 0));

		// buy button and event handler
		Button buyButton = new Button("Buy");
		buyButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					if (selectedClient.getName().equals("No client selected.")) {
						throw new NullPointerException("Client");
					}
					if (quantities.getSelectionModel().getSelectedItem() == null) {
						throw new NullPointerException("Quantity");
					}
					orders.add(new Order(company,
							Integer.valueOf(quantities.getSelectionModel().getSelectedItem().toString()), true, 0,
							"High", selectedClient));
					makeNewOrder.hide();
				} catch (NullPointerException e) {
					if (e.getMessage().equals("Client")) {
						throwErrorMessage(AlertType.ERROR, "Invalid Action", "You must select a Client.");
					}
					if (e.getMessage().equals("Quantity")) {
						throwErrorMessage(AlertType.ERROR, "Invalid Action", "You must select a Quantity.");
					}
				}
			}
		});

		// sell button and event handler
		Button sellButton = new Button("Sell");
		sellButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					if (selectedClient.getName().equals("No client selected.")) {
						throw new NullPointerException("Client");
					}
					if (quantities.getSelectionModel().getSelectedItem() == null) {
						throw new NullPointerException("Quantity");
					}
					orders.add(new Order(company,
							Integer.valueOf(quantities.getSelectionModel().getSelectedItem().toString()), false, 0,
							"High", selectedClient));
					makeNewOrder.hide();
				} catch (NullPointerException e) {
					if (e.getMessage().equals("Client")) {
						throwErrorMessage(AlertType.ERROR, "Invalid Action", "You must select a Client.");
					}
					if (e.getMessage().equals("Quantity")) {
						throwErrorMessage(AlertType.ERROR, "Invalid Action", "You must select a Quantity.");
					}
				}
			}
		});

		buyOrSell.setLeft(buyButton);
		buyOrSell.setRight(sellButton);

		orderPane.setLeft(quantityLabel);
		orderPane.setRight(quantities);
		orderPane.setBottom(buyOrSell);

		// setting up and styling scene for new order
		Scene newOrderScene = new Scene(orderPane);
		newOrderScene.getStylesheets().add("resources/com/guigarage/flatterfx/flatterfx.css");

		makeNewOrder.sizeToScene();
		makeNewOrder.setTitle("New Order");
		makeNewOrder.setScene(newOrderScene);
		makeNewOrder.show();
	}

	/**
	 * Creates the menu bar at the top of the stage.
	 *
	 * @return the menu bar
	 */
	private MenuBar createMenu() {
		// creating menu for loading data
		Menu fileMenu = new Menu("File");

		MenuItem loadClients = new MenuItem("Load Clients...");
		loadClients.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					exchange.setUpClients(chooseFile());
				} catch (Exception e) {
					throwErrorMessage(AlertType.ERROR, "File Not Found!", "Select a valid .csv file.");
				}
			}
		});
		MenuItem loadCompanies = new MenuItem("Load Companies...");
		loadCompanies.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					exchange.setUpCompanies(chooseFile());
					selectedCompany = exchange.getCompanies().get(0);
				} catch (Exception e) {
					throwErrorMessage(AlertType.ERROR, "File Not Found!", "Select a valid .csv file.");
				}
			}
		});
		MenuItem loadEvents = new MenuItem("Load Events...");
		loadEvents.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					exchange.setUpEvents(chooseFile());
				} catch (Exception e) {
					throwErrorMessage(AlertType.ERROR, "File Not Found!", "Select a valid .csv file.");
				}
			}
		});

		fileMenu.getItems().addAll(loadClients, loadCompanies, loadEvents);

		// creating menu for traders
		traderMenu = new Menu("Traders");

		// looping through all traders
		for (int i = 0; i < exchange.getTraders().size(); i++) {
			final int traderIndex = i;
			// filling menu up with all trader's names
			Menu trader = new Menu(exchange.getTraders().get(i).getTraderName());

			// looping through all clients for each trader
			for (int j = 0; j < exchange.getTraders().get(i).getClients().size(); j++) {
				final int clientIndex = j;
				// filling menuitems up with all trader's clients' names
				MenuItem client = new MenuItem(exchange.getTraders().get(i).getClients().get(j).getName());
				// setting up event handler for each client
				client.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						orders.clear();
						selectedTrader = exchange.getTraders().get(traderIndex);
						selectedClient = selectedTrader.getClients().get(clientIndex);
					}
				});
				trader.getItems().add(client);
			}

			// menuitem for adding a custom client
			MenuItem addClient = new MenuItem("Add Client...");
			addClient.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					selectedTrader = exchange.getTraders().get(traderIndex);
					addCustomClient();
				}
			});
			trader.getItems().add(addClient);

			// adding each trader to trader menu
			traderMenu.getItems().add(trader);
		}

		// creating menu for account
		Menu accountMenu = new Menu("Account");
		// menuitems for withdrawal and deposit
		MenuItem withdrawal = new MenuItem("Withdrawal");
		withdrawal.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					openAccountWindow("Withdraw");
				} catch (Exception e) {
					throwErrorMessage(AlertType.ERROR, "Internal Error",
							"Must pass \"Withdraw\" or \"Deposit\" as method parameter.");
				}
			}
		});
		MenuItem deposit = new MenuItem("Deposit");
		deposit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					openAccountWindow("Deposit");
				} catch (Exception e) {
					throwErrorMessage(AlertType.ERROR, "Internal Error",
							"Must pass \"Withdraw\" or \"Deposit\" as method parameter.");
				}
			}
		});
		MenuItem risk = new MenuItem("Risk");
		risk.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openRiskWindow();
			}
		});
		accountMenu.getItems().addAll(withdrawal, deposit, risk);

		// creating menu for orders
		Menu ordersMenu = new Menu("Orders");
		// menuitem for clearing all orders table
		MenuItem clearOrders = new MenuItem("Clear Orders Table");
		clearOrders.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				orders.removeAll(orders);
			}
		});
		ordersMenu.getItems().add(clearOrders);

		// creating menu for help
		Menu helpMenu = new Menu("Help");
		// menuitem for tutorial
		MenuItem tutorial = new MenuItem("Tutorial");
		tutorial.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				throwErrorMessage(AlertType.NONE, "Tutorial",
						"________________________________\n" +
						"- How to start the application:\n" +
						"Step 1: Click on Traders\n" +
						"Step 2: Select any existent client data\n" + 
						"Step 3: Select any existent company\n" + 
						"Step 4: Start simulation\n" +
						"________________________________\n" +
						"- How to import custom data:\n" +
						"Step 1: Click on File\n" +
						"Step 2: Select a .csv file for loading Clients, Companies or Events\n" +
						"________________________________\n" +
						"- How to edit the number of traders in the simulation:\n" +
						"Step 1: Click on Set Up Simulation\n" +
						"Step 2: Insert the number of traders\n" +
						"________________________________\n" +
						"- How to withdraw and deposit:\n" +
						"Step 1: Click on Account\n" +
						"Step 2: Select withdrawal or deposit\n" + 
						"Step 3: Insert the amount you wish to withdraw or deposit\n" +
						"________________________________\n" +
						"- How to clear data from orders table:\n" +
						"Step 1: Click on Orders\n" +
						"Step 2: Click on Clear Order Table\n" +
						"________________________________\n" +
						"- How to make New Order:\n" +
						"Step 1: Select any existent company\n" + 
						"Step 2: Click on New Order\n" +
						"Step 3: Select the amount of shares\n" +
						"Step 4: Click on Buy or Sell");
			}
		});
		helpMenu.getItems().add(tutorial);

		// creating menu for about
		Menu aboutMenu = new Menu("About");
		// menuitem for about
		MenuItem about = new MenuItem("About");
		about.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				throwErrorMessage(AlertType.NONE, "About",
						"JAWA trade© for Wolf and Gecko\nAll rights reserved.\n\n"
						+ "Jonathan Magbadelo - Alessia Nigretti - Jack O' Neill - Walid Lamaici");
			}
		});
		aboutMenu.getItems().add(about);

		// creating menu bar to show on top of stage
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu, traderMenu, accountMenu, ordersMenu, helpMenu, aboutMenu);

		return menuBar;
	}

	/**
	 * Creates the table view containing all orders.
	 *
	 * @return the table view
	 */
	private TableView<Order> createTableView() {
		// setting up tableview to fill table up with data
		TableView<Order> table = new TableView<Order>();
		table.setMaxHeight(190 / scaleHeight);
		table.setEditable(false);

		// setting up all columns and location of data in OrderTable class
		TableColumn instrumentsOrders = new TableColumn("Instrument");
		instrumentsOrders.setMinWidth(100 / scaleWidth);
		instrumentsOrders.setCellValueFactory(new PropertyValueFactory<Order, String>("companyColumn"));

		TableColumn quantityOrders = new TableColumn("Quantity");
		quantityOrders.setMinWidth(100 / scaleWidth);
		quantityOrders.setCellValueFactory(new PropertyValueFactory<Order, String>("quantityColumn"));

		TableColumn buyOrSellOrders = new TableColumn("Buy/Sell");
		buyOrSellOrders.setMinWidth(100 / scaleWidth);
		buyOrSellOrders.setCellValueFactory(new PropertyValueFactory<Order, String>("orderTypeColumn"));

		TableColumn priceOrders = new TableColumn("Price");
		priceOrders.setMinWidth(100 / scaleWidth);
		priceOrders.setCellValueFactory(new PropertyValueFactory<Order, String>("priceColumn"));

		TableColumn typeOrders = new TableColumn("Risk");
		typeOrders.setMinWidth(100 / scaleWidth);
		typeOrders.setCellValueFactory(new PropertyValueFactory<Order, String>("riskColumn"));

		TableColumn clientOrders = new TableColumn("Client");
		clientOrders.setMinWidth(110 / scaleWidth);
		clientOrders.setCellValueFactory(new PropertyValueFactory<Order, String>("clientColumn"));

		table.setItems(orders);
		table.getColumns().addAll(instrumentsOrders, quantityOrders, buyOrSellOrders, priceOrders, typeOrders,
				clientOrders);

		return table;
	}

	/**
	 * Creates the stage window to select a risk.
	 */
	private void openRiskWindow() {
		// creating new stage for opening new risk window
		Stage riskWindow = new Stage();

		// borderpane for form to fill (frame for riskPane)
		BorderPane form = new BorderPane();
		form.setPadding(new Insets(15));

		// gridpane for client labels and combobox
		GridPane riskPane = new GridPane();
		riskPane.setPadding(new Insets(10, 5, 5, 5));

		// creating and adding label and combobox to gridpane
		Label currentRiskLabel = new Label("Current Risk:  ");
		Label currentRisk = new Label(selectedClient.getRisk());
		Label riskLabel = new Label("Risk:  ");
		// combobox allowing to choose quantities
		ComboBox risks = new ComboBox();
		risks.setMinWidth(80 / scaleWidth);
		risks.setMaxWidth(80 / scaleWidth);
		for (int i = 0; i < Company.Risk.values().length; i++)
			risks.getItems().add(String.valueOf(Company.Risk.values()[i]));
		riskPane.add(currentRiskLabel, 0, 0);
		riskPane.add(currentRisk, 1, 0);
		riskPane.add(riskLabel, 0, 1);
		riskPane.add(risks, 1, 1);
		riskPane.add(new Label("             "), 0, 2);
		riskPane.add(new Label("             "), 1, 2);

		// creating bottom pane for buttons
		BorderPane bottomPane = new BorderPane();

		// button for confirming form and event handler
		Button confirm = new Button("Confirm");
		confirm.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					selectedClient.setRisk(risks.getSelectionModel().getSelectedItem().toString());
				} catch (Exception e) {
					throwErrorMessage(AlertType.ERROR, "Invalid Argument", "Select a valid risk.");
				}
				riskWindow.hide();
			}
		});
		bottomPane.setCenter(confirm);

		form.setCenter(riskPane);
		form.setBottom(bottomPane);

		// creating scene for account window
		Scene addRiskScene = new Scene(form);
		addRiskScene.getStylesheets().add("resources/com/guigarage/flatterfx/flatterfx.css");

		riskWindow.sizeToScene();
		riskWindow.setResizable(false);
		riskWindow.setTitle("Choose Risk");
		riskWindow.setScene(addRiskScene);
		riskWindow.show();
	}

	/**
	 * Creates the stage window to withdraw or deposit.
	 *
	 * @param action            the chosen action (withdrawal or deposit)
	 * @throws Exception the exception
	 */
	private void openAccountWindow(String action) throws Exception {
		// creating new stage for opening new account window
		Stage accountWindow = new Stage();

		// borderpane for form to fill (frame for accountPane)
		BorderPane form = new BorderPane();
		form.setPadding(new Insets(15));

		// gridpane for client labels and textfields
		GridPane accountPane = new GridPane();
		accountPane.setPadding(new Insets(10, 5, 5, 5));

		// creating and adding labels and textfields to gridpane
		Label accountBalanceLabel = new Label("Account Balance: ");
		Label accountBalance = new Label(String.valueOf(selectedClient.getCashHolding()));
		Label actionLabel = new Label(action);
		TextField amount = new TextField();
		accountPane.add(accountBalanceLabel, 0, 0);
		accountPane.add(accountBalance, 1, 0);
		accountPane.add(actionLabel, 0, 1);
		accountPane.add(amount, 1, 1);

		// creating bottom pane for buttons
		BorderPane bottomPane = new BorderPane();

		// button for clearing form and event handler
		Button clear = new Button("Clear");
		clear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				amount.setText("");
			}
		});

		// button for confirming form and event handler
		Button confirm = new Button("Confirm");
		confirm.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					//
				} catch (Exception e) {
					throwErrorMessage(AlertType.ERROR, "Invalid Argument", "The amount must be a number.");
				}
			}
		});

		bottomPane.setLeft(clear);
		bottomPane.setRight(confirm);

		form.setCenter(accountPane);
		form.setBottom(bottomPane);

		// creating scene for account window
		Scene addAccountScene = new Scene(form);
		addAccountScene.getStylesheets().add("resources/com/guigarage/flatterfx/flatterfx.css");

		accountWindow.sizeToScene();
		if (action.equals("Withdraw")) {
			accountWindow.setTitle("Withdrawal");
		} else if (action.equals("Deposit")) {
			accountWindow.setTitle("Deposit");
		} else {
			throw new Exception("Must be Withdrawal or Deposit.");
		}
		accountWindow.setScene(addAccountScene);
		accountWindow.show();
	}

	/**
	 * Allows the user to add a custom client.
	 */
	private void addCustomClient() {
		// creating new stage for creating custom client
		Stage addCustomClient = new Stage();

		// borderpane for form to fill (frame for clientPane)
		BorderPane form = new BorderPane();
		form.setPadding(new Insets(15));

		// gridpane for client labels and textfields
		GridPane clientPane = new GridPane();
		clientPane.setPadding(new Insets(10, 5, 5, 5));

		// creating and adding labels and textfields to gridpane
		Label name = new Label("Name: ");
		Label expectedReturn = new Label("Expected Return: ");
		Label initialInvestment = new Label("Initial Investment: ");
		TextField nameField = new TextField();
		TextField expectedReturnField = new TextField();
		TextField initialInvestmentField = new TextField();
		clientPane.add(name, 0, 0);
		clientPane.add(nameField, 1, 0);
		clientPane.add(expectedReturn, 0, 1);
		clientPane.add(expectedReturnField, 1, 1);
		clientPane.add(initialInvestment, 0, 2);
		clientPane.add(initialInvestmentField, 1, 2);

		// borderpane for clear and confirm buttons
		BorderPane bottomPane = new BorderPane();
		bottomPane.setPadding(new Insets(5));
		bottomPane.setMaxWidth(200 / scaleWidth);
		bottomPane.setMinWidth(200 / scaleWidth);

		// button for clearing form and event handler
		Button clear = new Button("Clear");
		clear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				nameField.setText("");
				expectedReturnField.setText("");
				initialInvestmentField.setText("");
			}
		});

		// button for confirming form and event handler
		Button confirm = new Button("Confirm");
		confirm.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Client client = new Client(nameField.getText(), Double.valueOf(expectedReturnField.getText()),
							Double.valueOf(initialInvestmentField.getText()));
					client.calculateNetWorth();
					selectedTrader.addClient(client);
					exchange.setCurrentClient(client);
					selectedClient = exchange.getCurrentClient();
					addCustomClient.hide();
				} catch (Exception e) {
					throwErrorMessage(AlertType.ERROR, "Invalid Argument",
							"Expected Return and Initial Investment must be numbers.");
				}
			}
		});

		bottomPane.setLeft(clear);
		bottomPane.setRight(confirm);

		form.setCenter(clientPane);
		form.setBottom(bottomPane);

		// creating scene for new client
		Scene addClientScene = new Scene(form);
		addClientScene.getStylesheets().add("resources/com/guigarage/flatterfx/flatterfx.css");

		addCustomClient.sizeToScene();
		addCustomClient.setTitle("New Custom Client");
		addCustomClient.setScene(addClientScene);
		addCustomClient.show();
	}

	/**
	 * Shows a custom error message.
	 * 
	 * @param errorType
	 *            the error type
	 * @param headerText
	 *            the text of the header of the error window
	 * @param contentText
	 *            the content of the error window
	 */
	private void throwErrorMessage(AlertType errorType, String headerText, String contentText) {
		Alert alert = new Alert(errorType, contentText, ButtonType.OK);
		alert.getDialogPane().getStylesheets().add("resources/com/guigarage/flatterfx/flatterfx.css");
		alert.setTitle(headerText);
		alert.setHeaderText(headerText);
		alert.showAndWait();
	}

	/**
	 * Allows the user to set up a custom simulation.
	 */
	private void setUpSimulation() {
		// creating new window for setting up a simulation
		Stage setUpSimStage = new Stage();

		// borderpane containing options to set up simulation
		BorderPane setUpPane = new BorderPane();
		setUpPane.setPadding(new Insets(15));

		GridPane form = new GridPane();
		form.setPadding(new Insets(15));

		// creating and adding labels and textfields to gridpane
		Label numOfTradersLabel = new Label("Number of\nRandom Traders:    ");
		numOfTradersLabel.setTextAlignment(TextAlignment.CENTER);
		TextField numOfTraders = new TextField();

		form.add(numOfTradersLabel, 0, 0);
		form.add(numOfTraders, 1, 0);

		// creating and adding confirm button
		Button confirm = new Button("Confirm");
		confirm.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					if (Integer.valueOf(numOfTraders.getText()) < 2) {
						throw new Exception("Num is too low.");
					}
					exchange.getTraders().clear();
					exchange.setUpRandomTraders(Integer.valueOf(numOfTraders.getText()));
					traderMenu.getItems().clear();
					for (int i = 0; i < exchange.getTraders().size(); i++) {
						final int traderIndex = i;
						// filling menu up with all trader's names
						Menu trader = new Menu(exchange.getTraders().get(i).getTraderName());

						// looping through all clients for each trader
						for (int j = 0; j < exchange.getTraders().get(i).getClients().size(); j++) {
							final int clientIndex = j;
							// filling menuitems up with all trader's clients'
							// names
							MenuItem client = new MenuItem(exchange.getTraders().get(i).getClients().get(j).getName());
							// setting up event handler for each client
							client.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									orders.clear();
									selectedTrader = exchange.getTraders().get(traderIndex);
									selectedClient = selectedTrader.getClients().get(clientIndex);
								}
							});
							trader.getItems().add(client);
						}

						// menuitem for adding a custom client
						MenuItem addClient = new MenuItem("Add Client...");
						addClient.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								selectedTrader = exchange.getTraders().get(traderIndex);
								addCustomClient();
							}
						});
						trader.getItems().add(addClient);

						// adding each trader to trader menu
						traderMenu.getItems().add(trader);
					}
					setUpSimStage.hide();
				} catch (Exception e) {
					throwErrorMessage(AlertType.ERROR, "Invalid Argument", "The minimum number of trader is 2.");
				}
			}
		});

		setUpPane.setCenter(form);
		setUpPane.setBottom(confirm);
		setUpPane.setAlignment(confirm, Pos.CENTER);

		// setting up and styling scene for new order
		Scene newSetUp = new Scene(setUpPane);
		newSetUp.getStylesheets().add("resources/com/guigarage/flatterfx/flatterfx.css");

		setUpSimStage.sizeToScene();
		setUpSimStage.setTitle("Set Up Simulation");
		setUpSimStage.setScene(newSetUp);
		setUpSimStage.show();
	}

	/**
	 * Opens a file chooser to explore the system.
	 *
	 * @return the CSV reader
	 * @throws FileNotFoundException the file not found exception
	 */
	private CSVReader chooseFile() throws FileNotFoundException {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV Files", "*.csv");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(new Stage());
		CSVReader csvFile = new CSVReader(new FileReader(file.getPath()));
		return csvFile;
	}

	/**
	 * Launches the program.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
