package com.vadim;


public class ShareAboveMA50 implements ShareState {
	
	private boolean above;
	private boolean essential;
	
	public ShareAboveMA50(boolean above)
	{
		this.above = above;
		this.essential = true;
	}
	
    public void markDays() 
    {
    	if (above)
    	{
    		System.out.println("Finding days above MA 50 ...");
    		StatsCollector.markSimilarDaysAboveMA50(History.days.size());
    	}
    	else
    	{
    		System.out.println("Not looking for days above MA 50 ...");
    	}

    }
    
    public void setEssentialState(boolean essential)
    {
       this.essential = essential;
    }
    
    public boolean isEssential()
    {
    	return essential;
    }
    
    public void setPhase(boolean above)
    {
    	this.above = above;
    }
    
    public boolean getPhase()
    {
    	return this.above;
    }

}

