/**
 * The Class Shares is responsible for representing the unit of ownership of a traded company.
 * 
 * @author Jonathan Magbadelo
 */
public class Shares {
	private Company company;
	private double currentPrice;
	private double shareValue;
	private double size;

	/**
	 * Instantiates a new share.
	 *
	 * @param size
	 *            the size
	 * @param company
	 *            the company
	 */
	public Shares(double size, Company company) {
		this.size = size;
		this.currentPrice = company.getCurrentShareValue();
		this.company = company;
		this.shareValue = currentPrice * size;
	}

	/**
	 * Updates the size.
	 *
	 * @param num
	 *            the extra
	 */
	public void updateSize(double num) {
		size = size + num;
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public double getSize() {
		return size;
	}

	/**
	 * Updates the price.
	 */
	public void updatePrice() {
		this.currentPrice = company.getCurrentShareValue();
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public double getPrice() {
		return currentPrice;
	}

	/**
	 * Gets the company name.
	 *
	 * @return the company name
	 */
	public String getCompanyName() {
		return company.getName();
	}

	/**
	 * Updates the share value.
	 */
	public void updateShareValue() {
		shareValue = getSize() * getPrice();
	}

	/**
	 * Gets the share value.
	 *
	 * @return the share value
	 */
	public double getShareValue() {
		return shareValue;
	}

}
