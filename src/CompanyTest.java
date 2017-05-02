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
	public void testClearFinalCount()
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
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetBuyCount()
	{
		assertNotEquals(10, company.getBuyCount());
		company.setBuyCount(10);
		assertEquals(10, company.getBuyCount(), 0.01);
	}
	
	@Test
	public void testGetCompanyTrend()
	{
		company.setCurrentShareValue(10);
		company.setCurrentShareValue(12);
		company.setCurrentShareValue(13);
		company.setCompanyTrend();
		assertEquals("^", company.getCompanyTrend());
	}
	
	@Test
	public void testGetCurrentShareValue()
	{
		assertNotEquals(30, company.getCurrentShareValue());
		company.setCurrentShareValue(30);
		assertEquals(30, company.getCurrentShareValue(), 0.01);
	}
	
	@Test
	public void testGetEventEnd()
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
	public void testGetNetWorth()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetRisk()
	{
		assertEquals("Low", company.getRisk());
	}
	
	@Test
	public void testGetSellCount()
	{
		assertNotEquals(company.getSellCount(), 10);
		company.setSellCount(10);
		assertEquals(company.getSellCount(), 10, 0.01);
	}
	
	@Test
	public void testGetShareCount()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetShareType()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetShareValueList()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testIsEventTriggered()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testRandomBool()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testSetBuyCount()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testSetCompanyTrend()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testSetCurrentShareValue()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testSetEventEnd()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testSetFinalCount()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testSetName()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testSetNetWorth()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testSetOrderType()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testSetRisk()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testSetSellCount()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testTriggerEvent()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testUpdateShareCount()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testUpdateShareValue()
	{
		fail("Not yet implemented");
	}
}
