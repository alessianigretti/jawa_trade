import static org.junit.Assert.*;

import org.junit.Test;

public class CompanyTest {
	
	Company company = new Company("Name", "Food", 100, 15);

	@Test
	public void testClearBuyCount()
	{
		company.setBuyCount(10);
		assertNotEquals(0, company.getBuyCount());
		company.clearBuyCount();
		assertEquals(0, company.getBuyCount(), 0.01);
	}
	
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
	
	@Test
	public void testClearSellCount()
	{
		company.setSellCount(10);
		assertNotEquals(0, company.getSellCount());
		company.clearSellCount();
		assertEquals(0, company.getSellCount(), 0.01);
	}
	
	@Test
	public void testEndEvent()
	{
		company.triggerEvent();
		assertTrue(company.isEventTriggered());
		company.endEvent();
		assertFalse(company.isEventTriggered());
	}
	
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
	
	@Test
	public void testGetAndSetBuyCount()
	{
		assertNotEquals(10, company.getBuyCount());
		company.setBuyCount(10);
		assertEquals(10, company.getBuyCount(), 0.01);
	}
	
	@Test
	public void testGetAndSetCompanyTrend()
	{
		company.setCurrentShareValue(10);
		company.setCurrentShareValue(12);
		company.setCurrentShareValue(13);
		company.setCompanyTrend();
		assertEquals("^", company.getCompanyTrend());
	}
	
	@Test
	public void testGetAndSetCurrentShareValue()
	{
		assertNotEquals(30, company.getCurrentShareValue());
		company.setCurrentShareValue(30);
		assertEquals(30, company.getCurrentShareValue(), 0.01);
	}
	
	@Test
	public void testGetAndSetEventEnd()
	{
		company.setEventEnd("End");
		assertEquals("End", company.getEventEnd());
	}
	
	@Test
	public void testGetFinalBuyCount()
	{
		company.setBuyCount(10);
		company.setFinalCount();
		assertEquals(10, company.getFinalBuyCount(), 0.01);
	}
	
	@Test
	public void testGetFinalSellCount()
	{
		company.setSellCount(10);
		company.setFinalCount();
		assertEquals(10, company.getFinalSellCount(), 0.01);
	}
	
	@Test
	public void testGetName()
	{
		assertEquals("Name", company.getName());
	}
	
	@Test
	public void testGetAndSetNetWorth()
	{
		assertNotEquals(15, company.getNetWorth(), 0.01);
		company.setNetWorth();
		assertEquals(15, company.getNetWorth(), 0.01);
	}
	
	@Test
	public void testGetRisk()
	{
		assertEquals("Low", company.getRisk());
	}
	
	@Test
	public void testGetAndSetSellCount()
	{
		assertNotEquals(company.getSellCount(), 10);
		company.setSellCount(10);
		assertEquals(company.getSellCount(), 10, 0.01);
	}
	
	@Test
	public void testGetShareCount()
	{
		assertEquals(15, company.getShareCount(), 0.01);
	}
	
	@Test
	public void testGetShareType()
	{
		assertEquals("Food", company.getShareType());
	}
	
	@Test
	public void testGetShareValueList()
	{
		assertEquals(1.0, company.getShareValueList().get(0));
		company.setCurrentShareValue(10.0);
		assertEquals(10.0, company.getShareValueList().get(1));
	}

	@Test
	public void testIsEventTriggered()
	{
		assertFalse(company.isEventTriggered());
	}
	
	@Test
	public void testRandomBool()
	{
		assertFalse(company.isEventTriggered());
		company.triggerEvent();
		assertFalse(company.getOrderType());
		company.setOrderType("Buy");
		assertTrue(company.isEventTriggered());
		assertTrue(company.getOrderType());
		assertTrue(company.randomBool());
	}
	
	@Test
	public void testSetName()
	{
		assertNotEquals("CompanyName", company.getName());
		company.setName("CompanyName");
		assertEquals("CompanyName", company.getName());
	}
	
	@Test
	public void testSetOrderTypeAndTriggerEvent()
	{
		company.triggerEvent();
		assertFalse(company.randomBool());
		company.setOrderType("buy");
		assertTrue(company.randomBool());
	}
	
	@Test
	public void testSetRisk()
	{
		assertNotEquals("High", company.getRisk());
		company.setRisk(Company.Type.Property);
		assertEquals("High", company.getRisk());
	}
	
	@Test
	public void testUpdateShareCount()
	{
		assertNotEquals(20, company.getShareCount());
		company.updateShareCount(5);
		assertEquals(20, company.getShareCount());
	}
	
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
