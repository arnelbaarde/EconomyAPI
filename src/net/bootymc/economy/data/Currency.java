package net.bootymc.economy.data;

public class Currency 
{
	String currencyName = null;
	
	public Currency(String currencyName)
	{
		this.currencyName = currencyName;
	}
	
	public String getCurrencyName()
	{
		return currencyName;
	}
}
