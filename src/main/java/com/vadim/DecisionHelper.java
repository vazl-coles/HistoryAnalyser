package com.vadim;

import java.util.Collections;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class DecisionHelper {
	
	//private static Logger log;
	static final Logger log = Logger.getLogger("DecisionHelper");
	
    public static void main(String[] args) throws Exception 
    {

    	PropertyHelper.readProperties();
    	
    	PropertyConfigurator.configure("log4j.properties");
    	
        log.info("DecisionHelper");
        
        // Move history into memory and add indicators that will be used to calculate probabilities
        History.init();
    	History.readPriceHistory();
    	History.addIndicators();
    	
        
        findDaysInFutureWhereMarketWillBe();
        

    }
    
    public static void findDaysInFutureWhereMarketWillBe()
    {
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
    	
    }

}

