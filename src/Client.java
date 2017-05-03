import java.util.LinkedList;

/**
 * The Class Client is responsible for representing an individual or organisation that chooses to invest money
 * to generate a return of their investment.
 * 
 * @author Jonathan Magbadelo
 */
public class Client
{
	private String name; // basic client details
	private double netWorth, initialNetworth, finalNetworth; // total value of portfolio plus left over cash
	private double cashHolding; // client investment value
	private double investment;
	private double expectedReturn; // expected return on clients investments
	private LinkedList<Shares> portfolio = new LinkedList(); // collection of shares that the client currently owns
	private Company.Risk risk = Company.Risk.High; // all clients by default have a high risk

	/**
	 * Instantiates a new client specifying name, expected return and cash holding.
	 *
	 * @param name
	 *            the name of the client
	 * @param expectedReturn
	 *            the expected return of the client
	 * @param cashHolding
	 *            the cash holding of the client
	 */
	public Client(String name, double expectedReturn, double cashHolding) {
		this.name = name;
		this.expectedReturn = expectedReturn;
		this.cashHolding = cashHolding;
	}

	/**
	 * Instantiates a new client specifying name and cash holding.
	 *
	 * @param name
	 *            the name of the client
	 * @param cashHolding
	 *            the cash holding of the client
	 */
	public Client(String name, double cashHolding) {
		this(name, 0, 0);
		this.cashHolding = cashHolding;
	}

	/**
	 * Sets the name of the client.
	 *
	 * @param name
	 *            the new name of the client
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the name of the client.
	 *
	 * @return the name of the client
	 */
	public String getName() {
		if (name == null) {
			return "No client selected.";
		} else {
			return name;
		}
	}

	/**
	 * Sets the expected return of the client.
	 *
	 * @param expectedReturn
	 *            the new expected return of the client
	 */
	public void setExpectedReturn(double expectedReturn) {
		this.expectedReturn = expectedReturn;
	}

	/**
	 * Gets the expected return of the client.
	 *
	 * @return the expected return of the client
	 */
	public double getExpectedReturn() {
		return expectedReturn;
	}

	/**
	 * Sets the risk of the client.
	 *
	 * @param risk
	 *            the new risk of the client
	 */
	public void setRisk(String risk) {
		if (risk.equalsIgnoreCase("High"))
			this.risk = Company.Risk.High;
		if (risk.equalsIgnoreCase("Low"))
			this.risk = Company.Risk.Low;
	}

	/**
	 * Gets the risk of the client.
	 *
	 * @return the risk of the client
	 */
	public String getRisk() {
		return String.valueOf(risk);
	}

	/**
	 * Sets a new share.
	 *
	 * @param quantity
	 *            the quantity of the share
	 * @param company
	 *            the company
	 */
	public void newShare(double quantity, Company company) {
		int iter = 0;
		while (iter < portfolio.size()) {
			// if order makes quantitiy negative, reject it
			if (portfolio.get(iter).getCompanyName().equals(company.getName()))
				portfolio.get(iter).updateSize(quantity);
			iter++;
		}
	}

	/**
	 * Gets the portfolio of the client.
	 *
	 * @return the portfolio of the client
	 */
	public LinkedList<Shares> getPortfolio() {
		return portfolio;
	}

	/**
	 * Initial share.
	 *
	 * @param quantity
	 *            the quantity
	 * @param company
	 *            the company
	 */
	public void initialShare(int quantity, Company company) {
		portfolio.add(new Shares(quantity, company));
	}

	/**
	 * Calculate the investment of the client.
	 */
	public void calculateInvestment() {
		investment = 0;
		for (int i = 0; i < portfolio.size(); i++) {
			investment = investment + portfolio.get(i).getShareValue();
		}

	}

	/**
	 * Update cash of the client.
	 *
	 * @param value the value of the cash of the client
	 */
	public void updateCash(double value) {
		cashHolding = cashHolding + value;
	}

	/**
	 * Gets the investment of the client.
	 *
	 * @return the investment of the client
	 */
	public double getInvestment() {
		return investment;
	}

	/**
	 * Gets the cash holding of the client.
	 *
	 * @return the cash holding of the client
	 */
	public double getCashHolding() {
		return cashHolding;
	}

	/**
	 * Calculate net worth of the client.
	 */
	public void calculateNetWorth() {
		calculateInvestment();
		netWorth = getInvestment() + getCashHolding();
	}

	/**
	 * Gets the net worth of the client.
	 *
	 * @return the net worth of the client
	 */
	public double getNetWorth() {
		return Math.round(netWorth);
	}

	/**
	 * Checks for share.
	 *
	 * @param company the company
	 * @return true, if successful
	 */
	public boolean hasShare(Company company) {
		for (int i = 0; i < portfolio.size(); i++) {
			if (company.getName().equals(portfolio.get(i).getCompanyName())) {
				if (portfolio.get(i).getSize() == 0)
					return false;
			}
		}
		return true;
	}

	/**
	 * Gets the share size.
	 *
	 * @param company the company
	 * @return the size
	 */
	public double shareSize(Company company) {
		double size = 0;
		for (int i = 0; i < portfolio.size(); i++) {
			if (company.getName().equals(portfolio.get(i).getCompanyName())) {
				size = portfolio.get(i).getSize();
			}
		}
		return size;
	}
}