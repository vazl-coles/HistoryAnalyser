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
    	        float resistance=0;
    	        float support=0;
    	        
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
    	        	/*
    	        	if (weekNumber > 1250)
    	        		System.out.println("Test=(i)" + days.get(i).getWeekNumber() + " "+ i + " " + days.get(i).getStringDate());
    	        	*/
    	        	//weeklyMA = calculateMA("Weekly", "Close", numberOfWeeks, weekNumber);
    	        	// weeklyMA = calculateMA("Weekly", "Close", numberOfWeeks, i);
    	        	weeklyMA = calculateMA("Daily", "Close", 250, i);
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
    	        	
    	        	resistance = calculateResistance(200,i );
    	        	
    	        	days.get(i).setResistance(resistance);
    	        	
    	        	support = calculateSupport(100,i );
    	        	
    	        	days.get(i).setSupport(support);
    	        	
    	        	days.get(i).setMarketPhase("initial");
					if (i > 500)
					{
						if (days.get(i-1).getMarketPhase().equals("bull"))
						{
							if((History.days.get(i).getWeeklyMA() - History.days.get(i-80).getWeeklyMA()) < (History.days.get(i- 80).getWeeklyMA() - History.days.get(i-160).getWeeklyMA()))
							{
								//System.out.println("trendless from bull Todays ma " + History.days.get(i).getWeeklyMA() + " ma-80 " + History.days.get(i-80).getWeeklyMA() + " ma-160 " + History.days.get(i-160).getWeeklyMA() );
								days.get(i).setMarketPhase("needsMoreBuyers");
								System.out.println(History.days.get(i).getStringDate() + " Phase " + days.get(i).getMarketPhase());
								//System.out.println("Moved from trendless to bear " + History.days.get(i).getStringDate());
								//System.out.println(History.days.get(i).getClose() + " Todays ma " + History.days.get(i).getWeeklyMA() + " Yesterdays ma " + History.days.get(i-1).getWeeklyMA() + " day before " + History.days.get(i-2).getWeeklyMA() );
								resistance = 0;
							}
							else
							{
								days.get(i).setMarketPhase("bull");
							}
						}
						else if (days.get(i-1).getMarketPhase().equals("bear"))
						{
							if((History.days.get(i-40).getWeeklyMA() - History.days.get(i).getWeeklyMA()) < (History.days.get(i- 80).getWeeklyMA() - History.days.get(i-40).getWeeklyMA()))
							{
								days.get(i).setMarketPhase("hopeful");
								System.out.println(History.days.get(i).getStringDate() + " Phase " + days.get(i).getMarketPhase());
								//System.out.println("Moved from trendless to bear " + History.days.get(i).getStringDate());
								//System.out.println(History.days.get(i).getClose() + " Todays ma " + History.days.get(i).getWeeklyMA() + " Yesterdays ma " + History.days.get(i-1).getWeeklyMA() + " day before " + History.days.get(i-2).getWeeklyMA() );
							}
							else
							{
								days.get(i).setMarketPhase("bear");
							}
						}
						else if (days.get(i-1).getMarketPhase().equals("needsMoreBuyers"))
						{
							if (days.get(i).getWeeklyMA() > days.get(i-80).getWeeklyMA())
							{
								if((History.days.get(i).getWeeklyMA() - History.days.get(i-80).getWeeklyMA()) > (History.days.get(i- 80).getWeeklyMA() - History.days.get(i-160).getWeeklyMA()))
								{
									//System.out.println("bull from trendless Todays ma " + History.days.get(i).getWeeklyMA() + " ma-80 " + History.days.get(i-80).getWeeklyMA() + " ma-160 " + History.days.get(i-160).getWeeklyMA() );
									days.get(i).setMarketPhase("bull");
									System.out.println(History.days.get(i).getStringDate() + " Phase " + days.get(i).getMarketPhase());
								}
								else
								{
									days.get(i).setMarketPhase("needsMoreBuyers");
								}
							}
							else
							{
								if((History.days.get(i-40).getWeeklyMA() - History.days.get(i).getWeeklyMA()) > (History.days.get(i- 80).getWeeklyMA() - History.days.get(i-40).getWeeklyMA()))
								{
									days.get(i).setMarketPhase("bear");
									System.out.println(History.days.get(i).getStringDate() + " Phase " + days.get(i).getMarketPhase());
								}
								else
								{
									days.get(i).setMarketPhase("hopeful");
								}
							}
						}
						else if (days.get(i-1).getMarketPhase().equals("hopeful"))
						{
							if (days.get(i).getWeeklyMA() > days.get(i-80).getWeeklyMA())
							{
								if((History.days.get(i).getWeeklyMA() - History.days.get(i-80).getWeeklyMA()) > (History.days.get(i- 80).getWeeklyMA() - History.days.get(i-160).getWeeklyMA()))
								{
									//System.out.println("bull from trendless Todays ma " + History.days.get(i).getWeeklyMA() + " ma-80 " + History.days.get(i-80).getWeeklyMA() + " ma-160 " + History.days.get(i-160).getWeeklyMA() );
									days.get(i).setMarketPhase("bull");
									System.out.println(History.days.get(i).getStringDate() + " Phase " + days.get(i).getMarketPhase());
								}
								else
								{
									days.get(i).setMarketPhase("needsMoreBuyers");
								}
							}
							else
							{
								if((History.days.get(i-40).getWeeklyMA() - History.days.get(i).getWeeklyMA()) > (History.days.get(i- 80).getWeeklyMA() - History.days.get(i-40).getWeeklyMA()))
								{
									days.get(i).setMarketPhase("bear");
									System.out.println(History.days.get(i).getStringDate() + " Phase " + days.get(i).getMarketPhase());
								}
								else
								{
									days.get(i).setMarketPhase("hopeful");
								}
							}
						}
						else // initial
						{
							if (History.days.get(i).getWeeklyMA() > History.days.get(i-1).getWeeklyMA())
							{
								days.get(i).setMarketPhase("bull");
							}
							else
								days.get(i).setMarketPhase("bear");
						}
						//System.out.println(History.days.get(i).getStringDate() + " Phase " + days.get(i).getMarketPhase());
						
						/*
						if ( History.days.get(i).getWeeklyMA() > History.days.get(i-81).getWeeklyMA() && History.days.get(i-1).getWeeklyMA() < History.days.get(i-2).getWeeklyMA())
						{
							days.get(i).setMarketPhase("trendless");
							//System.out.println("Moved from bear to trendless " + History.days.get(i).getStringDate());
							//System.out.println(History.days.get(i).getClose() + " Todays ma " + History.days.get(i).getWeeklyMA() + " Yesterdays ma " + History.days.get(i-1).getWeeklyMA() + " day before " + History.days.get(i-2).getWeeklyMA() );
						}
						else
						if (History.days.get(i).getWeeklyMA() < History.days.get(i-1).getWeeklyMA() && History.days.get(i-1).getWeeklyMA() > History.days.get(i-2).getWeeklyMA())
						{
							days.get(i).setMarketPhase("trendless");
							//System.out.println("Moved from bull to trendless on " + History.days.get(i).getStringDate());
							//System.out.println(History.days.get(i).getClose() + " Todays ma " + History.days.get(i).getWeeklyMA() + " Yesterdays ma " + History.days.get(i-1).getWeeklyMA() + " day before " + History.days.get(i-2).getWeeklyMA() );
						}
						else if (days.get(i-1).getMarketPhase().equals("trendless"))
						{
							if (History.days.get(i).getWeeklyMA() < History.days.get(i-1).getWeeklyMA() && History.days.get(i-1).getWeeklyMA() < History.days.get(i-2).getWeeklyMA())
							{
								if((History.days.get(i-1).getWeeklyMA() - History.days.get(i).getWeeklyMA()) > (History.days.get(i-2).getWeeklyMA() - History.days.get(i-1).getWeeklyMA()))
								{
									days.get(i).setMarketPhase("bear");
									//System.out.println("Moved from trendless to bear " + History.days.get(i).getStringDate());
									//System.out.println(History.days.get(i).getClose() + " Todays ma " + History.days.get(i).getWeeklyMA() + " Yesterdays ma " + History.days.get(i-1).getWeeklyMA() + " day before " + History.days.get(i-2).getWeeklyMA() );
									resistance = 0;
								}
							}
							else if (History.days.get(i).getWeeklyMA() > History.days.get(i-1).getWeeklyMA() && History.days.get(i-1).getWeeklyMA() > History.days.get(i-2).getWeeklyMA())
							{
								if((History.days.get(i).getWeeklyMA() - History.days.get(i-1).getWeeklyMA()) > (History.days.get(i-1).getWeeklyMA() - History.days.get(i-2).getWeeklyMA()))
								{
									days.get(i).setMarketPhase("bull");
									//System.out.println("Moved from trendless to bear " + History.days.get(i).getStringDate());
									//System.out.println(History.days.get(i).getClose() + " Todays ma " + History.days.get(i).getWeeklyMA() + " Yesterdays ma " + History.days.get(i-1).getWeeklyMA() + " day before " + History.days.get(i-2).getWeeklyMA() );
									support = 0;
								}
							}
							
						}
						else
						{
							days.get(i).setMarketPhase(days.get(i-1).getMarketPhase());
						}

						if (days.get(i).getMarketPhase().equals(days.get(i-1).getMarketPhase()) )
						{
							
						}
						else
						{
							System.out.println("Moved from " + days.get(i-1).getMarketPhase() + " to " + days.get(i).getMarketPhase() +  " on " + History.days.get(i).getStringDate());
						}
						
						*/
						/*
						if (days.get(i).getMarketPhase().equals("bull"))
						{
		    	        	if (ma50 > resistance)
		    	        		resistance = ma50;
		    	        	support = 0;
						}
						else if (days.get(i).getMarketPhase().equals("bear"))
						{
		    	        	if (ma50 < support)
		    	        		support = ma50;
		    	        	resistance = 0;
						}
						else
						{
		    	        	if (ma50 > resistance)
		    	        		resistance = ma50;
		    	        	if (ma50 < support || support < 10)
		    	        		support = ma50;		    	        	
						}
	    	        	*/
	    	        	//System.out.println(days.get(i).getStringDate() + " Resistance=" + resistance + " Support =" + support);
						
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
    	            		days.get(i).getNumberOfDaysSinceMA50CrossString(),
    	            		days.get(i).getMarketPhase(),
    	            		days.get(i).getResistanceString(),
    	            		days.get(i).getSupportString()
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
		if (typeOfMA.equals("Test"))
		{
    	
			i = days.size() - 1;// Last entry in the list
			//i = i -2; // Last entry still does not have week set 
			// Find entry in the list which has week number set to lastEntry -1.
			// It is important to subtract 1 because last week could be incomplete which would result in differences in MA50 values for successive days 
			while (i > 0)
			{
				if (days.get(i).getWeekNumber() == (lastEntry-1))
					break;
				i = i -1;
			}
			
			int lastWeek = days.get(i).getWeekNumber();
			int firstWeek = lastWeek - numOfEntries+1;
			//if (debug)
				//System.out.println("Last week=" + lastWeek + " First week=" + firstWeek); 
			//if (lastWeek > 1250)
				//System.out.println("Last week=" + lastWeek + " First week=" + firstWeek); 
			
			int alreadyProcessedWeek = days.get(i).getWeekNumber()+1;
			//System.out.println("Last week=" + lastWeek + " First week=" + firstWeek + " Last entry= " + lastEntry + " Size= " + days.size() );
			while (alreadyProcessedWeek > firstWeek && i > 0)
			{
				/*
		    	if (lastWeek > 1250)
		    		System.out.println("Test=(i)" + days.get(i).getWeekNumber() + " "+ i + " " + days.get(i).getStringDate() + " already processed="+alreadyProcessedWeek);
		    	*/
				/*
				if (lastWeek >= 1220)
				{
					System.out.println("Week number=" + days.get(i).getWeekNumber() + " " + days.get(i).getStringDate());
				}
				*/
				if (days.get(i).getWeekNumber() == alreadyProcessedWeek)
				{
					
				}
				else
				{
					//if (debug)
						//System.out.println("Week number=" + HistoryAnalyser.days.get(i).getWeekNumber() + " Close=" + HistoryAnalyser.days.get(i).getClose());
					//if (lastWeek > 1250) 
						//System.out.println("Adding " + days.get(i).getClose()+ " to total " + total + " " + days.get(i).getStringDate());
					total = total + days.get(i).getClose();
					//if (lastWeek > 825)
						//System.out.println("total = " + total + " Close = " + HistoryAnalyser.getClose(i) + " Week= " + HistoryAnalyser.days.get(i).getWeekNumber());
					alreadyProcessedWeek = days.get(i).getWeekNumber();
			    	//if (lastWeek > 1250)
			    		//System.out.println("Test=(i)" + days.get(i).getWeekNumber() + " "+ i + " " + days.get(i).getStringDate() + " already processed="+alreadyProcessedWeek);
				}
				i = i - 1;
			}
		}
		else if (typeOfMA.equals("Daily"))
		{
			for (i = lastEntry; i > lastEntry - numOfEntries; i-- )
			{
				if ( i < 0) break;
				total = total + days.get(i).getClose();
			}
		}
		else
		{
			for (i = lastEntry; i > lastEntry - numOfEntries*5; i = i -5 )
			{
				if ( i < 0) break;
				total = total + days.get(i).getClose();
				if (days.get(i).getClose() > 200) System.out.println("Close " + days.get(i).getClose() + " " + total + " " + numOfEntries + " " + days.get(i).getStringDate() + " " + lastEntry ) ;
			}
		}
		average = total/numOfEntries;
		if (debug)
			System.out.println("Moving average=" + average);
		
		//if (lastEntry > 6070) System.out.println("Moving average " + average + " " + total + " " + numOfEntries) ;
		
		return average;
		
	}
	
	private static float calculateResistance( int numOfEntries, int lastEntry)
	{
		int i=0;
		float resistance=0;
		boolean debug=false;


		for (i = lastEntry; i > lastEntry - numOfEntries; i-- )
		{
			if ( i < 0) break;
			if (resistance < days.get(i).getClose())
				resistance = days.get(i).getClose();
		}
		
		return resistance;
		
	}
	
	private static float calculateSupport( int numOfEntries, int lastEntry)
	{
		int i=0;
		float support=0;
		boolean debug=false;
		
		for (i = lastEntry; i > lastEntry - numOfEntries; i-- )
		{
			if ( i < 0) break;
			if (support == 0)
				support = days.get(i).getClose();
			if (support > days.get(i).getClose())
				support = days.get(i).getClose();
		}
		
		return support;
		
	}

}

