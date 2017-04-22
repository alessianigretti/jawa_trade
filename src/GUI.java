import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
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

/**
 *	The class GUI is used to visually represent the software.
 */
public class GUI extends Application
{
	// declaring objects from external classes that will be referenced by the UI
    private TradingExchange exchange = new TradingExchange();
    private Trader selectedTrader = new Trader();
    private Client selectedClient = new Client(null, 0);
    private XYChart.Series series = new XYChart.Series();
    private final ObservableList<OrderTable> orders = FXCollections.observableArrayList();   
    // declaring labels to update in real-time
    private Label traderLabel = new Label("Trader: " + selectedTrader);
    private Label clientLabel = new Label("Client: " + selectedClient);
    private Label netWorthLabel = new Label("Net Worth: " + selectedClient.getNetWorth());
    private Label currentDateTimeLabel = new Label("Current: " + exchange.getDate() + ", " + exchange.getTime());
    
    // hard-coded ideal window sizes
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = Screen.getPrimary().getBounds().getWidth() / 1.5;
    double height = Screen.getPrimary().getBounds().getHeight() / 1.3;
	double scaleHeight = (832/height)*1.1;
	double scaleWidth = (1200/width)*1.1;
	
    /* 
     * The start function sets up the stage.
     */
    @Override
    public void start(Stage stage)
    {    	
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
        Task<Void> task = new Task<Void>()
        {
      	  @Override
      	  public Void call() throws Exception
      	  {
      	    while (true)
      	    {
      	      Platform.runLater(new Runnable()
      	      {
      	        @Override
      	        public void run() {
      	        	traderLabel.setText("Trader: " + selectedTrader.getTraderName());
      	        	clientLabel.setText("Client: " + selectedClient.getName());
      	        	netWorthLabel.setText("Net Worth: " + selectedClient.getNetWorth());
      	        	currentDateTimeLabel = new Label("Current: " + exchange.getDate() + ", " + exchange.getTime());
      	        }
      	      });
      	      Thread.sleep(1000);
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
     * Creates the centre pane.
     *
     * @return the border pane
     */
    private BorderPane createCentrePane()
    {
    	// defining the axes
        final NumberAxis xAxis = new NumberAxis("X label (temporary)", 1, 28, 1);
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Value");
        // creating and setting up chart (centre of BorderPane centre)
        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
        lineChart.setMaxHeight(550/scaleHeight);
        lineChart.setMinHeight(550/scaleHeight);
        lineChart.setTitle("Stock Monitoring");
        // defining name of series
        series.setName("My portfolio");
        // adding data to chart
        lineChart.getData().add(series);
        
        // orders panel (bottom of BorderPane centre)
        TabPane bottomPane = createBottomPane();
        
        // centre panel (centre of root)
    	BorderPane centre = new BorderPane();
    	centre.setMaxWidth(650/scaleWidth);
    	centre.setMinWidth(650/scaleWidth);
        centre.setTop(lineChart);
        centre.setBottom(bottomPane);
        
        return centre;
    }
    
    /**
     * Creates the right pane.
     *
     * @return the scroll pane
     */
    private ScrollPane createRightPane()
    {
    	// right scrollpane (frame for gridpane)
    	ScrollPane newsfeedScroll = new ScrollPane();
        newsfeedScroll.setMaxSize(250/scaleWidth, 787/scaleHeight);
        newsfeedScroll.setMinSize(250/scaleWidth, 787/scaleHeight);
	    
        // gridpane containing all news
        GridPane allNews = displayAllNews();
        newsfeedScroll.setContent(allNews);
        
        return newsfeedScroll;
    }
    
    /**
     * Creates the left pane.
     *
     * @return the scroll pane
     */
    private ScrollPane createLeftPane()
    {
    	// left scrollpane (frame for gridpane)
    	ScrollPane commoditiesScroll = new ScrollPane();
        commoditiesScroll.setMaxSize(250/scaleWidth, 787/scaleHeight);
        commoditiesScroll.setMinSize(250/scaleWidth, 787/scaleHeight);
        
        // gridpane containing all commodities
        GridPane allCommodities = displayAllCommodities();
		commoditiesScroll.setContent(allCommodities);
        
		return commoditiesScroll;
    }
    
    /**
     * Creates the top pane.
     *
     * @return the border pane
     */
    private BorderPane createTopPane()
    {
    	// top borderpane (frame for menubar and toolbar)
    	BorderPane topPane = new BorderPane();
    	
    	// top menu bar for traders and orders
        MenuBar menuBar = createMenu();
        topPane.setTop(menuBar);
        
        // bottom toolbar for info panel
        BorderPane toolbar = new BorderPane();
        toolbar.setPadding(new Insets(15, 0, 15, 10));

        // gridpane for info panel
        GridPane info = new GridPane();
        info.add(traderLabel, 0, 0);
        info.add(clientLabel, 0, 1);
        info.add(new Label("          "), 1, 0);
        info.add(netWorthLabel, 2, 0);
        info.add(currentDateTimeLabel, 2, 1);
        info.add(new Label("          "), 3, 0);
        
        // setting up style and position for labels and toolbar
        traderLabel.setFont(new Font(20/((scaleHeight+scaleWidth)/2)));
        clientLabel.setFont(new Font(20/((scaleHeight+scaleWidth)/2)));
        netWorthLabel.setFont(new Font(20/((scaleHeight+scaleWidth)/2)));
        currentDateTimeLabel.setFont(new Font(20/((scaleHeight+scaleWidth)/2)));
        toolbar.setStyle("-fx-border-color: #606060;"
        		+ "-fx-border-width: 3 3 3 3;"
        		+ "-fx-font-size: 16;");
        toolbar.setRight(info);
        topPane.setBottom(toolbar);
        
        return topPane;
    }
    
    /**
     * Creates the bottom pane.
     *
     * @return the tab pane
     */
    private TabPane createBottomPane()
    {
    	// tab pane for orders table
    	TabPane bottomPane = new TabPane();
        bottomPane.setMaxHeight(230/scaleHeight);
        bottomPane.setMinHeight(230/scaleHeight);
        
        // tab for orders
        Tab ordersTab = new Tab();
        ordersTab.setText("Orders");
        ordersTab.setClosable(false);
        
        bottomPane.getTabs().addAll(ordersTab);
        
        // table view for holding data in table
        TableView<OrderTable> ordersTable = createTableView();
        ordersTab.setContent(ordersTable);
        
        return bottomPane;
    }

    /**
     * Display all news.
     *
     * @return the grid pane
     */
    private GridPane displayAllNews()
    {
    	// gridpane containing all news
    	GridPane allNews = new GridPane();
		allNews.setMaxWidth(215/scaleWidth);
		allNews.setMinWidth(215/scaleWidth);
		allNews.setPadding(new Insets(20, 0, 0, 20));
	
		for (int i = 0; i < exchange.getEvents().size(); i++)
		{
			// creating new cells + filler cells and adding them to gridpane
			BorderPane news = createNewsCell(exchange.getEvents().get(i).getDate().toString() + " - ", exchange.getEvents().get(i).getTime().toString(), exchange.getEvents().get(i).getEventText());
		    allNews.add(news, 0, (i * 2 + 1));
		    BorderPane filler = createNewsCell(" ", " ", " ");
		    allNews.add(filler, 0, (i * 2 + 2));
		}
	    
	    return allNews;
    }
    
    /**
     * Creates the news cell.
     *
     * @param newsDate the news date
     * @param newsTime the news time
     * @param newsContent the news content
     * @return the border pane
     */
    private BorderPane createNewsCell(String newsDate, String newsTime, String newsContent)
    {
    	// creating cell in news grid
    	BorderPane news = new BorderPane();
    	
    	// creating label for name of news
		Label newsNameLabel = new Label(newsDate + newsTime);
		newsNameLabel.setFont(new Font(20/((scaleHeight+scaleWidth)/2)));
		
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
     * @return the grid pane
     */
    private GridPane displayAllCommodities()
    {
    	// gridpane containing all commodities
    	GridPane allCommodities = new GridPane();
		allCommodities.setMinWidth(215/scaleWidth);
		allCommodities.setMaxWidth(215/scaleWidth);
		allCommodities.setPadding(new Insets(0, 20, 20, 20));
	
		for (int i = 0; i < exchange.getCompanies().size(); i++)
		{
			// creating new cells and adding them to main grid
			Button commodity = createCommodityCell(exchange.getCompanies().get(i), exchange.getCompanies().get(i).getCurrentShareValue(), "^");
	        allCommodities.add(commodity, 0, i * 2 + 1);
	        allCommodities.add(new Label(), 0, i * 2);
		}
        
        return allCommodities;
    }
    
    /**
     * Creates the commodity cell.
     *
     * @param company the company
     * @param shareValue the share value
     * @param trend the trend
     * @return the button
     */
    private Button createCommodityCell(Company company, double shareValue, String trend)
    {
    	// creating cell in commodities grid
    	BorderPane commodity = new BorderPane();
    	commodity.setMinWidth(165/scaleWidth);
    	commodity.setMaxWidth(165/scaleWidth);
    	
    	// creating button for cell
    	Button commodityButton = new Button(null, commodity);
    	commodityButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	series.getData().setAll(new XYChart.Data(0, 0));
            	for (int i = 0; i < company.getShareValueList().size(); i++)
            	{
            		// updating chart depending on selected commodity
            		exchange.getXChart().get(i);
            		series.getData().add(new XYChart.Data(exchange.getXChart().get(i), company.getShareValueList().get(i)));
            	}
            }
        });
    	
    	// creating label for name of commodity
		Label commodityNameLabel = new Label(company.getName());
		commodityNameLabel.setFont(new Font(20/((scaleHeight+scaleWidth)/2)));
		
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
		trendLabel.setFont(new Font(20/((scaleHeight+scaleWidth)/2)));
		
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
     * Creates the new order.
     *
     * @param company the company
     */
    private void createNewOrder(Company company)
    {
    	// creating new window for new order
    	Stage makeNewOrder = new Stage();
    	
    	// borderpane containing options to make new order
    	BorderPane orderPane = new BorderPane();
		orderPane.setPadding(new Insets(10, 30, 10, 30));
		
		// label describing following combobox
		Label quantityLabel = new Label("Quantity: ");
		quantityLabel.setFont(new Font(20/((scaleHeight+scaleWidth)/2)));
		
		// combobox allowing to choose quantities
		ComboBox quantities = new ComboBox();
		for (int i = 0; i < 6; i++)
		{
			if(i == 0)
			{
				quantities.getItems().add(50);
			} else {
				quantities.getItems().add(100*i);
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
            	orders.add(new OrderTable(company, Integer.valueOf(quantities.getSelectionModel().getSelectedItem().toString()), selectedClient, "Buy"));              		
            	makeNewOrder.hide();
            }
        });
		
		// sell button and event handler
		Button sellButton = new Button("Sell");
		sellButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	orders.add(new OrderTable(company, Integer.valueOf(quantities.getSelectionModel().getSelectedItem().toString()), selectedClient, "Sell"));
            	makeNewOrder.hide();
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
     * Creates the menu.
     *
     * @return the menu bar
     */
    private MenuBar createMenu()
    {
		// creating menu for traders
    	Menu traderMenu = new Menu("Traders");
		
    	// looping through all traders
		for(int i = 0; i < exchange.getTraders().size(); i++)
		{
			final int traderIndex = i;
			// filling menu up with all trader's names
			Menu trader = new Menu(exchange.getTraders().get(i).getTraderName());
			
			// looping through all clients for each trader
			for (int j = 0; j < exchange.getTraders().get(i).getClients().size(); j++)
			{
				final int clientIndex = j;
				// filling menuitems up with all trader's clients' names
				MenuItem client = new MenuItem(exchange.getTraders().get(i).getClients().get(j).getName());
				// setting up event handler for each client
				client.setOnAction(new EventHandler<ActionEvent>() {
    	            @Override
    	            public void handle(ActionEvent event) {
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
        
		// creating menu bar to show on top of stage
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(traderMenu);
        menuBar.getMenus().add(ordersMenu);
        
        return menuBar;
    }

    /**
     * Creates the table view.
     *
     * @return the table view
     */
    private TableView<OrderTable> createTableView()
    {
    	// setting up tableview to fill table up with data
    	TableView<OrderTable> table = new TableView<OrderTable>();
        table.setMaxHeight(190/scaleHeight);
        table.setEditable(false);
        
        // setting up all columns and location of data in OrderTable class
        
        TableColumn instrumentsOrders = new TableColumn("Instrument");
        instrumentsOrders.setMinWidth(100/scaleWidth);
        instrumentsOrders.setCellValueFactory(
		    new PropertyValueFactory<OrderTable,String>("company")
		);
        
        TableColumn quantityOrders = new TableColumn("Quantity");
        quantityOrders.setMinWidth(100/scaleWidth);
        quantityOrders.setCellValueFactory(
		    new PropertyValueFactory<OrderTable,String>("quantity")
		);
        
        TableColumn buyOrSellOrders = new TableColumn("Buy/Sell");
        buyOrSellOrders.setMinWidth(100/scaleWidth);
        buyOrSellOrders.setCellValueFactory(
		    new PropertyValueFactory<OrderTable,String>("orderType")
		);
        
        TableColumn priceOrders = new TableColumn("Price");
        priceOrders.setMinWidth(100/scaleWidth);
        priceOrders.setCellValueFactory(
    		    new PropertyValueFactory<OrderTable,String>("price")
    	);
        
        TableColumn typeOrders = new TableColumn("Type");
        typeOrders.setMinWidth(100/scaleWidth);
        
        TableColumn clientOrders = new TableColumn("Client");
        clientOrders.setMinWidth(110/scaleWidth);
        clientOrders.setCellValueFactory(
    		    new PropertyValueFactory<OrderTable,String>("client")
    	);
        
        table.setItems(orders);
        table.getColumns().addAll(instrumentsOrders, quantityOrders, buyOrSellOrders, priceOrders, typeOrders, clientOrders);

        return table;
    }
    
    /**
     * The Class OrderTable.
     */
    public static class OrderTable
    {
        // fields for filling table up with data
    	private final SimpleStringProperty company;
        private final SimpleIntegerProperty quantity;
        private final SimpleStringProperty client;
        private final SimpleDoubleProperty price;
        private final SimpleStringProperty orderType;
 
        /**
         * Instantiates a new order table.
         *
         * @param company the company
         * @param quantity the quantity
         * @param client the client
         * @param orderType the order type
         */
        private OrderTable(Company company, int quantity, Client client, String orderType)
        {
            this.company = new SimpleStringProperty(company.getName());
            this.quantity = new SimpleIntegerProperty(quantity);
            this.client = new SimpleStringProperty(client.getName());
            price = new SimpleDoubleProperty(quantity * company.getCurrentShareValue());
            this.orderType = new SimpleStringProperty(orderType);
        }
 
        /**
         * Gets the quantity.
         *
         * @return the quantity
         */
        public int getQuantity()
        {
            return quantity.get();
        }
 
        /**
         * Sets the quantity.
         *
         * @param quantity the new quantity
         */
        public void setQuantity(int quantity)
        {
            this.quantity.set(quantity);
        }
 
        /**
         * Gets the company.
         *
         * @return the company
         */
        public String getCompany()
        {
            return company.get();
        }
 
        /**
         * Sets the company.
         *
         * @param company the new company
         */
        public void setCompany(String company)
        {
            this.company.set(company);
        }
 
        /**
         * Gets the client.
         *
         * @return the client
         */
        public String getClient()
        {
            return client.get();
        }
 
        /**
         * Sets the client.
         *
         * @param client the new client
         */
        public void setClient(String client)
        {
            this.client.set(client);
        }
        
        /**
         * Gets the price.
         *
         * @return the price
         */
        public double getPrice()
        {
        	return price.get();
        }
        
        /**
         * Sets the price.
         *
         * @param price the new price
         */
        public void setPrice(double price)
        {
        	this.price.set(price);
        }
        
        /**
         * Gets the order type.
         *
         * @return the order type
         */
        public String getOrderType()
        {
        	return orderType.get();
        }
        
        /**
         * Sets the order type.
         *
         * @param orderType the new order type
         */
        public void setOrderType(String orderType)
        {
        	this.orderType.set(orderType);
        }
    } 
    
    /**
     * Adds the custom client.
     */
    private void addCustomClient()
    {
    	// creating new stage for creating custom client
    	Stage addCustomClient = new Stage();
    	
    	// borderpane for form to fill (frame for clientPane)
    	BorderPane form = new BorderPane();
    	form.setPadding(new Insets(15));
    	
    	// gridpane for client labels and textfields
    	GridPane clientPane = new GridPane();
    	clientPane.setPadding(new Insets(10, 5, 5, 5));
		
		// creating and adding labels and textfields to gridpane
		Label fName = new Label("First Name: ");
		Label lName = new Label("Last Name: ");
		Label expectedReturn = new Label("Expected Return: ");
		Label initialInvestment = new Label("Initial Investment: ");
		TextField fNameField = new TextField();
		TextField lNameField = new TextField();
		TextField expectedReturnField = new TextField();
		TextField initialInvestmentField = new TextField();
		clientPane.add(fName, 0, 0);
		clientPane.add(fNameField, 1, 0);
		clientPane.add(lName, 0, 1);
		clientPane.add(lNameField, 1, 1);
		clientPane.add(expectedReturn, 0, 2);
		clientPane.add(expectedReturnField, 1, 2);
		clientPane.add(initialInvestment, 0, 3);
		clientPane.add(initialInvestmentField, 1, 3);

		// borderpane for clear and confirm buttons
		BorderPane bottomPane = new BorderPane();
		bottomPane.setPadding(new Insets(5));
		bottomPane.setMaxWidth(200/scaleWidth);
		bottomPane.setMinWidth(200/scaleWidth);
		
		// button for clearing form and event handler
		Button clear = new Button("Clear");
		clear.setOnAction(new EventHandler<ActionEvent>() {
	       	@Override
	       	public void handle(ActionEvent event) {
	       		fNameField.setText("");
	       		lNameField.setText("");
	       		expectedReturnField.setText("");
	       		initialInvestmentField.setText("");
	       	}
	    });
		
		// button for confirming form and event handler
		Button confirm = new Button("Confirm");
		confirm.setOnAction(new EventHandler<ActionEvent>() {
	       	@Override
	       	public void handle(ActionEvent event) {
	       		Client client = new Client(fNameField.getText() + " " + lNameField.getText(), Double.valueOf(expectedReturnField.getText()), Double.valueOf(initialInvestmentField.getText()));
	       		client.calculateNetWorth();
	       		selectedTrader.addClient(client);
	       		exchange.setCurrentClient(client);
	       		selectedClient = exchange.getCurrentClient();
	       		addCustomClient.hide();
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
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
}
