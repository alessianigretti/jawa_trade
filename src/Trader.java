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
	public double newOrder(Client client, Company company, boolean type)
	{
		System.out.println("fuck smartOrder");
		return 1;
	}
	
	/**
	 * Completes the order.
	 *
	 * @param o
	 *            the order
	 */
	public void completeOrder(Order o)
	{
		for (Client c : getClients()) {
			if (o.getClientName().equals(c.getName())) {
				if (o.getOrderType() == true) {
					if (o.getCompany().getSellCount() != 0) {
						c.updateCash(-(o.getQuantity() * o.getCurrentShareValue()));
						if ((o.getQuantity() / o.getCompany().getFinalBuyCount()) * Math.abs(o.getCompany().getFinalSellCount()) >= o.getQuantity()) {
							c.newShare(o.getQuantity(), o.getCompany());
							o.getCompany().setSellCount(o.getQuantity());
							o.isFullyCompleted();
							c.calculateNetWorth();
							break;
						} else {
							c.newShare(Math.floor((o.getQuantity() / o.getCompany().getFinalBuyCount()) * Math.abs(o.getCompany().getFinalSellCount())), o.getCompany());
							o.getCompany().setSellCount((Math.ceil((o.getQuantity() / o.getCompany().getFinalBuyCount()) * Math.abs(o.getCompany().getFinalSellCount()))));
							c.calculateNetWorth();
							break;
						}

					}
				} else {
					if (o.getCompany().getBuyCount() != 0) {
						c.updateCash(-(o.getQuantity() * o.getCurrentShareValue()));
						if ((o.getQuantity() / o.getCompany().getFinalSellCount()) * o.getCompany().getFinalBuyCount() <= o.getQuantity()) { 
							c.newShare(o.getQuantity(), o.getCompany());
							o.getCompany().setBuyCount(-o.getQuantity());
							o.isFullyCompleted();
							c.calculateNetWorth();
							break;
						} else {
							if (o.getCompany().getFinalBuyCount() > Math.abs(o.getCompany().getFinalSellCount())) {
								c.newShare(o.getQuantity(), o.getCompany());
								o.getCompany().setBuyCount(o.getQuantity());
							} else {
								c.newShare(Math.ceil(-((o.getQuantity() / o.getCompany().getFinalSellCount()) * o.getCompany().getFinalBuyCount())), o.getCompany());
								if(c.shareSize(o.getCompany())<0)
								o.getCompany().setBuyCount( -(Math.floor(((o.getQuantity() / o.getCompany().getFinalSellCount()) * o.getCompany().getFinalBuyCount()))));
							}
							c.calculateNetWorth();
							break;
						}
					}
				}
			}
		}
	}
	
	public void switchMode(double num)
	{
		
	}
	
	public String getMode()
	{
		return "A.I";
	}
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
			if (!(orderHistoryList.get(i).getClient().getName().equalsIgnoreCase(client.getName())))
				orderHistoryList.remove(i);
		}
		return orderHistoryList;
	}
}
