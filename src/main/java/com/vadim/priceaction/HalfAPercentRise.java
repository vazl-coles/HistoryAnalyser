package com.vadim.priceaction;

import com.vadim.*;


public class HalfAPercentRise implements SharePriceAction{
	
	private boolean essential;
	
	public HalfAPercentRise()
	{
		this.essential = false;
	}
	
    public void markDays() 
    {

   		System.out.println("Finding days with greated than 0.5% days rise in one day.");
   		StatsCollector.markSimilarDaysOfHalfAPercentRise(History.getLastDay());


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
