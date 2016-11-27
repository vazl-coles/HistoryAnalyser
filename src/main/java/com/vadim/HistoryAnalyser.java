package com.vadim;

import java.util.*;

import com.opencsv.CSVReader;
import java.io.FileWriter;

import java.io.FileReader;
import java.io.IOException;

public class HistoryAnalyser {
	
	static String csvFile = "c:\\Java Projects\\Historical Data\\spy_yahoo.csv";
	static String csvFileOut = "c:\\Java Projects\\Historical Data\\spyNew.csv";
	static List<DailyActivity> days = new ArrayList<DailyActivity>();

    public static void main(String[] args) throws Exception {
       
    	readPriceHistory();
        Collections.sort(days );
        addIndicators();
        
        // Make predictions based on history
        Predictor.makePrediction(days.size()-1);

    }
    
    public static void readPriceHistory()
    {
    	
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            while ((line = reader.readNext()) != null) {
            	/*
                System.out.println("date= " + line[0] 
                		+ ", open= " + line[1] 
                		+" , high= " + line[2] 
                		+" , low= " + line[3]
                		+" , close= " + line[4]
                		+" , volume= " + line[5]);
				*/
            	if (line[1].contains("Open") || line[5].equals("0"))
            	{
            		
            	}
            	else
            	{
            		DailyActivity day = new DailyActivity(line[0], line[1], line[2], line[3], line[4], line[5]);
            		days.add(day);
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
    	        float weeklyMA50;
    	        
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
    	        	weeklyMA50 = MAcalculator.calculateMA("Weekly", "Close", 50, weekNumber);
    	        	days.get(i).setWeeklyMA50(weeklyMA50);
    	        	
    	        	
    	            System.out.println("date= " + days.get(i).getStringDate()
    	            		+ ", day= " + days.get(i).getDayOfWeek()
    	            		+ ", week number= " + days.get(i).getStringWeekNumber()
    	            		+ ", open= " + days.get(i).getOpen() 
    	            		+" , high= " + days.get(i).getHigh() 
    	            		+" , low= " + days.get(i).getLow()
    	            		+" , close= " + days.get(i).getClose()
    	            		+" , volume= " + days.get(i).getVolume()
    	            		+" , weekly MA50 = " + days.get(i).getWeeklyMA50());
    	            
    	            CSVUtils.writeLine(writer, Arrays.asList(
    	            		days.get(i).getStringDate(),
    	            		days.get(i).getDayOfWeek(),
    	            		days.get(i).getStringWeekNumber(),
    	            		days.get(i).getStringOpen(),
    	            		days.get(i).getStringHigh(),
    	            		days.get(i).getStringLow(),
    	            		days.get(i).getStringClose(),
    	            		days.get(i).getStringVolume(),
    	            		days.get(i).getStringWeeklyMA50()
    	            		));
    	            		
    	            		
    	        }


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
