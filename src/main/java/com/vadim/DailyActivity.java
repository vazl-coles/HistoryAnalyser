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
	private float weeklyMA50;

	
	public DailyActivity(String date, String open, String high, String low, String close, String volume)
	{
		setDate(date);
		setOpen(open);
		setHigh(high);
		setLow(low);
		setClose(close);
		setVolume(volume);
	}
	
	public DailyActivity()
	{
		
	}
	
	public void setDate(String date)
	{
		SimpleDateFormat ft = new SimpleDateFormat ("dd/mm/yyyy", Locale.ENGLISH);
        try {
             this.date = ft.parse(date);
             DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
             this.stringDate = dateFormat.format(this.date);
             this.dateForSort = this.date.getTime();
        }catch (ParseException e) {
             System.out.println("Unparseable (" + date + ")using " + ft);
        }
        
        DateFormat format2=new SimpleDateFormat("EEEE", Locale.ENGLISH); 
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
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
        
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
	
	public void setWeeklyMA50(float weeklyMA50)
	{
		this.weeklyMA50 = weeklyMA50;
	}
	
	public String getStringWeeklyMA50()
	{
		return String.valueOf(this.weeklyMA50);
	}
	
	public float getWeeklyMA50()
	{
		return this.weeklyMA50;
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
