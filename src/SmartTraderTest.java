import static org.junit.Assert.*;

import org.junit.Test;

public class SmartTraderTest {
	
	SmartTrader smartTrader = new SmartTrader();
	SmartTrader smartTrader1 = new SmartTrader();
	Client client = new Client("Jonathan", 300.00, 150.00);
	Client client2 = new Client("Alessia", 300.00, 150.00);
	Company company = new Company("Name","Food", 100, 15);

	/**
	 * Test the addClient method and getClients method in the SmartTrader class. This test validates that new clients can be added to a smart traders client list.
	 */
	@Test
	public void testAddGetClient() {
		smartTrader.addClient(client);
		assertEquals("Jonathan" , smartTrader.getClients().get(0).getName());
	}
	
	/**
	 * Test the setTraderName and getTraderName method in the SmartTrader class. This test validates that a smart traders name can be set and retrieved.
	 */
	@Test
	public void testSetGetTraderName(){
		assertNotEquals("John",smartTrader.getTraderName());
		smartTrader.setTraderName("John");
		assertEquals("John",smartTrader.getTraderName());
	}
	
	/**
	 * Test the getOrderList method in the SmartTrader class. This test validates that a smart traders order list can be retrieved.
	 */
	@Test
	public void testGetOrderList(){
		assertNotEquals(1, smartTrader.getOrderList().size());
		client.initialShare(500, company);
		smartTrader.newOrder(client, company, true);
		assertEquals(1, smartTrader.getOrderList().size());
	}
	
	/**
	 * Test the getBuyRate, getSellRate, setBuyRate and setSellRate methods in the SmartTrader class. This test validates that buy/sell rates can be set and retrieved for a smart trader.
	 */
	@Test
	public void testGetSetSellBuyRate(){
		assertNotEquals(0.03, smartTrader.getBuyRate());
		assertNotEquals(0.03, smartTrader.getSellRate());
		smartTrader.setBuyRate(0.03);
		smartTrader.setSellRate(0.03);
		assertEquals(0.03, smartTrader.getBuyRate(),0.01);
		assertEquals(0.03, smartTrader.getSellRate(),0.01);
	}
	
	/**
	 * Test the getMode method in the SmartTrader class. This test validates that the mode of a smart trader can be retrieved.
	 */
	@Test
	public void testGetMode(){
		assertEquals("A.I.",smartTrader.getMode());
	}
	
	/**
	 * Test the newOrder and completeOrder methods in the SmartTrader class. This test validates that the smart traders are capable of placing and fulfilling orders.
	 */
	@Test
	public void testNewCompleteOrder() {
		company.updateShareValue(100);
		company.updateShareValue(200);
		company.updateShareValue(300);
		client.initialShare(0, company);
		client2.initialShare(500, company);
		assertFalse(smartTrader.getOrderList().size() == 1);
		assertFalse(smartTrader1.getOrderList().size() == 1);
		assertEquals(0, client.shareSize(company),0.01);
		assertEquals(500, client2.shareSize(company),0.01);
		smartTrader.addClient(client);
		smartTrader1.addClient(client2);
		smartTrader.newOrder(client, company, true);
		smartTrader1.newOrder(client2, company, false);
		assertTrue(smartTrader.getOrderList().size() == 1);
		assertTrue(smartTrader1.getOrderList().size() == 1);
		smartTrader.completeOrder(smartTrader.getOrderList().getFirst());
		smartTrader1.completeOrder(smartTrader1.getOrderList().getFirst());
		assertNotEquals(0, client.shareSize(company),0.01);
		assertNotEquals(500, client2.shareSize(company),0.01);
	}
	
}
