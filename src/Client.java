import java.util.LinkedList;
public class Client
{
	private String name; // basic client details
	private double netWorth; // total cash value of portfolio plus left over investment
	private Trader trader; // trader that client is connected to
	private  double investment; // client investment value
	private double initialInvestment; // initial investment value;
	private double expectedReturn;// expected return on clients investments
	private LinkedList<Shares> portfolio = new LinkedList(); // collection of shares that the client currently owns
	private LinkedList<Order> orderList = new LinkedList();
	
	public Client(String name, double expectedReturn, double initialInvestment)
	{
		// add error handling if one of the parameters is not passed in
		
		this.name = name;
		this.expectedReturn = expectedReturn;
		this.initialInvestment = initialInvestment;
		this.investment = initialInvestment;
	}
	
	public Client(String name, double investment)
	{
		this(name, 0, 0);
		this.investment = investment;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		if (name == null)
		{
			return "No client selected.";
		} else {
			return name;
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
				portfolio.add(new Shares(quantity,company));
				//company.updateShareCount(quantity);
			}
		}
	}
	
	public void initialShare(int quantity, Company company) 
	{
		portfolio.add(new Shares(quantity, company));
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
		return Math.round(netWorth);
	}
}