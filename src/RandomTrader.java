import java.util.LinkedList;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class RandomTrader.
 */

/**
 * @author jon
 *
 */
public class RandomTrader extends Trader {
	
	private double buyRate, sellRate;
	Random rand  = new Random();
	
	
	
	/**
	 * The Enum Mode.
	 */
	public enum Mode
	{
		BALANCED, AGGRESSIVE_BUY, AGGRESSIVE_SELL
	}
	
	private Mode[] ranMode = Mode.values();
	
	private Mode mode;
	
	/**
	 * Instantiates a new random trader.
	 *
	 * @param mode the mode
	 * @param i the i
	 */
	RandomTrader(Mode mode, int i)
	{
		setMode(Mode.BALANCED);
		setTraderName("RanTrader " + String.valueOf(i));
	}
	
	/**
	 * Gets the buy rate.
	 *
	 * @return the buy rate
	 */
	public double getBuyRate()
	{
		return buyRate;
	}
	
	/**
	 * Gets the sell rate.
	 *
	 * @return the sell rate
	 */
	public double getSellRate()
	{
		return sellRate;
	}
	
	/**
	 * Sets the mode.
	 *
	 * @param mode the new mode
	 */
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
	
	/**
	 * Gets the mode.
	 *
	 * @return the mode
	 */
	public Mode getMode()
	{
		return mode;
	}
	
	/**
	 * Switch mode.
	 *
	 * @param ranNum the ran num
	 */
	public void switchMode(int ranNum)
	{
		switch (getMode())
		{
			case BALANCED:
					if(ranNum < 0.1)
						setMode(Mode.AGGRESSIVE_SELL);
					if(ranNum > 0.1 && ranNum < 0.9)
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
	
	/**
	 * New order.
	 *
	 * @param client the client
	 * @param company the company
	 * @return the double
	 */
	public double newOrder(Client client, Company company)
	{
		int quantity = randomQuantity();
		//System.out.println(quantity);
		boolean orderType = true;
		for(Order o : getOrderList())
		{
			if(o.getCompanyName().equals(company.getName()))
				orderType = o.getOrderType();
			else
				orderType = rand.nextBoolean();
		}
		if(orderType == false)
			quantity = -quantity;
		Order order = new Order(company,quantity,orderType,quantity*company.getCurrentShareValue(),"RiskLev",client);
		getOrderList().add(order);
		return quantity*company.getCurrentShareValue();
	}
	
	/**
	 * Complete order.
	 *
	 * @param o the o
	 */
	public void completeOrder(Order o)
	{
		for(Client c: getClients())
		{
			if(o.getClientName().equals(c.getName()))
			{
				if(o.getOrderType() == true)
				{
					c.updateCash(-(o.getQuantity()*o.getCurrentShareValue()));
					c.newShare((o.getQuantity()/o.getCompany().getBuyCount())*Math.abs(o.getCompany().getSellCount()), o.getCompany());
					c.calculateNetWorth();
				}
				else
				{
					c.updateCash(-(o.getQuantity()*o.getCurrentShareValue()));
					c.newShare(-((o.getQuantity()/o.getCompany().getSellCount())*o.getCompany().getBuyCount()), o.getCompany());
					c.calculateNetWorth();
				}
					
			}
		}
	}
	
	/**
	 * Random quantity.
	 *
	 * @return the int
	 */
	public int randomQuantity()
	{
		LinkedList temp = new LinkedList();
		for(int i = 0; i<6; i++)
		{
			if(i == 0) 
				temp.add(50);
			else
				temp.add(100*i);
		}
		return (int) temp.get(rand.nextInt(6));
	}

}
