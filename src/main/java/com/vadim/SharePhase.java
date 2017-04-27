package com.vadim;

public class SharePhase implements ShareState{
	private String phase;
	private boolean essential;
	
	public SharePhase(String phase)
	{
		this.phase = phase;
		this.essential = true;
	}
	
    public void markDays() 
    {
       System.out.println("Finding days for the current phase ...");
       // go through history and mark days with the same phase
       StatsCollector.markSimilarPhase(History.days.size());
    }
    
    public void setEssentialState(boolean essential)
    {
       this.essential = essential;
    }
    
    public boolean isEssential()
    {
    	return essential;
    }
    
    public void setPhase(String phase)
    {
    	this.phase = phase;
    }
    
    public String getPhase()
    {
    	return this.phase;
    }

	
}
