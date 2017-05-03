import java.util.*;

/**
 * The Class Trader is responsible for setting up a trader.
 * 
 * @author Jonathan Magbadelo
 */
public abstract class Trader {

	private LinkedList<Client> clientList;
	private LinkedList<Order> orderList = new LinkedList();
	private LinkedList<Order> orderHistory = new LinkedList();
	private String name;
	private double buyRate, sellRate;

	/**
	 * Instantiates a new trader.
	 */
	public Trader() {
		this.clientList = new LinkedList();
	}

	/**
	 * Sets the trader name.
	 *
	 * @param name
	 *            the new trader name
	 */
	public void setTraderName(String name) {
		this.name = name;
	}

	/**
	 * Gets the trader name.
	 *
	 * @return the trader name
	 */
	public String getTraderName() {
		if (name == null) {
			return "No trader selected.";
		} else {
			return name;
		}
	}

	/**
	 * Gets the order list.
	 *
	 * @return the order list
	 */
	public LinkedList<Order> getOrderList() {
		return orderList;
	}

	/**
	 * Gets the buy rate.
	 *
	 * @return the buy rate
	 */
	public double getBuyRate() {
		return buyRate;
	}

	/**
	 * Gets the sell rate.
	 *
	 * @return the sell rate
	 */
	public double getSellRate() {
		return sellRate;
	}

	/**
	 * Sets the sell rate.
	 *
	 * @param rate the new sell rate
	 */
	public void setSellRate(double rate) {
		sellRate = rate;
	}

	/**
	 * Sets the buy rate.
	 *
	 * @param rate the new buy rate
	 */
	public void setBuyRate(double rate) {
		buyRate = rate;
	}

	/**
	 * Sets a new order.
	 *
	 * @param client the client
	 * @param company the company
	 * @return the value of the new order
	 */
	public abstract double newOrder(Client client, Company company);

	/**
	 * Adds the client.
	 *
	 * @param client
	 *            the client
	 */
	public void addClient(Client client) {
		clientList.add(client);
	}

	/**
	 * Gets the clients.
	 *
	 * @return the clients
	 */
	public LinkedList<Client> getClients() {
		return clientList;
	}

	/**
	 * Clears the orders.
	 */
	public void clearOrders() {
		orderList.clear();
	}

	/**
	 * Adds the order history.
	 */
	public void addOrderHistory() {
		orderHistory.clear();
		orderHistory.addAll(orderList);
	}

	/**
	 * Gets the order history.
	 *
	 * @param client the client
	 * @return the order history
	 */
	public LinkedList<Order> getOrderHistory(Client client) {
		LinkedList<Order> orderHistoryList = orderHistory;
		for (int i = 0; i < orderHistoryList.size(); i++) {
			if (orderHistoryList.get(i).getClientName().equalsIgnoreCase(client.getName()))
				System.out.println("");

			if (orderHistoryList.get(i).getClientName().equalsIgnoreCase(client.getName()))
				System.out.println(orderHistoryList.get(i).getClientName());

			else
				orderHistoryList.remove(i);
		}
		return orderHistoryList;
	}
}
