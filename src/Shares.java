// TODO: Auto-generated Javadoc
/**
 * The Class Shares.
 *
 * @author jon
 */
public class Shares{
	private Company company;
	private double currentPrice;
	private double shareValue;
	private double size;
	
	/**
	 * Instantiates a new shares.
	 *
	 * @param size the size
	 * @param company the company
	 */
	public Shares(double size,  Company company)
	{
		this.size = size;
		this.currentPrice = company.getCurrentShareValue() ;
		this.company = company;
		this.shareValue = currentPrice * size;
	}
	
	/**
	 * Update size.
	 *
	 * @param num the num
	 */
	public void updateSize(double num)
	{
		size = size + num;
		//updateShareValue();
	}
	
	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public double getSize()
	{
		return size;
	}
	
	/**
	 * Update price.
	 */
	public void updatePrice()
	{
		this.currentPrice = company.getCurrentShareValue();
	}
	
	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public double getPrice()
	{
		return currentPrice;
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
	 * Update share value.
	 */
	public void updateShareValue()
	{
		shareValue = getSize()*getPrice();
	}
	
	/**
	 * Gets the share value.
	 *
	 * @return the share value
	 */
	public double getShareValue()
	{
		return shareValue;
	}
	
	
	

}
