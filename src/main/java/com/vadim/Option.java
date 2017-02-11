package com.vadim;

public class Option extends Contract {
	String expiryDate;
	float strikePrice;
	
	Option(String name,  int quantity, float price, String expiryDate, float strikePrice)
	{
		super(name,  quantity, price);
		this.expiryDate = expiryDate;
		this.strikePrice = strikePrice;
		
	}
	
	String getExpiryDate()
	{
		return expiryDate;
	}
	
	void setExpiryDate(String expiryDate)
	{
		this.expiryDate = expiryDate;
	}
	
	float getStrikePrice()
	{
		return strikePrice;
	}
	
	void setStrikePrice(float strikePrice)
	{
		this.strikePrice = strikePrice;
	}
	
}
