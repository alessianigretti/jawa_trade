import static org.junit.Assert.*;

import org.junit.Test;

public class ClientTest {
	
	Client client = new Client("Jonathan", 300.00, 150.00);
	Company company = new Company("Name","Food", 100, 15);
	
	/**
	 * Test the calculateInvestment method in the Client class. This test validates that the worth of a client's portfolio is calculated correctly.
	 */
	@Test
	public void testCalculateInvestment() {
		assertEquals(0, client.getInvestment(),0.01);
		client.initialShare(1, company);
		client.calculateInvestment();
		assertEquals(1, client.getInvestment(), 0.01);
	}
	
	/**
	 * Test the calculateNetWorth method in the Client class. This test validates that the client networth is calculated correctly.
	 */
	@Test
	public void testCalculateNetWorth() {
		client.calculateNetWorth();
		assertEquals(150.0, client.getNetWorth(),0.01);
		client.initialShare(1, company);
		client.calculateInvestment();
		client.calculateNetWorth();
		assertEquals(151.0, client.getNetWorth(), 0.01);
		
	}
	
	/**
	 * Test the getCashHolding method in the Client Class. This test validates that a clients cash holding can be retrieved.
	 */
	@Test
	public void testGetCashHolding() {
		assertEquals(150.00, client.getCashHolding(), 0.001);
	}
	
	/**
	 * Test the getExpectedReturn method in the client Class. This test validates that a clients expected return can be retrieved.
	 */
	@Test
	public void testGetExpectedReturn() {
		assertEquals(300.00, client.getExpectedReturn(), 0.001);
	}
	
	/**
	 * Test the getInvestment method in the Client class. This test validates that a clients investment value can be retrieved.
	 */
	@Test
	public void testGetInvestment() {
		assertEquals(0, client.getInvestment(), 0.01);;
	}
	
	/**
	 * Test the getName method in the Client class. This test validates that a clients name can be retrieved.
	 */
	@Test
	public void testGetName() {
		assertEquals("Jonathan", client.getName());
	}
	
	/**
	 * Test the getNetWorth method in the Client class. This test validates that a client networths can be retrieved.
	 */
	@Test
	public void testGetNetWorth() {
		assertEquals(0, client.getInvestment(), 0.01);
		assertEquals(150, client.getCashHolding(), 0.01);
		client.calculateNetWorth();
		assertEquals(150, client.getNetWorth(), 0.01);
	}
	
	/**
	 * Test the getPortfolio method in the Client class. This test validates that a client portfolio can be retrieved. 
	 */
	@Test
	public void testGetPortfolio() {
		assertEquals(0, client.getPortfolio().size());
		client.initialShare(1, company);
		assertEquals(1, client.getPortfolio().size());
	}
	
	/**
	 * Test the initialShare method in the Client class. This test validates that a client can add a new share to the portfolio.
	 */
	@Test
	public void testInitialShare() {
		assertFalse(client.hasShare(company)); 
		client.initialShare(1, company);
		assertTrue(client.hasShare(company));
	}
	
	/**
	 * The the newShare method in the Client class. This test validates that an existing share can have its size updated.
	 */
	@Test
	public void testNewShare() {
		client.initialShare(1, company);
		client.newShare(1, company);
		assertEquals(2, client.shareSize(company),0.01);
	}
	
	/**
	 * Test the setExpectedReturn method in the Client class. This test validates that a clients expected return on their investments can be set. 
	 */
	@Test
	public void testSetExpectedReturn() {
		assertNotEquals(1000.00, client.getExpectedReturn());
		client.setExpectedReturn(1000.00);
		assertEquals(1000.00, client.getExpectedReturn(), 0.001);
	}
	
	/**
	 * Test the setName method in the Client class. This test validates that a client's name can be changed.
	 */
	@Test
	public void testSetName() {
		assertNotEquals("Alessia", client.getName());
		client.setName("Alessia");
		assertEquals("Alessia", client.getName());
	}
	
	/**
	 * Test the getRisk and setRisk methods in the Client class. This test validates that a client can be assigned a different risk based off their preference.
	 */
	@Test
	public void testSetGetRisk() {
		assertEquals("High", client.getRisk());
		client.setRisk("Low");
		assertEquals("Low", client.getRisk());
		
	}
	
	
	/**
	 * Test the updateCash method in the Client class. This test validates that a clients cash holding can be updated.
	 */
	@Test
	public void testUpdateCash() {
		assertNotEquals(300.00, client.getCashHolding());
		client.updateCash(150.00);
		assertEquals(300.00, client.getCashHolding(), 0.001);
	}
	
	/**
	 * Test the hasShare method in the Client class. This test validates that a trader can check if a client has a particular share or not.
	 */
	@Test
	public void testHasShare(){
		assertFalse(client.hasShare(company));
		client.initialShare(500, company);
		assertTrue(client.hasShare(company));
	}
	
	/**
	 * Test the shareSize method in the Client class. This test validates that the share size is correctly set when creating a new share.
	 */
	@Test
	public void testShareSize(){
		assertNotEquals(500,client.shareSize(company),0.01);
		client.initialShare(500, company);
		assertEquals(500,client.shareSize(company),0.01);
	}
	
	/**
	 * Test the getBuyMax, getSellMax, setBuyMax and setSellMax methods in the Client class. These tests validate the the buy/sell max for a client in a trading cycle are kept and updated.
	 */
	@Test
	public void testSetGetBuySellMax(){
		assertNotEquals(1.5, client.getBuyMax());
		assertNotEquals(1.5, client.getSellMax());
		client.setBuyMax(0.01);
		client.setSellMax(0.01);
		assertEquals(1.5, client.getBuyMax(),0.01);
		assertEquals(1.5, client.getSellMax(),0.01);
	}
	
	/**
	 * Test the getBuyAmount, getSellAmount, setBuyAmount and setSellAmount methods in the Client class. These tests validate the the buy/sell amount for a client in a trading cycle are kept and updated.
	 */
	@Test
	public void testSetGetBuySellAmount(){
		assertNotEquals(100, client.getBuyAmount());
		assertNotEquals(150, client.getSellAmount());
		client.setBuyAmount(100);
		client.setSellAmount(150);
		assertEquals(100, client.getBuyAmount(),0.01);
		assertEquals(150, client.getSellAmount(),0.01);
	}
	

}
