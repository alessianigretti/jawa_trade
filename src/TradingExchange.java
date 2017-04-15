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
	private LinkedList shareIndexList;
	private LinkedList<Events> events;
	
	
	public TradingExchange()
	{
		companies = new LinkedList();
		//upForSell = new LinkedList();
		traders = new LinkedList();
		smartTrader = new SmartTrader();
		smartTrader.setTraderName("W&G Trader");
		traders.add(smartTrader);
		shareIndexList = new LinkedList();
		events = new LinkedList();
		setUpSim();
		updateShareIndex();
		System.out.println(getShareIndex());
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
	
	public double getShareIndex()
	{
		return shareIndex;
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
	
	public LinkedList<Trader> getTraders()
	{
		return traders;
	}
	
	public void tradeSim()
	{
		
	}
	
	public void setUpSim()
	{
		setUpCompanies();
		setUpEvents();
		setUpRandomTraders();
		setUpClients();
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
		String[] myEntries;
		int i = 0;
		try{
			 CSVReader reader = new CSVReader(new FileReader("ClientNames.csv"));
		     try {
				String[] next = reader.readNext();
				while(next != null)
				{
					myEntries = next;
					Client client = new Client(myEntries[0],Double.valueOf(myEntries[1]));
					String[] myEntries2;
					try{
						 CSVReader reader2 = new CSVReader(new FileReader("ClientShares.csv"));
					     try {
							String[] next2 = reader2.readNext();
							int j = 0;
							while(next2 != null)
							{
								myEntries2 = next2;
								client.initialShare(Integer.valueOf(myEntries2[i]), getCompanies().get(j));
								j++;
								next2 = reader2.readNext();
							}
							
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					catch (FileNotFoundException e){
						e.printStackTrace();
					}
					client.calculateNetWorth();
					System.out.println(client.getName() + " " + client.getNetWorth());
					if(client.getName().equals("Norbert DaVinci") || client.getName().equals("Justine Thyme") )
						smartTrader.addClient(client);
					else
						traders.get(1).addClient(client);
					i++;
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
	
	private void setUpRandomTraders()
	{
		for(int i = 0; i<4; i++)
		{
			RandomTrader randomTrader = new RandomTrader(RandomTrader.Mode.BALANCED, i);
			traders.add(randomTrader);
		}
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
