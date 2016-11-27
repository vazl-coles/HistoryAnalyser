package com.vadim;

public class Predictor {
	// Go through history and make prediction what the value will be at expiry
	
	public static void makePrediction(int dayNumber)
	{
		// Check for tomorrow
		float closeMinus2Percent = HistoryAnalyser.days.get(dayNumber).getClose() * 98 /100;
		int c = Math.round(closeMinus2Percent); 
		
		// Checking probability of a price to be below c
		float percentageFromclose;
		if (c < HistoryAnalyser.days.get(dayNumber).getClose())
		{
			percentageFromclose = c/HistoryAnalyser.days.get(dayNumber).getClose();
			System.out.println("Below close " + percentageFromclose);
		}
		
		/*
		for (int i = 0; i < 5; i++)
		{
			float closeMinus2Percent = HistoryAnalyser.days.get(i).getClose() * 98 /100;
			int c = Math.round(closeMinus2Percent); 
		}
		*/
		
	}

}
