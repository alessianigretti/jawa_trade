
import java.util.LinkedList;
import java.util.Random;

/**
 * The Class Company is responsible for issuing shares to be traded on the stock market.
 * 
 * @author Jonathan Magbadelo
 */
public class Company {

	private boolean eventTrigger = false; //indicates whether an event that affects the company has happended
	private String eventEnd = ""; //end date/time of the event affecting the company
	private boolean orderType = false; //set orderType for when a company event happens
	private String name; //name of the company
	private int shareCount; //company share count
	private double netWorth, currentShareValue; //the current share value and networth of the company
	private LinkedList<Double> shareValueList = new LinkedList<Double>(); //list of company share values
	private double sellCount; //current count of sell orders for the company
	private double buyCount; //current count of buy orders for the company
	private double finalSellCount; //final count of sell orders for the company
	private double finalBuyCount; //final count of buy orders for the company
	private String trend = "-"; //current trend of the companies share value
	private Random rand = new Random();

	/**
	 * The Enum Type for the types of commodities.
	 */
	public enum Type {
		Hitech, Property, Hard, Food
	}

	/**
	 * The Enum Risk for the available risks.
	 */
	public enum Risk {
		Low, High
	}

	private Type shareType; //the company share type
	private Risk risk; //the risk asscoicted with the company

	/**
	 * Instantiates a new company.
	 *
	 * @param name            the name of the company
	 * @param type 			the type of the company
	 * @param currentShareValue            the current share value of the company
	 * @param shareCount            the share count of the company
	 */
	public Company(String name, String type, double currentShareValue, int shareCount) {
		this.name = name;
		this.shareCount = shareCount;
		setNetWorth();
		this.netWorth = getNetWorth();
		this.currentShareValue = currentShareValue / 100;
		shareValueList.add(getCurrentShareValue());
		this.shareType = Type.valueOf(type);
		setRisk(shareType);
	}

	/**
	 * Gets the share value list of the company.
	 *
	 * @return the share value list of the company
	 */
	public LinkedList getShareValueList() {
		return shareValueList;
	}

	/**
	 * Sets the name of the company.
	 *
	 * @param name
	 *            the new name of the company
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the name of the company.
	 *
	 * @return the name of the company
	 */
	public String getName() {
		return name;
	}

	/**
	 * Update share count of the company.
	 *
	 * @param shareCount
	 *            the share count to update to
	 */
	public void updateShareCount(int shareCount) {
		this.shareCount = this.shareCount + shareCount;
	}

	/**
	 * Gets the share count of the company.
	 *
	 * @return the share count of the company
	 */
	public int getShareCount() {
		return shareCount;
	}

	/**
	 * Sets the net worth of the company.
	 */
	public void setNetWorth() {
		this.netWorth = getShareCount() * getCurrentShareValue();
	}

	/**
	 * Gets the net worth of the company.
	 *
	 * @return the net worth of the company
	 */
	public double getNetWorth() {
		return netWorth;
	}

	/**
	 * Sets the current share value of the company.
	 *
	 * @param currentShareValue
	 *            the new current share value of the company
	 */
	public void setCurrentShareValue(double currentShareValue) {
		this.currentShareValue = currentShareValue;
		shareValueList.add(getCurrentShareValue());
	}

	/**
	 * Gets the current share value of the company.
	 *
	 * @return the current share value of the company
	 */
	public double getCurrentShareValue() {
		return currentShareValue;
	}

	/**
	 * Update share value of the company.
	 *
	 * @param excess
	 *            the excess
	 */
	public void updateShareValue(double excess) {
		setCurrentShareValue(getCurrentShareValue() + (excess / shareCount) * getCurrentShareValue());// supply
																										// vs
																										// demand
		setCompanyTrend();
	}

	/**
	 * Gets the share type of the company.
	 *
	 * @return the share type of the company
	 */
	public String getShareType() {
		return String.valueOf(shareType);
	}

	/**
	 * Sets the risk of the company.
	 *
	 * @param type the type of the company, which is associated with a risk
	 */
	public void setRisk(Type type) {
		switch (type) {
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

	/**
	 * Gets the risk of the company.
	 *
	 * @return the risk of the company
	 */
	public String getRisk() {
		return String.valueOf(risk);
	}

	/**
	 * Sets the sell count of the company.
	 *
	 * @param sc the new sell count of the company
	 */
	public void setSellCount(double sc) {
		sellCount = sellCount + sc;
	}

	/**
	 * Sets the buy count of the company.
	 *
	 * @param bc the new buy count of the company
	 */
	public void setBuyCount(double bc) {
		buyCount = buyCount + bc;
	}

	/**
	 * Gets the sell count of the company.
	 *
	 * @return the sell count of the company
	 */
	public double getSellCount() {
		return sellCount;
	}

	/**
	 * Gets the buy count of the company.
	 *
	 * @return the buy count of the company
	 */
	public double getBuyCount() {
		return buyCount;
	}

	/**
	 * Clear count of the company.
	 */
	public void clearFinalCount() {
		finalSellCount = 0;
		finalBuyCount = 0;
	}

	/**
	 * Clear buy count of the company.
	 */
	public void clearBuyCount() {
		buyCount = 0;
	}

	/**
	 * Clear sell count of the company.
	 */
	public void clearSellCount() {
		sellCount = 0;
	}

	/**
	 * Sets the company trend.
	 */
	public void setCompanyTrend() {
		int end = shareValueList.size() - 1;
		if (shareValueList.size() >= 3) {
			if (shareValueList.get(end) > shareValueList.get(end - 1)
					&& shareValueList.get(end - 1) > shareValueList.get(end - 2))
				trend = "^";
			if (shareValueList.get(end) < shareValueList.get(end - 1)
					&& shareValueList.get(end - 1) < shareValueList.get(end - 2))
				trend = "v";
		} else {
			trend = "-";
		}
	}

	/**
	 * Gets the company trend.
	 *
	 * @return the company trend
	 */
	public String getCompanyTrend() {
		return trend;
	}

	/**
	 * Sets the final count of the company.
	 */
	public void setFinalCount() {
		finalBuyCount = getBuyCount();
		finalSellCount = getSellCount();
	}

	/**
	 * Gets the final sell count of the company.
	 *
	 * @return the final sell count of the company
	 */
	public double getFinalSellCount() {
		return finalSellCount;
	}

	/**
	 * Gets the final buy count of the company.
	 *
	 * @return the final buy count of the company
	 */
	public double getFinalBuyCount() {
		return finalBuyCount;
	}

	/**
	 * Trigger event.
	 */
	public void triggerEvent() {
		eventTrigger = true;
	}

	/**
	 * End event.
	 */
	public void endEvent() {
		eventTrigger = false;
		eventEnd = "";
	}

	/**
	 * Checks if event is triggered.
	 *
	 * @return true, if is event triggered
	 */
	public boolean isEventTriggered() {
		return eventTrigger;
	}
	
	/*public boolean randomBool()
	{
		if(isEventTriggered())
			return getOrderType();
		else
			return rand.nextBoolean();
	}
	
	public void setOrderType(String type)
	{
		if(type.contains("buy") || type.contains("Buy") )
			orderType = true;
		if(type.contains("sell") || type.contains("Sell") )
			orderType = false;
	}
	
	
	
	public void setEventEnd(String end)
	{
	}

	/**
	 * Generate random boolean is the event is not triggered.
	 *
	 * @return orderType if the event is triggered, random boolean otherwise
	 */
	public boolean randomBool() {
		
			return orderType;
	}

	/**
	 * Sets the order type of the company.
	 *
	 * @param type the new order type of the company
	 */
	public void setOrderType(String type) {
		if (type.contains("buy"))
			orderType = true;
		if (type.contains("sell"))
			orderType = false;
	}
	
	public boolean getOrderType()
	{
		return orderType;
	}

	/**
	 * Sets the event end.
	 *
	 * @param end the new event end
	 */
	public void setEventEnd(String end) {
		eventEnd = end;
	}

	/**
	 * Gets the event end.
	 *
	 * @return the event end
	 */
	public String getEventEnd() {
		return eventEnd;
	}

	/**
	 * Triggers an event.
	 *
	 * @param type the type
	 */
	public void event(String type) {
		if (type.equalsIgnoreCase(getName()) || type.equalsIgnoreCase(getShareType()) || type.equalsIgnoreCase("UK"))
			triggerEvent();
	}

}
