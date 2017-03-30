import java.awt.Panel;

import com.sun.glass.ui.MenuItem;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
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

/**
 *
 */
public class GUI extends Application
{
    private Stage stage;
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
        ScrollPane newsfeedScroll = new ScrollPane();
        newsfeedScroll.setPrefSize(215, 200);
	    	GridPane allNews = displayAllNews();
        newsfeedScroll.setContent(allNews);
        
        // commodities panel
        BorderPane commodities = new BorderPane();
        commodities.setPadding(new Insets(10, 0, 10, 10));
        TextField searchBar = new TextField("Search...");
        searchBar.setPrefWidth(215);
        ScrollPane commoditiesScroll = new ScrollPane();
        commoditiesScroll.setPrefSize(215, 100);
        	GridPane allCommodities = displayAllCommodities();
		commoditiesScroll.setContent(allCommodities);
		commodities.setTop(searchBar);
        commodities.setCenter(commoditiesScroll);
		
        // bottom of the chart
        TabPane bottomPane = new TabPane();
        bottomPane.setPrefHeight(200);
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
        Menu menu = new Menu("Settings");
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
        
        // put together all elements
        BorderPane root = new BorderPane();
        BorderPane centre = new BorderPane();
        root.setCenter(centre);
        centre.setTop(lineChart);
        centre.setBottom(bottomPane);
        root.setTop(menuBar);
        root.setLeft(commodities);
        root.setRight(newsfeedScroll);
        
        Scene scene = new Scene(root, 1200, 800);
        
        stage.setTitle("JAWATrade");
       
        stage.setScene(scene);
        stage.show();
    }
    
    private GridPane displayAllNews()
    {
    	// insert main grid for all news
    	GridPane allNews = new GridPane();
		allNews.setPrefWidth(210);
		allNews.setPadding(new Insets(10, 10, 10, 10));
	
		// create new cells and add them to main grid
		GridPane news1 = createNewsCell("News 1", "News Content 1 - Information about the first news to be displayed here.");
	    allNews.add(news1, 0, 1);
	    allNews.add(new Label(), 0, 2);
	    
	    GridPane news2 = createNewsCell("News 2", "News Content 2 - Information about the first news to be displayed here.");
	    allNews.add(news2, 0, 3);
	    allNews.add(new Label(), 0, 4);
	    
	    GridPane news3 = createNewsCell("News 3", "News Content 3 - Information about the first news to be displayed here.");
	    allNews.add(news3, 0, 5);
	    allNews.add(new Label(), 0, 6);
	    
	    GridPane news4 = createNewsCell("News 4", "News Content 4 - Information about the first news to be displayed here.");
	    allNews.add(news4, 0, 7);
	    allNews.add(new Label(), 0, 8);
	    
	    GridPane news5 = createNewsCell("News 5", "News Content 5 - Information about the first news to be displayed here.");
	    allNews.add(news5, 0, 9);
	    allNews.add(new Label(), 0, 10);
	    
	    GridPane news6 = createNewsCell("News 6", "News Content 6 - Information about the first news to be displayed here.");
	    allNews.add(news6, 0, 11);
	    allNews.add(new Label(), 0, 12);
	    
	    // set alignment of content of grid
	    allNews.setAlignment((Pos.TOP_CENTER));
	    
	    return allNews;
    }
    
    private GridPane createNewsCell(String newsName, String newsContent)
    {
    	// create cell in news grid
    	GridPane news = new GridPane();
    	// create label for name of news
		Label newsNameLabel = new Label(newsName);
			newsNameLabel.setFont(new Font(20));
		// create label for content of news
		Label newsContentLabel = new Label(newsContent);
			newsContentLabel.setWrapText(true);
		    news.add(newsNameLabel, 0, 0);
	        news.add(newsContentLabel, 0, 2);
	        news.setAlignment(Pos.TOP_CENTER);
	   return news;
    }
    
    private GridPane displayAllCommodities()
    {
    	// insert main grid for all commodities
    	GridPane allCommodities = new GridPane();
		allCommodities.setPrefSize(200, 100);
		allCommodities.setPadding(new Insets(10, 0, 10, 10));
	
		// create new cells and add them to main grid
		BorderPane commodity1 = createCommodityCell("Commodity 1", "1234.56", "1234.56", "^");
        allCommodities.add(commodity1, 0, 1);
        allCommodities.add(new Label(), 0, 2);
        
        BorderPane commodity2 = createCommodityCell("Commodity 2", "1234.56", "1234.56", "^");
        allCommodities.add(commodity2, 0, 3);
        allCommodities.add(new Label(), 0, 4);
        
        BorderPane commodity3 = createCommodityCell("Commodity 3", "1234.56", "1234.56", "^");
        allCommodities.add(commodity3, 0, 5);
        allCommodities.add(new Label(), 0, 6);
        
        BorderPane commodity4 = createCommodityCell("Commodity 4", "1234.56", "1234.56", "^");
        allCommodities.add(commodity4, 0, 7);
        allCommodities.add(new Label(), 0, 8);
        
        BorderPane commodity5 = createCommodityCell("Commodity 5", "1234.56", "1234.56", "^");
        allCommodities.add(commodity5, 0, 9);
        allCommodities.add(new Label(), 0, 10);
        
        allCommodities.setAlignment((Pos.TOP_CENTER));
        
        return allCommodities;
    }
    
    private BorderPane createCommodityCell(String commodityName, String buyValue, String sellValue, String upOrDownArrow)
    {
    	// create cell in commodities grid
    	BorderPane commodity = new BorderPane();
    		commodity.setPrefSize(200, 50);
    	// create label for name of commodity
		Label commodityNameLabel = new Label(commodityName);
			commodityNameLabel.setFont(new Font(20));
		// create content for commodity cell
		Button buyButton = new Button("Buy");
		buyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // create new window for buying/selling and inserting 
            	exchange.getSmartTrader().buy(client, quantity, company);
            }
        });
		Button sellButton = new Button("Sell");
		sellButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Trader.sell
            }
        });
		Label buyValueLabel = new Label(buyValue);
		Label sellValueLabel = new Label(sellValue);
		Label upOrDown = new Label(upOrDownArrow);
		upOrDown.setFont(new Font(20));
		
		commodity.setTop(commodityNameLabel);
		commodity.setCenter(upOrDown);

		// separate each commodity cell in left and right side
		BorderPane leftPaneCommodity = new BorderPane();
		leftPaneCommodity.setTop(buyButton);
		leftPaneCommodity.setBottom(buyValueLabel);
		
		commodity.setLeft(leftPaneCommodity);
		
		BorderPane rightPaneCommodity = new BorderPane();
		rightPaneCommodity.setTop(sellButton);
		rightPaneCommodity.setBottom(sellValueLabel);
		
		commodity.setRight(rightPaneCommodity);
		
		return commodity;
    }

    public static void main(String[] args)
    {
        launch(args);
    }
    
}
