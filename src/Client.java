import java.util.LinkedList;

/**
 * The Class Client.
 */
public class Client
{
	private String name; // basic client details
	private double netWorth; // total  value of portfolio plus left over cash
	private double cashHolding;// client investment value
	private double investment;
	private double expectedReturn;// expected return on clients investments
	private LinkedList<Shares> portfolio = new LinkedList(); // collection of shares that the client currently owns
	
	/**
	 * Instantiates a new client.
	 *
	 * @param name the name
	 * @param expectedReturn the expected return
	 * @param cashHolding the cash holding
	 */
	public Client(String name, double expectedReturn, double cashHolding)
	{
		this.name = name;
		this.expectedReturn = expectedReturn;
		this.cashHolding = cashHolding;
	}
	
	/**
	 * Instantiates a new client.
	 *
	 * @param name the name
	 * @param cashHolding the cash holding
	 */
	public Client(String name, double cashHolding)
	{
		this(name, 0, 0);
		this.cashHolding = cashHolding;
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
		if (name == null)
		{
			return "No client selected.";
		} else {
			return name;
		}
	}
	
	/**
	 * Sets the expected return.
	 *
	 * @param expectedReturn the new expected return
	 */
	public void setExpectedReturn(double expectedReturn)
	{
		this.expectedReturn = expectedReturn;
	}
	
	/**
	 * Gets the expected return.
	 *
	 * @return the expected return
	 */
	public double getExpectedReturn()
	{
		return expectedReturn;
	}
	
	/**
	 * Sets the risk all.
	 *
	 * @param risk the new risk all
	 */
	public void setRiskAll(int risk)
	{
		for(int i = 0; i<portfolio.size(); i++)
		{
			portfolio.get(i).setRisk(risk);
		}
	}
	
	/**
	 * New share.
	 *
	 * @param quantity the quantity
	 * @param company the company
	 */
	public void newShare(double quantity, Company company) 
	{
		int iter = 0;
		while(iter<portfolio.size())
		{
			//if order makes quantitiy negative, reject it
			if(portfolio.get(iter).getCompanyName().equals(company.getName()))
				portfolio.get(iter).updateSize(quantity);
			iter++;
		}
	}
	
	/**
	 * Gets the portfolio.
	 *
	 * @return the portfolio
	 */
	public LinkedList<Shares> getPortfolio()
	{
		return portfolio;
	}
	
	/**
	 * Initial share.
	 *
	 * @param quantity the quantity
	 * @param company the company
	 */
	public void initialShare(int quantity, Company company) 
	{
		portfolio.add(new Shares(quantity, company));
	}
	
	/**
	 * Calculate investment.
	 */
	public void calculateInvestment()
	{
		investment = 0;
		for(int i = 0; i <portfolio.size(); i++)
		{
			investment = investment + portfolio.get(i).getShareValue();
		}
		
	}
	
	public void updateCash(double value)
	{
		cashHolding = cashHolding + value;
	}
	
	/**
	 * Gets the investment.
	 *
	 * @return the investment
	 */
	public double getInvestment()
	{
		return investment;
	}
	
	/**
	 * Gets the cash holding.
	 *
	 * @return the cash holding
	 */
	public double getCashHolding()
	{
		return cashHolding;
	}
	
	/**
	 * Calculate net worth.
	 */
	public void calculateNetWorth()
	{
		calculateInvestment();
		netWorth = getInvestment() + getCashHolding();
	}
	
	/**
	 * Gets the net worth.
	 *
	 * @return the net worth
	 */
	public double getNetWorth()
	{
		return Math.round(netWorth);
	}
}