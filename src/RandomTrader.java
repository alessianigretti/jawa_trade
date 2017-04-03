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
		setMode(mode);
	}
	
	public void setMode(Mode mode)
	{
		this.mode = mode;
		switch (mode)
		{
			case BALANCED:
					buyRate = 0.01;
					sellRate = 0.01;
					break;
			case AGGRESSIVE_BUY:
					buyRate = 0.02;
					sellRate = 0.005;
					break;
			case AGGRESSIVE_SELL:
					buyRate = 0.005;
					sellRate = 0.02;
					break;
			default: 
		}
	}
	
	public Mode getMode()
	{
		return mode;
	}
	
	public void switchMode(int ranNum)
	{
		switch (getMode())
		{
			case BALANCED:
					if(ranNum < 0.1)
						setMode(Mode.AGGRESSIVE_SELL);
					if(ranNum > 0.1 && ranNum < 0.2)
						setMode(Mode.AGGRESSIVE_BUY);
					break;
			case AGGRESSIVE_BUY:
					if(ranNum < 0.7)
						setMode(Mode.BALANCED);
					break;
			case AGGRESSIVE_SELL:
					if(ranNum < 0.6)
						setMode(Mode.BALANCED);
					break;
			default: 
		}
	}

}
