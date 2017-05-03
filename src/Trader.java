import java.util.*;
// TODO: Auto-generated Javadoc

/**
 * The Class Trader.
 */

/**
 * @author jon
 *
 */
public abstract class Trader {
	
	private LinkedList<Client> clientList;
	private LinkedList<Order> orderList = new LinkedList();
	private LinkedList<Order> orderHistory = new LinkedList();
	private String name;
	private double buyRate, sellRate;
	
	/**
	 * Instantiates a new trader.
	 */
	public Trader()
	{
		this.clientList = new LinkedList();
	}
	
	/**
	 * Sets the trader name.
	 *
	 * @param name the new trader name
	 */
	public void setTraderName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Gets the trader name.
	 *
	 * @return the trader name
	 */
	public String getTraderName()
	{
		if (name == null)
		{
			return "No trader selected.";
		} else {
			return name;
		}
	}
	
	/**
	 * Gets the order list.
	 *
	 * @return the order list
	 */
	public LinkedList<Order> getOrderList()
	{
		return orderList;
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
	
	public void setSellRate(double rate)
	{
		sellRate = rate;
	}
	
	public void setBuyRate(double rate)
	{
		buyRate = rate;
	}

	/**
	 * New order.
	 * @param company 
	 * @param client 
	 */
	public abstract double newOrder(Client client, Company company);
	
	
	/**
	 * Adds the client.
	 *
	 * @param client the client
	 */
	public void addClient(Client client)
	{
		clientList.add(client);
	}
	
	/**
	 * Gets the clients.
	 *
	 * @return the clients
	 */
	public LinkedList<Client> getClients()
	{
		return clientList;
	}
	
	
	/**
	 * Clear orders.
	 */
	public void clearOrders()
	{
		orderList.clear();
	}
	
	public void addOrderHistory()
	{
		orderHistory.clear();
		orderHistory.addAll(orderList);
	}
	
	public LinkedList<Order> getOrderHistory(Client client)
	{
		LinkedList<Order> orderHistoryList = orderHistory;
		for(int i = 0; i<orderHistoryList.size(); i++)
		{
			if(orderHistoryList.get(i).getClientName().equalsIgnoreCase(client.getName()))
				System.out.println("");

			if(orderHistoryList.get(i).getClientName().equalsIgnoreCase(client.getName()))
				System.out.println(orderHistoryList.get(i).getClientName());

			else
				orderHistoryList.remove(i);		
		}
		return orderHistoryList;
	}
}
