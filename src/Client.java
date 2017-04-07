import java.util.LinkedList;
public class Client
{
	private String firstName, lastName; // basic client details
	private double netWorth; // total cash value of portfolio plus left over investment
	private Trader trader; // trader that client is connected to
	private  double investment; // client investment value
	private double initialInvestment; // initial investment value;
	private double expectedReturn;// expected return on clients investments
	private LinkedList<Shares> portfolio = new LinkedList(); // collection of shares that the client currently owns
	private LinkedList<Order> orderList = new LinkedList();
	
	private Client(String firstName, String lastName, int sharesOwned, Trader trader, double investment, double expectedReturn)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.trader = trader;
		this.investment = investment;
		this.initialInvestment = investment;
		this.expectedReturn = expectedReturn;
	}
	
	public Client(String firstName, String lastName, double expectedReturn, double initialInvestment)
	{
		// add error handling if one of the parameters is not passed in
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.expectedReturn = expectedReturn;
		this.initialInvestment = initialInvestment;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public String getFullName()
	{
		if (firstName == null && lastName == null)
		{
			return "No client selected.";
		} else {
			return firstName + " " + lastName;
		}
	}
	
	public void setExpectedReturn(double expectedReturn)
	{
		this.expectedReturn = expectedReturn;
	}
	
	public double getExpectedReturn()
	{
		return expectedReturn;
	}
	
	public void setInitialInvestment(double initialInvestment)
	{
		this.initialInvestment = initialInvestment;
	}
	
	public double getInitialInvestment()
	{
		return initialInvestment;
	}
	
	public void setRiskAll(int risk)
	{
		for(int i = 0; i<portfolio.size(); i++)
		{
			portfolio.get(i).setRisk(risk);
		}
	}
	
	public void newOrder(int quantity, Company company) //buying
	{
		Order order = new Order(quantity,company);
		orderList.add(order);
		for(int i = 0; i<portfolio.size(); i++)
		{
			if(portfolio.get(i).getCompanyName().equals(company.getName()))
			{
				portfolio.get(i).updateSize(quantity);	
				//company.updateShareCount(quantity); not needed as each company has a set number of shares!
			}
			else
			{
				portfolio.add(new Shares(quantity,portfolio.size()+1,company));
				//company.updateShareCount(quantity);
			}
		}
	}

	public void calculateNetWorth()
	{
		netWorth = 0;
		for(int i = 0; i <portfolio.size(); i++)
		{
			netWorth = netWorth + portfolio.get(i).getShareValue();
		}
		netWorth = netWorth + investment;
	}
	
	public double getNetWorth()
	{
		return netWorth;
	}
}