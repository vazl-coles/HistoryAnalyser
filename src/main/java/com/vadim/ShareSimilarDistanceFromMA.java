package com.vadim;


public class ShareSimilarDistanceFromMA implements ShareState {
	

	private boolean essential;
	
	public ShareSimilarDistanceFromMA(boolean above)
	{
		this.essential = true;
	}
	
    public void markDays() 
    {

    		System.out.println("Finding days similar distance from MA 50 ...");
    		StatsCollector.markSimilarDistanceFromMA50(History.days.size());


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

