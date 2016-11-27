package com.vadim;

public class MAcalculator {
	
	public static float calculateMA(String typeOfMA, String TypeOfIndicator, int numOfEntries, int lastEntry)
	{
		if (typeOfMA.equals("Weekly"))
		{
			if (lastEntry < numOfEntries)
			{
				return 0;
			}
			else
			{
				//System.out.println("Calculate MA for week = " + lastEntry);
				return calculateAverage(typeOfMA, TypeOfIndicator, numOfEntries, lastEntry);
			}
			
		}
		return 0;
		
	}
	
	private static float calculateAverage(String typeOfMA, String TypeOfIndicator, int numOfEntries, int lastEntry)
	{
		int i=0;
		float total=0;
		
		if (typeOfMA.equals("Weekly"))
		{
			i = HistoryAnalyser.days.size() - 1;// Last entry in the list
			//i = i -2; // Last entry still does not have week set 
			// Find entry in the list which has week number set to lastEntry
			while (i > 0)
			{
				if (HistoryAnalyser.days.get(i).getWeekNumber() == lastEntry)
					break;
				i = i -1;
			}
			
			int lastWeek = HistoryAnalyser.days.get(i).getWeekNumber();
			int firstWeek = lastWeek - numOfEntries+1;
			int alreadyProcessedWeek = HistoryAnalyser.days.get(i).getWeekNumber()+1;
			//System.out.println("Last week=" + lastWeek + " First week=" + firstWeek + " Last entry= " + lastEntry + " Size= " + HistoryAnalyser.days.size() );
			while (alreadyProcessedWeek > firstWeek && i > 0)
			{
				if (HistoryAnalyser.days.get(i).getWeekNumber() == alreadyProcessedWeek)
				{
					
				}
				else
				{
					total = total + HistoryAnalyser.days.get(i).getClose();
					//if (lastWeek > 825)
						//System.out.println("total = " + total + " Close = " + HistoryAnalyser.getClose(i) + " Week= " + HistoryAnalyser.days.get(i).getWeekNumber());
					alreadyProcessedWeek = HistoryAnalyser.days.get(i).getWeekNumber();
				}
				i = i - 1;
			}
		}
		return total/numOfEntries;
		
	}

}
