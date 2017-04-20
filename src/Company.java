/**
 * @author jon
 *
 */
import java.util.LinkedList;
public class Company {
	
	private String name;
	private int shareCount;
	private double netWorth, currentShareValue; 
	private LinkedList shareValueList = new LinkedList();
	private double sellCount;
	private double buyCount;
	/*private enum ShareType
	{
		FOOD, HARD, TECH, PROPERTY
	}*/
	private String shareType;
	
	public Company(String name, String shareType, double currentShareValue, int shareCount) 
	{
		this.name = name;
		this.shareCount = shareCount;
		setNetWorth();
		this.netWorth = getNetWorth();
		this.currentShareValue = currentShareValue/100;
		shareValueList.add(getCurrentShareValue());	
	}
	
	public LinkedList getShareValueList()
	{
		return shareValueList;	// i assume that this is the list of possible quantities that you can get for a given company?
	}

	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void updateShareCount(int shareCount) 
	{
		this.shareCount = this.shareCount + shareCount;
	}
	
	public int getShareCount() 
	{
		return shareCount;
	}
	
	public void setNetWorth() 
	{
		this.netWorth = getShareCount()*getCurrentShareValue();
	}
	
	public double getNetWorth() 
	{
		return netWorth;
	}
	
	public void setCurrentShareValue(double currentShareValue) 
	{
		this.currentShareValue = currentShareValue;
		shareValueList.add(getCurrentShareValue());
	}
	
	public double getCurrentShareValue() 
	{
		return currentShareValue;
	}
	
	public void updateShareValue(double excess)
	{
		setCurrentShareValue(getCurrentShareValue()+(excess/shareCount)*getCurrentShareValue());//supply vs demand
	}
	
	public String getShareType()
	{
		return shareType;
	}
	
	public void setSellCount(int sellCount)
	{
		this.sellCount = this.sellCount + sellCount;
	}
	
	public void setBuyCount(int buyCount)
	{
		this.buyCount = this.buyCount + buyCount;
	}
	
	public double getSellCount()
	{
		return sellCount;
	}
	
	public double getBuyCount()
	{
		return buyCount;
	}
	
	public void clearCount()
	{
		sellCount = 0;
		buyCount = 0;
	}
	
	
	
}
