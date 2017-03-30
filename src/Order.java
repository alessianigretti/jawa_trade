
public class Order 
{
	private Company company;
	private boolean pending;
	private double openingPrice, closingPrice;
	private int quantity;
	private enum OrderType
	{
		BUY, SELL
	}
	private OrderType orderType;
	
	public Order(int quantity,Company company)
	{
		this.company = company;
		this.openingPrice = company.getCurrentShareValue();
		this.quantity = quantity;
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
	
}
