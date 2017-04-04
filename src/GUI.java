import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Panel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.beans.property.Property;
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
import javafx.stage.Stage;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 *
 */
public class GUI extends Application
{
    private Stage stage;
    private Property<String> selectedClient; // temporarily only a string, in the future a client
    private TradingExchange exchange = new TradingExchange();
	
    @Override
    public void start(Stage stage) {
    	this.stage = stage;
    	stage.setTitle("Time Series Chart");
        // defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        // creating the chart
        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
        lineChart.setPrefHeight(600);
                
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
        
        // newsfeed panel
        BorderPane rightPane = new BorderPane();
        rightPane.setMaxHeight(760);
        rightPane.setMinWidth(250);
        rightPane.setPadding(new Insets(10, 10, 10, 0));
        ScrollPane newsfeedScroll = new ScrollPane();
        newsfeedScroll.setMaxHeight(760);
        newsfeedScroll.setStyle("-fx-border-color: gray;"
        		+ "-fx-border-width: 3;");
	    	GridPane allNews = displayAllNews();
        newsfeedScroll.setContent(allNews);
        rightPane.setCenter(newsfeedScroll);
        
        // commodities panel
        BorderPane leftPane = new BorderPane();
        leftPane.setMaxHeight(760);
        leftPane.setPadding(new Insets(10, 0, 10, 10));
        ScrollPane commoditiesScroll = new ScrollPane();
        commoditiesScroll.setMaxHeight(760);
        commoditiesScroll.setStyle("-fx-border-color: gray;"
        		+ "-fx-border-width: 3;");
        	GridPane allCommodities = displayAllCommodities();
		commoditiesScroll.setContent(allCommodities);
		//TextField searchBar = new TextField("Search...");
        //searchBar.setPrefWidth(215);
		//commodities.setTop(searchBar);
        leftPane.setCenter(commoditiesScroll);
		
        // bottom of the chart
        TabPane bottomPane = new TabPane();
        bottomPane.setMaxHeight(250);
        Tab ordersTab = new Tab();
        Tab pendingTab = new Tab();
        ordersTab.setText("Orders");
        ordersTab.setClosable(false);
        pendingTab.setText("Pending");
        pendingTab.setClosable(false);
        bottomPane.getTabs().addAll(ordersTab, pendingTab);
        
        // orders and pending tabs (extending bottom of the chart)
        TableView ordersTable = new TableView();
        TableColumn instrumentsOrders = new TableColumn("Instruments");
        TableColumn quantityOrders = new TableColumn("Quantity");
        TableColumn buyOrSellOrders = new TableColumn("Buy/Sell");
        TableColumn priceOrders = new TableColumn("Price");
        ordersTable.setEditable(false);
        instrumentsOrders.setMinWidth(100);
        quantityOrders.setMinWidth(100);
        buyOrSellOrders.setMinWidth(100);
        priceOrders.setMinWidth(100);
        TableView pendingTable = new TableView();
        TableColumn instrumentsPending = new TableColumn("Instruments");
        TableColumn quantityPending = new TableColumn("Quantity");
        TableColumn buyOrSellPending = new TableColumn("Buy/Sell");
        TableColumn pricePending = new TableColumn("Price");
        pendingTable.setEditable(false);
        instrumentsPending.setMinWidth(100);
        quantityPending.setMinWidth(100);
        buyOrSellPending.setMinWidth(100);
        pricePending.setMinWidth(100);
        ordersTable.getColumns().addAll(instrumentsOrders, quantityOrders, buyOrSellOrders, priceOrders);
        pendingTable.getColumns().addAll(instrumentsPending, quantityPending, buyOrSellPending, pricePending);
        ordersTab.setContent(ordersTable);
        pendingTab.setContent(pendingTable);
        
        // top menu
        BorderPane topPane = new BorderPane();
        MenuBar menuBar = createMenu();
        topPane.setTop(menuBar);
        BorderPane clientPane = new BorderPane();
        clientPane.setStyle("-fx-border-color: gray;"
        		+ "-fx-border-width: 0 0 3 0;"
        		+ "-fx-background-color: gray");
        clientPane.setPadding(new Insets(15, 0, 15, 10));
        Label clientLabel = new Label("Client: ");
        clientPane.setLeft(clientLabel);
        topPane.setBottom(clientPane);
        
        // put together all elements
        BorderPane root = new BorderPane();
        BorderPane centre = new BorderPane();
        root.setCenter(centre);
        centre.setTop(lineChart);
        centre.setBottom(bottomPane);
        root.setTop(topPane);
        root.setLeft(leftPane);
        root.setRight(rightPane);
        
        Scene scene = new Scene(root, 1200, 800);
        
        stage.setResizable(false);
        stage.setTitle("JAWATrade");
        stage.setScene(scene);
        stage.show();
    }
    
    private GridPane displayAllNews()
    {
    	// insert main grid for all news
    	GridPane allNews = new GridPane();
		allNews.setPrefSize(215, 200);
		allNews.setPadding(new Insets(10, 10, 10, 10));
	
		for (int i = 0; i < exchange.getEvents().size(); i++)
		{
			// create new cells and add them to main grid
			BorderPane news = createNewsCell(exchange.getEvents().get(i).getDateTime().toString(), exchange.getEvents().get(i).getEventText());
		    allNews.add(news, 0, (i * 2 + 1));
		    allNews.add(new Label(), 0, (i * 2));
		}
	    
	    // set alignment of content of grid
	    allNews.setAlignment((Pos.TOP_CENTER));
	    
	    return allNews;
    }
    
    private BorderPane createNewsCell(String newsDate, String newsContent)
    {
    	// create cell in news grid
    	BorderPane news = new BorderPane();
    	// create label for name of news
		Label newsNameLabel = new Label(newsDate);
			newsNameLabel.setFont(new Font(20));
		// create label for content of news
		Label newsContentLabel = new Label(newsContent);
		newsContentLabel.setMaxSize(180, 60);
		newsContentLabel.setMinSize(180, 60);
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
		allCommodities.setPrefSize(200, 100);
		allCommodities.setPadding(new Insets(10, 0, 10, 10));
	
		// create new cells and add them to main grid
		BorderPane commodity1 = createCommodityCell("Commodity 1", "1234.56", "^");
        allCommodities.add(commodity1, 0, 1);
        allCommodities.add(new Label(), 0, 2);
        
        BorderPane commodity2 = createCommodityCell("Commodity 2", "1234.56", "^");
        allCommodities.add(commodity2, 0, 3);
        allCommodities.add(new Label(), 0, 4);
        
        BorderPane commodity3 = createCommodityCell("Commodity 3", "1234.56", "^");
        allCommodities.add(commodity3, 0, 5);
        allCommodities.add(new Label(), 0, 6);
        
        BorderPane commodity4 = createCommodityCell("Commodity 4", "1234.56", "^");
        allCommodities.add(commodity4, 0, 7);
        allCommodities.add(new Label(), 0, 8);
        
        BorderPane commodity5 = createCommodityCell("Commodity 5", "1234.56", "^");
        allCommodities.add(commodity5, 0, 9);
        allCommodities.add(new Label(), 0, 10);
        
        allCommodities.setAlignment((Pos.TOP_CENTER));
        
        return allCommodities;
    }
    
    private BorderPane createCommodityCell(String commodityName, String shareValue, String trend)
    {
    	// create cell in commodities grid
    	BorderPane commodity = new BorderPane();
    		commodity.setPrefSize(200, 50);
    	// create label for name of commodity
		Label commodityNameLabel = new Label(commodityName);
			commodityNameLabel.setFont(new Font(20));
		// create content for commodity cell
		Button newOrderButton = new Button("New Order");
		newOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Stage newOrderStage = createNewOrder();
            }
        });
		Label shareValueLabel = new Label(shareValue);
		Label trendLabel = new Label(trend);
			trendLabel.setFont(new Font(20));
		
		commodity.setTop(commodityNameLabel);
		commodity.setCenter(trendLabel);

		// separate each commodity cell in left and right side
		BorderPane leftPaneCommodity = new BorderPane();
		leftPaneCommodity.setTop(newOrderButton);
		leftPaneCommodity.setBottom(shareValueLabel);
		
		commodity.setLeft(leftPaneCommodity);
		
		return commodity;
    }
    
    private Stage createNewOrder()
    {
    	Stage makeNewOrder = new Stage();
		BorderPane orderPane = new BorderPane();
		orderPane.setPadding(new Insets(10, 30, 10, 30));
		Label quantityLabel = new Label("Quantity:");
		quantityLabel.setFont(new Font(20));
		ComboBox quantities = new ComboBox();
		orderPane.setLeft(quantityLabel);
		orderPane.setRight(quantities);
		BorderPane buyOrSell = new BorderPane();
		Button buyButton = new Button("Buy");
		buyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	//exchange.getSmartTrader().buy(selectedClient, //quantities, company);
            	// need to test for no client selected
            }
        });
		Button sellButton = new Button("Sell");
		buyOrSell.setLeft(buyButton);
		buyOrSell.setRight(sellButton);
		orderPane.setBottom(buyOrSell);
		Scene newOrderScene = new Scene(orderPane, 200, 200);
		makeNewOrder.setScene(newOrderScene);
		
		makeNewOrder.show();
		return makeNewOrder;
    }
    
    private MenuBar createMenu()
    {
		Menu menu = new Menu("Clients");
        MenuItem client1 = new MenuItem("Norbert DaVinci");
        client1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	// need to add a field somewhere in the code where all clients are stored
            	// selectedClient = "Norbert DaVinci";
            }
        });
        MenuItem client2 = new MenuItem("Justine Thyme");
        client2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	//selectedClient = "Justine Thyme";
            }
        });
        menu.getItems().add(client1);
        menu.getItems().add(client2);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
        return menuBar;
    }

    public static void main(String[] args)
    {
        launch(args);
    }
    
}
