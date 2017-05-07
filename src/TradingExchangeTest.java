import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Test;

import com.opencsv.CSVReader;

public class TradingExchangeTest {

	TradingExchange tradingExchange = new TradingExchange();
	Events event = new Events("Event text","Jan 1 2017","9:15","buy","UK","1");
	Company company = new Company("Name", "Food", 11, 15);
	
	/**
	 * Test of the getCompanies method in the TradingExchange. This test validates that the companies are set up properly in the exchange
	 */
	@Test
	public void testGetCompanies() {
		assertEquals("Pear Computing", tradingExchange.getCompanies().get(0).getName());
	}
	
	/**
	 *  Test of the getCurrentClient and setCurrentClient in the TradingExchange. These tests validate 
	 */
	@Test
	public void testGetAndSetCurrentClient() {
		assertNull(tradingExchange.getCurrentClient());
		Client client = new Client("Alessia", 10);
		tradingExchange.setCurrentClient(client);
		assertEquals("Alessia", tradingExchange.getCurrentClient().getName());
	}
	
	/**
	 * Test of the getDate method in the TradingExchange. This test validates that the date is formatted in the correct format
	 */
	@Test
	public void testGetDate() {
		assertEquals("2017-01-01", tradingExchange.getDate());
	}
	
	/**
	 * Test of the getEvents method in the TradingExchange class. This test validates that the initial events are set up correctly in the events list
	 */
	@Test
	public void testGetEvents() {
		assertEquals("Q1Q tech announce exciting developments in their smartphone range anticipating a new model in June 2017", tradingExchange.getEvents().get(0).getEventText());
	}
	
	/**
	 * Test of the getShareIndex in the TradingExchange class. This test validates that the starting share index of all the companies is 2.395
	 */
	@Test
	public void testGetShareIndex() {
		assertEquals(2.395, tradingExchange.getShareIndex(), 0.01);
	}
	
	/**
	 * Test of the getSmartTrader method in the TradingExchange class. This test validates that a smartTrader is initialised on set up of the exchange.
	 */
	@Test
	public void testGetSmartTrader() {
		assertEquals("W&G Trader", tradingExchange.getSmartTrader().getTraderName());
	}
	
	/**
	 * Test of the getTime method in the Trading Exchange class. This test validates that the time is formatted in the correct format
	 */
	@Test
	public void testGetTime() {
		assertEquals("09:00", tradingExchange.getTime());
	}
	
	/**
	 * Test the getTraders method in the Trading exchange class. This test validates that the traders are set up correctly and that new traders can participate in the exchange
	 */
	@Test
	public void testGetTraders() {
		assertEquals(5, tradingExchange.getTraders().size());
		tradingExchange.getTraders().add(new SmartTrader());
		assertEquals(6, tradingExchange.getTraders().size());
	}
	
	/**
	 * Test of the getXChart method in the Trading exchange class. This test validates that the x axis in the chart is set up correctly
	 */
	@Test
	public void testGetXChart() {
		assertEquals("1", tradingExchange.getXChart().get(0).toString());
	}
	
	
	/**
	 * Test of the marketStatus method in the TradingExchange class. This test validates that the market state changes dependent on whether it's share index has changed
	 */
	@Test
	public void testMarketStatus() {
		assertEquals("Undefined", tradingExchange.marketStatus());
	}
	
	/**
	 * Test of the setUpClients method in the TradingExchange class. This test validates that the initial data for the clients are properly read into the simulation
	 * 
	 */
	@Test
	public void testSetUpClients() {
		try {
			CSVReader reader = new CSVReader(new FileReader("clients.csv"));
			tradingExchange.setUpClients(reader);
			assertEquals("Norbert DaVinci", tradingExchange.getTraders().get(0).getClients().get(0).getName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test of the setUpCompanies method in the TradingExchange class. This test validates that the initial data for the companies are properly read into the simulation
	 */
	@Test
	public void testSetUpCompanies() {
		try {
			CSVReader reader = new CSVReader(new FileReader("companies.csv"));
			tradingExchange.setUpCompanies(reader);
			assertEquals("Pear Computing", tradingExchange.getCompanies().get(0).getName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test of the setUpEvents method in the TradingExchange class. This test validates that the initial data for the events are properly read into the simulation
	 */
	@Test
	public void testSetUpEvents() {
		try {
			CSVReader reader = new CSVReader(new FileReader("events.csv"));
			tradingExchange.setUpEvents(reader);
			assertEquals("Q1Q tech announce exciting developments in their smartphone range anticipating a new model in June 2017", tradingExchange.getEvents().get(0).getEventText());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test of the updateDateTime method in the TradingExchange class. This test validates that both the date and time are incremented accordingly after every trade simulation
	 */
	@Test
	public void testUpdateDateTime() {
		assertEquals("09:00", tradingExchange.getTime());
		tradingExchange.updateDateTime();
		assertEquals("09:15", tradingExchange.getTime());
	}
	
	/**
	 * Test of updateShareIndex method in TradingExchange class. This test validates that the market index changes after a trade simulation
	 */
	@Test
	public void testUpdateShareIndex() {
		assertEquals(2.395, tradingExchange.getShareIndex(), 0.01);
		for(int i = 0; i<97; i++)
			tradingExchange.updateDateTime();
		tradingExchange.tradeSim();
		tradingExchange.updateShareIndex();
		assertNotEquals(2.395, tradingExchange.getShareIndex(), 0.01);
	}
	
	/**
	 * Test of both the checkEvent and endEvent method from the TradingExchange class. This test validates that timed events affect both the company and the market
	 */
	@Test
	public void testCheckEndEvent(){
		tradingExchange.getEvents().addFirst(event);
		assertFalse(tradingExchange.getCompanies().get(0).isEventTriggered());
		tradingExchange.updateDateTime();
		tradingExchange.checkEvent();
		assertTrue(tradingExchange.getCompanies().get(0).isEventTriggered());
		for(int i = 0; i<96; i++)
			tradingExchange.updateDateTime();
		tradingExchange.endEvents();
		assertFalse(tradingExchange.getCompanies().get(0).isEventTriggered());
	}
	
	/**
	 * Test of isMarketClosed method from TradingExchange class. This test validates that the market is closed on weekends and bank holidays
	 */
	@Test
	public void testIsMarketClosed(){
		assertTrue(tradingExchange.isMarketClosed());
		for(int i = 0; i<96; i++)
			tradingExchange.updateDateTime();
		assertFalse(tradingExchange.isMarketClosed());
	}
	
	/**
	 * Test of isCompanyTradeable method from TradingExchange class. This test validates that a company becomes insolvent after its share values goes below 0.01
	 */
	@Test
	public void testIsCompanyTradeable(){
		assertTrue(tradingExchange.isCompanyTradable(company));
		company.updateShareValue(-1000);
		assertFalse(tradingExchange.isCompanyTradable(company));
	}
	
}
