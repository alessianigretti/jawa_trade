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
	/*private enum ShareType
	{
		FOOD, HARD, TECH, PROPERTY
	}*/
	private String shareType;
	
	public Company(String name,String shareType,double currentShareValue,int shareCount) 
	{
		this.name = name;
		this.shareCount = shareCount;
		setNetWorth();
		this.netWorth = getNetWorth();
		this.currentShareValue = currentShareValue/100;
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
	}
	
	public double getCurrentShareValue() 
	{
		return currentShareValue;
	}
	
	/*public void updateShareValue(double excess)
	{
		setNetWorth((excess/shareCount)*getCurrentShareValue()); // supply vs demand
	}*/
	
	public String getShareType()
	{
		return shareType;
	}
	
	
	
}
