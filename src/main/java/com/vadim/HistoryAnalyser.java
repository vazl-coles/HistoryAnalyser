package com.vadim;

import java.util.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.RollingFileAppender;

import com.opencsv.CSVReader;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class HistoryAnalyser {
	
	static String csvFile = "c:\\Java Projects\\Historical Data\\spy history.csv";
	static String csvFileVIX = "c:\\Java Projects\\Historical Data\\vix history.csv";
	static String csvFileOut = "c:\\Java Projects\\Historical Data\\spyNew.csv";
	static List<DailyActivity> days = new ArrayList<DailyActivity>();
	
	static Properties prop = new Properties();
	static int numberOfWeeks=50;
	
	//private static Logger log;
	static final Logger log = Logger.getLogger("HistoryAnalyser");

    public static void main(String[] args) throws Exception {
       
    	readProperties();
    	init();
    	
    	PropertyConfigurator.configure("log4j.properties");
    	
        log.info("Reading history");
        log.debug("test");
    	
    	readPriceHistory();
        Collections.sort(days );
        addIndicators();
        
        // Make predictions based on history
        Predictor.makePrediction(days.size()-1);
        Predictor.performHistoricalProfitabilityAnalysis(days.size()-1);

    }
    
    public static void readProperties()
    {
		  InputStream input = null;

		  try
		  {
			  input = new FileInputStream("config.properties");
			  prop.load(input);
			  csvFileOut = prop.getProperty("csvFileOut");
		  }
		  catch (IOException ex) 
		  {
			  ex.printStackTrace();
	      }
    }
    
    public static void init()
    {
		csvFile = prop.getProperty("csvFile");
		csvFileVIX = prop.getProperty("csvFileVIX");
    }
    
    public static void readPriceHistory()
    {
    	
        CSVReader reader = null;
        CSVReader readerVIX = null;
                
        try {
            reader = new CSVReader(new FileReader(csvFile));
            readerVIX = new CSVReader(new FileReader(csvFileVIX));
            String[] line;
            String[] lineVIX;
            
            while ((line = reader.readNext()) != null) {
            	if ((lineVIX = readerVIX.readNext()) != null)
            	{
            		
            	}
            	
            	if (line[0].contains("/") || line[0].contains("Date"))
            	{
            		// System.out.println("date= " + line[0] );
            	}
            	else
            	{
            		System.out.println("date= " + line[0] );
            		System.out.println("Method returns : " + line[0].contains("Date"));
            		break;
            	}
            	/*
                System.out.println("date= " + line[0] 
                		+ ", open= " + line[1] 
                		+" , high= " + line[2] 
                		+" , low= " + line[3]
                		+" , close= " + line[4]
                		+" , volume= " + line[5]);
                		*/
				
            	if (line[1].contains("Open") || line[5].equals("0") || line[0].contains("Date"))
            	{
            		// System.out.println("Invalid field, date= " + line[0] );
            	}
            	else
            	{
            		if (line[0].equals(lineVIX[0]))
            		{
            			//System.out.println("date= " + line[0] + " vix date " + lineVIX[0]);
            			DailyActivity day = new DailyActivity(line[0], line[1], line[2], line[3], line[4], line[5], lineVIX[4]);
            			days.add(day);
            		}
            		else
            		{
            			System.out.println("Different dates, stock date= " + line[0] + " vix date " + lineVIX[0]);
            			/*
            			try {
							Thread.sleep(4000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						*/
            			break;
            		}
            	}
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    }
    
    public static void addIndicators() throws Exception
    {
    	FileWriter writer = new FileWriter(csvFileOut);
    	
    	try
    	{
    	        int weekNumber=0;
    	        float weeklyMA;
    	        
    	        for (int i=0;i<days.size();i++)
    	        {
    	        	if ((i+1) <days.size() && i > 0)
    	        	{
    	        		//System.out.println("Day of week="+days.get(i).getDayOfWeek());
    	        		if (days.get(i).getDayOfWeek().equals("Tuesday") && ( !days.get(i-1).getDayOfWeek().equals("Monday")))
    	        		{
    	        			weekNumber++;
    	        			
    	        		}
    	        		else if (days.get(i).getDayOfWeek().equals("Monday"))
    	        		{
    	        			weekNumber++;
    	        		}
    	        		else
    	        		{
    	        			//System.out.println("Day of week="+days.get(i).getDayOfWeek());
    	        		}
    	        		
    	        	}
    	        	days.get(i).setWeekNumber(weekNumber);
    	        	//if (weekNumber > 825)
    	        		//System.out.println("Test=(i)" + HistoryAnalyser.days.get(i).getWeekNumber() + " "+i+" Size =" + HistoryAnalyser.days.size());
    	        	weeklyMA = MAcalculator.calculateMA("Weekly", "Close", numberOfWeeks, weekNumber);
    	        	days.get(i).setWeeklyMA(weeklyMA);
    	        	
    	        	
    	            System.out.println("date= " + days.get(i).getStringDate()
    	            		+ ", day= " + days.get(i).getDayOfWeek()
    	            		+ ", week number= " + days.get(i).getStringWeekNumber()
    	            		+ ", open= " + days.get(i).getOpen() 
    	            		+" , high= " + days.get(i).getHigh() 
    	            		+" , low= " + days.get(i).getLow()
    	            		+" , close= " + days.get(i).getClose()
    	            		+" , volume= " + days.get(i).getVolume()
    	            		+" , weekly MA = " + days.get(i).getWeeklyMA());
    	            
    	            CSVUtils.writeLine(writer, Arrays.asList(
    	            		days.get(i).getStringDate(),
    	            		days.get(i).getDayOfWeek(),
    	            		days.get(i).getStringWeekNumber(),
    	            		days.get(i).getStringOpen(),
    	            		days.get(i).getStringHigh(),
    	            		days.get(i).getStringLow(),
    	            		days.get(i).getStringClose(),
    	            		days.get(i).getStringVolume(),
    	            		days.get(i).getStringWeeklyMA()
    	            		));
    	            		
    	            		
    	        }


    	        System.out.println("Finished writing to: " + csvFileOut);
    	        writer.flush();
    	        writer.close();

    		
    	} catch (IOException e) {
            e.printStackTrace();
        }
    	    	
    }
    
    public static float getClose(int index)
    {
    	return days.get(index).getClose();
    }
    
    public static int getWeekNumber(int index)
    {
    	return days.get(index).getWeekNumber();
    }
    

}
