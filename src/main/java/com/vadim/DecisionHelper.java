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
        
        History.init();
        StatsCollector.initialise();
        // Move history into memory and add indicators (like bull,ma50, etc) that will be used to calculate probabilities
    	History.readPriceHistory();
    	History.addIndicators();
    	
        
        findDaysInFutureWhereMarketWillBe();
        

    }
    
    public static void findDaysInFutureWhereMarketWillBe()
    {     
        StatsCollector.findBestDescriptionOfToday();
		// Check prior history in order to display what the price is likely to be at expiry. The process is the same for a number of
		// days before expiry, i.e. check what are the likely prices tomorrow or in 100 days.
        for ( int daysBeforeExpiry = 1; daysBeforeExpiry <= StatsCollector.getMaxNumberOfDaysBeforeExpiry(); daysBeforeExpiry++)
        {
        	// Find probabilities from the past days that were marked earlier
        	StatsCollector.updateStatsForToday(daysBeforeExpiry);
        }
        StatsCollector.findExpiryWithLimitedUpside();
    	
    }

}

