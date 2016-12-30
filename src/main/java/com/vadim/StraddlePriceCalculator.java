package com.vadim;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class StraddlePriceCalculator 
{

	public static float getStraddlePrice(float currentPrice, float strikePrice, int numberOfDaysBeforeExpiry)
	{
		// Find out straddle price
		return 1;
		
	}
	
	public static float getStraddlePrice(float currentPrice, float strikePrice, int numberOfDaysBeforeExpiry, float vix)
	{
		float straddlePrice=1;
		float distanceBetweenCurrentAndStrike = calcDistanceBetweenPrices(currentPrice,strikePrice);
		// Find out straddle price
		if (numberOfDaysBeforeExpiry == 2)
		{
			if (distanceBetweenCurrentAndStrike < 0.2 )
			{
				straddlePrice = (float)1.60; // End of 29.12.2016, vix = 13  
			}
			else if (distanceBetweenCurrentAndStrike < 0.4 )
			{
				straddlePrice = (float)1.50; // End of 29.12.2016, vix = 13 
			}
			else if (distanceBetweenCurrentAndStrike < 0.7 )
			{
				straddlePrice = (float)1.90;
			}
			
			straddlePrice = (float)straddlePrice*vix/13;
		}
		else if (numberOfDaysBeforeExpiry == 1)
		{
			if (distanceBetweenCurrentAndStrike < 0.2 )
			{
				straddlePrice = (float)1.07; // End of 30.12.2016, vix = 13.37  
			}
			else if (distanceBetweenCurrentAndStrike < 0.4 )
			{
				straddlePrice = (float)1.10; // End of 30.12.2016, vix = 13.37 
			}
			else if (distanceBetweenCurrentAndStrike < 0.7 )
			{
				straddlePrice = (float)1.70; // End of 30.12.2016, vix = 13.37
			}
			straddlePrice = (float)straddlePrice*vix/(float)13.37;
		} 
		
		return straddlePrice;
		
	}
	
	public static float calcDistanceBetweenPrices(float price1, float price2)
	{
		float distanceBetweenPrices = Math.abs(price1-price2)/price2*100;

		
		return distanceBetweenPrices;
		
	}
	
	
}
