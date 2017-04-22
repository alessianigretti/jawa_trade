
// TODO: Auto-generated Javadoc
/**
 * The Class Order.
 */
public class Order 
{
	private Company company;
	private boolean pending;
	private double openingPrice, closingPrice;
	private int quantity;
	private String clientName;
	private boolean orderType; //true == buy; false == sell;
	
	/**
	 * Instantiates a new order.
	 *
	 * @param quantity the quantity
	 * @param company the company
	 * @param clientName the client name
	 * @param orderType the order type
	 */
	public Order(int quantity,Company company,String clientName, boolean orderType)
	{
		this.company = company;
		this.openingPrice = company.getCurrentShareValue();
		this.quantity = quantity;
		this.clientName = clientName;
		this.orderType = orderType;
	}
	
	/**
	 * Switch pending.
	 */
	public void switchPending()
	{
		pending = !pending;
	}
	
	/**
	 * Checks if is pending.
	 *
	 * @return true, if is pending
	 */
	public boolean isPending()
	{
		return pending;
	}
	
	/**
	 * Gets the current share value.
	 *
	 * @return the current share value
	 */
	public double getCurrentShareValue()
	{
		return company.getCurrentShareValue();
	}
	
	/**
	 * Gets the opening price.
	 *
	 * @return the opening price
	 */
	public double getOpeningPrice()
	{
		return openingPrice;
	}
	
	/**
	 * Sets the closing price.
	 */
	public void setClosingPrice()
	{
		closingPrice = company.getCurrentShareValue();
	}
	
	/**
	 * Gets the closing price.
	 *
	 * @return the closing price
	 */
	public double getClosingPrice()
	{
		return closingPrice;
	}
	
	/**
	 * Gets the company name.
	 *
	 * @return the company name
	 */
	public String getCompanyName()
	{
		return company.getName();
	}
	
	/**
	 * Update quantity.
	 *
	 * @param extra the extra
	 */
	public void updateQuantity(int extra)
	{
		quantity = quantity + extra;
	}
	
	/**
	 * Gets the quanitity.
	 *
	 * @return the quanitity
	 */
	public int getQuanitity()
	{
		return quantity;
	}
	
	/**
	 * Gets the client name.
	 *
	 * @return the client name
	 */
	public String getClientName()
	{
		return clientName;
	}
	
	/**
	 * Gets the company.
	 *
	 * @return the company
	 */
	public Company getCompany()
	{
		return company;
	}
	
	/**
	 * Gets the order type.
	 *
	 * @return the order type
	 */
	public boolean getOrderType()
	{
		return orderType;
	}
	
}
