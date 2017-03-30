/**
 * 
 */

/**
 * @author jon
 *
 */
public class RandomTrader extends Trader {
	
	private double buyRate, sellRate;
	
	public enum Mode
	{
		BALANCED, AGGRESSIVE_BUY, AGGRESSIVE_SELL
	}
	
	private Mode mode;
	
	private RandomTrader(Mode mode)
	{
		this.mode = mode;
		switch (mode)
		{
			case BALANCED:
					buyRate = 1;
					sellRate = 1;
					break;
			case AGGRESSIVE_BUY:
					buyRate = 2;
					sellRate = 0.5;
					break;
			case AGGRESSIVE_SELL:
					buyRate = 0.5;
					sellRate = 2;
					break;
			default: 
		}
	}
	
	public void setMode(Mode mode)
	{
		this.mode = mode;
	}
	
	public String getMode()
	{
		return String.valueOf(mode);
	}

}
