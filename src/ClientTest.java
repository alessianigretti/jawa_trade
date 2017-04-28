import static org.junit.Assert.*;

import org.junit.Test;

public class ClientTest {
	
	Client client = new Client("Jonathan", 300.00, 150.00);
	
	@Test
	public void testCalculateInvestment() {
		assertTrue(false);
	}
	
	@Test
	public void testCalculateNetWorth() {
		assertTrue(false);
	}
	
	@Test
	public void testGetCashHolding() {
		assertEquals(150.00, client.getCashHolding(), 0.001);
	}
	
	@Test
	public void testGetExpectedReturn() {
		assertEquals(300.00, client.getExpectedReturn(), 0.001);
	}
	
	@Test
	public void testGetInvestment() {
		assertTrue(false);
	}
	
	@Test
	public void testGetName() {
		assertEquals("Jonathan", client.getName());
	}
	
	@Test
	public void testGetNetWorth() {
		assertTrue(false);
	}
	
	@Test
	public void testGetPortfolio() {
		assertTrue(false);
	}
	
	@Test
	public void testInitialShare() {
		assertTrue(false);
	}
	
	@Test
	public void testNewShare() {
		assertTrue(false);
	}
	
	@Test
	public void testSetExpectedReturn() {
		assertNotEquals(1000.00, client.getExpectedReturn());
		client.setExpectedReturn(1000.00);
		assertEquals(1000.00, client.getExpectedReturn(), 0.001);
	}
	
	@Test
	public void testSetName() {
		assertNotEquals("Alessia", client.getName());
		client.setName("Alessia");
		assertEquals("Alessia", client.getName());
	}
	
	@Test
	public void testSetRiskAll() {
		assertTrue(false);
	}
	
	@Test
	public void testUpdateCash() {
		assertNotEquals(300.00, client.getCashHolding());
		client.updateCash(150.00);
		assertEquals(300.00, client.getCashHolding(), 0.001);
	}
	
	

}
