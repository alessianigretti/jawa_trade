import java.util.*;

/**
 * 
 */

/**
 * @author jon
 *
 */

public class TradingExchange {
	
	private LinkedList<Company> exchange;
	//private LinkedList<Shares> upForSell; not needed as all shares are occupied by a client
	private LinkedList<Trader> traders;
	private SmartTrader smartTrader;
	private double shareIndex;
	
	public TradingExchange()
	{
		exchange = new LinkedList();
		//upForSell = new LinkedList();
		traders = new LinkedList();
		smartTrader = new SmartTrader();
		updateShareIndex();
	}
	
	public SmartTrader getSmartTrader()
	{
		return smartTrader;
	}
	//public void update
	public void updateShareIndex()
	{
		
	}
	
	
	
	
	
	

}
