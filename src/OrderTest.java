import static org.junit.Assert.*;

import org.junit.Test;

public class OrderTest {

	Company company = new Company("Company", "Food", 100, 10);
	Client client = new Client("Client", 100);
	Order order = new Order(company, 10, true, 100, "High", client);
	
	@Test
	public void testFullyCompleted() {
		assertFalse(order.isFullyCompleted());
		order.fullyCompleted();
		assertTrue(order.isFullyCompleted());
	}

	@Test
	public void testGetClient() {
		assertEquals(client, order.getClient());
	}
	
	@Test
	public void testGetAndSetClientColumn() {
		assertNotEquals("Client Test", order.getClientColumn());
		order.setClientColumn("Client Test");
		assertEquals("Client Test", order.getClientColumn());
	}
	
	@Test
	public void testGetClientName() {
		assertEquals("Client", order.getClientName());
	}
	
	@Test
	public void testGetAndSetClosingPrice() {
		assertNotEquals(1, order.getClosingPrice());
		order.setClosingPrice();
		assertEquals(1, order.getClosingPrice(), 0.01);
	}
	
	@Test
	public void testGetCompany() {
		assertEquals(company, order.getCompany());
	}
	
	@Test
	public void testGetAndSetCompanyColumn() {
		assertNotEquals("Company Test", order.getCompanyColumn());
		order.setCompanyColumn("Company Test");
		assertEquals("Company Test", order.getCompanyColumn());
	}
	
	@Test
	public void testGetCompanyName() {
		assertEquals("Company", order.getCompanyName());
	}
	
	@Test
	public void testGetCurrentShareValue() {
		assertEquals(1, order.getCurrentShareValue(), 0.01);
	}
	
	@Test
	public void testGetOpeningPrice() {
		assertEquals(1, order.getOpeningPrice(), 0.01);
	}
	
	@Test
	public void testGetOrderType() {
		assertTrue(order.getOrderType());
	}
	
	@Test
	public void testGetAndSetOrderTypeColumn() {
		assertNotEquals("Order Type Test", order.getOrderTypeColumn());
		order.setOrderTypeColumn("Order Type Test");
		assertEquals("Order Type Test", order.getOrderTypeColumn());
	}
	
	@Test
	public void testGetAndSetPriceColumn() {
		assertNotEquals(10, order.getPriceColumn());
		order.setPriceColumn(10);
		assertEquals(10, order.getPriceColumn(), 0.01);
	}
	
	@Test
	public void testGetQuantity() {
		assertEquals(10, order.getQuantity());
	}
	
	@Test
	public void testGetAndSetQuantityColumn() {
		assertNotEquals(100, order.getQuantityColumn());
		order.setQuantityColumn(100);
		assertEquals(100, order.getQuantityColumn());
	}
	
	@Test
	public void testGetAndSetRiskColumn() {
		assertNotEquals("High", order.getRiskColumn());
		order.setRiskColumn("High");
		assertEquals("High", order.getRiskColumn());
	}
	
	@Test
	public void testUpdateQuantity() {
		assertNotEquals(15, order.getQuantity());
		order.updateQuantity(5);
		assertEquals(15, order.getQuantity());
	}
}
