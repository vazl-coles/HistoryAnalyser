package com.vadim;

class StatsPerDay {
	
}

class statsEntry {
	
}

public class StatsCollector {
	
	static StatsPerDay[] daysBeforeWxpiryArray = new StatsPerDay[20]; // Array 0 to 19 representing 1 - 20 day before expiry
	
	StatsCollector()
	{
		
	}
	
	public static void updateStats(int daysBeforeExpiry)
	{
		float priceDiff;
		for (int i = 0; i < HistoryAnalyser.days.size()- daysBeforeExpiry -1; i++)
		{
			priceDiff = HistoryAnalyser.days.get(i+daysBeforeExpiry).getClose() - HistoryAnalyser.days.get(i).getClose();
			//System.out.println("Price Difference " + priceDiff + " " + HistoryAnalyser.days.get(i+daysBeforeExpiry).getClose() + " " + HistoryAnalyser.days.get(i).getClose());
			
		}
		
	}

}
