/**
 * @author jon
 *
 */
public class Shares{
	private Company company;
	private int risk, number;
	private double currentPrice;
	private double shareValue;
	private double size;
	
	
	
	
	public Shares(double size,  Company company)
	{
		this.size = size;
		this.risk = 1;
		this.currentPrice = company.getCurrentShareValue() ;
		this.company = company;
		this.shareValue = currentPrice * size;
	}
	
	public void updateSize(double num)
	{
		size = size + num;
		//updateShareValue();
	}
	public double getSize()
	{
		return size;
	}
	
	public void setRisk(int risk)
	{
		this.risk = risk;
	}
	
	public int getRisk()
	{
		return risk;
	}
	
	public void updatePrice()
	{
		this.currentPrice = company.getCurrentShareValue();
	}
	
	public double getPrice()
	{
		return currentPrice;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public String getCompanyName()
	{
		return company.getName();
	}
	
	
	public void updateShareValue()
	{
		shareValue = getSize()*getPrice();
	}
	
	public double getShareValue()
	{
		return shareValue;
	}
	
	
	

}
