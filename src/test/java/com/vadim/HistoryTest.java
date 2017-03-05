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

public class HistoryTest {
	
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
		  }
		  catch (IOException ex) 
		  {
			  ex.printStackTrace();
	      }
		  
		  //Confirm that the file located under test\resources folder can also be read
		  String result = getFile(testFileName);
		  assertEquals(result, "Date");
		  

	  }
	  
	  private String getFile(String fileName)
	  {
		  String result = "";
		  
		  //Get file from resources folder
		  ClassLoader classLoader = getClass().getClassLoader();
	      CSVReader reader = null;
	      String[] line;
	      try {
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
		  
		  return result;
	  }

}
