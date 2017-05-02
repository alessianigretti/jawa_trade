/**
 * @author jon
 *
 */
import java.util.LinkedList;
import java.util.Random;

/**
 * The Class Company.
 */
public class Company {
	
	private boolean eventTrigger = false;
	private String eventEnd = "";
	private boolean orderType;
	private String name;
	private int shareCount;
	private double netWorth, currentShareValue; 
	private LinkedList<Double> shareValueList = new LinkedList<Double>();
	private double sellCount;
	private double buyCount;
	private double finalSellCount;
	private double finalBuyCount;
	private String trend = "-";
	private Random rand  = new Random();
	public enum Type {
	    Hitech, Property, Hard, Food
	}
	public enum Risk {
		Low, High
	}
	private Type shareType;
	private Risk risk;
	
	/**
	 * Instantiates a new company.
	 *
	 * @param name the name
	 * @param shareType the share type
	 * @param currentShareValue the current share value
	 * @param shareCount the share count
	 */
	public Company(String name, String type, double currentShareValue, int shareCount) 
	{
		this.name = name;
		this.shareCount = shareCount;
		setNetWorth();
		this.netWorth = getNetWorth();
		this.currentShareValue = currentShareValue/100;
		shareValueList.add(getCurrentShareValue());	
		this.shareType = Type.valueOf(type);
		setRisk(shareType);
	}
	
	/**
	 * Gets the share value list.
	 *
	 * @return the share value list
	 */
	public LinkedList getShareValueList()
	{
		return shareValueList;	// i assume that this is the list of possible quantities that you can get for a given company?
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) 
	{
		this.name = name;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() 
	{
		return name;
	}
	
	/**
	 * Update share count.
	 *
	 * @param shareCount the share count
	 */
	public void updateShareCount(int shareCount) 
	{
		this.shareCount = this.shareCount + shareCount;
	}
	
	/**
	 * Gets the share count.
	 *
	 * @return the share count
	 */
	public int getShareCount() 
	{
		return shareCount;
	}
	
	/**
	 * Sets the net worth.
	 */
	public void setNetWorth() 
	{
		this.netWorth = getShareCount()*getCurrentShareValue();
	}
	
	/**
	 * Gets the net worth.
	 *
	 * @return the net worth
	 */
	public double getNetWorth() 
	{
		return netWorth;
	}
	
	/**
	 * Sets the current share value.
	 *
	 * @param currentShareValue the new current share value
	 */
	public void setCurrentShareValue(double currentShareValue) 
	{
		this.currentShareValue = currentShareValue;
		shareValueList.add(getCurrentShareValue());
	}
	
	/**
	 * Gets the current share value.
	 *
	 * @return the current share value
	 */
	public double getCurrentShareValue() 
	{
		return currentShareValue;
	}
	
	/**
	 * Update share value.
	 *
	 * @param excess the excess
	 */
	public void updateShareValue(double excess)
	{
		setCurrentShareValue(getCurrentShareValue()+(excess/shareCount)*getCurrentShareValue());//supply vs demand
		setCompanyTrend();
	}
	
	/**
	 * Gets the share type.
	 *
	 * @return the share type
	 */
	public String getShareType()
	{
		return String.valueOf(shareType);
	}
	
	public void setRisk(Type type)
	{
		switch(type)
		{
			case Hitech:
				risk = Risk.High;
				break;
			case Property:
				risk = Risk.High;
				break;
			case Food:
				risk = Risk.Low;
				break;
			case Hard:
				risk = Risk.Low;
				break;
			default:
		}
	}
	
	public String getRisk()
	{
		return String.valueOf(risk);
	}
	/**
	 * Sets the sell count.
	 *
	 * @param sellCount the new sell count
	 */
	public void setSellCount(double sc)
	{
		sellCount = sellCount + sc;
	}
	
	/**
	 * Sets the buy count.
	 *
	 * @param buyCount the new buy count
	 */
	public void setBuyCount(double bc)
	{
		buyCount = buyCount + bc;
	}
	
	/**
	 * Gets the sell count.
	 *
	 * @return the sell count
	 */
	public double getSellCount()
	{
		return sellCount;
	}
	
	/**
	 * Gets the buy count.
	 *
	 * @return the buy count
	 */
	public double getBuyCount()
	{
		return buyCount;
	}
	
	/**
	 * Clear count.
	 */
	public void clearFinalCount()
	{
		finalSellCount = 0;
		finalBuyCount = 0;
	}
	
	public void clearBuyCount()
	{
		buyCount = 0;
	}
	
	public void clearSellCount()
	{
		sellCount = 0;
	}
	
	public void setCompanyTrend()
	{
		int end = shareValueList.size()-1;
		if(shareValueList.size()>=3)
		{
			if(shareValueList.get(end) > shareValueList.get(end-1) && shareValueList.get(end-1) > shareValueList.get(end-2))
				trend =  "^";
			if(shareValueList.get(end) < shareValueList.get(end-1) && shareValueList.get(end-1) < shareValueList.get(end-2))
				trend =  "v";
		} else {
			trend = "-";
		}
	}
	
	public String getCompanyTrend()
	{
		return trend;
	}
	
	public void setFinalCount()
	{
		finalBuyCount = getBuyCount();
		finalSellCount = getSellCount();
	}
	
	public double getFinalSellCount()
	{
		return finalSellCount;
	}
	
	public double getFinalBuyCount()
	{
		return finalBuyCount;
	}
	
	public void triggerEvent()
	{
		eventTrigger = true;
	}
	
	public void endEvent()
	{
		eventTrigger = false;
		eventEnd = "";
	}
	
	public boolean isEventTriggered()
	{
		return eventTrigger;
	}
	
	public boolean randomBool()
	{
		if(isEventTriggered())
			return orderType;
		else
			return rand.nextBoolean();
	}
	
	public void setOrderType(String type)
	{
		if(type.contains("buy"))
			orderType = true;
		if(type.contains("sell"))
			orderType = false;
	}
	
	public void setEventEnd(String end)
	{
		eventEnd = end;
	}
	
	public String getEventEnd()
	{
		return eventEnd;
	}
	
	public void event(String type)
	{
		if(type.equalsIgnoreCase(getName()) || type.equalsIgnoreCase(getShareType()) || type.equalsIgnoreCase("UK"))
			triggerEvent();
	}
	
	
}
