import java.util.*;
/**
 * 
 */

/**
 * @author jon
 *
 */
public class Trader {
	
	private LinkedList<Client> clientList;
	private LinkedList<Order> orderList = new LinkedList();
	private String name;
	
	
	public Trader()
	{
		this.clientList = new LinkedList();
	}
	
	public void setTraderName(String name)
	{
		this.name = name;
	}
	
	public String getTraderName()
	{
		if (name == null)
		{
			return "No trader selected.";
		} else {
			return name;
		}
	}
	
	public LinkedList<Order> getOrderList()
	{
		return orderList;
	}

	/*public void buy(Client client, int quantity, Company company)
	{
		Order order = new Order(quantity,company,client.getName());
		orderList.add(order);
	}
	
	public void sell(Client client, int quantity, Company company)
	{
		Order order = new Order(quantity,company,client.getName());
		orderList.add(order);
		//client.newOrder(-quantity, company);
	}
	*/
	public double newOrder(Client client, int quantity, Company company, boolean orderType)
	{
		Order order = new Order(quantity,company,client.getName(),orderType);
		orderList.add(order);
		return Math.abs(quantity*company.getCurrentShareValue());
	}
	
	public void addClient(Client client)
	{
		clientList.add(client);
	}
	
	public LinkedList<Client> getClients()
	{
		return clientList;
	}
	
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
	
	public void setRisk(int risk, String firstname)
	{
		
	}
}
