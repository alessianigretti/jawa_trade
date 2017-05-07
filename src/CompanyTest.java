import static org.junit.Assert.*;

import org.junit.Test;

public class CompanyTest {
	
	Company company = new Company("Name", "Food", 100, 15);
	
	/**
	 * Test the clearBuyCount method in the Company class. This test validates that a companies buy count can be cleared.
	 */
	@Test
	public void testClearBuyCount()
	{
		company.setBuyCount(10);
		assertNotEquals(0, company.getBuyCount());
		company.clearBuyCount();
		assertEquals(0, company.getBuyCount(), 0.01);
	}
	
	/**
	 * Test the clearFinalCount and setFinalCount method in the Company class. This test validates that the final buy/sell count can be set and cleared.
	 */
	@Test
	public void testClearAndSetFinalCount()
	{
		company.setBuyCount(10);
		company.setSellCount(20);
		company.setFinalCount();
		assertNotEquals(0, company.getFinalBuyCount());
		assertNotEquals(0, company.getFinalSellCount());
		company.clearFinalCount();
		assertEquals(0, company.getFinalBuyCount(), 0.01);
		assertEquals(0, company.getFinalSellCount(), 0.01);
	}
	
	/**
	 * Test the clearSellCount method in the Company class. This test validates that the sell count can be cleared.
	 */
	@Test
	public void testClearSellCount()
	{
		company.setSellCount(10);
		assertNotEquals(0, company.getSellCount());
		company.clearSellCount();
		assertEquals(0, company.getSellCount(), 0.01);
	}
	
	/**
	 * Test the endEvent method in the Company class. This test validates that an event ending affects its effects on a company.
	 */
	@Test
	public void testEndEvent()
	{
		company.triggerEvent();
		assertTrue(company.isEventTriggered());
		company.endEvent();
		assertFalse(company.isEventTriggered());
	}
	
	/**
	 * Test the event method in the Company class. This test validates that a company can be affected by an event starting.
	 */
	@Test
	public void testEvent()
	{
		assertFalse(company.isEventTriggered());
		company.event("Name");
		assertTrue(company.isEventTriggered());
		company.endEvent();
		assertFalse(company.isEventTriggered());
		company.event("Food");
		assertTrue(company.isEventTriggered());
		company.endEvent();
		assertFalse(company.isEventTriggered());
		company.event("Uk");
		assertTrue(company.isEventTriggered());
		company.endEvent();
	}
	
	/**
	 * Test the getBuyCount and setBuyCount methods in the Company class. This test validates that the buy count can be set and retrieved.
	 */
	@Test
	public void testGetAndSetBuyCount()
	{
		assertNotEquals(10, company.getBuyCount());
		company.setBuyCount(10);
		assertEquals(10, company.getBuyCount(), 0.01);
	}
	
	/**
	 * Test the getCompanyTrend and setCompanyTrend methods in the Company class. This test validates that the company trend can be set and retrieved.
	 */
	@Test
	public void testGetAndSetCompanyTrend()
	{
		company.setCurrentShareValue(10);
		company.setCurrentShareValue(12);
		company.setCurrentShareValue(13);
		company.setCompanyTrend();
		assertEquals("^", company.getCompanyTrend());
	}
	
	/**
	 * Test the getCurrentShareValue and setCurrentShareValue methods in the Company class. This test validates that the current share value can be set and retrieved.
	 */
	@Test
	public void testGetAndSetCurrentShareValue()
	{
		assertNotEquals(30, company.getCurrentShareValue());
		company.setCurrentShareValue(30);
		assertEquals(30, company.getCurrentShareValue(), 0.01);
	}
	
	/**
	 * Test the getEventEnd and setEventEnd methods in the Company class. This test validates that an events end can be set and retrieved.
	 */
	@Test
	public void testGetAndSetEventEnd()
	{
		company.setEventEnd("End");
		assertEquals("End", company.getEventEnd());
	}
	
	/**
	 * Test the getFinalBuyCount method in the Company class. This test validates that the final buy count for a company can be retrieved.
	 */
	@Test
	public void testGetFinalBuyCount()
	{
		company.setBuyCount(10);
		company.setFinalCount();
		assertEquals(10, company.getFinalBuyCount(), 0.01);
	}
	
	/**
	 * Test the getFinalSellCount method in the Company class. This test validates that a companies final sell count can be retrieved.
	 */
	@Test
	public void testGetFinalSellCount()
	{
		company.setSellCount(10);
		company.setFinalCount();
		assertEquals(10, company.getFinalSellCount(), 0.01);
	}
	
	/**
	 * Test the getName method in the Company class. This test validates that a companies name can be retrieved.
	 */
	@Test
	public void testGetName()
	{
		assertEquals("Name", company.getName());
	}
	
	/**
	 * Test the getNetWorth and setNetWorth methods in the Company class. This test validates that a companies networth can be set and retrieved.
	 */
	@Test
	public void testGetAndSetNetWorth()
	{
		assertNotEquals(15, company.getNetWorth(), 0.01);
		company.setNetWorth();
		assertEquals(15, company.getNetWorth(), 0.01);
	}
	
	/**
	 * Test the getRisk method in the Company class. This test validates that the risk associated with a company can be retrieved.
	 */
	@Test
	public void testGetRisk()
	{
		assertEquals("Low", company.getRisk());
	}
	
	/**
	 * Test the getSellCount and setSellCount method in the Company class. This test validates that the sell count can be set and retrieved.
	 */
	@Test
	public void testGetAndSetSellCount()
	{
		assertNotEquals(company.getSellCount(), 10);
		company.setSellCount(10);
		assertEquals(company.getSellCount(), 10, 0.01);
	}
	
	/**
	 * Test the getShareCount method in the Company class. This test validates that the share count for a company can be retrieved.
	 */
	@Test
	public void testGetShareCount()
	{
		assertEquals(15, company.getShareCount(), 0.01);
	}
	
	/**
	 * Test the getShareType method in the Company class. This test validates that the share type of a company can be retrieved.
	 */
	@Test
	public void testGetShareType()
	{
		assertEquals("Food", company.getShareType());
	}
	
	/**
	 * Test the getShareValueList method in the Company class. This test validates that the share value list for a company can be retrieved.
	 */
	@Test
	public void testGetShareValueList()
	{
		assertEquals(1.0, company.getShareValueList().get(0));
		company.setCurrentShareValue(10.0);
		assertEquals(10.0, company.getShareValueList().get(1));
	}

	/**
	 * Test the isEventTriggered method in the Company class. This test validates that an event can be triggered which affects whether a company is brought/sold.
	 */
	@Test
	public void testIsEventTriggered()
	{
		assertFalse(company.isEventTriggered());
		company.triggerEvent();
		assertTrue(company.isEventTriggered());
	}
	
	/**
	 * Test the getOrderType method in the Company class. This test validates that the order type associated with a event that affects a company can be retrieved.
	 */
	@Test
	public void testGetOrderType()
	{
		assertFalse(company.isEventTriggered());
		company.triggerEvent();
		assertFalse(company.getOrderType());
		company.setOrderType("buy");
		assertTrue(company.isEventTriggered());
		assertTrue(company.getOrderType());
	}
	
	/**
	 * Test the setName method in the Company class. This test validates that the company name can be changed.
	 */
	@Test
	public void testSetName()
	{
		assertNotEquals("CompanyName", company.getName());
		company.setName("CompanyName");
		assertEquals("CompanyName", company.getName());
	}
	
	/**
	 * Test the setOrderType and triggerEvent method in the Company class. This test validates that the triggering of an event affects how traders buy and sell.
	 */
	@Test
	public void testSetOrderTypeAndTriggerEvent()
	{
		company.triggerEvent();
		assertFalse(company.getOrderType());
		company.setOrderType("buy");
		assertTrue(company.getOrderType());
	}
	
	/**
	 * Test the setRisk method in the Company class. This test validates that the risk associated with a company can be changed.
	 */
	@Test
	public void testSetRisk()
	{
		assertNotEquals("High", company.getRisk());
		company.setRisk(Company.Type.Property);
		assertEquals("High", company.getRisk());
	}
	
	/**
	 * Test the updateShareCount method in the Company class. This test validates that the share count of a company can be updated.
	 */
	@Test
	public void testUpdateShareCount()
	{
		assertNotEquals(20, company.getShareCount());
		company.updateShareCount(5);
		assertEquals(20, company.getShareCount());
	}
	
	/**
	 * Test the updateShareValue method in the Company class. This test validates that a companies share value increases due to supply vs demand.
	 */
	@Test
	public void testUpdateShareValue()
	{
		assertEquals(1, company.getCurrentShareValue(), 0.01);
		assertEquals(15, company.getShareCount());
		double excess = 30;
		company.updateShareValue(excess);
		assertEquals(3, company.getCurrentShareValue(),0.01);
	}
}
