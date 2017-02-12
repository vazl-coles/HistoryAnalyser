package com.vadim;

public class Option extends Contract {
	String putOrCall;
	String expiryDate;
	float strikePrice;
	float commission=(float)1.1;
	
	Option(String putOrCall, String name,  int quantity, float price, String expiryDate, float strikePrice)
	{
		super(name,  quantity, price);
		this.expiryDate = expiryDate;
		this.strikePrice = strikePrice;
		this.putOrCall = putOrCall;
		
	}
	
	String getExpiryDate()
	{
		return expiryDate;
	}
	
	void setExpiryDate(String expiryDate)
	{
		this.expiryDate = expiryDate;
	}
	
	String getPutOrCall()
	{
		return putOrCall;
	}
	
	void setPutOrCall(String putOrCall)
	{
		this.putOrCall = putOrCall;
	}
	
	float getStrikePrice()
	{
		return strikePrice;
	}
	
	void setStrikePrice(float strikePrice)
	{
		this.strikePrice = strikePrice;
	}
	
	@Override
	float getCommission()
	{
		return commission;
	}
	
	@Override
	float getSlippage(float vix)
	{
		return (float)2.0;
	}
	
}
