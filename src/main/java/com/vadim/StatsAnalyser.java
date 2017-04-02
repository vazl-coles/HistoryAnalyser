package com.vadim;

import java.util.Collections;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class StatsAnalyser {
	
	//private static Logger log;
	static final Logger log = Logger.getLogger("StatsAnalyser");
	
    public static void main(String[] args) throws Exception 
    {

    	PropertyHelper.readProperties();
    	
    	PropertyConfigurator.configure("log4j.properties");
    	
        log.info("Reading history");
        log.debug("test");
    	
        History.init();
    	History.readPriceHistory();

    	History.addIndicators();
    	
		buildStatsFromDaysOfInterest();

        if (StatsCollector.getSampleSize() < 20)
        {
        	StatsCollector.setMarkType(1);
        	StatsCollector.init(); // Will mark all days in the past which look similar, e.g. above MA
        	//System.out.println("Check dates");
            for ( int daysBeforeExpiry = 1; daysBeforeExpiry <= StatsCollector.getMaxNumberOfDaysBeforeExpiry(); daysBeforeExpiry++)
            {
            	/*
            	if (daysBeforeExpiry > 100) continue;
            	if (daysBeforeExpiry < 10)
            	{
            		
            	}
            	else
            	if (daysBeforeExpiry == 20 || daysBeforeExpiry == 40 || daysBeforeExpiry == 70 || daysBeforeExpiry == 100 )
            	{
            		
            	}
            	else continue;
            	*/
            	StatsCollector.updateStatsForToday(daysBeforeExpiry);
            	//System.out.println("Probabilities for " + daysBeforeExpiry + " days before expiry" );
            	//StatsCollector.displayProbabilities(daysBeforeExpiry);
            	
            	//System.out.println("Total sample size " + StatsCollector.getTotalForAllEntries(daysBeforeExpiry));
            	//StatsCollector.updateStats(daysBeforeExpiry); // Simple stats collections, check all dates without regards for MA or vix or whatever
            }
        	
        }
        
        StatsCollector.findMostLikelyDays(); 
        
    	/*
		for (int i = 0; i <= History.getLastDay()-1; i++)
		{
			// Accumulate stats for a number of days before expiry
			StatsCollector.init();
	        for ( int daysBeforeExpiry = 1; daysBeforeExpiry <= StatsCollector.getMaxNumberOfDaysBeforeExpiry(); daysBeforeExpiry++)
	        {
	        	StatsCollector.updateStats(daysBeforeExpiry); // Simple stats collections, check all dates without regards for MA or vix or whatever
	        }
	        

		}
		*/
    }
    
    public static void buildStatsFromDaysOfInterest()
    {
		// Accumulate stats for a number of days before expiry
		StatsCollector.init(); // Will mark all days in the past which look similar, e.g. above MA. These are days of interest
        System.out.println("Sample size " + StatsCollector.getSampleSize());
        for ( int daysBeforeExpiry = 1; daysBeforeExpiry <= StatsCollector.getMaxNumberOfDaysBeforeExpiry(); daysBeforeExpiry++)
        {
        	// Find probabilities from the past days that were marked earlier
        	StatsCollector.updateStatsForToday(daysBeforeExpiry);
        	//System.out.println("Probabilities for " + daysBeforeExpiry + " days before expiry" );
        	//StatsCollector.displayProbabilities(daysBeforeExpiry);
        	
        	//System.out.println("Total sample size " + StatsCollector.getTotalForAllEntries(daysBeforeExpiry));
        	//StatsCollector.updateStats(daysBeforeExpiry); // Simple stats collections, check all dates without regards for MA or vix or whatever
        }
        StatsCollector.findMostLikelyDays();
    }
        



}


