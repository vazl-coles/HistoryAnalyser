package com.vadim;

import java.util.Comparator;

public class StraddleInfo implements Comparable<StraddleInfo> {
	
	private int numberOfDaysBeforeExpiry;
	private float currentPrice;
	private float strikePrice;
	private float distanceBetweenCurrentAndStrike;
	private float vix;
	private float straddlePrice;
	
	public StraddleInfo(String numberOfDaysBeforeExpiry, String currentPrice, String strikePrice, String straddlePrice, String vix)
	{
		setNumberOfDaysBeforeExpiry(numberOfDaysBeforeExpiry);
		setCurrentPrice(currentPrice);
		setStrikePrice(strikePrice);
		setVIX(vix);
		setStraddlePrice(straddlePrice);
		setDistanceBetweenCurrentAndStrike();
	}

	private void setDistanceBetweenCurrentAndStrike() {
		this.distanceBetweenCurrentAndStrike = (currentPrice - strikePrice) / currentPrice;
		
	}

	private void setStraddlePrice(String straddlePrice) {
		this.straddlePrice = Float.parseFloat(straddlePrice);
		
	}
	
	public String getStringStraddlePrice()
	{
		return String.valueOf(this.straddlePrice);
	}
	
	public float getStraddlePrice()
	{
		return this.straddlePrice;
	}

	private void setVIX(String vix) {
		this.vix = Float.parseFloat(vix);
		
	}
	
	public String getStringVIX()
	{
		return String.valueOf(this.vix);
	}

	private void setStrikePrice(String strikePrice) {
		this.strikePrice = Float.parseFloat(strikePrice);
		
	}
	
	public String getStringStrikePrice()
	{
		return String.valueOf(this.strikePrice);
	}

	private void setCurrentPrice(String currentPrice) {
		this.currentPrice = Float.parseFloat(currentPrice);
		
	}
	
	public String getStringCurrentPrice()
	{
		return String.valueOf(this.currentPrice);
	}

	private void setNumberOfDaysBeforeExpiry(String numberOfDaysBeforeExpiry) {
		this.numberOfDaysBeforeExpiry = Integer.parseInt(numberOfDaysBeforeExpiry);
		
	}
	
	public int getNumberOfDaysBeforeExpiry()
	{
		return this.numberOfDaysBeforeExpiry;
	}
	
	public String getNumberOfDaysBeforeExpiryString()
	{
		return String.valueOf(this.numberOfDaysBeforeExpiry);
	}
	
	public int compareTo(StraddleInfo straddle) {

		int numberOfDaysBeforeExpiry = straddle.numberOfDaysBeforeExpiry;
		
		//ascending order
		return this.numberOfDaysBeforeExpiry - numberOfDaysBeforeExpiry;
	}
	
	public int compare(StraddleInfo s, StraddleInfo s1) 
	{
		if (s.numberOfDaysBeforeExpiry < s1.numberOfDaysBeforeExpiry)
		{
			System.out.println("before");
			return -1;			
		}
		else if (s.numberOfDaysBeforeExpiry > s1.numberOfDaysBeforeExpiry)
		{
			System.out.println("after");
			return 1;
		}
		else
		{
			if (s.distanceBetweenCurrentAndStrike < s1.distanceBetweenCurrentAndStrike)
				return -1;
			else if (s.distanceBetweenCurrentAndStrike > s1.distanceBetweenCurrentAndStrike)
				return 1;
			else 
				return 0;
		}

	}
	
	public static Comparator<StraddleInfo> StraddleInfoComparator
    = new Comparator<StraddleInfo>() {
		
		public int compare(StraddleInfo s, StraddleInfo s1) 
		{
			if (s.numberOfDaysBeforeExpiry < s1.numberOfDaysBeforeExpiry)
			{
				System.out.println("before");
				return -1;			
			}
			else if (s.numberOfDaysBeforeExpiry > s1.numberOfDaysBeforeExpiry)
			{
				System.out.println("after");
				return 1;
			}
			else
			{
				if (s.distanceBetweenCurrentAndStrike < s1.distanceBetweenCurrentAndStrike)
					return -1;
				else if (s.distanceBetweenCurrentAndStrike > s1.distanceBetweenCurrentAndStrike)
					return 1;
				else 
					return 0;
			}

		}
		
		
	};
	
	
	
}
	



