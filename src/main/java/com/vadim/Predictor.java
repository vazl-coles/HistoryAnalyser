package com.vadim;

import java.util.ArrayList;

class PredictorOutput
{
	HistoricalTradeResult historicalTradeResult;
	float strikePrice;
	int numberOfDaysBeforeExpiry;
	
	public PredictorOutput(float strikePrice, int  numberOfDaysBeforeExpiry, HistoricalTradeResult historicalTradeResult)
	{
		this.strikePrice = strikePrice;
		this.numberOfDaysBeforeExpiry = numberOfDaysBeforeExpiry;
		this.historicalTradeResult = historicalTradeResult;
	}
}

public class Predictor {
	static ArrayList<PredictorOutput> predictorOutputArrayList = new ArrayList<PredictorOutput>(); 
	// Go through history and check how profitable is the trade
	public static void performHistoricalProfitabilityAnalysis(int dayNumber)
	{
		float startOfRange = HistoryAnalyser.days.get(dayNumber).getClose() * 99 / 100;
		int numberOfDaysBeforeExpiry;
		float vix;
		int i=0;
		
		startOfRange = Math.round(startOfRange);
		float strikePrice = startOfRange; // strike price will typically look like 220, 220.5, 221, etc
		while (strikePrice < HistoryAnalyser.days.get(dayNumber).getClose() * 101 /100 )
		{
			numberOfDaysBeforeExpiry = 1;
			while (numberOfDaysBeforeExpiry < 5)
			{
				//HistoricalTradeResult result = calculate(dayNumber, HistoryAnalyser.days.get(dayNumber).getClose(), strikePrice, numberOfDaysBeforeExpiry);
				//System.out.println("Average profit=" + result.getAverageProfit());

				//numberOfDaysBeforeExpiry++;
				
				//System.out.println("Strike price= " + strikePrice + " Number of Days before expiry=" + numberOfDaysBeforeExpiry);
				//System.out.println("Day Number= " + dayNumber + " Close price=" + HistoryAnalyser.days.get(dayNumber).getClose());
				vix = HistoryAnalyser.days.get(dayNumber).getVIX();
				predictorOutputArrayList.add(
						new PredictorOutput( strikePrice, numberOfDaysBeforeExpiry++,
								calculate(dayNumber, 
										HistoryAnalyser.days.get(dayNumber).getClose(), 
										strikePrice, 
										numberOfDaysBeforeExpiry,
										vix))
						);
																							
			}
			strikePrice = strikePrice + (float)0.5;
			
		}
		
		int last = predictorOutputArrayList.size();
		for (i=0; i < last; i++)
		{
			System.out.println("Strike price=" + predictorOutputArrayList.get(i).strikePrice +
					           " Number of days before expiry="+ predictorOutputArrayList.get(i).numberOfDaysBeforeExpiry+
					           " Profit=" + predictorOutputArrayList.get(i).historicalTradeResult.getAverageProfit());
			
		}
		
	}
	
	private static HistoricalTradeResult calculate(int dayNumber, float currentPrice, float strikePrice, int numberOfDaysBeforeExpiry, float vix )
	{
		HistoricalTradeResult result = new HistoricalTradeResult();
		float straddlePrice, priceAtExpiry, roughStrikePrice;
		// Scan through history and populate the result object
		//System.out.println("Scan through history and populate the result object ");
		for (int i = 0; i <= dayNumber; i++)
		{
			straddlePrice = StraddlePriceCalculator.getStraddlePrice(currentPrice, strikePrice, numberOfDaysBeforeExpiry, vix);
			if ((i+numberOfDaysBeforeExpiry) < dayNumber)
			{
				//System.out.println("Calculate profit at "+ HistoryAnalyser.days.get(i).getStringDate());
				priceAtExpiry = HistoryAnalyser.days.get(i+numberOfDaysBeforeExpiry).getClose();
				// strikePrice should be calculated relative to the time in history. At the moment it contains todays strike price
				roughStrikePrice = strikePrice/currentPrice*HistoryAnalyser.days.get(i).getClose();
				/*
				if ( strikePrice < currentPrice*0.995)
					System.out.println("Historical close=" + HistoryAnalyser.days.get(i).getClose() + 
									" Rough strike price=" + roughStrikePrice);
				*/
				/*
				if (Math.abs(HistoryAnalyser.days.get(i).getClose()-priceAtExpiry) > 5)
				{
					System.out.println("Powerful move after "+ HistoryAnalyser.days.get(i).getStringDate());
					System.out.println("Prices: "+ HistoryAnalyser.days.get(i).getClose() + 
							";" + priceAtExpiry + 
							";" + result.getTotalProfit() + 
							";" + roughStrikePrice);
					System.out.println("Straddle price:"+straddlePrice);
				}
				else if (Math.abs(HistoryAnalyser.days.get(i).getClose()-priceAtExpiry) < 1)
				{
					System.out.println("Weak move after "+ HistoryAnalyser.days.get(i).getStringDate());
					System.out.println("Prices: "+ HistoryAnalyser.days.get(i).getClose() + 
							";" + priceAtExpiry + 
							";" + result.getTotalProfit() + 
							";" + roughStrikePrice);
					System.out.println("Straddle price:"+straddlePrice);
				}
				else
				{
				}
				*/

				result.calculateProfit(straddlePrice, priceAtExpiry, roughStrikePrice);
				
				/*
				if (Math.abs(HistoryAnalyser.days.get(i).getClose()-priceAtExpiry) < 1)
				{
					System.out.println("Profit: "+ HistoryAnalyser.days.get(i).getClose() + ";" + priceAtExpiry + ";" + result.getTotalProfit());
				}
				*/
					
			}
		}
		// System.out.println("calculate Average profit=" + result.getAverageProfit());
		return result;
	}
	
	
	// Go through history and make prediction what the value will be at expiry
	
	public static void makePrediction(int dayNumber)
	{
		// Check for tomorrow
		float closeMinus2Percent = HistoryAnalyser.days.get(dayNumber).getClose() * 98 /100;
		float closePlus2Percent = HistoryAnalyser.days.get(dayNumber).getClose() * 102 /100;
		int first = Math.round(closeMinus2Percent); 
		int last = Math.round(closePlus2Percent);
		boolean risingMarket=true;
		
		if (dayNumber > 0)
		{
			risingMarket = (HistoryAnalyser.days.get(dayNumber).getWeeklyMA() > HistoryAnalyser.days.get(dayNumber-1).getWeeklyMA());
			
			/*
			System.out.println("Rising market = " + risingMarket + "  date =" + HistoryAnalyser.days.get(dayNumber).getStringDate());
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
		}
		// System.out.println("Threshold =" +  first);
		
		for (int i = first; i <= last; i++)
		{
			calculateProbabilityBelowThreshold(i, dayNumber,  risingMarket);
		}
		
		for (int i = first; i <= last; i++)
		{
			calculateProbabilityAboveThreshold(i, dayNumber, risingMarket);
		}


		
		/*
		for (int i = 0; i < 5; i++)
		{
			float closeMinus2Percent = HistoryAnalyser.days.get(i).getClose() * 98 /100;
			int c = Math.round(closeMinus2Percent); 
		}
		*/
		
	}
	
	private static void calculateProbabilityBelowThreshold(int c, int dayNumber, boolean risingMarket)
	{
		// Checking probability of a price to be below c
		float percentageFromClose=0;
		if (c < HistoryAnalyser.days.get(dayNumber).getClose())
		{
			System.out.println("Below close " + c);
		}
		else
		{
			System.out.println("Above close " + c);
		}
		percentageFromClose = c/HistoryAnalyser.days.get(dayNumber).getClose();
		
		// Scan through history and check if tomorrows close is below percentageFromclose
		int totalAbove=0;
		int totalBelow=0;
		for (int i = 0; i < dayNumber; i++)
		{
			if (percentageFromClose < 0.9 && percentageFromClose > 1.1)
			{
				System.out.println("Invalid percentage "+ percentageFromClose);
				return;
			}
			else
			{
				if (HistoryAnalyser.days.get(i).getWeeklyMA() == 0)
				{
					/*
					System.out.println("ma50 is 0,Ignoring date = " + HistoryAnalyser.days.get(i).getStringDate());
        			try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					*/
					continue;
				}
				
				if (risingMarket)
				{
					// Do not look at this day as this was in falling market
					if (HistoryAnalyser.days.get(i+1).getWeeklyMA() < HistoryAnalyser.days.get(i).getWeeklyMA())
					{
						continue;
					}
				}
				else
				{
					// Do not look at this day as this was in rising market
					if (HistoryAnalyser.days.get(i+1).getWeeklyMA() > HistoryAnalyser.days.get(i).getWeeklyMA())
						continue;
				}
				
				//System.out.println("Comparing " + HistoryAnalyser.days.get(i+1).getClose() + " with " + HistoryAnalyser.days.get(i).getClose()*percentageFromClose);
				if (HistoryAnalyser.days.get(i+1).getClose() > HistoryAnalyser.days.get(i).getClose()*percentageFromClose)
				{
					totalAbove++;
				}
				else
				{
					//System.out.println("Found");
					totalBelow++;
				}
			}
			
		}
		
		System.out.println("Above = " + totalAbove + " Below = " + totalBelow);
		System.out.println("Probability = " + (float)totalBelow/(totalAbove+totalBelow)*100);
		
	}
	
	private static void calculateProbabilityAboveThreshold(int c, int dayNumber, boolean risingMarket)
	{
		// Checking probability of a price to be below c
		float percentageFromClose=0;
		if (c < HistoryAnalyser.days.get(dayNumber).getClose())
		{
			System.out.println("Below close " + c);
		}
		else
		{
			System.out.println("Above close " + c);
		}
		percentageFromClose = c/HistoryAnalyser.days.get(dayNumber).getClose();
		
		// Scan through history and check if tomorrows close is below percentageFromclose
		int totalAbove=0;
		int totalBelow=0;
		for (int i = 0; i < dayNumber; i++)
		{
			if (percentageFromClose < 0.9 && percentageFromClose > 1.1)
			{
				System.out.println("Invalid percentage "+ percentageFromClose);
				return;
			}
			else
			{
				if (risingMarket)
				{
					// Do not look at this day as this was in falling market
					if (HistoryAnalyser.days.get(i+1).getWeeklyMA() < HistoryAnalyser.days.get(i).getWeeklyMA())
						continue;
				}
				else
				{
					// Do not look at this day as this was in rising market
					if (HistoryAnalyser.days.get(i+1).getWeeklyMA() > HistoryAnalyser.days.get(i).getWeeklyMA())
						continue;
				}
				//System.out.println("Comparing " + HistoryAnalyser.days.get(i+1).getClose() + " with " + HistoryAnalyser.days.get(i).getClose()*percentageFromClose);
				if (HistoryAnalyser.days.get(i+1).getClose() > HistoryAnalyser.days.get(i).getClose()*percentageFromClose)
				{
					totalAbove++;
				}
				else
				{
					//System.out.println("Found");
					totalBelow++;
				}
			}
			
		}
		
		System.out.println("Above = " + totalAbove + " Below = " + totalBelow);
		System.out.println("Probability = " + (float)totalAbove/(totalAbove+totalBelow)*100);
		
	}

}
