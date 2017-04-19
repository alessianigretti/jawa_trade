import java.util.LinkedList;
import java.util.Random;

/**
 * 
 */

/**
 * @author jon
 *
 */
public class RandomTrader extends Trader {
	
	private double buyRate, sellRate;
	Random rand  = new Random();
	
	
	
	public enum Mode
	{
		BALANCED, AGGRESSIVE_BUY, AGGRESSIVE_SELL
	}
	
	private Mode[] ranMode = Mode.values();
	
	private Mode mode;
	
	RandomTrader(Mode mode, int i)
	{
		setMode(Mode.BALANCED);
		setTraderName("RanTrader " + String.valueOf(i));
	}
	
	public double getBuyRate()
	{
		return buyRate;
	}
	
	public double getSellRate()
	{
		return sellRate;
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
		Order order = new Order(quantity,company,client.getName(),orderType);
		getOrderList().add(order);
		return quantity*company.getCurrentShareValue();
	}
	
	public void completeOrder(Order o)
	{
		for(Client c: getClients())
		{
			if(o.getClientName().equals(c.getName()))
			{
				if(o.getOrderType() == true)
				{
					System.out.println(c.getName());
					System.out.println(o.getCompanyName());
					System.out.println(o.getCompany().getBuyCount() + " BUYCOUNT");
					System.out.println(o.getQuanitity() + " Quantity");
					System.out.println(o.getCompany().getSellCount() + " SELLCOUNT");
					c.newShare((o.getQuanitity()/o.getCompany().getBuyCount())*Math.abs(o.getCompany().getSellCount()), o.getCompany());
				}
				else
				{
					System.out.println(c.getName());
					System.out.println(o.getCompanyName());
					System.out.println(o.getCompany().getBuyCount() + " SELLCOUNT");
					System.out.println(o.getQuanitity() + " Quantity");
					System.out.println(o.getCompany().getSellCount() + " BUYCOUNT");
					c.newShare(-((o.getQuanitity()/o.getCompany().getSellCount())*o.getCompany().getBuyCount()), o.getCompany());
				}
					
			}
		}
	}
	
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
