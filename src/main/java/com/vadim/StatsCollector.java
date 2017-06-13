package com.vadim;

import java.util.ArrayList;
import java.util.List;
import com.vadim.priceaction.*;

class StatsPerDay {
	
	StatsEntry[]statsEntries = new StatsEntry[50];
	int totalForAllEntries=0; // Used for calculation of probabilities
	int modForAllEntries=0; // Used for selection of most accurate predictors
	int range=0; // Used to indicate the quality of prediction.  The range should not be wide.
	
	StatsPerDay()
	{
		totalForAllEntries=0;
		modForAllEntries=0;
		range=0;
		for (int i = 0; i < 50; i++)
		{
			statsEntries[i] = new StatsEntry();
		}
	}
	
	void updateStatsEntry(float roundedPercentageDiff)
	{
		int arraySubscript = calculateSubscriptFromPercentage(roundedPercentageDiff);
		/*
		System.out.println("Updating "+ arraySubscript );
		System.out.println(" Current total " + statsEntries[arraySubscript].getTotal());
		System.out.println("Subscript "+ arraySubscript + " of " + roundedPercentageDiff);
		*/
		statsEntries[arraySubscript].incrementTotal(); 
		if (statsEntries[arraySubscript].getTotal() > statsEntries[modForAllEntries].getTotal())
		{
			//System.out.println("modForAllEntries "+ modForAllEntries );
			modForAllEntries = arraySubscript; // This is a subscript of the array that has a highest total
		}
		totalForAllEntries++;
		//System.out.println("mod is at "+ modForAllEntries + " number is " + statsEntries[modForAllEntries].getTotal());
		//System.out.println("current total is at "+ arraySubscript + " number is " + statsEntries[arraySubscript].getTotal());
		
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
		/*
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
		*/
		return modForAllEntries;

	}
	
	int getModTotal()
	{

		return statsEntries[modForAllEntries].getTotal();

	}
	
	
	void setRange()
	{
		range = getHighest() - getLowest();


	}
	
	int getRange()
	{
		return range;

	}
	
	void displayAll()
	{
		int i;
		for (i = 0; i < 50; i++)
		{
			System.out.println("Checking = " + statsEntries[i].getTotal());
		}
		
	}
	
    void displayProbabilities()
    {
            int i;
            for (i = 0; i < 50; i++)
            {
                    if (statsEntries[i].getTotal() > 0)
                    {
                            System.out.println("Probability of " + calculatePercentageFromSubscript(i) + "% is "+ (float)statsEntries[i].getTotal()/ (float)totalForAllEntries * (float) 100 + "(subscript=" + i + ")");
                            //System.out.println("Total expiry=" + statsEntries[i].getTotal() + " for " + calculatePercentageFromSubscript(i));
                            //System.out.println("Total sample for all expiries=" + totalForAllEntries);
                    }
            }
            
            //System.out.println("displayProbabilities finished");

    }
    
    void displayProbabilitiesWithDetails()
    {
            int i;
            for (i = 0; i < 50; i++)
            {
                    if (statsEntries[i].getTotal() > 0)
                    {
                            System.out.println("Probability of " + calculatePercentageFromSubscript(i) + "% is "+ (float)statsEntries[i].getTotal()/ (float)totalForAllEntries * (float) 100);
                            System.out.println("Total " +  statsEntries[i].getTotal());
                            //System.out.println("Total expiry=" + statsEntries[i].getTotal() + " for " + calculatePercentageFromSubscript(i));
                            //System.out.println("Total sample for all expiries=" + totalForAllEntries);
                    }
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
	
	static int sampleSize = 0;
	
	static StatsPerDay[] daysBeforeExpiryArray; // Array 0 to 19 representing 1 - 20 day before expiry
	static int[] daysSelection; // Used to flag days to be used for calculating probabilities
	static int markType=0;
	
	static int numberOfDaysSinceMA50CrossStart=120;
	static int numberOfDaysSinceMA50CrossEnd=80;
	static int numberOfDaysSinceMA50CrossJump=10;
	
	
	static void init()
	{
		maxNumberOfDaysBeforeExpiry= 300;
		sampleSize = 0;
		daysBeforeExpiryArray = new StatsPerDay[maxNumberOfDaysBeforeExpiry];
		for (int i = 1; i <= maxNumberOfDaysBeforeExpiry ; i++)
		{
			daysBeforeExpiryArray[i-1] = new StatsPerDay();
			//daysBeforeExpiryArray[i-1].displayAll();
		}
		
		markDaysOfInterest(History.days.size()); // These are the days which are similar to the last day, e.g. have similar vix, above or below MA
	}
	
	static void initialise()
	{
		maxNumberOfDaysBeforeExpiry= 300;
		sampleSize = 0;
		daysBeforeExpiryArray = new StatsPerDay[maxNumberOfDaysBeforeExpiry];
		for (int i = 1; i <= maxNumberOfDaysBeforeExpiry ; i++)
		{
			daysBeforeExpiryArray[i-1] = new StatsPerDay();
			//daysBeforeExpiryArray[i-1].displayAll();
		}
		
		for (int i = 0; i < History.days.size()-1; i++)
		{
			daysSelection[i] = 0;
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
				/*
				if (History.days.get(i).getClose() > History.days.get(i).getWeeklyMA()
					&& History.days.get(i-1).getClose() < History.days.get(i-1).getWeeklyMA())
				{
					System.out.println("Moved from bear to bull on " + History.days.get(i-1).getStringDate());
				}
				
				if (History.days.get(i).getClose() < History.days.get(i).getWeeklyMA()
					&& History.days.get(i-1).getClose() > History.days.get(i-1).getWeeklyMA())
				{
					System.out.println("Moved from bull to bear on " + History.days.get(i-1).getStringDate());
				}
				*/

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
	
	public static void updateStatsForToday(int daysBeforeExpiry)
    {
           float priceDiff;
           float roundedPercentageDiff;
          
           // Go through all days in history and use the information to build stats
           for (int i = 0; i < History.days.size()- daysBeforeExpiry -1; i++)
           {
               if (isDayOfInterest(i))
               {
            	   priceDiff = History.days.get(i+daysBeforeExpiry).getClose() - History.days.get(i).getClose();
            	   roundedPercentageDiff = Math.round(priceDiff/History.days.get(i).getClose()*100);
            	   if (Math.abs(roundedPercentageDiff) > 5 && daysBeforeExpiry < 2)
            	   {
            		   System.out.println("Jump " + History.days.get(i+daysBeforeExpiry).getClose() + " " + History.days.get(i+daysBeforeExpiry).getStringDate() );
            		   System.out.println("From " + History.days.get(i).getClose() + " " + History.days.get(i).getStringDate() );
            	   }
            	   daysBeforeExpiryArray[daysBeforeExpiry-1].updateStatsEntry(roundedPercentageDiff);
               }
               
           }

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
		
		System.out.println("updateStatsTest " + daysBeforeExpiry);
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
	
	public static int getRange(int daysBeforeExpiry)
	{
		
		return daysBeforeExpiryArray[daysBeforeExpiry-1].getRange();
		//daysBeforeExpiryArray[daysBeforeExpiry-1].displayHighest();
		
	}
	
	public static void setRange(int daysBeforeExpiry)
	{
		
		daysBeforeExpiryArray[daysBeforeExpiry-1].setRange();
		
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
	
    public static void displayProbabilities(int daysBeforeExpiry)
    {
            daysBeforeExpiryArray[daysBeforeExpiry-1].displayProbabilities();
            /*
            if (daysBeforeExpiry == 100)
            {
            	daysBeforeExpiryArray[daysBeforeExpiry-1].displayProbabilitiesWithDetails();
            }
            */
    }
    
    public static void findMostLikelyDays()
    {
    	int mod=0;
    	
		for (int i = 1; i <= maxNumberOfDaysBeforeExpiry ; i++)
		{
			daysBeforeExpiryArray[i-1].setRange(); // Calculate the spread between predictions
			if (daysBeforeExpiryArray[i-1].getModTotal() > mod)
			{
				mod = daysBeforeExpiryArray[i-1].getModTotal(); // Find most interesting predictions
				//System.out.println("mod="+mod+ " total " + daysBeforeExpiryArray[i-1].getModTotal());
			}
			
			//daysBeforeExpiryArray[i-1].displayAll();
		}
		
		for (int i = 1; i <= maxNumberOfDaysBeforeExpiry ; i++)
		{
			if (daysBeforeExpiryArray[i-1].getModTotal() == 0) continue;
			if (markType == 0)
			{
				if (daysBeforeExpiryArray[i-1].getModTotal() >= mod -1  )
				{
					System.out.println("Probabilities for " + i + " days before expiry" );
					StatsCollector.displayProbabilities(i);
        	
					System.out.println("Total sample size " + StatsCollector.getTotalForAllEntries(i));
				}
			}
			else
			{
				if (daysBeforeExpiryArray[i-1].getModTotal() >= mod -15  )
				{
					System.out.println("Probabilities for " + i + " days before expiry" );
					StatsCollector.displayProbabilities(i);
        	
					System.out.println("Total sample size " + StatsCollector.getTotalForAllEntries(i));
				}
			}
		}
    }
    
    public static void findExpiryWithLimitedUpside()
    {
    	int upperValue=0;
    	int numOfDaysBeforeExpiry=0;
    	
    	System.out.println("Find expiry with limited upside");
		for (int i = 1; i <= maxNumberOfDaysBeforeExpiry ; i++)
		{
			if (upperValue == 0)
			{
				upperValue = daysBeforeExpiryArray[i-1].getHighest();
				System.out.println("Initial setting=" + upperValue);
				numOfDaysBeforeExpiry=i;
			}
			if (daysBeforeExpiryArray[i-1].getHighest() < upperValue)
			{
				upperValue = daysBeforeExpiryArray[i-1].getHighest(); // Find most interesting predictions
				numOfDaysBeforeExpiry=i;
				System.out.println("highest="+upperValue);
			}
			
			//daysBeforeExpiryArray[i-1].displayAll();
		}
		
		if (numOfDaysBeforeExpiry > 0)
			StatsCollector.displayProbabilities(numOfDaysBeforeExpiry);
		

    }
    
    public static void findMostSpreadOutDays()
    {
    	int range=0;
    	
		for (int i = 1; i <= maxNumberOfDaysBeforeExpiry ; i++)
		{
			if (daysBeforeExpiryArray[i-1].getRange() > range)
			{
				range = daysBeforeExpiryArray[i-1].getRange(); // Find most interesting predictions
				//System.out.println("mod="+mod+ " total " + daysBeforeExpiryArray[i-1].getModTotal());
			}
			
			//daysBeforeExpiryArray[i-1].displayAll();
		}
		
		for (int i = 1; i <= maxNumberOfDaysBeforeExpiry ; i++)
		{
			if (daysBeforeExpiryArray[i-1].getRange() == 0) continue;

			if (daysBeforeExpiryArray[i-1].getRange() >= range -1  )
			{
				System.out.println("Probabilities for " + i + " days before expiry" );
				StatsCollector.displayProbabilities(i);
        	
				System.out.println("Total sample size " + StatsCollector.getTotalForAllEntries(i));
			}

		}
    }
    
	public static int getMaxNumberOfDaysBeforeExpiry()
	{
		return maxNumberOfDaysBeforeExpiry;
	}
	
    public static void markDaysOfInterest(int lastDay)
    {
		daysSelection = new int[History.days.size()];
		System.out.println("Last day= " + lastDay);
		if (lastDay > 0)
			System.out.println("Calculating probabilities for " + History.days.get(lastDay-1).getMarketPhase());
		for (int i = 0; i < History.days.size() -1; i++)
		{
			if (i < lastDay - 1)
            {
				// Mark days which belong to similar phase of the market, i.e. bull, bear, trendless
				if (PropertyHelper.getProperty("statsMarketPhases").contains("Y") )
				{
					//System.out.println("statsMarketPhases");
					// VIX should indicate the calmness of the market
					// todays close is above MA
					if (History.days.get(i).getMarketPhase().equals(History.days.get(lastDay-1).getMarketPhase()))
					{
                        // not interested in this day
						daysSelection[i] = 1;
					}
					else
					{
						daysSelection[i] = 0;
						continue;
					}
				}
				
                // Check only days before this one
				if (PropertyHelper.getProperty("statsAboveBelowMA200").contains("Y") )
				{
					//System.out.println("statsAboveBelowMA200");
					// Basic test whether we are in the bull market or not
					/*
					System.out.println(History.days.get(lastDay-1).getClose() + " " + History.days.get(lastDay-1).getWeeklyMA());
					System.out.println(History.days.get(lastDay-1).getStringDate());
					*/
					/*
					if (i > 0)
					{
						if (History.days.get(i).getClose() > History.days.get(i).getWeeklyMA() && History.days.get(i-1).getClose() < History.days.get(i-1).getWeeklyMA())
						{
							System.out.println("Moved from bear to bull on " + History.days.get(i).getStringDate());
							System.out.println(History.days.get(i).getClose() + " " + History.days.get(i).getWeeklyMA());
						}
						
						if (History.days.get(i).getClose() < History.days.get(i).getWeeklyMA() && History.days.get(i-1).getClose() > History.days.get(i-1).getWeeklyMA())
						{
							System.out.println("Moved from bull to bear on " + History.days.get(i).getStringDate());
							System.out.println(History.days.get(i).getClose() + " " + History.days.get(i).getWeeklyMA());
						}
					}
					*/
						
					if (History.days.get(lastDay-1).getClose() > History.days.get(lastDay-1).getWeeklyMA())
					{
						// todays close is below MA
						if (History.days.get(i).getClose() < History.days.get(i).getWeeklyMA())
						{
							// not interested in this day
                            daysSelection[i] = 0;
                            continue;
						}
						else
							daysSelection[i] = 1;
					}
					else
					{
						// todays close is above MA
						if (History.days.get(i).getClose() > History.days.get(i).getWeeklyMA())
						{
							// not interested in this day
                            daysSelection[i] = 0;
                            continue;
						}
						else
							daysSelection[i] = 1;
					}
				}
				
				if (PropertyHelper.getProperty("statsSimilarVIX").contains("Y") )
				{
					// VIX should indicate the calmness of the market
					// todays close is above MA
					if (History.days.get(i).getVIX() > History.days.get(lastDay-1).getVIX() * 1.5 ||
						History.days.get(i).getVIX() < History.days.get(lastDay-1).getVIX() * 0.7)
					{
                        // not interested in this day
						daysSelection[i] = 0;
						continue;
					}
					else
						daysSelection[i] = 1;
				}
				
				if (PropertyHelper.getProperty("statsSimilarMA50").contains("Y") )
				{
					// Check what happened when the distance from MA50 is similar to what it is today
					float distanceFromMA50;
					float distanceFromMA50LastDay;
					
					if (History.days.get(i).getMA50() == 0 || History.days.get(lastDay-1).getMA50() == 0)
					{
                        // not interested in this day
						daysSelection[i] = 0;
						continue;
					}
						
					// Calculate distance from MA50 in percentages
					distanceFromMA50 = (History.days.get(i).getClose() - History.days.get(i).getMA50())/History.days.get(i).getMA50()*100;
					distanceFromMA50LastDay = (History.days.get(lastDay-1).getClose() - History.days.get(lastDay-1).getMA50())/History.days.get(lastDay-1).getMA50()*100;
					if (distanceFromMA50 > (distanceFromMA50LastDay + 0.5)||
						distanceFromMA50 < (distanceFromMA50LastDay - 0.5 ))
					{
                        // not interested in this day
						daysSelection[i] = 0;
						continue;
					}
					else
						daysSelection[i] = 1;
				}
				
				if (PropertyHelper.getProperty("statsSimilarWeek").contains("Y") )
				{
					if (i < 10)
					{
                        // not interested in this day
						daysSelection[i] = 0;
						continue;
					}
					// Check if this was a rising week
					if (History.days.get(lastDay-1).getClose() > History.days.get(lastDay-1-5).getClose())
					{
						if (History.days.get(i).getClose() < History.days.get(i-5).getClose())
						{
	                        // not interested in this day
							daysSelection[i] = 0;
							continue;
						}
						else
							daysSelection[i] = 1;
					}
					else
					{
						if (History.days.get(i).getClose() > History.days.get(i-5).getClose())
						{
	                        // not interested in this day
							daysSelection[i] = 0;
							continue;
						}
						else
							daysSelection[i] = 1;
					}
				}
				
				if (PropertyHelper.getProperty("statsSimilarMonth").contains("Y") )
				{
					if (i < 30)
					{
                        // not interested in this day
						daysSelection[i] = 0;
						continue;
					}
					// Check if this was a rising week
					if (History.days.get(lastDay-1).getClose() > History.days.get(lastDay-1-20).getClose())
					{
						if (History.days.get(i).getClose() < History.days.get(i-20).getClose())
						{
	                        // not interested in this day
							daysSelection[i] = 0;
							continue;
						}
						else
							daysSelection[i] = 1;
					}
					else
					{
						if (History.days.get(i).getClose() > History.days.get(i-20).getClose())
						{
	                        // not interested in this day
							daysSelection[i] = 0;
							continue;
						}
						else
							daysSelection[i] = 1;
					}
				}
				
				if (PropertyHelper.getProperty("statsSimilarMA50Trend").contains("Y") )
				{
					if (i < 100)
					{
                        // not interested in this day
						daysSelection[i] = 0;
						continue;
					}
					// Check if this was a rising week
					if (History.days.get(lastDay-1).getMA50() > History.days.get(lastDay-1-20).getMA50())
					{
						if (History.days.get(i).getMA50() < History.days.get(i-20).getMA50())
						{
	                        // not interested in this day
							daysSelection[i] = 0;
							continue;
						}
						else
							daysSelection[i] = 1;
					}
					else
					{
						if (History.days.get(i).getMA50() > History.days.get(i-20).getMA50())
						{
	                        // not interested in this day
							daysSelection[i] = 0;
							continue;
						}
						else
							daysSelection[i] = 1;
					}
				}
				
				
				if (PropertyHelper.getProperty("statsAfter1UpOrDownDay").contains("Y") )
				{
					//System.out.println("statsAfter1UpOrDownDay");
					if (i < 10)
					{
                        // not interested in this day
						daysSelection[i] = 0;
						continue;
					}
					// Check if this was a rising day
					if (History.days.get(lastDay-1).getClose() > History.days.get(lastDay-1-1).getClose())
					{
						if (History.days.get(i).getClose() < History.days.get(i-1).getClose())
						{
	                        // not interested in this day
							//System.out.println("Not interested in this day");
							daysSelection[i] = 0;
							continue;
						}
						else
							daysSelection[i] = 1;
					}
					else
					{
						if (History.days.get(i).getClose() > History.days.get(i-1).getClose())
						{
	                        // not interested in this day
							daysSelection[i] = 0;
							continue;
						}
						else
							daysSelection[i] = 1;
					}
				}
				
				/*
				
				if (PropertyHelper.getProperty("statsAfter3UpOrDownDays").contains("Y") )
				{
					if (i < 10)
					{
                        // not interested in this day
						daysSelection[i] = 0;
						continue;
					}
					// Check if this was a rising few days
					if (RoseThreeDaysInARow(lastDay-1))
					{
						if (RoseThreeDaysInARow(i))
						{
							// not interested in this day
							daysSelection[i] = 1;
						}
						else
						{
	                        // not interested in this day
							daysSelection[i] = 0;
							continue;						
						}
					}
					else if (DroppedThreeDaysInARow(lastDay-1))
					{
						if (DroppedThreeDaysInARow(i))
						{
							daysSelection[i] = 1;
						}
						else
						{
	                        // not interested in this day
							daysSelection[i] = 0;
							continue;
						}
					}
					else
					{
                        // not interested in this day
						daysSelection[i] = 0;
						continue;
						
					}
				}
				*/
				
				/*
				if (RoseThreeDaysInARow(lastDay-1) && History.days.get(lastDay-1).getMarketPhase().equals("bull") )
				{
					if (i < 10)
					{
                        // not interested in this day
						daysSelection[i] = 0;
						continue;
					}
					daysSelection[i] = 1;
				}
				*/
				
				if (RoseThreeDaysInARow(lastDay-1) || DroppedThreeDaysInARow(lastDay-1) )
				{
					if (i < 10)
					{
                        // not interested in this day
						daysSelection[i] = 0;
						continue;
					}
					// Check if this was a rising few days
					if (RoseThreeDaysInARow(lastDay-1))
					{
						if (RoseThreeDaysInARow(i))
						{
							// not interested in this day
							daysSelection[i] = 1;
						}
						else
						{
	                        // not interested in this day
							daysSelection[i] = 0;
							continue;						
						}
					}
					else if (DroppedThreeDaysInARow(lastDay-1))
					{
						if (DroppedThreeDaysInARow(i))
						{
							daysSelection[i] = 1;
						}
						else
						{
	                        // not interested in this day
							daysSelection[i] = 0;
							continue;
						}
					}
					else
					{
                        // not interested in this day
						daysSelection[i] = 0;
						continue;
						
					}
				}
				
				if (PropertyHelper.getProperty("statsNumberOfDaysSinceMA50Cross").contains("Y") )
				{

					if (History.days.get(lastDay-1).getNumberOfDaysSinceMA50Cross() > 0 && markType == 0)
					{
						if (History.days.get(lastDay-1).getNumberOfDaysSinceMA50Cross() < (float)History.days.get(i).getNumberOfDaysSinceMA50Cross()*numberOfDaysSinceMA50CrossStart/100)
						{
							daysSelection[i] = 1;
						}
						else
						{
							// not interested in this day
							daysSelection[i] = 0;
							continue;
						}
					}

	
				}

				
				if (daysSelection[i] == 1)
				{
					sampleSize++; // The bigger the sample size the better is the prediction
					/*
					System.out.println(History.days.get(i).getStringDate());
					System.out.println(History.days.get(i).getClose() + " MA " + History.days.get(i).getWeeklyMA());
					if (i < lastDay-100)
						System.out.println("100 later close" + History.days.get(i+100).getClose());
					*/
				}
           }
           else
           {
                  daysSelection[i] = 0;
           }
		}    
    }
    
    public static void findBestDescriptionOfToday()
    {
    	int lastDay=History.days.size()-1 ;
    	ShareState[] characteristics = new ShareState[4];
    	
		maxNumberOfDaysBeforeExpiry= 300;
		sampleSize = 0;
		// Array which is used to calculate probability where the price will be a number of days from today
		daysBeforeExpiryArray = new StatsPerDay[maxNumberOfDaysBeforeExpiry];
		for (int i = 1; i <= maxNumberOfDaysBeforeExpiry ; i++)
		{
			daysBeforeExpiryArray[i-1] = new StatsPerDay();
			//daysBeforeExpiryArray[i-1].displayAll();
		}
    	
    	// The first characteristic of a day is a phase, such as bull, bear,...
    	characteristics[0] = new SharePhase(History.days.get(lastDay).getMarketPhase());
    	
    	// Another characteristic of a day is whether close is above long term moving average
		if (History.days.get(lastDay).getClose() > History.days.get(lastDay).getWeeklyMA())
		{
			characteristics[1] = new ShareAboveLongTermMA(true);
		}
		
    	// Another characteristic of a day is whether close is above moving average 50
		if (History.days.get(lastDay).getClose() > History.days.get(lastDay).getWeeklyMA())
		{
			characteristics[2] = new ShareAboveMA50(true);
		}
		
    	// Another characteristic of a day is the distance from MA50
		characteristics[3] = new ShareSimilarDistanceFromMA(true);
		daysSelection = new int[History.days.size()-1];
		findNumberOfSimilarDays(characteristics);
		
		SharePriceAction[] priceAction = new SharePriceAction[3];
    	// Another characteristic of a day is the rise of 0.5% or more in one day
		priceAction[0] = new HalfAPercentRise();
		
    	// Another characteristic of a day is the rise of two days in a row
		priceAction[1] = new RiseOfTwoDaysInARow();
		
    	// Another characteristic of a day is the rise of three days in a row
		priceAction[2] = new RiseOfThreeDaysInARow();
		
		findNumberOfSimilarDays(priceAction);

    }
    
    public static void findNumberOfSimilarDays(ShareState[] characteristics)
    {
    	System.out.println("Total days = " + History.days.size());
    	//daysSelection = new int[History.days.size()-1];
    	for (int i=0; i < characteristics.length; i++)
    	{
    		characteristics[i].markDays();
    		System.out.println("Checked criterion " + i + " Total days found " + sampleSize);
    	}
    	System.out.println("Total days found= " + sampleSize);
    }
    
    public static void findNumberOfSimilarDays(SharePriceAction[] priceAction)
    {
    	System.out.println("Total days = " + History.days.size());
    	//daysSelection = new int[History.days.size()-1];
    	for (int i=0; i < priceAction.length; i++)
    	{
    		priceAction[i].markDays();
    		System.out.println("Checked criterion " + i + " Total days found " + sampleSize);
    	}
    	System.out.println("Total days found= " + sampleSize);
    }
   
    public static void markSimilarPhase(int lastDay)
    {
		//System.out.println("Last day= " + lastDay);
		//if (lastDay > 0)
			//System.out.println("Marking days for " + History.days.get(lastDay-1).getMarketPhase() + " phase");
		for (int i = 0; i < History.days.size() -1; i++)
		{
			if ( daysSelection[i] == -1)
				continue;
			
			if (i < lastDay - 1)
            {
				// Mark days which belong to similar phase of the market, i.e. bull, bear, trendless
				// VIX should indicate the calmness of the market
				// todays close is above MA
				if (History.days.get(i).getMarketPhase().equals(History.days.get(lastDay-1).getMarketPhase()))
				{
                    // not interested in this day
					daysSelection[i] = 1;
					sampleSize++;
				}
				else
				{
					daysSelection[i] = -1;
					
				}
            }
		}
    }
    
    public static void markSimilarDaysAboveLongTermMA(int lastDay)
    {
		//System.out.println("Last day= " + lastDay);
		//if (lastDay > 0)
			//System.out.println("Marking days above Long Term MA ");
		for (int i = 0; i < History.days.size() -1; i++)
		{
			if (i < lastDay - 1 && daysSelection[i] != -1)
            {
				// Mark days which are above long term MA
				if (History.days.get(i).getClose() > History.days.get(i).getWeeklyMA())
				{
                    daysSelection[i] = 1;
				}
				else
				{
					daysSelection[i] = -1;
					sampleSize--;
				}
            }
		}
    }
    
    public static void markSimilarDaysAboveMA50(int lastDay)
    {
		//System.out.println("Last day= " + lastDay);
		//if (lastDay > 0)
			//System.out.println("Marking days above MA 50 ");
		for (int i = 0; i < History.days.size() -1; i++)
		{
			if (i < lastDay - 1 && daysSelection[i] != -1)
            {
				// Mark days which are above long term MA
				if (History.days.get(i).getClose() > History.days.get(i).getMA50())
				{
                    daysSelection[i] = 1;
				}
				else
				{
					daysSelection[i] = -1;
					sampleSize--;
				}
            }
		}
    }
    
    public static void markSimilarDaysOfHalfAPercentRise(int lastDay)
    {
		//System.out.println("Last day= " + lastDay);
		//if (lastDay > 0)
			//System.out.println("Marking days above MA 50 ");
    	
    	if ((History.days.get(lastDay-1).getClose() - History.days.get(lastDay-2).getClose())/History.days.get(lastDay-1).getClose()*100 > 0.5)
    	{
    		for (int i = 0; i < History.days.size() -1; i++)
    		{
    			if (i < lastDay - 1 && daysSelection[i] != -1 && i > 0)
    			{
    				// Mark days which are above long term MA
    				if ((History.days.get(i).getClose() - History.days.get(i-1).getClose())/History.days.get(i).getClose()*100 > 0.5)
    				{
    					daysSelection[i] = 1;
    				}
    				else
    				{
    					daysSelection[i] = -1;
    					sampleSize--;
    				}
    			}
    		}
    	}
    }
    
    public static void markSimilarDaysWithRiseOfTwoDaysInARow(int lastDay)
    {
		//System.out.println("Last day= " + lastDay);
		//if (lastDay > 0)
			//System.out.println("Marking days above MA 50 ");
    	if (History.days.get(lastDay-1).getClose() > History.days.get(lastDay-2).getClose() && History.days.get(lastDay-2).getClose() > History.days.get(lastDay-3).getClose())
		{
    		for (int i = 0; i < History.days.size() -1; i++)
    		{
    			if (i < lastDay - 1 && daysSelection[i] != -1 && i > 10)
    			{
    				if (History.days.get(i).getClose() > History.days.get(i-1).getClose())
    				{
    					if (History.days.get(i-1).getClose() > History.days.get(i-2).getClose())
    						daysSelection[i] = 1;
    					else
    					{
    						daysSelection[i] = -1;
    						sampleSize--;
    					}
    				}
    				else
    				{
    					daysSelection[i] = -1;
    					sampleSize--;
    				}
    			}
    		}
		}
    }
    
    public static void markSimilarDaysWithRiseOfThreeDaysInARow(int lastDay)
    {
		//System.out.println("Last day= " + lastDay);
		//if (lastDay > 0)
			//System.out.println("Marking days above MA 50 ");
    	if (History.days.get(lastDay-1).getClose() > History.days.get(lastDay-2).getClose() && 
    		History.days.get(lastDay-2).getClose() > History.days.get(lastDay-3).getClose() &&
    		History.days.get(lastDay-3).getClose() > History.days.get(lastDay-4).getClose())
		{
    		for (int i = 0; i < History.days.size() -1; i++)
    		{
    			if (i < lastDay - 1 && daysSelection[i] != -1 && i > 10)
    			{
    				if (History.days.get(i).getClose() > History.days.get(i-1).getClose())
    				{
    					if (History.days.get(i-1).getClose() > History.days.get(i-2).getClose())
    					{
    						if (History.days.get(i-2).getClose() > History.days.get(i-3).getClose())
    						{
    							daysSelection[i] = 1;
    						}
    						else
    						{
        						daysSelection[i] = -1;
        						sampleSize--;
    						}
    					}
    					else
    					{
    						daysSelection[i] = -1;
    						sampleSize--;
    					}
    				}
    				else
    				{
    					daysSelection[i] = -1;
    					sampleSize--;
    				}
    			}
    		}
		}
    }
    
    public static void markSimilarDistanceFromMA50(int lastDay)
    {
    	float total=0;
    	int numOfEntries=0;

		for (int i = 0; i < History.days.size() -1; i++)
		{
			if (i < lastDay - 1 && daysSelection[i] != -1)
            {
				if (daysSelection[i] == 1)
				{
					total = (Math.abs(History.days.get(i).getMA50() - History.days.get(i).getClose()))/History.days.get(i).getMA50()*100 + total;
					numOfEntries++;
				}
            }
		}
		
		float average = total/numOfEntries;
		float distance;
		float currDistance = (Math.abs(History.days.get(lastDay-1).getMA50() - History.days.get(lastDay-1).getClose()))/History.days.get(lastDay-1).getMA50()*100;
		System.out.println("current distance= " + currDistance + " average = " + average);
		System.out.println("MA= " + History.days.get(lastDay-1).getMA50() + " close = " + History.days.get(lastDay-1).getClose());
		
		for (int i = 0; i < History.days.size() -1; i++)
		{
			if (i < lastDay - 1 && daysSelection[i] != -1)
            {
				if (daysSelection[i] == 1)
				{
					distance = Math.abs(History.days.get(i).getMA50() - History.days.get(i).getClose());
					if (currDistance < average)
						if (distance < average)
							daysSelection[i] = 1;
						else
						{
							daysSelection[i] = -1;
							sampleSize--;
						}
					else
						if (distance > average)
							daysSelection[i] = 1;
						else
						{
							daysSelection[i] = -1;
							sampleSize--;
						}
							
				}
            }
		}
    }
    
    public static boolean RoseThreeDaysInARow(int i)
    {
    	/*
		System.out.println(History.days.get(i).getStringDate());
		System.out.println(History.days.get(i).getClose() + " MA " + History.days.get(i-1).getWeeklyMA());
		System.out.println(History.days.get(i-1).getClose() + " Prev " + History.days.get(i-2).getClose() + " " + History.days.get(i-3).getClose());
		*/
    	if (History.days.get(i).getClose() > History.days.get(i-1).getClose())
    		if (History.days.get(i-1).getClose() > History.days.get(i-2).getClose())
    			if (History.days.get(i-2).getClose() > History.days.get(i-3).getClose())
    				return true;

        return false;
    }
    
    public static boolean DroppedThreeDaysInARow(int i)
    {
    	if (History.days.get(i).getClose() < History.days.get(i-1).getClose())
    		if (History.days.get(i-1).getClose() < History.days.get(i-2).getClose())
    			if (History.days.get(i-2).getClose() < History.days.get(i-3).getClose())
    				return true;

        return false;
    }

    public static boolean isDayOfInterest(int i)
    {
    	if (daysSelection[i] == 1)
    		return true;
        else
            return false;
    }
    
    public static int getSampleSize()
    {

    	return sampleSize;
    }
    
    public static void setMarkType(int mt)
    {
    	markType = mt;
    }

}
