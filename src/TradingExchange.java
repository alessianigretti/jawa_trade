import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.opencsv.CSVReader;

/**
 * The Class TradingExchange is responsible for buying and selling in the stock
 * market.
 * 
 * @author Jonathan Magbadelo
 */
public class TradingExchange {

	private LinkedList<Company> companies; //List of companies on the stock exchange
	private LinkedList<Trader> traders; // list of traders trading on the stock exchange
	private SmartTrader smartTrader; //smart trader who trades on behalf of wolf and geko
	private double shareIndex; //avarage share price of all the companies on the exchange
	private Client currentClient; //current client 
	private int numOfTraders; //number of random traders
	private LinkedList<Double> shareIndexList; //list of share index to check if market becomes bull or bear
	private LinkedList<Events> events; //list of events
	private Random rand = new Random();
	private CSVReader currentClientsFile;
	private CSVReader currentCompaniesFile;
	private CSVReader currentEventsFile;
	private LocalDate currentDate; //current date of simulation
	private LocalTime currentTime; //current time of simulation
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d yyyy");
	private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:m");
	private int nextEvent = 0; //index of next scheduled event

	/**
	 * Instantiates a new trading exchange.
	 */
	public TradingExchange() {
		currentDate = LocalDate.parse("Jan 1 2017", dateFormatter);
		currentTime = LocalTime.parse("09:00", timeFormatter);
		companies = new LinkedList();
		traders = new LinkedList();
		smartTrader = new SmartTrader();
		smartTrader.setTraderName("W&G Trader");
		traders.add(smartTrader);
		shareIndexList = new LinkedList<Double>();
		events = new LinkedList();
		numOfTraders = 4;
		setUpSim();
		//checkShareNum();
		updateShareIndex();
		shareIndexList.add(getShareIndex());
	}

	/**
	 * Generates and returns the x axis for the chart.
	 *
	 * @return the x axis for the chart
	 */
	public LinkedList<Integer> getXChart() {
		// placeholder
		LinkedList<Integer> x = new LinkedList<Integer>();
		for (int i = 1; i <= 2880; i++) {
			x.add(i);
		}
		return x;
	}

	/**
	 * Gets the smart trader.
	 *
	 * @return the smart trader
	 */
	public SmartTrader getSmartTrader() {
		return smartTrader;
	}

	/**
	 * Updates the share index.
	 */
	public void updateShareIndex() {
		shareIndex = 0;
		for (int i = 0; i < companies.size(); i++) {
			shareIndex = shareIndex + companies.get(i).getCurrentShareValue();
		}
		shareIndex = shareIndex / companies.size();
		shareIndexList.add(shareIndex);
	}

	/**
	 * Gets the share index.
	 *
	 * @return the share index
	 */
	public double getShareIndex() {
		return shareIndex;
	}

	/**
	 * Sets the current client.
	 *
	 * @param client
	 *            the new current client
	 */
	public void setCurrentClient(Client client) {
		currentClient = client;
	}

	/**
	 * Gets the current client.
	 *
	 * @return the current client
	 */
	public Client getCurrentClient() {
		return currentClient;
	}

	/**
	 * Gets the events.
	 *
	 * @return the events
	 */
	public LinkedList<Events> getEvents() {
		return events;
	}

	/**
	 * Gets the companies.
	 *
	 * @return the companies
	 */
	public LinkedList<Company> getCompanies() {
		return companies;
	}

	/**
	 * Gets the traders.
	 *
	 * @return the traders
	 */
	public LinkedList<Trader> getTraders() {
		return traders;
	}

	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public String getTime() {
		return String.valueOf(currentTime);
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate() {
		return String.valueOf(currentDate);
	}

	/**
	 * Updates the date and time.
	 */
	public void updateDateTime() {
		currentTime = currentTime.plusMinutes(15);
		if (String.valueOf(currentTime).equals("00:00"))
		{
			currentDate = currentDate.plusDays(1);
			updateShareIndex();
		}	
		checkEvent();
		endEvents();
	}

	/**
	 * Selects the status of the market.
	 *
	 * @return the status of the market
	 */
	public String marketStatus() {
		int end = shareIndexList.size() - 1;
		if (shareIndexList.size() >= 3) {
			if (shareIndexList.get(end) > shareIndexList.get(end - 1)
					&& shareIndexList.get(end - 1) > shareIndexList.get(end - 2))
				return "Bull";
			if (shareIndexList.get(end) < shareIndexList.get(end - 1)
					&& shareIndexList.get(end - 1) < shareIndexList.get(end - 2))
				return "Bear";
		}
		return "Undefined";
	}

	/**
	 * Starts a trading cycle which represents 15 minutes of trading in real time.
	 */
	public void tradeSim() {
		if (!isMarketClosed()) 
		{
			for (int i = 0; i < traders.size(); i++) 
			{
				for (int j = 0; j < traders.get(i).getClients().size(); j++) 
				{
					traders.get(i).getClients().get(j).setBuyMax(traders.get(i).getBuyRate());
					traders.get(i).getClients().get(j).setSellMax(traders.get(i).getSellRate());
					traders.get(i).getClients().get(j).setBuyAmount(0);
					traders.get(i).getClients().get(j).setSellAmount(0);
					System.out.println(traders.get(0).getClients().get(0).getBuyMax());
					System.out.println(traders.get(0).getClients().get(0).getCashHolding());
					while(traders.get(i).getClients().get(j).getSellAmount() < traders.get(i).getClients().get(j).getSellMax()-10 && traders.get(i).getClients().get(j).getBuyAmount() < traders.get(i).getClients().get(j).getBuyMax()-10) 
					{
						boolean type;
						int randomCompanyIndex = rand.nextInt(companies.size());
						if (companies.get(randomCompanyIndex).getRisk().equalsIgnoreCase(traders.get(i).getClients().get(j).getRisk()) || traders.get(i).getClients().get(j).getRisk().equalsIgnoreCase("High")) 
						{
							if(traders.get(i).getClients().get(j).hasShare(companies.get(randomCompanyIndex)))
								type = rand.nextBoolean();
							else
								type = true;
							traders.get(i).newOrder(traders.get(i).getClients().get(j), companies.get(randomCompanyIndex),type);
						}
					}
				}
			}
			System.out.println(traders.get(0).getOrderList().size());
			//Update share values using rules of supply vs demand
			for (int i = 0; i < companies.size(); i++) {
				companies.get(i).updateShareValue(companies.get(i).getBuyCount() + companies.get(i).getSellCount());
				companies.get(i).setFinalCount();
			}
			//
			for (int i = 0; i < traders.size(); i++) 
			{
				for (int j = 0; j < traders.get(i).getOrderList().size(); j++) 
				{
					 traders.get(i).completeOrder(traders.get(i).getOrderList().get(j));
				}
				traders.get(i).switchMode(Math.random());
				traders.get(i).addOrderHistory();
			}

			for (int i = 0; i < companies.size(); i++) 
			{
				if (companies.get(i).getBuyCount() > Math.abs(companies.get(i).getSellCount())) 
				{
					companies.get(i).clearBuyCount();
				} 
				else 
				{
					companies.get(i).clearSellCount();
				}

				for (int a = 0; a < traders.size(); a++) 
				{
					for (int b = 0; b < traders.get(a).getOrderList().size(); b++) 
					{
						if (traders.get(a).getOrderList().get(b).isFullyCompleted() == false) 
						{
							if (traders.get(a).getOrderList().get(b).getOrderType() == true && companies.get(i).getSellCount() != 0)
							{
								traders.get(a).getOrderList().get(b).getClient().newShare(1, companies.get(i));
								companies.get(i).setSellCount(-1);
							}
							if (traders.get(a).getOrderList().get(b).getOrderType() == false && companies.get(i).getBuyCount() != 0) {
								traders.get(a).getOrderList().get(b).getClient().newShare(-1, companies.get(i));
								companies.get(i).setBuyCount(-1);
							}
						}
					}
					traders.get(a).clearOrders();
				}
				companies.get(i).clearFinalCount();
				companies.get(i).clearBuyCount();
				companies.get(i).clearSellCount();
				//removes company from stock market if its share value is less than 1 penny
				if (isCompanyTradable(companies.get(i)) == false) 
				{
					for (int j = 0; j < traders.size(); j++) 
					{
						for (int k = 0; k < traders.get(j).getClients().size(); k++) 
						{
							for (int x = 0; x < traders.get(j).getClients().get(k).getPortfolio().size(); x++) 
							{
								if (traders.get(j).getClients().get(k).getPortfolio().get(x).getCompanyName().equals(companies.get(i).getName()))
									traders.get(j).getClients().get(k).getPortfolio().remove(traders.get(j).getClients().get(k).getPortfolio().get(x));
							}
							traders.get(j).getClients().get(k).calculateNetWorth();
						}
					}
					companies.remove(companies.get(i));
				}
			}
		}
		//checkShareNum();
		updateDateTime();
	}

	/**
	 * Sets up the simulation.
	 */
	
	public void setUpSim() {
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
	 * Sets up the companies.
	 *
	 * @param reader the new companies file
	 */
	public void setUpCompanies(CSVReader reader) {
		currentCompaniesFile = reader;
		String[] myEntries;
		try {
			String[] next = reader.readNext();
			while (next != null) {
				myEntries = next;
				Company company = new Company(myEntries[0], myEntries[1], Double.valueOf(myEntries[2]),
						Integer.valueOf(myEntries[3]));
				companies.add(company);
				next = reader.readNext();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets up the clients.
	 *
	 * @param reader the new clients file
	 */
	public void setUpClients(CSVReader reader) {
		currentClientsFile = reader;
		int index = 1;
		String[] myEntries;
		try {
			String[] next = reader.readNext();
			while (next != null) {
				myEntries = next;
				Client client = new Client(myEntries[0], Double.valueOf(myEntries[1]));
				for (int i = 2; i <= next.length - 1; i++) {
					client.initialShare(Integer.valueOf(myEntries[i]), companies.get(i - 2));
				}
				client.calculateNetWorth();
				if (client.getName().equals("Norbert DaVinci") || client.getName().equals("Justine Thyme"))
					smartTrader.addClient(client);
				else {
					traders.get(index).addClient(client);
					index++;
					if (index > numOfTraders)
						index = 1;

				}
				next = reader.readNext();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets up the random traders.
	 *
	 * @param num the new number of random traders
	 */
	public void setUpRandomTraders(int num) {
		numOfTraders = num;
		for (int i = 0; i < numOfTraders; i++) {
			RandomTrader randomTrader = new RandomTrader(i);
			traders.add(randomTrader);
		}
	}

	/**
	 * Gets the number of traders.
	 *
	 * @return the number of traders
	 */
	public int getNumOfTraders() {
		return numOfTraders;
	}

	/**
	 * Sets up the events.
	 *
	 * @param reader the new events file
	 */
	public void setUpEvents(CSVReader reader) {
		currentEventsFile = reader;
		String[] myEntries;
		try {
			String[] next = reader.readNext();
			while (next != null) {
				myEntries = next;
				Events event = new Events(myEntries[1], myEntries[0], myEntries[2], myEntries[3], myEntries[4],
						myEntries[5]);
				events.add(event);
				next = reader.readNext();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks if the company is tradable.
	 *
	 * @param company the company
	 * @return true, if company is tradable
	 */
	public boolean isCompanyTradable(Company company) {
		if (company.getCurrentShareValue() < 0.01)
			return false;

		return true;
	}
	

	/**
	 * Checks if the market is closed.
	 *
	 * @return true, if the market is closed
	 */
	public boolean isMarketClosed() {
		if (currentDate.getDayOfWeek().equals(DayOfWeek.SATURDAY))
			return true;
		if (currentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY))
			return true;
		if (currentDate.toString().equals("2017-12-25"))
			return true;
		if (currentDate.toString().equals("2017-12-26"))
			return true;
		if (currentDate.toString().equals("2017-04-17"))
			return true;
		if (currentDate.toString().equals("2017-04-14"))
			return true;
		if (Integer.valueOf(getTime().substring(0, 2)) >= 16)
			return true;
		if (Integer.valueOf(getTime().substring(0, 2)) < 9) 
			return true;

		return false;
	}
	

	/**
	 * Checks the event.
	 */
	public void checkEvent() {
		if (getDate().equals(String.valueOf(events.get(nextEvent).getDate())) && getTime().equals(String.valueOf(events.get(nextEvent).getTime()))) {
			for (int i = 0; i < companies.size(); i++) 
			{
				companies.get(i).event(events.get(nextEvent).getEventType()[1]);
				if (companies.get(i).isEventTriggered()) 
				{
					companies.get(i).setOrderType(events.get(nextEvent).getEventType()[0]);
					companies.get(i).setEventEnd(events.get(nextEvent).getEventType()[2] + " " + String.valueOf(events.get(nextEvent).getTime()));
				}
			}
			events.get(nextEvent).trigger();
			nextEvent++;
		}

	}
	/**
	 * End the events.
	 */
	public void endEvents() 
	{
		String currentDateTime = getDate() + " " + getTime();
		for (int i = 0; i < companies.size(); i++) 
		{
			if (companies.get(i).getEventEnd().equals(currentDateTime)) 
			{
				companies.get(i).endEvent();
			}
		}
	}

}
