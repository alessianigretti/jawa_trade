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
		try{
		 CSVReader reader = new CSVReader(new FileReader("companies.csv"));
	     try {
			List<String[]> myEntries = reader.readAll();
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
		List<String[]> myEntries;
		try{
			 CSVReader reader = new CSVReader(new FileReader("events.csv"));
		     try {
				myEntries = reader.readAll();
				for(String[] array:myEntries)
				{
					Events event = new Events(array[1],array[0]);
					events.add(event);
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
