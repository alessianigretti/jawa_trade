/**
 * @author jon
 *
 */
import java.util.LinkedList;
public class Company {
	
	private String name;
	private int  shareCount;
	private double netWorth, currentShareValue; 
	private LinkedList shareValueList = new LinkedList();
	
	public Company(String name,  int shareCount, double netWorth, double currentShareValue) 
	{
		this.name = name;
		this.shareCount = shareCount;
		this.netWorth = netWorth;
		this.currentShareValue = currentShareValue;
		shareValueList.add(currentShareValue);	
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
	
	public void setNetWorth(double netWorth) 
	{
		this.netWorth = netWorth;
	}
	
	public double getNetWorth() 
	{
		return netWorth;
	}
	
	public void setCurrentShareValue(double currentShareValue) 
	{
		this.currentShareValue = currentShareValue;
	}
	
	public double getCurrentShareValue() 
	{
		return currentShareValue;
	}
	
	public void updateShareValue(double excess)
	{
		setNetWorth((excess/shareCount)*getCurrentShareValue()); // supply vs demand
	}
	
	
}
