package com.vadim;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyHelper {
	static Properties prop = new Properties();
	
    public static void readProperties()
    {
		  InputStream input = null;

		  try
		  {
			  input = new FileInputStream("config.properties");
			  prop.load(input);
		  }
		  catch (IOException ex) 
		  {
			  ex.printStackTrace();
	      }
    }
    
    public static String getProperty(String key)
    {
    	return prop.getProperty(key);
    	
    }
    
    public static void  setProperty(String key, String keyValue)
    {
    	prop.setProperty(key, keyValue);
    	
    }

}
