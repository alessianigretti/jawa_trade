import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Panel;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;

import com.sun.glass.events.MouseEvent;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
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
    
    // hard-coded ideal window sizes
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = Screen.getPrimary().getBounds().getWidth() / 1.5;
    double height = Screen.getPrimary().getBounds().getHeight() / 1.3;
	double scaleHeight = (832/height)*1.1;
	double scaleWidth = (1200/width)*1.1;
	
	
    @Override
    public void start(Stage stage)
    {    	
    	// defining the axes
        final NumberAxis xAxis = new NumberAxis("X label (temporary)", 1, 28, 1);
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Value");
        // creating and setting up chart (centre of BorderPane centre)
        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
        lineChart.setMaxHeight(height/(height/(550/scaleHeight)));
        lineChart.setMinHeight(height/(height/(550/scaleHeight)));
        lineChart.setTitle("Stock Monitoring");
        // defining name of series
        series.setName("My portfolio");
        // adding data to chart
        lineChart.getData().add(series);
        
        // orders panel (bottom of BorderPane centre)
        TabPane bottomPane = createBottomPane();
        
        // centre panel (containing chart and orders)
    	BorderPane centre = new BorderPane();
    	centre.setMaxWidth(width/(width/(650/scaleWidth)));
    	centre.setMinWidth(width/(width/(650/scaleWidth)));
        centre.setTop(lineChart);
        centre.setBottom(bottomPane);
        
        
        // newsfeed panel (right of root
        ScrollPane rightPane = createRightPane();
        
        // commodities panel
        ScrollPane leftPane = createLeftPane();
		

        
        // top menu and info panel
        BorderPane topPane = createTopPane();
        
        // put together all elements
        BorderPane root = new BorderPane();
        root.setCenter(centre);
        root.setTop(topPane);
        root.setLeft(leftPane);
        root.setRight(rightPane);
        
        // real-time updates
        Task task = new Task<Void>() {
      	  @Override
      	  public Void call() throws Exception {
      	    int i = 0;
      	    while (true) {
      	      final int finalI = i;
      	      Platform.runLater(new Runnable() {
      	        @Override
      	        public void run() {
      	        	traderLabel.setText("Trader: " + selectedTrader.getTraderName());
      	        	clientLabel.setText("Client: " + selectedClient.getName());
      	        	netWorthLabel.setText("Net Worth: " + selectedClient.getNetWorth());
      	        }
      	      });
      	      i++;
      	      Thread.sleep(1000);
      	    }
      	  }
      	};
      	Thread th = new Thread(task);
      	th.setDaemon(true);
      	th.start();
        
      	// set up scene
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/resources/com/guigarage/flatterfx/flatterfx.css");
        
        // set up stage
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Stock Market Simulation by JAWA Trade");
        stage.setScene(scene);
        stage.show();
    }
    
    private GridPane displayAllNews()
    {
    	// insert main grid for all news
    	GridPane allNews = new GridPane();
		allNews.setMaxWidth(width/(width/(215/scaleWidth)));
		allNews.setMinWidth(width/(width/(215/scaleWidth)));
		allNews.setPadding(new Insets(20, 0, 0, 20));
	
		for (int i = 0; i < exchange.getEvents().size(); i++)
		{
			// create new cells and add them to main grid
			BorderPane news = createNewsCell(exchange.getEvents().get(i).getDate().toString() + " - ", exchange.getEvents().get(i).getTime().toString(), exchange.getEvents().get(i).getEventText());
		    allNews.add(news, 0, (i * 2 + 1));
		    BorderPane empty = createNewsCell(" ", " ", " ");
		    allNews.add(empty, 0, (i * 2 + 2));
		}
	    
	    return allNews;
    }
    
    private BorderPane createNewsCell(String newsDate, String newsTime, String newsContent)
    {
    	// create cell in news grid
    	BorderPane news = new BorderPane();
    	
    	// create label for name of news
		Label newsNameLabel = new Label(newsDate + newsTime);
		newsNameLabel.setFont(new Font(20/((scaleHeight+scaleWidth)/2)));
		
		// create label for content of news
		Label newsContentLabel = new Label(newsContent);
		newsContentLabel.setTextAlignment(TextAlignment.LEFT);
		newsContentLabel.setWrapText(true);
		
		news.setAlignment(newsNameLabel, Pos.TOP_LEFT);
		news.setAlignment(newsContentLabel, Pos.TOP_LEFT);
    	
		news.setTop(newsNameLabel);
	    news.setCenter(newsContentLabel);
	   
	    return news;
    }
    
    private GridPane displayAllCommodities()
    {
    	// insert main grid for all commodities
    	GridPane allCommodities = new GridPane();
		allCommodities.setMinWidth(width/(width/(215/scaleWidth)));
		allCommodities.setMaxWidth(width/(width/(215/scaleWidth)));
		allCommodities.setPadding(new Insets(0, 20, 20, 20));
	
		for (int i = 0; i < exchange.getCompanies().size(); i++)
		{
			// create new cells and add them to main grid
			Button commodity = createCommodityCell(exchange.getCompanies().get(i), exchange.getCompanies().get(i).getCurrentShareValue(), "^");
	        allCommodities.add(commodity, 0, i * 2 + 1);
	        allCommodities.add(new Label(), 0, i * 2);
		}
        
        return allCommodities;
    }
    
    private Button createCommodityCell(Company company, double shareValue, String trend)
    {
    	// create cell in commodities grid
    	BorderPane commodity = new BorderPane();
    	commodity.setMinWidth(width/(width/(165/scaleWidth)));
    	commodity.setMaxWidth(width/(width/(165/scaleWidth)));
    	
    	// create button for cell
    	Button commodityButton = new Button(null, commodity);
    	commodityButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	series.getData().setAll(new XYChart.Data(0, 0));
            	System.out.println(company.getShareValueList().size());
            	for (int i = 0; i < company.getShareValueList().size(); i++)
            	{
            		exchange.getXChart().get(i);
            		series.getData().add(new XYChart.Data(exchange.getXChart().get(i), company.getShareValueList().get(i)));
            	}
            }
        });
    	
    	// create label for name of commodity
		Label commodityNameLabel = new Label(company.getName());
		commodityNameLabel.setFont(new Font(20/((scaleHeight+scaleWidth)/2)));
		
		// create content for commodity cell
		Button newOrderButton = new Button("New Order");
		newOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Stage newOrderStage = createNewOrder(company);
            }
        });
		
		Label shareValueLabel = new Label(String.valueOf(shareValue));
		
		Label trendLabel = new Label(trend);
		trendLabel.setFont(new Font(20/((scaleHeight+scaleWidth)/2)));
		
		// separate each commodity cell in left and right side
		BorderPane leftPaneCommodity = new BorderPane();
		leftPaneCommodity.setTop(newOrderButton);
		leftPaneCommodity.setBottom(shareValueLabel);
		
		commodity.setTop(commodityNameLabel);
		commodity.setRight(trendLabel);
		commodity.setCenter(leftPaneCommodity);
		
		return commodityButton;
    }
    
    private Stage createNewOrder(Company company)
    {
    	Stage makeNewOrder = new Stage();
    	
    	BorderPane orderPane = new BorderPane();
		orderPane.setPadding(new Insets(10, 30, 10, 30));
		
		Label quantityLabel = new Label("Quantity: ");
		quantityLabel.setFont(new Font(20/((scaleHeight+scaleWidth)/2)));
		
		ComboBox quantities = new ComboBox();
		for(int i = 0; i<6; i++)
		{
			if(i == 0)
				quantities.getItems().add(50);
			else
				quantities.getItems().add(100*i);
		}
		
		BorderPane buyOrSell = new BorderPane();
		buyOrSell.setPadding(new Insets(20, 0, 0, 0));
		
		Button buyButton = new Button("Buy");
		buyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	orders.add(new OrderTable(company, Integer.valueOf(quantities.getSelectionModel().getSelectedItem().toString()), selectedClient, "Buy"));              		
            	// need to test for no client selected and no quantity
            	makeNewOrder.hide();
            }
        });
		
		Button sellButton = new Button("Sell");
		sellButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	orders.add(new OrderTable(company, Integer.valueOf(quantities.getSelectionModel().getSelectedItem().toString()), selectedClient, "Sell"));
            	// need to test for no client selected and no quantity
            	makeNewOrder.hide();
            }
        });
		
		buyOrSell.setLeft(buyButton);
		buyOrSell.setRight(sellButton);
		
		orderPane.setLeft(quantityLabel);
		orderPane.setRight(quantities);
		orderPane.setBottom(buyOrSell);
		
		Scene newOrderScene = new Scene(orderPane);
		newOrderScene.getStylesheets().add("resources/com/guigarage/flatterfx/flatterfx.css");
		
		makeNewOrder.sizeToScene();
		makeNewOrder.setTitle("New Order");
		makeNewOrder.setScene(newOrderScene);
		makeNewOrder.show();
		
		return makeNewOrder;
    }
    
    private MenuBar createMenu()
    {
		Menu traderMenu = new Menu("Traders");
		
		for(int i = 0; i < exchange.getTraders().size(); i++)
		{
			final int traderIndex = i;
			Menu trader = new Menu(exchange.getTraders().get(i).getTraderName());
			for (int j = 0; j < exchange.getTraders().get(i).getClients().size(); j++)
			{
				final int clientIndex = j;
				MenuItem client = new MenuItem(exchange.getTraders().get(i).getClients().get(j).getName());
				client.setOnAction(new EventHandler<ActionEvent>() {
    	            @Override
    	            public void handle(ActionEvent event) {
    	            	selectedTrader = exchange.getTraders().get(traderIndex);
    	            	selectedClient = selectedTrader.getClients().get(clientIndex);
    	            }
    	        });
				trader.getItems().add(client);
			}
			
			MenuItem addClient = new MenuItem("Add Client...");
	        addClient.setOnAction(new EventHandler<ActionEvent>() {
	        	@Override
	        	public void handle(ActionEvent event) {
	        		selectedTrader = exchange.getTraders().get(traderIndex);
	        		addCustomClient();
	        	}
	        });
	        trader.getItems().add(addClient);
			traderMenu.getItems().add(trader);
		}
		
		Menu ordersMenu = new Menu("Orders");
		MenuItem clearOrders = new MenuItem("Clear Orders");
		clearOrders.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				orders.removeAll(orders);
			}
		});
		ordersMenu.getItems().add(clearOrders);
        
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(traderMenu);
        menuBar.getMenus().add(ordersMenu);
        
        return menuBar;
    }
    
    private ScrollPane createRightPane()
    {
    	ScrollPane newsfeedScroll = new ScrollPane();
        newsfeedScroll.setMaxSize(width/(width/(250/scaleWidth)), height/(height/(787/scaleHeight)));
        newsfeedScroll.setMinSize(width/(width/(250/scaleWidth)), height/(height/(787/scaleHeight)));
	    
        GridPane allNews = displayAllNews();
        
        newsfeedScroll.setContent(allNews);
        
        return newsfeedScroll;
    }
    
    private ScrollPane createLeftPane()
    {
    	ScrollPane commoditiesScroll = new ScrollPane();
        commoditiesScroll.setMaxSize(width/(width/(250/scaleWidth)), height/(height/(787/scaleHeight)));
        commoditiesScroll.setMinSize(width/(width/(250/scaleWidth)), height/(height/(787/scaleHeight)));
        
        GridPane allCommodities = displayAllCommodities();
		commoditiesScroll.setContent(allCommodities);
        
		return commoditiesScroll;
    }
    
    private BorderPane createTopPane()
    {
    	BorderPane topPane = new BorderPane();
    	
        MenuBar menuBar = createMenu();
        topPane.setTop(menuBar);
        
        BorderPane clientPane = new BorderPane();
        clientPane.setPadding(new Insets(15, 0, 15, 10));
        
        traderLabel.setFont(new Font(20/((scaleHeight+scaleWidth)/2)));
        clientLabel.setFont(new Font(20/((scaleHeight+scaleWidth)/2)));
        netWorthLabel.setFont(new Font(20/((scaleHeight+scaleWidth)/2)));
        GridPane info = new GridPane();
        info.add(traderLabel, 0, 0);
        info.add(clientLabel, 0, 1);
        info.add(new Label("          "), 1, 0);
        info.add(netWorthLabel, 2, 0);
        info.add(new Label("          "), 3, 0);
        clientPane.setStyle("-fx-border-color: #606060;"
        		+ "-fx-border-width: 3 3 3 3;"
        		+ "-fx-font-size: 16;");
        clientPane.setRight(info);
        topPane.setBottom(clientPane);
        
        return topPane;
    }
    
    private TabPane createBottomPane()
    {
    	TabPane bottomPane = new TabPane();
        bottomPane.setMaxHeight(height/(height/(230/scaleHeight)));
        bottomPane.setMinHeight(height/(height/(230/scaleHeight)));
        
        Tab ordersTab = new Tab();
        ordersTab.setText("Orders");
        ordersTab.setClosable(false);
        
        bottomPane.getTabs().addAll(ordersTab);
        
        // orders and pending tabs (extending bottom of the chart)
        TableView ordersTable = createTableView();
        ordersTab.setContent(ordersTable);
        
        return bottomPane;
    }
    
    private TableView createTableView()
    {
    	TableView<OrderTable> table = new TableView<OrderTable>();
        table.setMaxHeight(height/(height/(190/scaleHeight)));
        table.setEditable(false);
        
        TableColumn instrumentsOrders = new TableColumn("Instrument");
        instrumentsOrders.setMinWidth(width/(width/(100/scaleWidth)));
        instrumentsOrders.setCellValueFactory(
		    new PropertyValueFactory<OrderTable,String>("company")
		);
        
        TableColumn quantityOrders = new TableColumn("Quantity");
        quantityOrders.setMinWidth(width/(width/(100/scaleWidth)));
        quantityOrders.setCellValueFactory(
		    new PropertyValueFactory<OrderTable,String>("quantity")
		);
        
        TableColumn buyOrSellOrders = new TableColumn("Buy/Sell");
        buyOrSellOrders.setMinWidth(width/(width/(100/scaleWidth)));
        buyOrSellOrders.setCellValueFactory(
		    new PropertyValueFactory<OrderTable,String>("orderType")
		);
        
        TableColumn priceOrders = new TableColumn("Price");
        priceOrders.setMinWidth(width/(width/(100/scaleWidth)));
        priceOrders.setCellValueFactory(
    		    new PropertyValueFactory<OrderTable,String>("price")
    	);
        
        TableColumn clientOrders = new TableColumn("Client");
        clientOrders.setMinWidth(width/(width/(110/scaleWidth)));
        clientOrders.setCellValueFactory(
    		    new PropertyValueFactory<OrderTable,String>("client")
    	);
        
        table.setItems(orders);
        table.getColumns().addAll(instrumentsOrders, quantityOrders, buyOrSellOrders, priceOrders,clientOrders);

        return table;
    }
    
    public static class OrderTable {
    	 
        private final SimpleStringProperty company;
        private final SimpleIntegerProperty quantity;
        private final SimpleStringProperty client;
        private final SimpleDoubleProperty price;
        private final SimpleStringProperty orderType;
 
        private OrderTable(Company company, int quantity, Client client, String orderType) {
            this.company = new SimpleStringProperty(company.getName());
            this.quantity = new SimpleIntegerProperty(quantity);
            this.client = new SimpleStringProperty(client.getName());
            price = new SimpleDoubleProperty(quantity * company.getCurrentShareValue());
            this.orderType = new SimpleStringProperty(orderType);
        }
 
        public int getQuantity() {
            return quantity.get();
        }
 
        public void setQuantity(int quantity) {
            this.quantity.set(quantity);
        }
 
        public String getCompany() {
            return company.get();
        }
 
        public void setCompany(String company) {
            this.company.set(company);
        }
 
        public String getClient() {
            return client.get();
        }
 
        public void setClient(String client) {
            this.client.set(client);
        }
        
        public double getPrice() {
        	return price.get();
        }
        
        public void setPrice(double price) {
        	this.price.set(price);
        }
        
        public String getOrderType() {
        	return orderType.get();
        }
        
        public void setOrderType(String orderType) {
        	this.orderType.set(orderType);
        }
    } 
    
    private void addCustomClient()
    {
    	Stage addCustomClient = new Stage();
    	
    	BorderPane form = new BorderPane();
    	form.setPadding(new Insets(15));
    	
    	GridPane clientPane = new GridPane();
    	clientPane.setPadding(new Insets(10, 5, 5, 5));
		
		// content of grid
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

		BorderPane bottomPane = new BorderPane();
		bottomPane.setPadding(new Insets(5));
		bottomPane.setMaxWidth(width/(width/(200/scaleWidth)));
		bottomPane.setMinWidth(width/(width/(200/scaleWidth)));
		
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
		
		bottomPane.setAlignment(clear, Pos.CENTER);
		bottomPane.setAlignment(confirm, Pos.CENTER);
		bottomPane.setLeft(clear);
		bottomPane.setRight(confirm);
		
    	form.setAlignment(bottomPane, Pos.CENTER);
		form.setCenter(clientPane);
		form.setBottom(bottomPane);
		
		Scene addClientScene = new Scene(form);
		addClientScene.getStylesheets().add("resources/com/guigarage/flatterfx/flatterfx.css");
		
		addCustomClient.sizeToScene();
		addCustomClient.setTitle("New Custom Client");
		addCustomClient.setScene(addClientScene);
		addCustomClient.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
    
}
