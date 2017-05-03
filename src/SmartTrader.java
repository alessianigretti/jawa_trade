import java.util.LinkedList;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class SmartTrader.
 */

/**
 * @author jon
 *
 */
public class SmartTrader extends Trader {
	
	private LinkedList<Company> companyList;
	private Random rand = new Random();
	
	public SmartTrader()
	{
		setTraderName("W&G Trader");
		setBuyRate(0.01);
		setBuyRate(0.01);
	}
	
	public void setCompanyList(LinkedList<Company> companies)
	{
		companyList = companies;
	}
	
	public double newOrder(Client client, Company company)
	{	
		int quantity = rand.nextInt(300);
		while(quantity*company.getCurrentShareValue()>client.getCashHolding())
		{
			quantity = quantity - 20;
		}
		
		boolean orderType;
		switch(company.getCompanyTrend())
		{
			case("^"): 
				orderType = false;
				break;
			case("v"):
				orderType = true;
				break;
			default:
				orderType = company.randomBool();
		}
		
		if(client.shareSize(company) == 0)
			orderType = true;
		if(orderType == false)
			quantity = -quantity;
		
		Order order = new Order(company,quantity,orderType,quantity*company.getCurrentShareValue(),company.getRisk(),client);
		getOrderList().add(order);
		
		return quantity*company.getCurrentShareValue();
	}
	
	public void completeOrder(Order order)
	{
		int orderAmount = 0;
		if(order.getOrderType() == true)
		{
			orderAmount = (int) ((order.getQuantity()/order.getCompany().getFinalBuyCount()) * order.getCompany().getFinalSellCount());
			order.getClient().newShare(orderAmount, order.getCompany());
		}
		else
		{
			orderAmount = (int) ((order.getQuantity()/order.getCompany().getFinalSellCount()) * order.getCompany().getFinalBuyCount());
			order.getClient().newShare(orderAmount, order.getCompany());
		}
			
			
	}
	
	

}
