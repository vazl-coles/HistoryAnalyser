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

public class History {
	
	static String csvFile = "c:\\Java Projects\\Historical Data\\spy history.csv";
	static String csvFileVIX = "c:\\Java Projects\\Historical Data\\vix history.csv";
	static String csvFileOut = "c:\\Java Projects\\Historical Data\\spyNew.csv";
	static List<DailyActivity> days = new ArrayList<DailyActivity>();
	
	static Properties prop = new Properties();
	static int numberOfWeeks=50;
	
    public static void init()
    {
		csvFile = PropertyHelper.getProperty("csvFile");
		csvFileVIX = PropertyHelper.getProperty("csvFileVIX");
		csvFileOut = PropertyHelper.getProperty("csvFileOut");
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
        
        Collections.sort(days );
    	
    }
    
    public static void addIndicators() throws Exception
    {
    	FileWriter writer = new FileWriter(csvFileOut);
    	
    	try
    	{
    	        int weekNumber=0;
    	        float weeklyMA;
    	        float ma50;
    	        int numberOfDaysSinceMA50Cross;
    	        
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
    	        	weeklyMA = calculateMA("Weekly", "Close", numberOfWeeks, weekNumber);
    	        	days.get(i).setWeeklyMA(weeklyMA);
    	        	
    	        	ma50 = calculateMA("Daily", "Close", 50, i);
    	        	days.get(i).setMA50(ma50);
    	        	
    	        	if (ma50 == 0)
    	        	{
    	        		numberOfDaysSinceMA50Cross = 0;
    	        		days.get(i).setNumberOfDaysSinceMA50Cross(0);
    	        	}
    	        	else
    	        	{
    	        		if (days.get(i).getMA50() > days.get(i).getClose() && days.get(i-1).getMA50() > days.get(i-1).getClose())
    	        		{
    	        			days.get(i).setNumberOfDaysSinceMA50Cross(days.get(i-1).getNumberOfDaysSinceMA50Cross() + 1);
    	        		}
    	        		else if (days.get(i).getMA50() < days.get(i).getClose() && days.get(i-1).getMA50() < days.get(i-1).getClose())
        	        	{
        	        		days.get(i).setNumberOfDaysSinceMA50Cross(days.get(i-1).getNumberOfDaysSinceMA50Cross() + 1);
        	        	}
    	        		else if (days.get(i).getMA50() < days.get(i).getClose() && days.get(i-1).getMA50() > days.get(i-1).getClose())
    	        		{
    	        			// MA50 cross
    	        			days.get(i).setNumberOfDaysSinceMA50Cross(0);
    	        		}
    	        		else if (days.get(i).getMA50() > days.get(i).getClose() && days.get(i-1).getMA50() < days.get(i-1).getClose())
    	        		{
    	        			// MA50 cross
    	        			days.get(i).setNumberOfDaysSinceMA50Cross(0);
    	        		}
    	        		else if (days.get(i-1).getNumberOfDaysSinceMA50Cross() == 0)
    	        		{
    	        			days.get(i).setNumberOfDaysSinceMA50Cross(1);
    	        		}
    	        		else
    	        		{
    	        			days.get(i).setNumberOfDaysSinceMA50Cross(0);
    	        		}
    	        		
    	        	}
    	        	
    	        	/*
    	            System.out.println("date= " + days.get(i).getStringDate()
    	            		+ ", day= " + days.get(i).getDayOfWeek()
    	            		+ ", week number= " + days.get(i).getStringWeekNumber()
    	            		+ ", open= " + days.get(i).getOpen() 
    	            		+" , high= " + days.get(i).getHigh() 
    	            		+" , low= " + days.get(i).getLow()
    	            		+" , close= " + days.get(i).getClose()
    	            		+" , volume= " + days.get(i).getVolume()
    	            		+" , weekly MA = " + days.get(i).getWeeklyMA());
    	            */
    	            
    	            CSVUtils.writeLine(writer, Arrays.asList(
    	            		days.get(i).getStringDate(),
    	            		days.get(i).getDayOfWeek(),
    	            		days.get(i).getStringWeekNumber(),
    	            		days.get(i).getStringOpen(),
    	            		days.get(i).getStringHigh(),
    	            		days.get(i).getStringLow(),
    	            		days.get(i).getStringClose(),
    	            		days.get(i).getStringVolume(),
    	            		days.get(i).getStringWeeklyMA(),
    	            		days.get(i).getMA50String(),
    	            		days.get(i).getNumberOfDaysSinceMA50CrossString()
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
    
    public static int getLastDay()
    {
    	return days.size();
    }
    
	public static float calculateMA(String typeOfMA, String TypeOfIndicator, int numOfEntries, int lastEntry)
	{

			if (lastEntry < numOfEntries)
			{
				return 0;
			}
			else
			{
				//System.out.println("Calculate MA for week = " + lastEntry);
				return calculateAverage(typeOfMA, TypeOfIndicator, numOfEntries, lastEntry);
			}
			
		
	}
	
	private static float calculateAverage(String typeOfMA, String TypeOfIndicator, int numOfEntries, int lastEntry)
	{
		int i=0;
		float total=0;
		boolean debug=false;
		float average;
		
		if (numOfEntries < 5) // If moving average is based on the number of entries which is less than 5 
			debug = true;     // we can assume that this is called as part of unit test  
		if (typeOfMA.equals("Weekly"))
		{
			i = days.size() - 1;// Last entry in the list
			//i = i -2; // Last entry still does not have week set 
			// Find entry in the list which has week number set to lastEntry
			while (i > 0)
			{
				if (days.get(i).getWeekNumber() == lastEntry)
					break;
				i = i -1;
			}
			
			int lastWeek = days.get(i).getWeekNumber();
			int firstWeek = lastWeek - numOfEntries+1;
			//if (debug)
				//System.out.println("Last week=" + lastWeek + " First week=" + firstWeek); 
			int alreadyProcessedWeek = days.get(i).getWeekNumber()+1;
			//System.out.println("Last week=" + lastWeek + " First week=" + firstWeek + " Last entry= " + lastEntry + " Size= " + HistoryAnalyser.days.size() );
			while (alreadyProcessedWeek > firstWeek && i > 0)
			{
				if (days.get(i).getWeekNumber() == alreadyProcessedWeek)
				{
					
				}
				else
				{
					//if (debug)
						//System.out.println("Week number=" + HistoryAnalyser.days.get(i).getWeekNumber() + " Close=" + HistoryAnalyser.days.get(i).getClose());
					total = total + days.get(i).getClose();
					//if (lastWeek > 825)
						//System.out.println("total = " + total + " Close = " + HistoryAnalyser.getClose(i) + " Week= " + HistoryAnalyser.days.get(i).getWeekNumber());
					alreadyProcessedWeek = days.get(i).getWeekNumber();
				}
				i = i - 1;
			}
		}
		else
		{
			for (i = lastEntry; i > lastEntry - numOfEntries; i-- )
			{
				if ( i < 0) break;
				total = total + days.get(i).getClose();
			}
		}
		average = total/numOfEntries;
		if (debug)
			System.out.println("Moving average=" + average);
		return average;
		
	}

}

