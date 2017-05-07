import static org.junit.Assert.*;

import org.junit.Test;

public class SmartTraderTest {
	
	SmartTrader smartTrader = new SmartTrader();
	Client client = new Client("Jonathan", 300.00, 150.00);
	Company company = new Company("Name","Food", 100, 15);

	@Test
	public void testAddGetClient() {
		smartTrader.addClient(client);
		assertEquals("Jonathan" , smartTrader.getClients().get(0).getName());
	}
	
	@Test
	public void testSetGetTraderName(){
		assertNotEquals("John",smartTrader.getTraderName());
		smartTrader.setTraderName("John");
		assertEquals("John",smartTrader.getTraderName());
	}
	
	@Test
	public void testGetOrderList(){
		assertNotEquals(1, smartTrader.getOrderList().size());
		client.initialShare(500, company);
		smartTrader.newOrder(client, company, true);
		assertEquals(1, smartTrader.getOrderList().size());
	}
	
	@Test
	public void testGetSetSellBuyRate(){
		assertNotEquals(0.03, smartTrader.getBuyRate());
		assertNotEquals(0.03, smartTrader.getSellRate());
		smartTrader.setBuyRate(0.03);
		smartTrader.setSellRate(0.03);
		assertEquals(0.03, smartTrader.getBuyRate(),0.01);
		assertEquals(0.03, smartTrader.getSellRate(),0.01);
	}
	
	@Test
	public void testGetMode(){
		assertEquals("A.I.",smartTrader.getMode());
	}
	
	@Test
	public void testNewCompleteOrder() {
		/*client.initialShare(1000, company);
		client2.initialShare(1000, company);
		assertEquals(1000, client.shareSize(company),0.01);
		assertEquals(1000, client2.shareSize(company),0.01);
		smartTrader.addClient(client);
		randomTrader1.addClient(client2);
		randomTrader.newOrder(client, company, true);
		randomTrader1.newOrder(client2, company, false);
		randomTrader.completeOrder(randomTrader.getOrderList().getFirst());
		randomTrader1.completeOrder(randomTrader1.getOrderList().getFirst());
		assertNotEquals(1000, client.shareSize(company),0.01);
		assertNotEquals(1000, client2.shareSize(company),0.01);*/
	}
	
}
