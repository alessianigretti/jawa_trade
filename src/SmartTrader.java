import java.util.LinkedList;
import java.util.Random;

/**
 * The Class SmartTrader is responsible for setting up a trader that acts
 * intelligently on the market.
 * 
 * @author Jonathan Magbadelo
 */
public class SmartTrader extends Trader {
	
	private Random rand = new Random();

	/**
	 * Instantiates a new smart trader.
	 */
	public SmartTrader() {
		setTraderName("W&G Trader");
		setBuyRate(0.02);
		setSellRate(0.02);
	}
	/**
	 * Sets a new order.
	 *
	 * @param client	the client
	 * @param company the company
	 * @param order type
	 * @return the value of the new order
	 */
	public double newOrder(Client client, Company company,boolean type) {
		System.out.println("start new smartOrder");
		boolean orderType = type;
		boolean alreadyOrdered = false;
		int quantity;
		switch (company.getCompanyTrend()) {
		case ("^"):
			orderType = false;
			break;
		case ("v"):
			orderType = true;
			break;
		default:
			orderType = rand.nextBoolean();
		}
		
		if(client.hasShare(company) == false)
			orderType = true;
		if(orderType == true)
			quantity = rand.nextInt(500);
		else
		{
			if (client.shareSize(company) <= 3)
			{
				quantity = (int) client.shareSize(company);
			}
			else
				quantity = rand.nextInt((int)client.shareSize(company)/3);
			quantity = -quantity;
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
}
