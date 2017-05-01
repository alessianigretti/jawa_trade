import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
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
	private int numOfTraders;
	private LinkedList<Double> shareIndexList;
	private LinkedList<Events> events;
	private Random rand = new Random();
	private CSVReader currentClientsFile;
	private CSVReader currentCompaniesFile;
	private CSVReader currentEventsFile;
	private LocalDate currentDate;
	private LocalTime currentTime;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d yyyy");
	private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:m");
	private int nextEvent = 0;
	
	
	/**
	 * Instantiates a new trading exchange.
	 */
	public TradingExchange()
	{
		currentDate = LocalDate.parse("Feb 7 2017", dateFormatter);
		currentTime = LocalTime.parse("09:00", timeFormatter);
		companies = new LinkedList();
		traders = new LinkedList();
		smartTrader = new SmartTrader();
		smartTrader.setTraderName("W&G Trader");
		traders.add(smartTrader);
		shareIndexList = new LinkedList<Double>();
		events = new LinkedList();
		numOfTraders = 2;
		setUpSim();
		System.out.println(getShareIndex());
		checkShareNum();
		updateShareIndex();
		shareIndexList.add(getShareIndex());
		traders.add(new RandomTrader(3));
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
		for (int i = 1; i <= 2880; i++)
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
		shareIndexList.add(shareIndex);
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
		checkEvent();
	}
	
	/**
	 * Trade sim.
	 */
	
	public String marketStatus()
	{
		int end = shareIndexList.size()-1;
		if(shareIndexList.size()>=3)
		{
			if(shareIndexList.get(end) > shareIndexList.get(end-1) && shareIndexList.get(end-1) > shareIndexList.get(end-2))
				return "Bull";
			if(shareIndexList.get(end) < shareIndexList.get(end-1) && shareIndexList.get(end-1) < shareIndexList.get(end-2))
				return "Bear";
		}
		return "Undefined";
	}
	
	public void tradeSim()
	{
		if(!isMarketClosed())
		{
			System.out.println("zero " + companies.get(0).getBuyCount());
			System.out.println("zero " + companies.get(0).getSellCount());
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
			System.out.println("Before order buy " + companies.get(0).getBuyCount());
			System.out.println("Before order sell " + companies.get(0).getSellCount());
			
			for(int i = 0; i<companies.size(); i++)
			{
				companies.get(i).updateShareValue(companies.get(i).getBuyCount()+companies.get(i).getSellCount());
				companies.get(i).setFinalCount();
			}
			System.out.println("Before order finalbuy " + companies.get(0).getFinalBuyCount());
			System.out.println("Before order finalsell " + companies.get(0).getFinalSellCount());
			for(int i = 1; i<traders.size(); i++)
			{
				for(int j = 0; j<traders.get(i).getOrderList().size(); j++)
				{
					((RandomTrader) traders.get(i)).completeOrder(traders.get(i).getOrderList().get(j));
				}
				
				((RandomTrader) traders.get(i)).switchMode(Math.random());
				traders.get(i).addOrderHistory();
			}
			System.out.println("After order buy " + companies.get(0).getBuyCount());
			System.out.println("After order sell " + companies.get(0).getSellCount());
			
			for(int i = 0; i<companies.size(); i++)
			{
				if(companies.get(i).getBuyCount() > Math.abs(companies.get(i).getSellCount()))
				{
						companies.get(i).clearBuyCount();	
				}	
				else
				{
						companies.get(i).clearSellCount();
				}
				
				for(int a = 1; a<traders.size(); a++)
				{
					for(int b = 1; b<traders.get(a).getOrderList().size(); b++)
					{
						if(traders.get(a).getOrderList().get(b).isFullyCompleted() == false)
						{
							if(traders.get(a).getOrderList().get(b).getOrderType() == true && companies.get(i).getSellCount() != 0)
							{
								traders.get(a).getOrderList().get(b).getClient().newShare(1, companies.get(i) );
								companies.get(i).setSellCount(-1);
							}

							if(traders.get(a).getOrderList().get(b).getOrderType() == false && companies.get(i).getBuyCount() != 0)
							{
									traders.get(a).getOrderList().get(b).getClient().newShare(-1, companies.get(i) );
									companies.get(i).setBuyCount(-1);
							}
						}
					}
					traders.get(a).clearOrders();
				}
				companies.get(i).clearFinalCount();
				companies.get(i).clearBuyCount();
				companies.get(i).clearSellCount();
				if(isCompanyTradable(companies.get(i)) == false)
				{
					for(int j = 0; j<traders.size(); j++)
					{
						for(int k = 0; k<traders.get(j).getClients().size(); k++)
						{
							for(int x = 0; x<traders.get(j).getClients().get(k).getPortfolio().size(); x++)
							{
								if(traders.get(j).getClients().get(k).getPortfolio().get(x).getCompanyName().equals(companies.get(i).getName()))
									traders.get(j).getClients().get(k).getPortfolio().remove(traders.get(j).getClients().get(k).getPortfolio().get(x));
							}
							traders.get(j).getClients().get(k).calculateNetWorth();
						}
					}
					companies.remove(companies.get(i));
				}
			}
			checkShareNum();
			updateDateTime();
		}
		checkShareNum();
		updateDateTime();
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
		setUpRandomTraders(numOfTraders);
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
		currentCompaniesFile = reader;
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
	

	public void setUpClients(CSVReader reader)
	{
		currentClientsFile = reader;
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
					if(index > numOfTraders)
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
	public void setUpRandomTraders(int num)
	{
		numOfTraders = num;
		for(int i = 0; i<numOfTraders; i++)
		{
			RandomTrader randomTrader = new RandomTrader(i);
			traders.add(randomTrader);
		}
	}
	
	public int getNumOfTraders()
	{
		return numOfTraders;
	}
	
	/**
	 * Sets the up events.
	 */
	public void setUpEvents(CSVReader reader)
	{
		currentEventsFile = reader;
		String[] myEntries;
		     try {
				String[] next = reader.readNext();
				while(next != null)
				{
					myEntries = next;
					Events event = new Events(myEntries[1],myEntries[0],myEntries[2],myEntries[3],myEntries[4],myEntries[5]);
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
	
	public boolean isCompanyTradable(Company company)
	{
		if(company.getCurrentShareValue() < 0.01)
			return false;
		
		return true;
	}
	
	public boolean isMarketClosed()
	{
		if(currentDate.getDayOfWeek().equals(DayOfWeek.SATURDAY))
			return true;
		if(currentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY))
			return true;
		if(currentDate.toString().equals("2017-12-25"))
			return true;
		if(currentDate.toString().equals("2017-12-26"))
			return true;
		if(currentDate.toString().equals("2017-04-17"))
			return true;
		if(currentDate.toString().equals("2017-04-14"))
			return true;
		
		return false;
	}
	
	public void checkEvent()
	{
		if(getDate().equals(String.valueOf(events.get(nextEvent).getDate())) && getTime().equals(String.valueOf(events.get(nextEvent).getTime())))
		{
			for(int i = 0; i<companies.size(); i++)
			{
				companies.get(i).event(events.get(nextEvent).getEventType()[1]);
				companies.get(i).setOrderType(events.get(nextEvent).getEventType()[0]);
			}
			nextEvent++;
		}
			
	}

}
