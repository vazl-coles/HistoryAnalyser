package com.vadim;

import java.util.ArrayList;
import java.util.List;

class StatsPerDay {
	
	StatsEntry[]statsEntries = new StatsEntry[50];
	int totalForAllEntries=0; // Used for calculation of probabilities
	
	StatsPerDay()
	{
		for (int i = 0; i < 50; i++)
		{
			statsEntries[i] = new StatsEntry();
		}
	}
	
	void updateStatsEntry(float roundedPercentageDiff)
	{
		int arraySubscript = calculateSubscriptFromPercentage(roundedPercentageDiff);
		//System.out.println("Updating "+ arraySubscript );
		//System.out.println(" Current total " + statsEntries[arraySubscript].getTotal());
		//System.out.println("Subscript "+ arraySubscript + " of " + roundedPercentageDiff);
		statsEntries[arraySubscript].incrementTotal(); 
		totalForAllEntries++;
		
	}
	
	static int calculateSubscriptFromPercentage(float roundedPercentageDiff)
	{
		int subscript = (int)Math.round(roundedPercentageDiff) + 25;
		
		//if (subscript <= 15 || subscript >= 40)
			//System.out.println(" Unusual result " + roundedPercentageDiff + " " + subscript);
		
		
		if (subscript <= 0  )
			return 0;
		else if (subscript >= 50)
			return 49;
		else
			return subscript;
	}
	
	static float calculatePercentageFromSubscript(int subscript)
	{
		float roundedPercentageDiff = subscript - 25;
		return roundedPercentageDiff;

	}
	
	int getTotalForAllEntries()
	{
		return totalForAllEntries;
	}
	
	int getNumberOfEntriesForSpecificExpiry(int subscript)
	{
		return statsEntries[subscript].total;
	}
	
	void displayLowest()
	{
		int i;
		for (i = 0; i <= 49; i++)
		{
			if (statsEntries[i].getTotal() > 0)
				break;
		}
		if (i >= 50)
			System.out.println("Can't find Lowest ");
		else
			System.out.println("Lowest = " + i + " Total = " + statsEntries[i].getTotal());
	}
	
	int getLowest()
	{
		int i;
		for (i = 0; i <= 49; i++)
		{
			if (statsEntries[i].getTotal() > 0)
				break;
		}
		return i-25;

	}
	
	void displayHighest()
	{
		int i;
		for (i = 49; i >= 0; i--)
		{
			if (statsEntries[i].getTotal() > 0)
				break;
		}
		if (i >= 0)
			System.out.println("Highest = " + i + " Total = " + statsEntries[i].getTotal());
		else
			System.out.println("Can't find Highest ");
	}
	
	int getHighest()
	{
		int i;
		for (i = 49; i >= 0; i--)
		{
			if (statsEntries[i].getTotal() > 0)
				break;
		}
		return i-25;
	}
	
	int getMod()
	{
		int i;
		int mod=0;
		int modSubscript=0;
		for (i = 0; i <= 49; i++)
		{
			//System.out.println("Test "+ statsEntries[i].getTotal());
			if (statsEntries[i].getTotal() > mod )
			{
				//System.out.println("Mod "+ statsEntries[i].getTotal());
				mod = statsEntries[i].getTotal();
				modSubscript = i;
				
			}
		}
		return modSubscript;

	}
	
	void displayAll()
	{
		int i;
		for (i = 0; i < 50; i++)
		{
			System.out.println("Checking = " + statsEntries[i].getTotal());
		}
		
	}
	
}

class StatsEntry {
	int total=0;
	
	StatsEntry()
	{
		total = 0;
	}
	
	void incrementTotal()
	{
		//System.out.println("Current total is "+ total);
		total++;
		
	}
	
	int getTotal()
	{
		return total;
	}
	
}

public class StatsCollector {
	
	static int maxNumberOfDaysBeforeExpiry;
	
	static StatsPerDay[] daysBeforeExpiryArray; // Array 0 to 19 representing 1 - 20 day before expiry
	
	static void init()
	{
		maxNumberOfDaysBeforeExpiry= 300;
		daysBeforeExpiryArray = new StatsPerDay[maxNumberOfDaysBeforeExpiry];
		for (int i = 1; i <= maxNumberOfDaysBeforeExpiry ; i++)
		{
			daysBeforeExpiryArray[i-1] = new StatsPerDay();
			//daysBeforeExpiryArray[i-1].displayAll();
		}
		
	}
	
	public static void updateStats(int daysBeforeExpiry)
	{
		float priceDiff;
		float roundedPercentageDiff;
		
		for (int i = 0; i < History.days.size()- daysBeforeExpiry -1; i++)
		{
			if (PropertyHelper.getProperty("statsAboveBelowMA200").contains("Y") )
			{
				if (History.days.get(History.days.size()-1).getClose() > History.days.get(History.days.size()-1).getWeeklyMA())
				{
					// todays close is above MA
					if (History.days.get(i).getClose() > History.days.get(History.days.size()-1).getWeeklyMA())
					{
						priceDiff = History.days.get(i+daysBeforeExpiry).getClose() - History.days.get(i).getClose();
						roundedPercentageDiff = Math.round(priceDiff/History.days.get(i).getClose()*100);
						daysBeforeExpiryArray[daysBeforeExpiry-1].updateStatsEntry(roundedPercentageDiff);
					}
					else
					{
						// close is below MA
					}
				}
				else
				{
					// todays is below MA
					if (History.days.get(i).getClose() < History.days.get(History.days.size()-1).getWeeklyMA())
					{
						priceDiff = History.days.get(i+daysBeforeExpiry).getClose() - History.days.get(i).getClose();
						roundedPercentageDiff = Math.round(priceDiff/History.days.get(i).getClose()*100);
						daysBeforeExpiryArray[daysBeforeExpiry-1].updateStatsEntry(roundedPercentageDiff);
					}
					else
					{
						// close is above MA
					}
					
				}
			}
			else
			{
				priceDiff = History.days.get(i+daysBeforeExpiry).getClose() - History.days.get(i).getClose();
				//System.out.println("Price Difference " + priceDiff + " " + HistoryAnalyser.days.get(i+daysBeforeExpiry).getClose() + " " + HistoryAnalyser.days.get(i).getClose());
				roundedPercentageDiff = Math.round(priceDiff/History.days.get(i).getClose()*100);
				//if (Math.abs(priceDiff) > 8 && daysBeforeExpiry == 1)
					//System.out.println(HistoryAnalyser.days.get(i+daysBeforeExpiry).getStringDate() + "Price Difference " + priceDiff + " " + HistoryAnalyser.days.get(i+daysBeforeExpiry).getClose() + " " + HistoryAnalyser.days.get(i).getClose());
				
				//System.out.println("Price Difference " + roundedPercentageDiff + " " + HistoryAnalyser.days.get(i+daysBeforeExpiry).getClose() + " " + HistoryAnalyser.days.get(i).getClose());
				daysBeforeExpiryArray[daysBeforeExpiry-1].updateStatsEntry(roundedPercentageDiff);
			}
			
		}
		/*
		if (daysBeforeExpiry == 1)
		{
			System.out.println("Number of days befory expiry" + daysBeforeExpiry);
		
			daysBeforeExpiryArray[daysBeforeExpiry-1].displayLowest();
		}
		*/
		
	}
	
	public static void updateStats(int daysBeforeExpiry, String flag)
	{
		float priceDiff;
		float roundedPercentageDiff;
		
		for (int i = 0; i < History.days.size()- daysBeforeExpiry -1; i++)
		{
			if (flag.equals("MA"))
			{
				//... We only consider days which are also above or below MA200, similar to today
				priceDiff = History.days.get(i+daysBeforeExpiry).getClose() - History.days.get(i).getClose();
				roundedPercentageDiff = Math.round(priceDiff/History.days.get(i).getClose()*100);
				daysBeforeExpiryArray[daysBeforeExpiry-1].updateStatsEntry(roundedPercentageDiff);
			}
		}
		
	}
	
	// This method exists for test purposes only
	public static void updateStatsTest(int daysBeforeExpiry)
	{
		float priceDiff;
		float roundedPercentageDiff;
		
		float i = -5*daysBeforeExpiry;
		while (i < 5*daysBeforeExpiry)
		{

			//System.out.println("Price Difference " + priceDiff + " " + HistoryAnalyser.days.get(i+daysBeforeExpiry).getClose() + " " + HistoryAnalyser.days.get(i).getClose());
			roundedPercentageDiff = i;
			//System.out.println("Price Difference " + roundedPercentageDiff + " " + HistoryAnalyser.days.get(i+daysBeforeExpiry).getClose() + " " + HistoryAnalyser.days.get(i).getClose());
			daysBeforeExpiryArray[daysBeforeExpiry-1].updateStatsEntry(roundedPercentageDiff);
			i = (float) (i + 0.1);
			//System.out.println("Percentage "+i);
			
		}
		//daysBeforeExpiryArray[daysBeforeExpiry-1].displayAll();
		/*
		if (daysBeforeExpiry == 1)
		{
			System.out.println("Number of days befory expiry" + daysBeforeExpiry);
		
			daysBeforeExpiryArray[daysBeforeExpiry-1].displayLowest();
		}
		*/
		
	}
	
	public static void displayExtremeValues(int daysBeforeExpiry)
	{
		daysBeforeExpiryArray[daysBeforeExpiry-1].displayLowest();
		daysBeforeExpiryArray[daysBeforeExpiry-1].displayHighest();
		
	}
	
	public static int getLowest(int daysBeforeExpiry)
	{
		return daysBeforeExpiryArray[daysBeforeExpiry-1].getLowest();
		//daysBeforeExpiryArray[daysBeforeExpiry-1].displayHighest();
		
	}
	
	public static int getHighest(int daysBeforeExpiry)
	{
		return daysBeforeExpiryArray[daysBeforeExpiry-1].getHighest();
		//daysBeforeExpiryArray[daysBeforeExpiry-1].displayHighest();
		
	}
	
	public static int getMod(int daysBeforeExpiry)
	{
		return daysBeforeExpiryArray[daysBeforeExpiry-1].getMod();
		//daysBeforeExpiryArray[daysBeforeExpiry-1].displayHighest();
		
	}
	
	public static int getTotalForAllEntries(int daysBeforeExpiry)
	{
		return daysBeforeExpiryArray[daysBeforeExpiry-1].getTotalForAllEntries();
		//daysBeforeExpiryArray[daysBeforeExpiry-1].displayHighest();
		
	}
	
	public static float getProbabilityOfPercentageChange(int daysBeforeExpiry, float percentageChange)
	{
		// find the subscript from percentage, e.g. 2% is subscript 27
		int subscript = daysBeforeExpiryArray[daysBeforeExpiry-1].calculateSubscriptFromPercentage(percentageChange);
		
		//System.out.println("Number of entries " + daysBeforeExpiryArray[daysBeforeExpiry-1].getNumberOfEntriesForSpecificExpiry(subscript));
		//System.out.println("Total of all entries " + daysBeforeExpiryArray[daysBeforeExpiry-1].getTotalForAllEntries());
		return (float)daysBeforeExpiryArray[daysBeforeExpiry-1].getNumberOfEntriesForSpecificExpiry(subscript) / (float) daysBeforeExpiryArray[daysBeforeExpiry-1].getTotalForAllEntries();
	}
	
	// this is used mainly for testing purpose
	public static float calculatePercentageFromSubscript(int daysBeforeExpiry, int subscript)
	{
		return daysBeforeExpiryArray[daysBeforeExpiry-1].calculatePercentageFromSubscript(subscript);
		
	}
	
	public static int getMaxNumberOfDaysBeforeExpiry()
	{
		return maxNumberOfDaysBeforeExpiry;
	}

}
