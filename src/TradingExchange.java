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
	private LinkedList<Trader> traders;
	private SmartTrader smartTrader;
	private double shareIndex;
	private Client currentClient;
	private LinkedList shareIndexList;
	private LinkedList<Events> events;
	private Random rand = new Random();
	
	
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
		tradeSim();
	}
	
	public LinkedList getXChart()
	{
		//placeholder
		LinkedList x = new LinkedList();
		x.add(1);
		x.add(2);
		x.add(3);
		x.add(4);
		x.add(5);
		x.add(6);
		x.add(7);
		x.add(8);
		x.add(9);
		x.add(10);
		x.add(11);
		x.add(12);
		return x;
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
		for(int i = 1; i<traders.size(); i++)
		{
			for(int j = 0; j<traders.get(i).getClients().size(); j++)
			{
				double sellAmountMax =  ((RandomTrader) traders.get(i)).getSellRate() * traders.get(i).getClients().get(j).getNetWorth();
				double buyAmountMax  =  ((RandomTrader) traders.get(i)).getBuyRate() * traders.get(i).getClients().get(j).getNetWorth();
				double sellAmount = 0;
				double buyAmount = 0;
				while(sellAmount <= sellAmountMax && buyAmount <= buyAmountMax)
				{
						double amount = ((RandomTrader)traders.get(i)).newOrder(traders.get(i).getClients().get(j), companies.get(rand.nextInt(companies.size())));
						if(amount < 0)
							sellAmount = sellAmount + Math.abs(amount);
						else
							buyAmount = buyAmount + amount;
				}
			}
		}
		
		for(int i = 1; i<traders.size(); i++)
		{
			for(int j = 0; j<traders.get(i).getOrderList().size(); j++)
			{
				for(int k = 0; k<companies.size(); k++)
				{
					if(traders.get(i).getOrderList().get(j).getCompanyName().equals(companies.get(k).getName()))
					{
						if(traders.get(i).getOrderList().get(j).getQuanitity() < 0)
							companies.get(k).setSellCount(traders.get(i).getOrderList().get(j).getQuanitity());
						else
							companies.get(k).setBuyCount(traders.get(i).getOrderList().get(j).getQuanitity());
					}
				}
				
			}
		}
		
		for(int i = 0; i<companies.size(); i++)
		{
			System.out.println(companies.get(i).getName() + " " + companies.get(i).getCurrentShareValue());
			companies.get(i).updateShareValue(companies.get(i).getBuyCount()+companies.get(i).getSellCount());
			System.out.println(companies.get(i).getName() + " " + companies.get(i).getCurrentShareValue());
		}
		
		for(int i = 1; i<traders.size(); i++)
		{
			for(int j = 0; j<traders.get(i).getOrderList().size(); j++)
			{
				((RandomTrader) traders.get(i)).completeOrder(traders.get(i).getOrderList().get(j));	
			}
		}
		
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
		int index = 1;
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
					//System.out.println(client.getName() + " " + client.getNetWorth());
					if(client.getName().equals("Norbert DaVinci") || client.getName().equals("Justine Thyme") )
						smartTrader.addClient(client);
					else
					{
						traders.get(index).addClient(client);
						index++;
						if(index > 4)
							index = 1;
					}
						
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
