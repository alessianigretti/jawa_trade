import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.opencsv.CSVReader;

/**
 * 
 */

/**
 * @author jon
 *
 */

public class TradingExchange {
	
	private LinkedList<Company> companies;
	//private LinkedList<Shares> upForSell; not needed as all shares are occupied by a client
	private LinkedList<Trader> traders;
	private SmartTrader smartTrader;
	private double shareIndex;
	private Client currentClient;
	public LinkedList<Events> events;
	
	public TradingExchange()
	{
		companies = new LinkedList();
		//upForSell = new LinkedList();
		traders = new LinkedList();
		smartTrader = new SmartTrader();
		events = new LinkedList();
		setUpSim();
		updateShareIndex();
	}
	
	public SmartTrader getSmartTrader()
	{
		return smartTrader;
	}
	
	public void updateShareIndex()
	{
		shareIndex = 0;
		for(int i = 0; i<companies.size(); i++)
		{
			shareIndex =  shareIndex + companies.get(i).getCurrentShareValue();
		}
		shareIndex = shareIndex/companies.size();	
	}
	
	public void setCurrentClient(Client client)
	{
		currentClient = client;
	}
	
	public Client getCurrentClient()
	{
		return currentClient;
	}
	
	public LinkedList<Events> getEvents()
	{
		return events;
	}
	
	public LinkedList<Company> getCompanies()
	{
		return companies;
	}
	
	public void tradeSim()
	{
		
	}
	
	public void setUpSim()
	{
		setUpCompanies();
		setUpEvents();
	}
	
	private void setUpCompanies()
	{
		String[] myEntries;
		try{
			 CSVReader reader = new CSVReader(new FileReader("companies.csv"));
		     try {
				String[] next = reader.readNext();
				while(next != null)
				{
					myEntries = next;
					Company company = new Company(myEntries[0],myEntries[1],Double.valueOf(myEntries[2]),Integer.valueOf(myEntries[3]));
					companies.add(company);
					next = reader.readNext();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	private void setUpClients()
	{
		
	}
	
	private void setUpRandomTraders()
	{
		
	}
	
	public void setUpEvents()
	{
		String[] myEntries;
		try{
			 CSVReader reader = new CSVReader(new FileReader("events.csv"));
		     try {
				String[] next = reader.readNext();
				while(next != null)
				{
					myEntries = next;
					Events event = new Events(myEntries[1],myEntries[0]);
					events.add(event);
					next = reader.readNext();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	
	
	

}
