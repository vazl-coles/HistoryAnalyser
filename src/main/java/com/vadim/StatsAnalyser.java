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
    	
		// Accumulate stats for a number of days before expiry
		StatsCollector.init();
        for ( int daysBeforeExpiry = 1; daysBeforeExpiry <= StatsCollector.getMaxNumberOfDaysBeforeExpiry(); daysBeforeExpiry++)
        {
        	if (daysBeforeExpiry > 100) continue;
        	StatsCollector.updateStatsForToday(daysBeforeExpiry);
        	System.out.println("Probabilities for " + daysBeforeExpiry + " days before expiry" );
        	StatsCollector.displayProbabilities(daysBeforeExpiry);
        	
        	System.out.println("Total sample size " + StatsCollector.getTotalForAllEntries(daysBeforeExpiry));
        	//StatsCollector.updateStats(daysBeforeExpiry); // Simple stats collections, check all dates without regards for MA or vix or whatever
        }
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
        



}


