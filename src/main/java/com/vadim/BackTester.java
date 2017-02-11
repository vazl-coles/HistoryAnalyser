package com.vadim;

public class BackTester {
	
	static ProposedTransaction[] proposedTransactions;
	
	public static void backTest(int dayNumber)
	{
		for (int i = 0; i <= dayNumber; i++)
		{
			// Calculate profit, biggest drowdown, etc at the end of a day
			// Add position according to what is written in rules file
			proposedTransactions = Rules.whatDoIDoToday();
			for (int j=0; j<proposedTransactions.length ; j++)
			proposedTransactions[j].execute();

		}
		
		// Rule: when price > weekly MA50, buy 10 after every down day
		// For each 
		
		// Save result containing profit, biggest drowdown in array which will later be sorted to show best system
		
		
		
	}

}