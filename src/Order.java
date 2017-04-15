
public class Order 
{
	private Company company;
	private boolean pending;
	private double openingPrice, closingPrice;
	private int quantity;
	private String clientName;
	private boolean orderType = true; //true == buy; false == sell;
	
	public Order(int quantity,Company company,String clientName, boolean orderType)
	{
		this.company = company;
		this.openingPrice = company.getCurrentShareValue();
		this.quantity = quantity;
		this.clientName = clientName;
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
	
	
}
