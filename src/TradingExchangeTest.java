import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Test;

import com.opencsv.CSVReader;

public class TradingExchangeTest {

	TradingExchange tradingExchange = new TradingExchange();
	
	@Test
	public void testGetCompanies() {
		assertEquals("Pear Computing", tradingExchange.getCompanies().get(0).getName());
	}
	
	@Test
	public void testGetAndSetCurrentClient() {
		assertNull(tradingExchange.getCurrentClient());
		Client client = new Client("Alessia", 10);
		tradingExchange.setCurrentClient(client);
		assertEquals("Alessia", tradingExchange.getCurrentClient().getName());
	}
	
	@Test
	public void testGetDate() {
		assertEquals("2017-02-06", tradingExchange.getDate());
	}
	
	@Test
	public void testGetEvents() {
		assertEquals("Q1Q tech announce exciting developments in their smartphone range anticipating a new model in June 2017", tradingExchange.getEvents().get(0).getEventText());
	}
	
	@Test
	public void testGetShareIndex() {
		assertEquals(2.395, tradingExchange.getShareIndex(), 0.01);
	}
	
	@Test
	public void testGetSmartTrader() {
		assertEquals("W&G Trader", tradingExchange.getSmartTrader().getTraderName());
	}
	
	@Test
	public void testGetTime() {
		assertEquals("09:00", tradingExchange.getTime());
	}
	
	@Test
	public void testGetTraders() {
		assertEquals(4, tradingExchange.getTraders().size());
		tradingExchange.getTraders().add(new SmartTrader());
		assertEquals(5, tradingExchange.getTraders().size());
	}
	
	@Test
	public void testGetXChart() {
		assertEquals("1", tradingExchange.getXChart().get(0).toString());
	}
	
	
	@Test
	public void testMarketStatus() {
		assertEquals("Undefined", tradingExchange.marketStatus());
	}
	
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
	
	@Test
	public void testUpdateDateTime() {
		assertEquals("09:00", tradingExchange.getTime());
		tradingExchange.updateDateTime();
		assertEquals("09:15", tradingExchange.getTime());
	}
	
	@Test
	public void testUpdateShareIndex() {
		assertEquals(2.395, tradingExchange.getShareIndex(), 0.01);
		tradingExchange.tradeSim();
		tradingExchange.updateShareIndex();
		assertNotEquals(2.395, tradingExchange.getShareIndex(), 0.01);
	}
	
}
