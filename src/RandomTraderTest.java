import static org.junit.Assert.*;

import org.junit.Test;

public class RandomTraderTest {

	RandomTrader randomTrader = new RandomTrader(0);
	
	@Test
	public void testCompleteOrder() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBuyRate() {
		assertEquals(0.01, randomTrader.getBuyRate(), 0.01);
	}
	
	@Test
	public void testGetAndSetMode() {
		assertEquals(RandomTrader.Mode.BALANCED, randomTrader.getMode());
		randomTrader.setMode(RandomTrader.Mode.AGGRESSIVE_BUY);
		assertEquals(RandomTrader.Mode.AGGRESSIVE_BUY, randomTrader.getMode());
	}
	
	@Test
	public void testGetSellRate() {
		assertEquals(0.01, randomTrader.getSellRate(), 0.01);
	}
	
	@Test
	public void testNewOrder() {
		fail("Not yet implemented");
	}
}
