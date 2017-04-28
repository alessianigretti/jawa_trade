import static org.junit.Assert.*;

import org.junit.Test;

public class SharesTest {

	public int size = 1;
	public Company company = new Company("JAWA", "FOOD", 1, 1);
	public Shares shares = new Shares(size, company);
	
	@Test
	public void testGetCompanyName() {
		assertEquals(company.getName(), shares.getCompanyName());
	}
	
	@Test
	public void testGetPrice() {
		assertEquals(company.getCurrentShareValue(), shares.getPrice(), 0.001);
	}
	
	@Test
	public void testGetRisk() {
		assertTrue(false);
	}
	
	@Test
	public void testGetShareValue() {
		assertEquals(shares.getPrice() * size, shares.getShareValue(), 0.001);
	}
	
	@Test
	public void testGetSize() {
		assertEquals(size, shares.getSize(), 0.001);
	}
	
	@Test
	public void testSetRisk() {
		assertTrue(false);
	}
	
	@Test
	public void testUpdatePrice() {
		company.setCurrentShareValue(3);
		assertNotEquals(3, shares.getPrice());
		shares.updatePrice();
		assertEquals(3, shares.getPrice(), 0.001);
	}
	
	@Test
	public void testUpdateShareValue() {
		company.setCurrentShareValue(3);
		assertNotEquals(3, shares.getShareValue());
		shares.updatePrice();
		shares.updateShareValue();
		assertEquals(3, shares.getShareValue(), 0.001);
	}
	
	@Test
	public void testUpdateSize() {
		shares.updateSize(3);
		assertEquals(4, shares.getSize(), 0.001);
	}
}
