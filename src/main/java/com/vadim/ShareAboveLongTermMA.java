package com.vadim;

public class ShareAboveLongTermMA implements ShareState {
	
	private boolean above;
	private boolean essential;
	
	public ShareAboveLongTermMA(boolean above)
	{
		this.above = above;
	}
	
    public void markDays() 
    {
       System.out.println("Finding days above long term MA ...");
       // go through history and mark days with the same phase
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
