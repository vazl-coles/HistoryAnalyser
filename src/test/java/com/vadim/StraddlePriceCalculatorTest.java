package com.vadim;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

import com.opencsv.CSVReader;

public class StraddlePriceCalculatorTest {
	
	Properties prop = new Properties();
	
	private static float straddlePrice = 0;
	private static float priceAtExpiry = 0;
	private static float strikePrice = 0;
	
	private static String testFileName="";
	
	
	  @Test
	  public void testGetStraddlePrice() {
			float currentPrice = 220;
			float strikePrice = 220;
			int numberOfDaysBeforeExpiry = 2;
			float vix = 13;
			
			readProperties();
			StraddlePriceCalculator.readStraddleFile();
			/*
			StraddlePriceCalculator.straddlePricesFile = getFileNameIncludingPath(prop.getProperty("straddlePrices"));
			float straddlePrice=StraddlePriceCalculator.getStraddlePrice(numberOfDaysBeforeExpiry, currentPrice, strikePrice, vix);
			
			assertEquals(1.6, (Math.round(straddlePrice*100.0)/100.0),0);
			
			float distanceBetweenPrices = StraddlePriceCalculator.calcDistanceBetweenPrices((float)224.4,(float)223);
			
			assertEquals(0.63, (Math.round(distanceBetweenPrices*100.0)/100.0),0);
			
			currentPrice = (float)224.4;
			strikePrice = 223;
			numberOfDaysBeforeExpiry = 2;
			vix = 13;
			straddlePrice=StraddlePriceCalculator.getStraddlePrice(numberOfDaysBeforeExpiry, currentPrice, strikePrice, vix);
			
			assertEquals(1.9, (Math.round(straddlePrice*100.0)/100.0),0);
			*/
			currentPrice = (float)224.4;
			strikePrice = 226;
			numberOfDaysBeforeExpiry = 2;
			vix = 12;
			straddlePrice=StraddlePriceCalculator.getStraddlePrice(numberOfDaysBeforeExpiry, currentPrice, strikePrice, vix);
			
			assertEquals(2.3, (Math.round(straddlePrice*100.0)/100.0),0);
			
			
	  }
	  
	  @Test
	  public void testReadingPropertiesFile() {

		  InputStream input = null;
		  String testFileName="";
		  String fileName="";

		  try
		  {
			  String filename = "config.properties";
			  prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
			  testFileName = prop.getProperty("straddlePrices");
		  }
		  catch (IOException ex) 
		  {
			  ex.printStackTrace();
	      }
		  
		  assertEquals(testFileName, "straddlePrices.csv");
	  }
	  
	    public void readProperties()
	    {
			  try
			  {
				  String filename = "config.properties";
				  prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
			  }
			  catch (IOException ex) 
			  {
				  ex.printStackTrace();
		      }
	    }
	  
		  private String getFileNameIncludingPath(String fileName)
		  {
			  String result = "";
			  
			  //Get file from resources folder
			  ClassLoader classLoader = getClass().getClassLoader();
		      CSVReader reader = null;
		      String[] line;

		      System.out.println("getFileNameIncludingPath:" + fileName);
		    	  
		    	  String f = classLoader.getResource(fileName).getFile();
		    	  System.out.println("getFileNameIncludingPath :" + f);
		    	  f = f.replace("%20", " "); 

		          
		          return f;
		  }

}
