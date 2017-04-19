import java.util.LinkedList;
public class Client
{
	private String name; // basic client details
	private double netWorth; // total  value of portfolio plus left over cash
	private Trader trader; // trader that client is connected to
	private  double cashHolding;// client investment value
	private double investment;
	private double deposit; // initial deposit value;
	private double expectedReturn;// expected return on clients investments
	private LinkedList<Shares> portfolio = new LinkedList(); // collection of shares that the client currently owns
	
	public Client(String name, double expectedReturn, double deposit)
	{
		this.name = name;
		this.expectedReturn = expectedReturn;
		this.deposit = deposit;
		this.cashHolding = deposit;
	}
	
	public Client(String name, double cashHolding)
	{
		this(name, 0, 0);
		this.cashHolding = cashHolding;
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
	
	public double getDeposit()
	{
		return deposit;
	}
	
	public void setRiskAll(int risk)
	{
		for(int i = 0; i<portfolio.size(); i++)
		{
			portfolio.get(i).setRisk(risk);
		}
	}
	
	public void newShare(double quantity, Company company) 
	{
		quantity = Math.floor(quantity);
		for(int i = 0; i<portfolio.size(); i++)
		{
			if(portfolio.get(i).getCompanyName().equals(company.getName()))
			{
				portfolio.get(i).updateSize(quantity);
				//System.out.println(quantity);
			}
			else
			{
				portfolio.add(new Shares(quantity,company));
			}
		}
	}
	
	public LinkedList<Shares> getPortfolio()
	{
		return portfolio;
	}
	
	public void initialShare(int quantity, Company company) 
	{
		portfolio.add(new Shares(quantity, company));
	}
	
	public void calculateInvestment()
	{
		investment = 0;
		for(int i = 0; i <portfolio.size(); i++)
		{
			investment = investment + portfolio.get(i).getShareValue();
		}
		
	}
	
	public double getInvestment()
	{
		return investment;
	}
	
	public double getCashHolding()
	{
		return cashHolding;
	}
	
	public void calculateNetWorth()
	{
		calculateInvestment();
		netWorth = getInvestment() + getCashHolding();
	}
	
	public double getNetWorth()
	{
		return Math.round(netWorth);
	}
}