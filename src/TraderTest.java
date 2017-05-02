import static org.junit.Assert.*;

import org.junit.Test;

public class TraderTest {

	Trader trader = new Trader();
	Client client = new Client("Jon", 10);
	
	@Test
	public void testAddAndGetClients() {
		trader.addClient(client);
		assertEquals(client, trader.getClients().get(0));
	}
	
	@Test
	public void testAddAndGetOrderHistory() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testClearOrders() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetOrderList() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetAndSetTraderName() {
		assertEquals("No trader selected.", trader.getTraderName());
		trader.setTraderName("Alessia");
		assertEquals("Alessia", trader.getTraderName());
	}
	
	@Test
	public void testNewOrder() {
		fail("Not yet implemented");
	}

}
