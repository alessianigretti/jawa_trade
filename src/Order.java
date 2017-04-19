
public class Order 
{
	private Company company;
	private boolean pending;
	private double openingPrice, closingPrice;
	private int quantity;
<<<<<<< HEAD
	private String clientName;
	private boolean orderType; //true == buy; false == sell;
=======
	private Client client;
	public enum OrderType
	{
		BUY, SELL
	}
	private OrderType orderType;
>>>>>>> 37f585622f4368e0082fde0394cedd8b7332e2f1
	
	public Order(int quantity,Company company,Client client, OrderType orderType)
	{
		this.company = company;
		this.openingPrice = company.getCurrentShareValue();
		this.quantity = quantity;
		this.client = client;
		this.orderType = orderType;
	}
	
	public void switchPending()
	{
		pending = !pending;
	}
	
	public boolean isPending()
	{
		return pending;
	}
	
	public double getCurrentShareValue()
	{
		return company.getCurrentShareValue();
	}
	
	public double getOpeningPrice()
	{
		return openingPrice;
	}
	
	public void setClosingPrice()
	{
		closingPrice = company.getCurrentShareValue();
	}
	
	public double getClosingPrice()
	{
		return closingPrice;
	}
	
	public String getCompanyName()
	{
		return company.getName();
	}
	
	public void updateQuantity(int extra)
	{
		quantity = quantity + extra;
	}
	
<<<<<<< HEAD
	public boolean getOrderType()
	{
		return orderType;
	}
	
	public int getQuanitity()
	{
		return quantity;
	}
	
	public String getClientName()
	{
		return clientName;
	}
	
	public Company getCompany()
	{
		return company;
	}
	
=======
	public OrderType getOrderType()
	{
		return orderType;
	}
>>>>>>> 37f585622f4368e0082fde0394cedd8b7332e2f1
	
}
