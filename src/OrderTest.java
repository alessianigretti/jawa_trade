import static org.junit.Assert.*;

import org.junit.Test;

public class OrderTest {

	Company company = new Company("Company", "Food", 100, 10);
	Client client = new Client("Client", 100);
	Order order = new Order(company, 10, true, 100, "High", client);
	
	/**
	 * Test the fullyCompleted method in the Orders class.
	 */
	@Test
	public void testFullyCompleted() {
		assertFalse(order.isFullyCompleted());
		order.fullyCompleted();
		assertTrue(order.isFullyCompleted());
	}

	/**
	 * Test the getClient method in the Orders class.
	 */
	@Test
	public void testGetClient() {
		assertEquals(client, order.getClient());
	}
	
	/**
	 * Test the getClientColumn and setClientColumn methods in the Orders class.
	 */
	@Test
	public void testGetAndSetClientColumn() {
		assertNotEquals("Client Test", order.getClientColumn());
		order.setClientColumn("Client Test");
		assertEquals("Client Test", order.getClientColumn());
	}
	
	/**
	 * Test the getClientName method in the Orders class.
	 */
	@Test
	public void testGetClientName() {
		assertEquals("Client", order.getClientName());
	}
	
	/**
	 * Test the getClosingPrice and setClosingPrice methods in the Orders class.
	 */
	@Test
	public void testGetAndSetClosingPrice() {
		assertNotEquals(1, order.getClosingPrice());
		order.setClosingPrice();
		assertEquals(1, order.getClosingPrice(), 0.01);
	}
	
	/**
	 * Test the getCompany method in the Orders class. This test validates that the order company for an order can be retrieved.
	 */
	@Test
	public void testGetCompany() {
		assertEquals(company, order.getCompany());
	}
	
	/**
	 * Test the getCompanyColumn and setCompanyColumn methods in the Orders class.
	 */
	@Test
	public void testGetAndSetCompanyColumn() {
		assertNotEquals("Company Test", order.getCompanyColumn());
		order.setCompanyColumn("Company Test");
		assertEquals("Company Test", order.getCompanyColumn());
	}
	
	/**
	 * Test the getCompanyName method in the Orders class. This test validates that the company name for an order can be retrieved.
	 */
	@Test
	public void testGetCompanyName() {
		assertEquals("Company", order.getCompanyName());
	}
	
	/**
	 * Test the getCurrentShareValue method in the Orders class. This test validates that the current share price for an order can be retrieved.
	 */
	@Test
	public void testGetCurrentShareValue() {
		assertEquals(1, order.getCurrentShareValue(), 0.01);
	}
	
	/**
	 * Test the getOpeningPrice method in the Orders class. This test validates that the opening price for an order can be retrieved.
	 */
	@Test
	public void testGetOpeningPrice() {
		assertEquals(1, order.getOpeningPrice(), 0.01);
	}
	
	/**
	 * Test the getOrderType method in the Orders class. This test validates that the order type for an order can be retrieved.
	 */
	@Test
	public void testGetOrderType() {
		assertTrue(order.getOrderType());
	}
	
	/**
	 * Test the getOrderTypeColumn and setOrderTypeColumn methods in the Orders class.
	 */
	@Test
	public void testGetAndSetOrderTypeColumn() {
		assertNotEquals("Order Type Test", order.getOrderTypeColumn());
		order.setOrderTypeColumn("Order Type Test");
		assertEquals("Order Type Test", order.getOrderTypeColumn());
	}
	
	/**
	 * Test the getPriceColumn and setPriceColumn methods in the Orders class.
	 */
	@Test
	public void testGetAndSetPriceColumn() {
		assertNotEquals(10, order.getPriceColumn());
		order.setPriceColumn(10);
		assertEquals(10, order.getPriceColumn(), 0.01);
	}
	
	/**
	 * Test the getQuantity method in the Orders class. This test validates that the quantity for an order can be retrieved.
	 */
	@Test
	public void testGetQuantity() {
		assertEquals(10, order.getQuantity());
	}
	
	/**
	 * Test the getQuantityColumn and setQuantityColumn methods in the Orders class.
	 */
	@Test
	public void testGetAndSetQuantityColumn() {
		assertNotEquals(100, order.getQuantityColumn());
		order.setQuantityColumn(100);
		assertEquals(100, order.getQuantityColumn());
	}
	
	/**
	 * Test the getRiskColumn and setRiskColumn methods in the Orders class.
	 */
	@Test
	public void testGetAndSetRiskColumn() {
		assertNotEquals("High", order.getRiskColumn());
		order.setRiskColumn("High");
		assertEquals("High", order.getRiskColumn());
	}
	
	/**
	 * Test the updateQuantity method in the Orders class. This test validates that a orders quantity can be updateed.
	 */
	@Test
	public void testUpdateQuantity() {
		assertNotEquals(15, order.getQuantity());
		order.updateQuantity(5);
		assertEquals(15, order.getQuantity());
	}
}
