package com.vadim;

public class HistoricalTradeResult {
	
	private float averageProfit=0;
	private float totalProfit=0;
	private float biggestDrowdown=0;
	private float successRate=0;
	
	private int numberOfTrades;
	private int numberOfSuccessfulTrades;
	private int numberOfFailures;
	
	public void calculateProfit(float straddlePrice, float priceAtExpiry, float strikePrice)
	{
		float profit = (straddlePrice - Math.abs(priceAtExpiry - strikePrice))*100;
		totalProfit = totalProfit + profit;
		numberOfTrades++;
		
		if (profit < 0)
		{
			if (Math.abs(profit) > biggestDrowdown)
				biggestDrowdown = Math.abs(profit);
			numberOfFailures++;
		}
		else
		{
			numberOfSuccessfulTrades++;
		}
		
		averageProfit = totalProfit/numberOfTrades;
		successRate = Math.round((float)numberOfSuccessfulTrades/(float)numberOfTrades*100);
	}
	
	public float getAverageProfit()
	{
		return this.averageProfit;
	}
	
	public float getTotalProfit()
	{
		return this.totalProfit;
	}
	
	public float getBiggestDrowdown()
	{
		return this.biggestDrowdown;
	}

	public float getSuccessRate()
	{
		return this.successRate;
	}
	
	public float getNumberOfTrades()
	{
		return this.numberOfTrades;
	}
	
	public float getNumberOfSuccessfulTrades()
	{
		return this.numberOfSuccessfulTrades;
	}

	public float getNumberOfFailures()
	{
		return this.numberOfFailures;
	}
	
}
