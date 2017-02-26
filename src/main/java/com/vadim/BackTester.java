package com.vadim;

import java.util.List;

public class BackTester {
	
	static List <ProposedTransaction> proposedTransactions;
	
	public static void backTest(int dayNumber)
	{
		for (int i = 0; i <= dayNumber; i++)
		{
			/*
			// Calculate profit, biggest drowdown, etc at the end of a day
			// Add position according to what is written in rules file
			proposedTransactions = Rules.whatDoIDoToday();
			*/
			// proposedTransactions = Analyser.suggestTransactions(HistoryAnalyser.days[i]);
			StatsCollector.init();
	        for ( int daysBeforeExpiry = 1; daysBeforeExpiry <= StatsCollector.getMaxNumberOfDaysBeforeExpiry(); daysBeforeExpiry++)
	        {
	        	StatsCollector.updateStats(daysBeforeExpiry);
	        	//StatsCollector.displayExtremeValues(daysBeforeExpiry);
	        	//System.out.println("Day " + i);
	        }
	        
			proposedTransactions = Analyser.suggestTransactions();
			for (int j=0; j<proposedTransactions.size() ; j++)
				proposedTransactions.get(j).execute();

		}
		
		// Rule: when price > weekly MA50, buy 10 after every down day
		// For each 
		
		// Save result containing profit, biggest drowdown in array which will later be sorted to show best system
		
		
		
	}

}