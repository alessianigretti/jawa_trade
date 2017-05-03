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
	
	
	Random rand  = new Random();
	int orderNum = 0;
	
	
	
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
	RandomTrader(int i)
	{
		setMode(Mode.BALANCED);
		setTraderName("RanTrader " + String.valueOf(i));
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
					setBuyRate(0.01);
					setSellRate(0.01);
					break;
			case AGGRESSIVE_BUY:
					setBuyRate(0.02);
					setSellRate(0.005);
					break;
			case AGGRESSIVE_SELL:
					setBuyRate(0.005);
					setSellRate(0.02);
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
	public void switchMode(double ranNum)
	{
		switch (getMode())
		{
			case BALANCED:
					if(ranNum < 0.1)
						setMode(Mode.AGGRESSIVE_SELL);
					if(ranNum > 0.1 && ranNum < 0.2)
						setMode(Mode.AGGRESSIVE_BUY);
					//System.out.println("blaaaaaaaaaaaaaa " + ranNum);
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
		Order oldOrder = null;
		Order order = null;
		boolean alreadyOrdered = false;
		//System.out.println(quantity);
		boolean orderType = rand.nextBoolean();
		if(client.shareSize(company) != 0)
		{
			for(Order o : getOrderList())
			{
				if(o.getCompanyName().equals(company.getName()) && client.getName().equals(o.getClient().getName()))
				{
					orderType = o.getOrderType();
					alreadyOrdered = true;
				    oldOrder = o;
				}	
				else
						orderType = company.randomBool();
			}
		}
		else
			orderType = true;
		int quantity = randomQuantity();
		if(orderType == false)
		{
			if(client.shareSize(company) < 50 || client.shareSize(company) - quantity < 0)
			{
				if(client.getName().equals("Ellen Fotheringay-Smythe"))
					System.out.println(company.getName() + " "  + quantity + " " + client.shareSize(company));
				quantity = rand.nextInt((int) client.shareSize(company));
			}	
			quantity = -quantity;
		}
			
		if(client.getName().equals("Ellen Fotheringay-Smythe"))
			System.out.println(company.getName() + " "  + quantity + " "+ client.shareSize(company));
		if(alreadyOrdered)
			quantity = oldOrder.updateQuantity(quantity);
		else
		{
			order = new Order(company,quantity,orderType,quantity*company.getCurrentShareValue(),"RiskLev",client);
			if(orderType == true)
				getOrderList().addFirst(order);
			else
				getOrderList().addLast(order);
		}
		
		if(orderType == false)
			company.setSellCount(quantity);
		else
			company.setBuyCount(quantity);
		
		return quantity*company.getCurrentShareValue();
	}
	
	/**
	 * Complete order.
	 *
	 * @param o the o
	 */
	public void completeOrder(Order o)
	{	
		orderNum++;
		for(Client c: getClients())
		{
			if(o.getClientName().equals(c.getName()))
			{
				if(o.getOrderType() == true)
				{
					if(o.getCompany().getSellCount() != 0)
					{
						c.updateCash(-(o.getQuantity()*o.getCurrentShareValue()));
						if((o.getQuantity()/o.getCompany().getFinalBuyCount())*Math.abs(o.getCompany().getFinalSellCount()) >= o.getQuantity())
						{
							c.newShare(o.getQuantity(), o.getCompany());

							System.out.println(orderNum);
							//System.out.println("worked?" + o.getCompany().setBuyCount);
							//o.getCompany().setBuyCount(-(o.getQuantity()));

							o.getCompany().setSellCount(o.getQuantity());
							o.isFullyCompleted();
							c.calculateNetWorth();
							break;
						}
						else
						{
							c.newShare(Math.floor((o.getQuantity()/o.getCompany().getFinalBuyCount())*Math.abs(o.getCompany().getFinalSellCount())), o.getCompany());
							System.out.println(orderNum);
							if(o.getCompany().getName().equals("Pear Computing"))
							{
								//System.out.println("Buy ratio got " + ( Math.floor((o.getQuantity()/o.getCompany().getFinalBuyCount())*Math.abs(o.getCompany().getFinalSellCount()))));
								//System.out.println(o.getQuantity() + " final buy = " + o.getCompany().getFinalBuyCount());
							}
								
							o.getCompany().setSellCount(( Math.ceil((o.getQuantity()/o.getCompany().getFinalBuyCount())*Math.abs(o.getCompany().getFinalSellCount()))));
							c.calculateNetWorth();
							break;
						}
						
					}
				}
				else
				{
					if(o.getCompany().getBuyCount() != 0)
					{
						c.updateCash(-(o.getQuantity()*o.getCurrentShareValue()));
						if((o.getQuantity()/o.getCompany().getFinalSellCount())*o.getCompany().getFinalBuyCount() <= o.getQuantity())
						{
							c.newShare(o.getQuantity(), o.getCompany());

							System.out.println(orderNum);
							//o.getCompany().setSellCount(-(o.getQuantity()));

							o.getCompany().setBuyCount(-o.getQuantity());
							o.isFullyCompleted();
							c.calculateNetWorth();
							break;
						}
						else
						{
							if(o.getCompany().getFinalBuyCount() > Math.abs(o.getCompany().getFinalSellCount()))
							{
								c.newShare(o.getQuantity(), o.getCompany());
								System.out.println(orderNum);
								o.getCompany().setBuyCount(o.getQuantity());
							}
							else
							{
										System.out.println(orderNum);
										c.newShare(Math.ceil(-((o.getQuantity()/o.getCompany().getFinalSellCount())*o.getCompany().getFinalBuyCount())), o.getCompany());
										o.getCompany().setBuyCount(-(Math.floor(((o.getQuantity()/o.getCompany().getFinalSellCount())*o.getCompany().getFinalBuyCount()))));
							}
							c.calculateNetWorth();
							break;
						}
					}
				}
					System.out.println(orderNum + " ooooo" + o.getQuantity() + " " + o.getCompany().getFinalBuyCount() + " " + o.getCompany().getFinalSellCount());
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
