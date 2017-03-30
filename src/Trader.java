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
	
	
	public Trader()
	{
		this.clientList = new LinkedList();
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
	
	public void setRiskAll(int risk, String firstname)
	{
		for(int i = 0; i<clientList.size(); i++)
		{
			if(clientList.get(i).getFirstName().equals(firstname))
			{
				clientList.get(i).setRiskAll(risk);
			}
		}
	}
	
	public void setRisk(int risk, String firstname)
	{
		
	}
}
