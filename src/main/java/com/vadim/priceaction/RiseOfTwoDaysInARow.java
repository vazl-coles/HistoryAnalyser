package com.vadim.priceaction;

import com.vadim.History;
import com.vadim.StatsCollector;

public class RiseOfTwoDaysInARow implements SharePriceAction{

	private boolean essential;
	
	public RiseOfTwoDaysInARow()
	{
		this.essential = false;
	}
	
    public void markDays() 
    {

   		System.out.println("Finding days of two days in a row rises.");
   		StatsCollector.markSimilarDaysWithRiseOfTwoDaysInARow(History.getLastDay());


    }
    
    public void setEssentialState(boolean essential)
    {
       this.essential = essential;
    }
    
    public boolean isEssential()
    {
    	return essential;
    }
    
}
