package com.vadim;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import com.opencsv.CSVReader;

public class StraddlePriceCalculatorTest {
	
	static String straddlePricesFile = "";
	
	static Properties prop = new Properties();
	
	private static float straddlePrice = 0;
	private static float priceAtExpiry = 0;
	private static float strikePrice = 0;
	
	private static String testFileName="";
	
	static List<StraddleInfo> straddles = new ArrayList<StraddleInfo>();
	
	@Test
	public void testReadingOneLineFromStraddleFile() {
		
		  String result = readOneLineFromStraddleFile();
		  assertEquals(result, "2");
	}
	
	
	  @Test
	  public void testGetStraddlePrice() {
			float currentPrice = 220;
			float strikePrice = 220;
			int numberOfDaysBeforeExpiry = 2;
			float vix = 13;
			
			readProperties();
			readStraddleFile();
			// Confirm that test file which contains one line was correctly read
			assertEquals(1, straddles.size());
			
			// Confirm that the straddle read is for 2 days before expiry
			assertEquals(2, straddles.get(0).getNumberOfDaysBeforeExpiry());
			
			StraddlePriceCalculator.straddles = new ArrayList<StraddleInfo>(straddles);
			
			// Confirm that the copied straddle is for 2 days before expiry
			assertEquals(2, StraddlePriceCalculator.straddles.get(0).getNumberOfDaysBeforeExpiry());
			
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
		  
		    public void readStraddleFile()
		    {
		    	straddlePricesFile = getProperty("straddlePrices");
		    	//System.out.println("getClass() :" + getClass());
		    	ClassLoader classLoader = getClass().getClassLoader();
		    	File file = new File(classLoader.getResource(straddlePricesFile).getFile());
		    	
		    	//System.out.println("getClass().getClassLoader().getClass() :" + getClass().getClassLoader().getClass());
		    	//System.out.println("getClass().getClassLoader().getResource(straddlePricesFile) :" + getClass().getClassLoader().getResource(straddlePricesFile));
		    	String fileToBeRead = getClass().getClassLoader().getResource(straddlePricesFile).getPath();
		    	
		    	fileToBeRead = fileToBeRead.replace("%20", " ");
		    	// System.out.println("Test readStraddleFile :" + fileToBeRead);
		    	
		    	try 
		    	{
		    		CSVReader reader = null;
		    		reader = new CSVReader(new FileReader(fileToBeRead));
		            String[] line;       
		            while ((line = reader.readNext()) != null) {
		            	StraddleInfo straddle = new StraddleInfo(line[0], line[1], line[2], line[3], line[4]);

		            	straddles.add(straddle);
		            }
		    	}
		        catch (IOException e) {
		            e.printStackTrace();
		        }
		    	
		    }
		    public String readOneLineFromStraddleFile()
		    {
		    	straddlePricesFile = getProperty("straddlePrices");
		    	ClassLoader classLoader = getClass().getClassLoader();
		    	File file = new File(classLoader.getResource(straddlePricesFile).getFile());
		    	
		    	String fileToBeRead = getClass().getClassLoader().getResource(straddlePricesFile).getPath();

		    	fileToBeRead = fileToBeRead.replace("%20", " ");
		    	//fileToBeRead = "c:\\Java Projects\\HistoryAnalyser\\target\\test-classes\\straddlePrices.csv";
		    	try 
		    	{
		    		CSVReader reader = null;
		    		reader = new CSVReader(new FileReader(fileToBeRead));
		            String[] line;       
		            line = reader.readNext();

		            return line [0];

		    	}
		        catch (IOException e) {
		            e.printStackTrace();
		        }
		    	
		    	return "";
		    }
		    
		    public static String getProperty(String key)
		    {
		    	return prop.getProperty(key);
		    	
		    }

}
