import java.util.LinkedList;
import java.util.Random;

/**
 * The Class RandomTrader is responsible for setting up a trader that acts
 * randomly on the market.
 * 
 * @author Jonathan Magbadelo
 */
public class RandomTrader extends Trader {

	Random rand = new Random();
	int orderNum = 0;

	/**
	 * The Enum Mode for the modes of trading.
	 */
	public enum Mode {
		BALANCED, AGGRESSIVE_BUY, AGGRESSIVE_SELL
	}

	private Mode[] ranMode = Mode.values();

	private Mode mode;

	/**
	 * Instantiates a new random trader.
	 *
	 * @param i            the number of the trader
	 */
	RandomTrader(int i) {
		setMode(Mode.BALANCED);
		setTraderName("RanTrader " + String.valueOf(i));
	}

	/**
	 * Sets the mode.
	 *
	 * @param mode
	 *            the new mode
	 */
	public void setMode(Mode mode) {
		this.mode = mode;
		switch (mode) {
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
	public String getMode() {
		return mode.toString();
	}

	/**
	 * Switch mode.
	 *
	 * @param ranNum
	 *            the random number
	 */
	public void switchMode(double ranNum) {
		switch (getMode()) {
		case "BALANCED":
			if (ranNum < 0.1)
				setMode(Mode.AGGRESSIVE_SELL);
			if (ranNum > 0.1 && ranNum < 0.2)
				setMode(Mode.AGGRESSIVE_BUY);
			break;
		case "AGGRESSIVE_BUY":
			if (ranNum < 0.7)
				setMode(Mode.BALANCED);
			break;
		case "AGGRESSIVE_SELL":
			if (ranNum < 0.6)
				setMode(Mode.BALANCED);
			break;
		default:
		}
	}

	/**
	 * Sets a new order.
	 *
	 * @param client
	 *            the client
	 * @param company
	 *            the company
	 * @return the double
	 */
	public double newOrder(Client client, Company company, boolean type)
	{
		boolean orderType = type;
		boolean alreadyOrdered = false;
		int quantity;
		
		if(company.isEventTriggered())
			orderType = company.randomBool();
		
		if(orderType == true)
		{
			quantity = randomQuantity();
		}
		else
		{
			if(client.shareSize(company) <= 4)
			{
				quantity = (int) client.shareSize(company);;
			}
			else	
				quantity = rand.nextInt((int)client.shareSize(company)/4);
			
			quantity = quantity*-1;
			
		}
		for(Order o: getOrderList())
		{
			if(o.getCompany().getName().equals(company.getName()) && o.getClient().getName().equals(client.getName()))
			{
				if(o.getOrderType() == orderType)
				{
					if(orderType == true)
					{
						quantity = rand.nextInt((int)((client.getBuyMax()-client.getBuyAmount())/company.getCurrentShareValue()));
					}
					else
					{
						int moneyR = rand.nextInt((int)((client.getSellMax()-client.getSellAmount())/company.getCurrentShareValue()));
						int shareR = (int)client.shareSize(company)-Math.abs(o.getQuantity());
						quantity = Math.min(moneyR, shareR);
						quantity = quantity*-1;
					}
				}
				else
					quantity = 0;
				o.updateQuantity(quantity);
				alreadyOrdered = true;
				break;
			}
		}
		
		if(alreadyOrdered == false)
		{
			Order order = new Order(company,quantity,orderType,quantity*company.getCurrentShareValue(),company.getRisk(),client);
			getOrderList().add(order);
		}
		
		if (orderType == false)
		{
			company.setSellCount(quantity);
			client.setSellAmount(client.getSellAmount()+Math.abs((quantity*company.getCurrentShareValue())));
		}
		else
		{
			company.setBuyCount(quantity);	
			client.setBuyAmount(client.getBuyAmount()+(quantity*company.getCurrentShareValue()));
		}	
		return quantity*company.getCurrentShareValue();
	}

	
	public int randomQuantity() 
	{
		int[] temp = new int[5];
		for (int i = 0; i < 5; i++) {
			if (i == 0)
				temp[i] = 100;
			else
				temp[i] = (100 * i);
		}
		return  temp[rand.nextInt(5)];
	}

}
