import java.util.*;
// TODO: Auto-generated Javadoc

/**
 * The Class Trader.
 */

/**
 * @author jon
 *
 */
public class Trader {
	
	private LinkedList<Client> clientList;
	private LinkedList<Order> orderList = new LinkedList();
	private LinkedList<Order> orderHistory = new LinkedList();
	private String name;
	
	
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
	 * New order.
	 */
	public void newOrder()
	{
		//tbc
	}
	
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
	 * Sets the risk all.
	 *
	 * @param risk the risk
	 * @param name the name
	 */
	public void setRiskAll(int risk, String name)
	{
		for(int i = 0; i<clientList.size(); i++)
		{
			if(clientList.get(i).getName().equals(name))
			{
				clientList.get(i).setRiskAll(risk);
			}
		}
	}
	
	/**
	 * Sets the risk.
	 *
	 * @param risk the risk
	 * @param firstname the firstname
	 */
	public void setRisk(int risk, String firstname)
	{
		
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
		LinkedList<Order> oH = orderHistory;
		for(int i = 0; i<oH.size(); i++)
		{
			if(!(oH.get(i).getClient().getName().equals(client.getName())))
			{
				oH.remove(i);
			}
		}
		return oH;
	}
}
