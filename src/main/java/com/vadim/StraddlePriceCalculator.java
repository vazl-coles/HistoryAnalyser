package com.vadim;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Properties;

public class StraddlePriceCalculator 
{
	// The file will be created to include straddle prices which were collected over time
	static String straddlePricesFileNew = "";
	static Properties prop = new Properties();
	
    public static void main(String[] args) throws Exception {
        
    	System.out.println(args[0]);
    	System.out.println(args[1]);
    	readProperties();
    	/*
    	float numberOfDaysBeforeExpiry=Float.parseFloat(args[0]);
    	float currentPrice=Float.parseFloat(args[1]);
    	float straddlePrice=Float.parseFloat(args[2]);
    	float volatility=Float.parseFloat(args[3]);
    	*/
    	addStraddle(args[0], args[1], args[2], args[3]);
    }
    
    public static void readProperties()
    {
		  InputStream input = null;

		  try
		  {
			  input = new FileInputStream("config.properties");
			  prop.load(input);
			  straddlePricesFileNew = prop.getProperty("straddlePrices");
		  }
		  catch (IOException ex) 
		  {
			  ex.printStackTrace();
	      }
    }
    
    public static void addStraddle(String numberOfDaysBeforeExpiry, 
    		String currentPrice, 
    		String straddlePrice, 
    		String volatility) throws Exception
    {
    	FileWriter writer = new FileWriter(straddlePricesFileNew);
    	
    	try
    	{
    		CSVUtils.writeLine(writer, Arrays.asList(
    				numberOfDaysBeforeExpiry,
    				currentPrice,
    				straddlePrice,
    				volatility
            		));
	        writer.flush();
	        writer.close();
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}
    }

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
