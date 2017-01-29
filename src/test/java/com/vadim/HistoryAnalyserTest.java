package com.vadim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;

import com.opencsv.CSVReader;

import java.util.Collections;
import java.util.Properties;
import java.io.InputStream;

public class HistoryAnalyserTest {
	
	Properties prop = new Properties();
	
	  @Test
	  public void testReadingFile() {

		  String result = getFile("spy history.csv");
	      System.out.print("Return Value :" );
	      //System.out.println(result.substring(0, 100) );
		  System.out.println(result);
		  assertEquals(result, "Date");

	  }

	  @Test
	  public void testReadingPropertiesFile() 
	  {
		  Properties prop = new Properties();
		  InputStream input = null;
		  String testFileName="";
		  String fileName="";

		  try
		  {
			  String filename = "config.properties";
			  prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
			  testFileName = prop.getProperty("testCsvFile");
			  //System.out.println(testFileName);
			  //fileName = prop.getProperty("csvFile");
			  //System.out.println(fileName);
		  }
		  catch (IOException ex) 
		  {
			  ex.printStackTrace();
	      }
		  
		  //Confirm that the file located under test\resources folder can also be read
		  String result = getFile(testFileName);
		  assertEquals(result, "Date");
		  
		  //Confirm that the full path name can also be the way to read a file
		  /*
		  result = readFile(fileName);
		  assertEquals(result, "Date");
		  */

	  }
	  
	  /*
	  @Test
	  public void testCalculatingMA() throws Exception 
	  {
		  readProperties();
		  HistoryAnalyser.csvFile = getFileNameIncludingPath(prop.getProperty("csvFile"));
		  HistoryAnalyser.csvFileVIX = getFileNameIncludingPath(prop.getProperty("csvFileVIX"));
		  HistoryAnalyser.csvFileOut = getFileNameIncludingPath(prop.getProperty("csvFileOut"));
		  HistoryAnalyser.numberOfWeeks = 3;
		  HistoryAnalyser.prop = prop;
		  HistoryAnalyser.readPriceHistory();
	      Collections.sort(HistoryAnalyser.days );
	      HistoryAnalyser.addIndicators();
	      //Predictor.performHistoricalProfitabilityAnalysis(HistoryAnalyser.days.size()-1);


	  }
	  */
	  
	  @Test
	  public void testPredictor() throws Exception 
	  {
		  readProperties();
		  HistoryAnalyser.csvFile = getFileNameIncludingPath(prop.getProperty("csvFile"));
		  HistoryAnalyser.csvFileVIX = getFileNameIncludingPath(prop.getProperty("csvFileVIX"));
		  HistoryAnalyser.csvFileOut = getFileNameIncludingPath(prop.getProperty("csvFileOut"));
		  HistoryAnalyser.numberOfWeeks = 3;
		  HistoryAnalyser.prop = prop;
		  HistoryAnalyser.readPriceHistory();
	      Collections.sort(HistoryAnalyser.days );
	      HistoryAnalyser.addIndicators();
	      
	      PropertyHelper.readProperties();
	      Predictor.performHistoricalProfitabilityAnalysis(HistoryAnalyser.days.size()-1);


	  }
	  
	  private String getFileNameIncludingPath(String fileName)
	  {
		  String result = "";
		  
		  //Get file from resources folder
		  ClassLoader classLoader = getClass().getClassLoader();
	      CSVReader reader = null;
	      String[] line;

	      System.out.println("getFileNameIncludingPath:" + fileName);
	    	  //reader = new CSVReader(new FileReader("d:\\Java Projects\\HistoryAnalyser\\target\\test-classes\\spy history.csv"));
	    	  //line = reader.readNext();
	    	  
	    	  String f = classLoader.getResource(fileName).getFile();
	    	  System.out.println("getFileNameIncludingPath :" + f);
	    	  f = f.replace("%20", " "); 

	          
	          return f;
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
	  
		  private String getFile(String fileName)
		  {
			  String result = "";
			  
			  //Get file from resources folder
			  ClassLoader classLoader = getClass().getClassLoader();
		      CSVReader reader = null;
		      String[] line;
		      try {
		    	  //reader = new CSVReader(new FileReader("d:\\Java Projects\\HistoryAnalyser\\target\\test-classes\\spy history.csv"));
		    	  //line = reader.readNext();
		    	  System.out.println("getFile:" + fileName);
		    	  String f = classLoader.getResource(fileName).getFile();
		    	  f = f.replace("%20", " "); 
		    	  System.out.println("getFile : " + f);
		          reader = new CSVReader(new FileReader(f));
		          line = reader.readNext();
		          
		          return line[0];
		      }  
		      catch (IOException e) {
		          e.printStackTrace();
		      }
			  
			  /*
			  File file = new File(classLoader.getResource(fileName).getFile());
			  try 
			  {
				  result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
				  
			  } 
			  catch (IOException e) 
			  {
				  e.printStackTrace();
			  }
			  */
			  return result;
		  }

	  private String readFile(String fileName)
	  {
	      CSVReader reader = null;
	      CSVReader readerVIX = null;
	      String[] line;
	      try {
	    	  //fileName = "d:\\temp\\historical data\\spy history.csv";
	    	  System.out.println("Reading " + fileName);
	          reader = new CSVReader(new FileReader(fileName));
	          line = reader.readNext();
	          return line[0];
	      }  
	      catch (IOException e) {
	          e.printStackTrace();
	      }
	      
	      return null;

	  }

}
