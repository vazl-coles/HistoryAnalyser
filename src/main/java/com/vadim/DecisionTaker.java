package com.vadim;

import java.util.Collections;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class DecisionTaker {
	
	//private static Logger log;
	static final Logger log = Logger.getLogger("DecisionTaker");
	
    public static void main(String[] args) throws Exception 
    {

    	PropertyHelper.readProperties();
    	
    	PropertyConfigurator.configure("log4j.properties");
    	
        log.info("DecisionTaker");
        
        // Move history into memory and add indicators that will be used to calculate probabilities
        History.init();
    	History.readPriceHistory();
    	History.addIndicators();
    	
        
        findDaysInFutureWhereMarketWillBe();
        

    }
    
    public static void findDaysInFutureWhereMarketWillBe()
    {
    	// Use different criteria to mark similar days in the past
    	// Collect the results in the list and display most interesting ones, i.e. marked days are not spread out
    	
    	/*
    	PropertyHelper.setProperty("statsAboveBelowMA200", "N");
    	PropertyHelper.setProperty("statsSimilarVIX", "N");
    	PropertyHelper.setProperty("statsSimilarMA50", "N");
    	PropertyHelper.setProperty("statsSimilarWeek", "N");
    	PropertyHelper.setProperty("statsSimilarMonth", "N");
    	PropertyHelper.setProperty("statsSimilarMA50Trend", "N");
    	PropertyHelper.setProperty("statsAfter1UpOrDownDay", "N");
    	PropertyHelper.setProperty("statsAfter3UpOrDownDays", "N");
    	*/
		// Accumulate stats for a number of days before expiry
		StatsCollector.init(); // Will mark all days in the past which look similar, e.g. above MA. These are days of interest
        for ( int daysBeforeExpiry = 1; daysBeforeExpiry <= StatsCollector.getMaxNumberOfDaysBeforeExpiry(); daysBeforeExpiry++)
        {
        	// Find probabilities from the past days that were marked earlier
        	StatsCollector.updateStatsForToday(daysBeforeExpiry);
        }
        StatsCollector.findMostLikelyDays();
        //StatsCollector.findMostSpreadOutDays();
        
        StatsCollector.findBestDescriptionOfToday();
        
        for ( int daysBeforeExpiry = 1; daysBeforeExpiry <= StatsCollector.getMaxNumberOfDaysBeforeExpiry(); daysBeforeExpiry++)
        {
        	// Find probabilities from the past days that were marked earlier
        	StatsCollector.updateStatsForToday(daysBeforeExpiry);
        }
        StatsCollector.findMostLikelyDays();
    	
        /*
    	int scanNum=1;
    	while (scanNum < 100)
    	{
    		System.out.println("Scanning history for " + scanNum + " time");
    		StatsCollector.init(); // Will mark all days in the past which look similar, e.g. above MA. These are days of interest
    		scanNum ++;
    	}
    	*/
    }

}
