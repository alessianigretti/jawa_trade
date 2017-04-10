import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Panel;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;

import com.sun.glass.events.MouseEvent;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.Property;
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
import javafx.scene.image.Image;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 *
 */
public class GUI extends Application
{
    private Stage stage;
    
    private TradingExchange exchange = new TradingExchange();
    private Trader selectedTrader = new Trader();	// to replace with type of trader when available
    private Client selectedClient = new Client( null,0);
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = Screen.getPrimary().getBounds().getWidth() / 1.5;
    double height = Screen.getPrimary().getBounds().getHeight() / 1.3;
	double scale = (832/height)*1.1;
	double scale2 = (1200/width)*1.1;
	
	
    @Override
    public void start(Stage stage) {
    	this.stage = stage;
    	
    	BorderPane centre = new BorderPane();
    	centre.setMaxWidth(width/(width/(650/scale2)));
    	centre.setMinWidth(width/(width/(650/scale2)));
    	// defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        // creating the chart
        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
        lineChart.setMaxHeight(height/(height/(550/scale)));
        lineChart.setMinHeight(height/(height/(550/scale)));
                
        lineChart.setTitle("Stock Monitoring");
        // defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");
        // populating the series with data
        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));
        
        lineChart.getData().add(series);
        
        centre.setTop(lineChart);
        
        // newsfeed panel
        BorderPane rightPane = createRightPane();
        
        // commodities panel
        BorderPane leftPane = createLeftPane();
		
        // orders panel
        TabPane bottomPane = createBottomPane();
        centre.setBottom(bottomPane);
        
        // top menu and info panel
        BorderPane topPane = new BorderPane();
        MenuBar menuBar = createMenu();
        topPane.setTop(menuBar);
        BorderPane clientPane = new BorderPane();
        clientPane.setPadding(new Insets(15, 0, 15, 10));
        Label traderLabel = new Label("Trader: " + selectedTrader);
        traderLabel.setFont(new Font(20/((scale+scale2)/2)));
        Label clientLabel = new Label("Client: " + selectedClient);
        clientLabel.setFont(new Font(20/((scale+scale2)/2)));
        Label netWorthLabel = new Label("Net Worth: " + selectedClient.getNetWorth());
        netWorthLabel.setFont(new Font(20/((scale+scale2)/2)));
        BorderPane topLeftPanel = new BorderPane();
        BorderPane infoPanel = new BorderPane();
        topLeftPanel.setMaxWidth(width/(width/(400/scale2)));
        topLeftPanel.setMinWidth(width/(width/(400/scale2)));
        topLeftPanel.setLeft(infoPanel);
        topLeftPanel.setRight(netWorthLabel);
        topLeftPanel.setPadding(new Insets(0, 10, 0, 0));
        topLeftPanel.setAlignment(traderLabel, Pos.CENTER_RIGHT);
        topLeftPanel.setAlignment(clientLabel, Pos.CENTER_RIGHT);
        infoPanel.setTop(traderLabel);
        infoPanel.setBottom(clientLabel);
        clientPane.setStyle("-fx-border-color: #606060;"
        		+ "-fx-border-width: 3 3 3 3;"
        		+ "-fx-font-size: 16;");
        clientPane.setRight(topLeftPanel);
        topPane.setBottom(clientPane);
        
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
		allNews.setMaxWidth(width/(width/(215/scale2)));
		allNews.setMinWidth(width/(width/(215/scale2)));
		allNews.setPadding(new Insets(20, 0, 0, 20));
	
		for (int i = 0; i < exchange.getEvents().size(); i++)
		{
			// create new cells and add them to main grid
			BorderPane news = createNewsCell(exchange.getEvents().get(i).getDateTime().toString(), exchange.getEvents().get(i).getEventText());
		    allNews.add(news, 0, (i * 2 + 1));
		}
	    
	    return allNews;
    }
    
    private BorderPane createNewsCell(String newsDate, String newsContent)
    {
    	// create cell in news grid
    	BorderPane news = new BorderPane();
    	
    	// create label for name of news
		Label newsNameLabel = new Label(newsDate);
		newsNameLabel.setFont(new Font(20/((scale+scale2)/2)));
		
		// create label for content of news
		Label newsContentLabel = new Label(newsContent);
		newsContentLabel.setMinSize(width/(width/(190/scale2)), height/(height/(75/scale)));
		newsContentLabel.setMaxSize(width/(width/(190/scale2)), height/(height/(90/scale)));
		newsContentLabel.setTextAlignment(TextAlignment.JUSTIFY);
		newsContentLabel.setWrapText(true);
		
		news.setTop(newsNameLabel);
	    news.setCenter(newsContentLabel);
	   
	    return news;
    }
    
    private GridPane displayAllCommodities()
    {
    	// insert main grid for all commodities
    	GridPane allCommodities = new GridPane();
		allCommodities.setMinWidth(width/(width/(215/scale2)));
		allCommodities.setMaxWidth(width/(width/(215/scale2)));
		allCommodities.setPadding(new Insets(0, 0, 0, 20));
	
		for (int i = 0; i < exchange.getCompanies().size(); i++)
		{
			// create new cells and add them to main grid
			BorderPane commodity = createCommodityCell(exchange.getCompanies().get(i).getName(), exchange.getCompanies().get(i).getCurrentShareValue(), "^");
	        allCommodities.add(commodity, 0, i * 2 + 1);
	        allCommodities.add(new Label(), 0, i * 2);
		}
        
        return allCommodities;
    }
    
    private BorderPane createCommodityCell(String commodityName, double shareValue, String trend)
    {
    	// create cell in commodities grid
    	BorderPane commodity = new BorderPane();
    	commodity.setMaxWidth(width/(width/(230/scale2)));
    	
    	// create label for name of commodity
		Label commodityNameLabel = new Label(commodityName);
		commodityNameLabel.setFont(new Font(20/((scale+scale2)/2)));
		
		// create content for commodity cell
		Button newOrderButton = new Button("New Order");
		newOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Stage newOrderStage = createNewOrder(commodityName);
            }
        });
		
		Label shareValueLabel = new Label(String.valueOf(shareValue));
		
		Label trendLabel = new Label(trend);
		trendLabel.setFont(new Font(20/((scale+scale2)/2)));
		
		// separate each commodity cell in left and right side
		BorderPane leftPaneCommodity = new BorderPane();
		leftPaneCommodity.setTop(newOrderButton);
		leftPaneCommodity.setBottom(shareValueLabel);
		
		commodity.setTop(commodityNameLabel);
		commodity.setRight(trendLabel);
		commodity.setCenter(leftPaneCommodity);
		
		return commodity;
    }
    
    private Stage createNewOrder(String commodityName)
    {
    	Stage makeNewOrder = new Stage();
    	
    	BorderPane orderPane = new BorderPane();
		orderPane.setPadding(new Insets(10, 30, 10, 30));
		
		Label quantityLabel = new Label("Quantity: ");
		quantityLabel.setFont(new Font(20/((scale+scale2)/2)));
		
		ComboBox quantities = new ComboBox();
		//for (int i = 0; i < exchange.getCompanies().get(index).getShareValueList().size(); i++)
		//{
		//		quantities.getItems().add(exchange.getCompanies().get(index).getShareValueList().get(i);
		//}
		
		BorderPane buyOrSell = new BorderPane();
		buyOrSell.setPadding(new Insets(20, 0, 0, 0));
		
		Button buyButton = new Button("Buy");
		buyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	//exchange.getSmartTrader().buy(selectedClient, quantities, exchange.);
            	// need to test for no client selected
            	makeNewOrder.hide();
            }
        });
		
		Button sellButton = new Button("Sell");
		sellButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	//exchange.getSmartTrader().buy(selectedClient, //quantities, company);
            	// need to test for no client selected
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
		
	/*	MenuItem trader1 = new MenuItem("Random Trader");
		trader1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	selectedTrader = "Random Trader";
            }
        });*/
		
		/*MenuItem trader2 = new MenuItem("Intelligent Trader");
		trader2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	selectedTrader = "Intelligent Trader";
            }
        });*/
		
		Menu clientMenu = new Menu("Clients");
		
		/*MenuItem client1 = new MenuItem("Norbert DaVinci");
        client1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	exchange.setCurrentClient(new Client("Norbert DaVinci", 123, 123));
            	selectedClient = exchange.getCurrentClient();
            }
        });
        
        MenuItem client2 = new MenuItem("Justine Thyme");
        client2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	exchange.setCurrentClient(new Client("Justine Thyme", 123, 123));
            	selectedClient = exchange.getCurrentClient();
            }
        });*/
		Menu traderMenu = new Menu("Traders");
		
		
		for(int i = 0; i<exchange.getTraders().size(); i++)
		{
			final int index = i;
			MenuItem trader = new MenuItem(exchange.getTraders().get(i).getTraderName());
			trader.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	
	            	clientMenu.getItems().clear();
	            	selectedTrader = exchange.getTraders().get(index);;
	            	for(int i = 0; i<selectedTrader.getClients().size(); i++)
	        		{
	        			final int index = i;
	        			MenuItem client = new MenuItem(selectedTrader.getClients().get(i).getName());
	        			client.setOnAction(new EventHandler<ActionEvent>() {
	        	            @Override
	        	            public void handle(ActionEvent event) {
	        	            	selectedClient =  selectedTrader.getClients().get(index);
	        	            }
	        	        });
	        			clientMenu.getItems().add(client);
	        		}
	            	MenuItem addClient = new MenuItem("Add Client...");
        	        addClient.setOnAction(new EventHandler<ActionEvent>() {
        	        	@Override
        	        	public void handle(ActionEvent event) {
        	        		addCustomClient();
        	        	}
        	        });
        	        clientMenu.getItems().add(addClient);
	            }
	        });
			traderMenu.getItems().add(trader);
		}
        
        
        
      
        
        
        
        
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(traderMenu);
        menuBar.getMenus().add(clientMenu);
        
        return menuBar;
    }
    
    private BorderPane createRightPane()
    {
    	BorderPane rightPane = new BorderPane();
        rightPane.setMaxSize(width/(width/(250/scale2)), height/(height/(787/scale)));
        rightPane.setMinSize(width/(width/(250/scale2)), height/(height/(787/scale)));
        
        ScrollPane newsfeedScroll = new ScrollPane();
        newsfeedScroll.setMaxSize(width/(width/(250/scale2)), height/(height/(787/scale)));
        newsfeedScroll.setMinSize(width/(width/(250/scale2)), height/(height/(787/scale)));
	    
        GridPane allNews = displayAllNews();
        
        newsfeedScroll.setContent(allNews);
        
        rightPane.setCenter(newsfeedScroll);
        
        return rightPane;
    }
    
    private BorderPane createLeftPane()
    {
    	BorderPane leftPane = new BorderPane();
        leftPane.setMaxSize(width/(width/(250/scale2)), height/(height/(787/scale)));
        leftPane.setMinSize(width/(width/(250/scale2)), height/(height/(787/scale)));
        
        ScrollPane commoditiesScroll = new ScrollPane();
        commoditiesScroll.setMaxSize(width/(width/(250/scale2)), height/(height/(787/scale)));
        commoditiesScroll.setMinSize(width/(width/(250/scale2)), height/(height/(787/scale)));
        
        GridPane allCommodities = displayAllCommodities();
		commoditiesScroll.setContent(allCommodities);
        
		leftPane.setCenter(commoditiesScroll);
        
		return leftPane;
    }
    
    private TabPane createBottomPane()
    {
    	TabPane bottomPane = new TabPane();
        bottomPane.setMaxHeight(height/(height/(230/scale)));
        bottomPane.setMinHeight(height/(height/(230/scale)));
        
        Tab ordersTab = new Tab();
        ordersTab.setText("Orders");
        ordersTab.setClosable(false);
        Tab pendingTab = new Tab();
        pendingTab.setText("Pending");
        pendingTab.setClosable(false);
        
        bottomPane.getTabs().addAll(ordersTab, pendingTab);
        
        // orders and pending tabs (extending bottom of the chart)
        TableView ordersTable = createTableView();
        ordersTab.setContent(ordersTable);
        
        TableView pendingTable = createTableView();
        pendingTab.setContent(pendingTable);
        
        return bottomPane;
    }
    
    private TableView createTableView()
    {
    	TableView table = new TableView();
        table.setMaxHeight(height/(height/(150/scale)));
        table.setEditable(false);
        
        TableColumn instrumentsOrders = new TableColumn("Instruments");
        instrumentsOrders.setMinWidth(width/(width/(100/scale2)));
        
        TableColumn quantityOrders = new TableColumn("Quantity");
        quantityOrders.setMinWidth(width/(width/(100/scale2)));
        
        TableColumn buyOrSellOrders = new TableColumn("Buy/Sell");
        buyOrSellOrders.setMinWidth(width/(width/(100/scale2)));
        
        TableColumn priceOrders = new TableColumn("Price");
        priceOrders.setMinWidth(width/(width/(100/scale2)));
        
        table.getColumns().addAll(instrumentsOrders, quantityOrders, buyOrSellOrders, priceOrders);
        
        return table;
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
		bottomPane.setMaxWidth(width/(width/(200/scale2)));
		bottomPane.setMinWidth(width/(width/(200/scale2)));
		
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
