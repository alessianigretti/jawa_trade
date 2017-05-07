import static org.junit.Assert.*;

import org.junit.Test;

public class SharesTest {

	public int size = 1;
	public Company company = new Company("JAWA", "Food", 1, 1);
	public Shares shares = new Shares(size, company);
	
	/**
	 * Test of the getCompanyName in the Shares class. This test validates that the company name referenced in a share is the same as the company name that the share is referring to.
	 */
	@Test
	public void testGetCompanyName() {
		assertEquals(company.getName(), shares.getCompanyName());
	}
	
	/**
	 * Test of the getPrice method in the Shares class. This test validates that the company share price referenced in the share is the same share price that represents the company
	 */
	@Test
	public void testGetPrice() {
		assertEquals(company.getCurrentShareValue(), shares.getPrice(), 0.001);
	}
	
	
	/**
	 * Test of the getShareValue method in the Shares class. This test validates that the share value is calculated correctly.
	 */
	@Test
	public void testGetShareValue() {
		assertEquals(shares.getPrice() * size, shares.getShareValue(), 0.001);
	}
	
	/**
	 * Test of the getSize method in the Shares class. This test validates the size of the share is retrievable.
	 */
	@Test
	public void testGetSize() {
		assertEquals(size, shares.getSize(), 0.001);
	}
	
	/**
	 * Test of the updatePrice method in the Shares class. This test validates that the share price updates as a result of a change of the company share value.
	 */
	@Test
	public void testUpdatePrice() {
		company.setCurrentShareValue(3);
		assertNotEquals(3, shares.getPrice());
		shares.updatePrice();
		assertEquals(3, shares.getPrice(), 0.001);
	}
	
	/**
	 * Test of the updateShareValue in the Shares class. This test validates that the share value referred from a company is always updated.
	 */
	@Test
	public void testUpdateShareValue() {
		company.setCurrentShareValue(3);
		assertNotEquals(3, shares.getShareValue());
		shares.updatePrice();
		shares.updateShareValue();
		assertEquals(3, shares.getShareValue(), 0.001);
	}
	
	/**
	 * Test of the updateSize method in the Shares class. This test validates that the share size can be updated and changed.
	 */
	@Test
	public void testUpdateSize() {
		shares.updateSize(3);
		assertEquals(4, shares.getSize(), 0.001);
	}
}
