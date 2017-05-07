import static org.junit.Assert.*;

import org.junit.Test;

public class RandomTraderTest {

	RandomTrader randomTrader = new RandomTrader(0);
	RandomTrader randomTrader1 = new RandomTrader(1);
	Client client = new Client("Jonathan", 300.00, 150.00);
	Client client2 = new Client("Alessia", 300.00, 150.00);
	Company company = new Company("Name","Food", 100, 15);
	
	
	@Test
	public void testGetAndSetMode() {
		assertEquals(RandomTrader.Mode.BALANCED.toString(), randomTrader.getMode());
		randomTrader.setMode(RandomTrader.Mode.AGGRESSIVE_BUY);
		assertEquals(RandomTrader.Mode.AGGRESSIVE_BUY.toString(), randomTrader.getMode());
	}
	
	@Test
	public void testSwitchMode(){
		assertEquals(RandomTrader.Mode.BALANCED.toString(), randomTrader.getMode());
		randomTrader.switchMode(0.05);
		assertEquals(RandomTrader.Mode.AGGRESSIVE_SELL.toString(), randomTrader.getMode());
	}
	
	@Test
	public void testNewCompleteOrder() {
		client.initialShare(1000, company);
		client2.initialShare(1000, company);
		assertEquals(1000, client.shareSize(company),0.01);
		assertEquals(1000, client2.shareSize(company),0.01);
		randomTrader.addClient(client);
		randomTrader1.addClient(client2);
		randomTrader.newOrder(client, company, true);
		randomTrader1.newOrder(client2, company, false);
		randomTrader.completeOrder(randomTrader.getOrderList().getFirst());
		randomTrader1.completeOrder(randomTrader1.getOrderList().getFirst());
		assertNotEquals(1000, client.shareSize(company),0.01);
		assertNotEquals(1000, client2.shareSize(company),0.01);
	}
	
	@Test
	public void testAddGetClient() {
		randomTrader.addClient(client);
		assertEquals("Jonathan" , randomTrader.getClients().get(0).getName());
	}
	
	@Test
	public void testSetGetTraderName(){
		assertNotEquals("John",randomTrader.getTraderName());
		randomTrader.setTraderName("John");
		assertEquals("John",randomTrader.getTraderName());
	}
	
	@Test
	public void testGetOrderList(){
		assertNotEquals(1, randomTrader.getOrderList().size());
		client.initialShare(500, company);
		randomTrader.newOrder(client, company, true);
		assertEquals(1, randomTrader.getOrderList().size());
	}
	
	@Test
	public void testGetSetSellBuyRate(){
		assertNotEquals(0.03, randomTrader.getBuyRate());
		assertNotEquals(0.03, randomTrader.getSellRate());
		randomTrader.setBuyRate(0.03);
		randomTrader.setSellRate(0.03);
		assertEquals(0.03, randomTrader.getBuyRate(),0.01);
		assertEquals(0.03, randomTrader.getSellRate(),0.01);
	}
	
	@Test
	public void testGetMode(){
		assertEquals("BALANCED",randomTrader.getMode());
	}
}
