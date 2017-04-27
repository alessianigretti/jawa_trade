import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class Order.
 */
public class Order 
{
	// fields for filling table up with data
	private final SimpleStringProperty companyColumn;
    private final SimpleIntegerProperty quantityColumn;
    private final SimpleStringProperty orderTypeColumn;
    private final SimpleDoubleProperty priceColumn;
    private final SimpleStringProperty riskColumn;
    private final SimpleStringProperty clientColumn;
    
	private Company company;
	private double openingPrice, closingPrice;
	private int quantity;
	private String clientName;
	private boolean orderType; //true == buy; false == sell;
	
    /**
     * Instantiates a new order.
     *
     * @param company the company
     * @param quantity the quantity
     * @param orderType the order type
     * @param price the price
     * @param risk the risk
     * @param client the client
     */
    public Order(Company company, int quantity, boolean orderType, double price, String risk, Client client)
    {
    	this.company = company;
		this.openingPrice = company.getCurrentShareValue();
		this.quantity = quantity;
		this.clientName = client.getName();
		this.orderType = orderType;
    	
    	companyColumn = new SimpleStringProperty(company.getName());
        quantityColumn = new SimpleIntegerProperty(quantity);
        if (orderType)
		{
        	orderTypeColumn = new SimpleStringProperty("Buy");
		} else {
			orderTypeColumn = new SimpleStringProperty("Sell");
		}
        priceColumn = new SimpleDoubleProperty(quantity * company.getCurrentShareValue());
        riskColumn = new SimpleStringProperty(company.getShareType());
        clientColumn = new SimpleStringProperty(client.getName());
    }
    
    /**
     * Gets the risk.
     *
     * @return the risk
     */
    public String getRiskColumn()
    {
    	return riskColumn.get();
    }
    
    /**
     * Sets the risk.
     *
     * @param risk the new risk
     */
    public void setRiskColumn(String risk)
    {
    	this.riskColumn.set(risk);
    }
    
    /**
     * Gets the quantity.
     *
     * @return the quantity
     */
    public int getQuantityColumn()
    {
        return quantityColumn.get();
    }

    /**
     * Sets the quantity.
     *
     * @param quantity the new quantity
     */
    public void setQuantityColumn(int quantity)
    {
        this.quantityColumn.set(quantity);
    }

    /**
     * Gets the company.
     *
     * @return the company
     */
    public String getCompanyColumn()
    {
        return companyColumn.get();
    }

    /**
     * Sets the company.
     *
     * @param company the new company
     */
    public void setCompanyColumn(String company)
    {
        this.companyColumn.set(company);
    }

    /**
     * Gets the client.
     *
     * @return the client
     */
    public String getClientColumn()
    {
        return clientColumn.get();
    }

    /**
     * Sets the client.
     *
     * @param client the new client
     */
    public void setClientColumn(String client)
    {
        this.clientColumn.set(client);
    }
    
    /**
     * Gets the price.
     *
     * @return the price
     */
    public double getPriceColumn()
    {
    	return priceColumn.get();
    }
    
    /**
     * Sets the price.
     *
     * @param price the new price
     */
    public void setPriceColumn(double price)
    {
    	this.priceColumn.set(price);
    }
    
    /**
     * Gets the order type.
     *
     * @return the order type
     */
    public String getOrderTypeColumn()
    {
    	return orderTypeColumn.get();
    }
    
    /**
     * Sets the order type.
     *
     * @param orderType the new order type
     */
    public void setOrderTypeColumn(String orderType)
    {
    	this.orderTypeColumn.set(orderType);
    }
	
	/**
	 * Gets the current share value.
	 *
	 * @return the current share value
	 */
	public double getCurrentShareValue()
	{
		return company.getCurrentShareValue();
	}
	
	/**
	 * Gets the opening price.
	 *
	 * @return the opening price
	 */
	public double getOpeningPrice()
	{
		return openingPrice;
	}
	
	/**
	 * Sets the closing price.
	 */
	public void setClosingPrice()
	{
		closingPrice = company.getCurrentShareValue();
	}
	
	/**
	 * Gets the closing price.
	 *
	 * @return the closing price
	 */
	public double getClosingPrice()
	{
		return closingPrice;
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
	 * Update quantity.
	 *
	 * @param extra the extra
	 */
	public void updateQuantity(int extra)
	{
		quantity = quantity + extra;
	}
	
	/**
	 * Gets the quanitity.
	 *
	 * @return the quanitity
	 */
	public int getQuantity()
	{
		return quantity;
	}
	
	/**
	 * Gets the client name.
	 *
	 * @return the client name
	 */
	public String getClientName()
	{
		return clientName;
	}
	
	/**
	 * Gets the company.
	 *
	 * @return the company
	 */
	public Company getCompany()
	{
		return company;
	}
	
	/**
	 * Gets the order type.
	 *
	 * @return the order type
	 */
	public boolean getOrderType()
	{
		return orderType;
	}
	
}
