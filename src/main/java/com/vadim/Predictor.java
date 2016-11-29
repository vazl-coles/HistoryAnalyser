package com.vadim;

public class Predictor {
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
			risingMarket = (HistoryAnalyser.days.get(dayNumber).getWeeklyMA50() > HistoryAnalyser.days.get(dayNumber-1).getWeeklyMA50());
			
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
				if (HistoryAnalyser.days.get(i).getWeeklyMA50() == 0)
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
					if (HistoryAnalyser.days.get(i+1).getWeeklyMA50() < HistoryAnalyser.days.get(i).getWeeklyMA50())
					{
						continue;
					}
				}
				else
				{
					// Do not look at this day as this was in rising market
					if (HistoryAnalyser.days.get(i+1).getWeeklyMA50() > HistoryAnalyser.days.get(i).getWeeklyMA50())
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
					if (HistoryAnalyser.days.get(i+1).getWeeklyMA50() < HistoryAnalyser.days.get(i).getWeeklyMA50())
						continue;
				}
				else
				{
					// Do not look at this day as this was in rising market
					if (HistoryAnalyser.days.get(i+1).getWeeklyMA50() > HistoryAnalyser.days.get(i).getWeeklyMA50())
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
