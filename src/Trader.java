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
		return name;
	}

	public void buy(Client client, int quantity, Company company)
	{
		client.newOrder(quantity, company);
	}
	
	public void sell(Client client, int quantity, Company company)
	{
		client.newOrder(-quantity, company);
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
