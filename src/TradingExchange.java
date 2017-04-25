import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.opencsv.CSVReader;

// TODO: Auto-generated Javadoc
/**
 * The Class TradingExchange.
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
	private LocalDate currentDate;
	private LocalTime currentTime;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d yyyy");
	private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:m");
	
	
	/**
	 * Instantiates a new trading exchange.
	 */
	public TradingExchange()
	{
		currentDate = LocalDate.parse("Jan 1 2017", dateFormatter);
		currentTime = LocalTime.parse("09:00", timeFormatter);
		companies = new LinkedList();
		traders = new LinkedList();
		smartTrader = new SmartTrader();
		smartTrader.setTraderName("W&G Trader");
		traders.add(smartTrader);
		shareIndexList = new LinkedList();
		events = new LinkedList();
		setUpRandomTraders(4);
		System.out.println(getShareIndex());
		checkShareNum();
		setUpSim();
		updateShareIndex();
	}
	
	/**
	 * Gets the x chart.
	 *
	 * @return the x chart
	 */
	public LinkedList getXChart()
	{
		//placeholder
		LinkedList x = new LinkedList();
		for (int i = 1; i <= 192; i++)
		{
			x.add(i);
		}
		return x;
	}
	
	/**
	 * Gets the smart trader.
	 *
	 * @return the smart trader
	 */
	public SmartTrader getSmartTrader()
	{
		return smartTrader;
	}
	
	/**
	 * Update share index.
	 */
	public void updateShareIndex()
	{
		shareIndex = 0;
		for(int i = 0; i<companies.size(); i++)
		{
			shareIndex =  shareIndex + companies.get(i).getCurrentShareValue();
		}
		shareIndex = shareIndex/companies.size();	
	}
	
	/**
	 * Gets the share index.
	 *
	 * @return the share index
	 */
	public double getShareIndex()
	{
		return shareIndex;
	}
	
	/**
	 * Sets the current client.
	 *
	 * @param client the new current client
	 */
	public void setCurrentClient(Client client)
	{
		currentClient = client;
	}
	
	/**
	 * Gets the current client.
	 *
	 * @return the current client
	 */
	public Client getCurrentClient()
	{
		return currentClient;
	}
	
	/**
	 * Gets the events.
	 *
	 * @return the events
	 */
	public LinkedList<Events> getEvents()
	{
		return events;
	}
	
	/**
	 * Gets the companies.
	 *
	 * @return the companies
	 */
	public LinkedList<Company> getCompanies()
	{
		return companies;
	}
	
	/**
	 * Gets the traders.
	 *
	 * @return the traders
	 */
	public LinkedList<Trader> getTraders()
	{
		return traders;
	}
	
	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public String getTime()
	{
		return String.valueOf(currentTime);
	}
	
	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate()
	{
		return String.valueOf(currentDate);
	}
	
	/**
	 * Update date time.
	 */
	public void updateDateTime()
	{
		currentTime = currentTime.plusMinutes(15);
		if(String.valueOf(currentTime).equals("00:00"))
			currentDate = currentDate.plusDays(1);
	}
	
	/**
	 * Trade sim.
	 */
	public void tradeSim()
	{
		long startTime = System.nanoTime();
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
			companies.get(i).updateShareValue(companies.get(i).getBuyCount()+companies.get(i).getSellCount());
		}
		
		for(int i = 1; i<traders.size(); i++)
		{
			for(int j = 0; j<traders.get(i).getOrderList().size(); j++)
			{
				((RandomTrader) traders.get(i)).completeOrder(traders.get(i).getOrderList().get(j));
			}
			traders.get(i).clearOrders();
		}
		
		for(int i = 0; i<companies.size(); i++)
		{
			companies.get(i).clearCount();
		}
		
		updateDateTime();
		//System.out.println(getDate() + getTime());
		long endTime = System.nanoTime();

		long duration = ((endTime - startTime));
		//System.out.println(duration);
		
	}
	
	/**
	 * Sets the up sim.
	 */
	public void setUpSim()
	{
		try {
			setUpCompanies(new CSVReader(new FileReader("companies.csv")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			setUpEvents(new CSVReader(new FileReader("events.csv")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//setUpRandomTraders(randomTradersNum);
		try {
			setUpClients(new CSVReader(new FileReader("clients.csv")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the up companies.
	 */
	public void setUpCompanies(CSVReader reader)
	{
		String[] myEntries;
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
	
	/**
	 * Sets the up clients.
	 */
	private void setUpClients2()
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
	
	public void setUpClients(CSVReader reader)
	{
		int index = 1;
		String[] myEntries;
	     try {
			String[] next = reader.readNext();
			while(next != null)
			{
				myEntries = next;
				Client client = new Client(myEntries[0],Double.valueOf(myEntries[1]));
				for(int i = 2; i<=next.length-1; i++ )
				{
					client.initialShare(Integer.valueOf(myEntries[i]), companies.get(i-2));
				}
				client.calculateNetWorth();
				if(client.getName().equals("Norbert DaVinci") || client.getName().equals("Justine Thyme") )
					smartTrader.addClient(client);
				else
				{
					
					traders.get(index).addClient(client);
					index++;
					if(index > 4)
						index = 1;
					
				}
				next = reader.readNext();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the up random traders.
	 */
	private void setUpRandomTraders(int num)
	{
		for(int i = 0; i<num; i++)
		{
			RandomTrader randomTrader = new RandomTrader(RandomTrader.Mode.BALANCED, i);
			traders.add(randomTrader);
		}
	}
	
	/**
	 * Sets the up events.
	 */
	public void setUpEvents(CSVReader reader)
	{
		String[] myEntries;
		     try {
				String[] next = reader.readNext();
				while(next != null)
				{
					myEntries = next;
					Events event = new Events(myEntries[1],myEntries[0],myEntries[2]);
					events.add(event);
					next = reader.readNext();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Check share num.
	 */
	public void checkShareNum()
	{
		double count = 0;
		for(int i = 0; i<traders.size(); i++)
		{
			for(int j = 0; j<traders.get(i).getClients().size(); j++)
			{
				for(int k = 0; k<traders.get(i).getClients().get(j).getPortfolio().size(); k++)
				{
					if(companies.get(0).getName().equals(traders.get(i).getClients().get(j).getPortfolio().get(k).getCompanyName()))
						count = count + traders.get(i).getClients().get(j).getPortfolio().get(k).getSize();
				}
			}
		}
		System.out.println(count);
	}
	
	
	
	

}
