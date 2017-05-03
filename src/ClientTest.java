import static org.junit.Assert.*;

import org.junit.Test;

public class ClientTest {
	
	Client client = new Client("Jonathan", 300.00, 150.00);
	Company company = new Company("Name","Food", 100, 15);
	
	@Test
	public void testCalculateInvestment() {
		assertEquals(0, client.getInvestment(),0.01);
		client.initialShare(1, company);
		client.calculateInvestment();
		assertEquals(1, client.getInvestment(), 0.01);
	}
	
	@Test
	public void testCalculateNetWorth() {
		client.calculateNetWorth();
		assertEquals(150.0, client.getNetWorth(),0.01);
		client.initialShare(1, company);
		client.calculateInvestment();
		client.calculateNetWorth();
		assertEquals(151.0, client.getNetWorth(), 0.01);
		
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
		assertEquals(0, client.getInvestment(), 0.01);;
	}
	
	@Test
	public void testGetName() {
		assertEquals("Jonathan", client.getName());
	}
	
	@Test
	public void testGetNetWorth() {
		assertEquals(0, client.getInvestment(), 0.01);
		assertEquals(150, client.getCashHolding(), 0.01);
		client.calculateNetWorth();
		assertEquals(150, client.getNetWorth(), 0.01);
	}
	
	@Test
	public void testGetPortfolio() {
		assertEquals(0, client.getPortfolio().size());
		client.initialShare(1, company);
		assertEquals(1, client.getPortfolio().size());
	}
	
	@Test
	public void testInitialShare() {
		assertFalse(client.hasShare(company)); //found a massive bug through this!! Thanks JUnit!
		client.initialShare(1, company);
		assertTrue(client.hasShare(company));
	}
	
	@Test
	public void testNewShare() {
		client.initialShare(1, company);
		client.newShare(1, company);
		assertEquals(2, client.shareSize(company),0.01);
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
	public void testSetRisk() {
		assertEquals("High", client.getRisk());
		client.setRisk("Low");
		assertEquals("Low", client.getRisk());
		
	}
	
	@Test
	public void testUpdateCash() {
		assertNotEquals(300.00, client.getCashHolding());
		client.updateCash(150.00);
		assertEquals(300.00, client.getCashHolding(), 0.001);
	}
	
	

}
