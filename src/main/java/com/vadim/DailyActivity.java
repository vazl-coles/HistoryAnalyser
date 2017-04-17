package com.vadim;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;

public class DailyActivity implements /*Comparator<DailyActivity> ,*/ Comparable<DailyActivity> {
	
	private Date date;
	private String stringDate;
	private long dateForSort;
	private String dayOfWeek;
	private int weekNumber;
	private float open;
	private float high;
	private float low;
	private float close;
	private float volume;
	private float vix;
	private float weeklyMA;
	private float ma50;
	private int numberOfDaysSinceMA50Cross;
	private String marketPhase;

	
	public DailyActivity(String date, String open, String high, String low, String close, String volume, String vix)
	{
		setDate(date);
		setOpen(open);
		setHigh(high);
		setLow(low);
		setClose(close);
		setVolume(volume);
		setVIX(vix);
	}
	
	public DailyActivity()
	{
		
	}
	
	public void setDate(String date)
	{
		SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy", Locale.US);
        try {
             this.date = ft.parse(date);
             DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
             this.stringDate = dateFormat.format(this.date);
             this.dateForSort = this.date.getTime();
        }catch (ParseException e) {
             System.out.println("Unparseable (" + date + ")using " + ft);
        }
        
        DateFormat format2=new SimpleDateFormat("EEEE", Locale.US); 
        this.dayOfWeek=format2.format(this.date);
        //System.out.println("Day="+this.dayOfWeek);
	}
	
	public Date getDate()
	{
		return this.date;
	}
	
	public long getDateForSort()
	{
		return this.dateForSort;
	}
	
	public String getStringDateForSort()
	{
		return String.valueOf(this.dateForSort);
	}
	
	public String getStringDate()
	{
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        
        //to convert Date to String, use format method of SimpleDateFormat class.
        String strDate = dateFormat.format(this.date);
        return strDate;
	}
	
	public String getDayOfWeek()
	{
		return this.dayOfWeek;
	}
	
	public void setWeekNumber(int weekNumber)
	{
		this.weekNumber = weekNumber;

	}
	
	public String getStringWeekNumber()
	{
		return String.valueOf(this.weekNumber);
	}
	
	public int getWeekNumber()
	{
		return this.weekNumber;
	}
	
	public void setOpen(String open)
	{
		this.open = Float.parseFloat(open);

	}
	
	public String getStringOpen()
	{
		return String.valueOf(this.open);
	}
	
	public float getOpen()
	{
		return this.open;
	}
	
	public void setHigh(String high)
	{
		this.high = Float.parseFloat(high);

	}
	
	public String getStringHigh()
	{
		return String.valueOf(this.high);
	}
	
	public float getHigh()
	{
		return this.high;
	}

	public void setLow(String low)
	{
		this.low = Float.parseFloat(low);
	}
	
	public String getStringLow()
	{
		return String.valueOf(this.low);
	}
	
	public float getLow()
	{
		return this.low;
	}
	
	public void setClose(String close)
	{
		this.close = Float.parseFloat(close);
	}
	
	public String getStringClose()
	{
		return String.valueOf(this.close);
	}
	
	public float getClose()
	{
		return this.close;
	}
	
	public void setVolume(String volume)
	{
		this.volume = Float.parseFloat(volume);
	}
	
	public String getStringVolume()
	{
		return String.valueOf(this.volume);
	}
	
	public float getVolume()
	{
		return this.volume;
	}
	
	public void setVIX(String vix)
	{
		this.vix = Float.parseFloat(vix);
	}
	
	public String getStringVIX()
	{
		return String.valueOf(this.vix);
	}
	
	public float getVIX()
	{
		return this.vix;
	}
	
	public void setWeeklyMA(float weeklyMA)
	{
		this.weeklyMA = weeklyMA;
	}
	
	public String getStringWeeklyMA()
	{
		return String.valueOf(this.weeklyMA);
	}
	
	public float getWeeklyMA()
	{
		return this.weeklyMA;
	}
	
	public void setMA50(float ma50)
	{
		this.ma50 = ma50;
	}
	
	public String getMA50String()
	{
		return String.valueOf(this.ma50);
	}
	
	public float getMA50()
	{
		return this.ma50;
	}
	
	public void setNumberOfDaysSinceMA50Cross(int numberOfDaysSinceMA50Cross)
	{
		this.numberOfDaysSinceMA50Cross = numberOfDaysSinceMA50Cross;
	}
	
	public String getNumberOfDaysSinceMA50CrossString()
	{
		return String.valueOf(this.numberOfDaysSinceMA50Cross);
	}
	
	public int getNumberOfDaysSinceMA50Cross()
	{
		return this.numberOfDaysSinceMA50Cross;
	}
	
	public void setMarketPhase(String marketPhase)
	{
		this.marketPhase = marketPhase;
	}
	
	public String getMarketPhase()
	{
		return this.marketPhase;
	}
	
	
	// Overriding the compare method to sort by date 
	public int compare(DailyActivity d, DailyActivity d1) 
	{
		//System.out.println("this.date "+ d.getDate() + " compare to " + d1.getDate());
		long n1 = d.getDateForSort();
		long n2 = d1.getDateForSort();
		if (n1 < n2)
		{
			System.out.println("before");
			return -1;			
		}
		else if (n1 > n2)
		{
			System.out.println("after");
			return 1;
		}
		else return 0;
	}
	
	public int compareTo(DailyActivity day) {

		//ascending order
		return this.stringDate.compareTo(day.stringDate);
	}
	
	/*
	public int compareTo(DailyActivity day) 
	{

		//ascending order
		System.out.println("this.date "+ this.date + " compare to " + day.getDate());
		long result = this.dateForSort - day.getDateForSort();
		if (result >0 )
		{
			System.out.println("before");
			return 1;
			
		}
		else
			if (result < 0)
			{
				System.out.println("after");
				return -1;
			}
			else
				return 0;

		
		//return result;
	}
	*/
}
