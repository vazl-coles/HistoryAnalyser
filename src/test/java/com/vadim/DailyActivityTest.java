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


import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DailyActivityTest {
	
	  @Test
	  public void testDates() {
		  
      	/*
          System.out.println("date= " + line[0] 
          		+ ", open= " + line[1] 
          		+" , high= " + line[2] 
          		+" , low= " + line[3]
          		+" , close= " + line[4]
          		+" , volume= " + line[5]);
date= 23/11/2016, day= Saturday, week number= 8, open= 219.98 , high= 220.76 , l
ow= 219.75 , close= 220.7 , volume= 5.66202E7 , weekly MA = 219.845
Last week=8 First week=7
Week number=8 Close=221.52
          		*/

		  DailyActivity day = new DailyActivity("23/11/2016", "219.98", "220.76", "219.75", "220.7", "5.66202E7", "12");
		  assertEquals(day.getDayOfWeek(), "Wednesday");
		  assertEquals(day.getStringDate(), "23/11/2016");
		  
		  //assertEquals(getWeekDay("23/11/2016"), "Wednesday");

	  }
	  
	  private String getWeekDay(String date)
	  {
		  Date dt1 = null;
		  SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy", Locale.US);
		  try {
			  dt1=ft.parse(date);
			  
		  }
		  catch (ParseException e) {
	             System.out.println("Unparseable (" + date + ")using " + ft);
	        }

		  DateFormat format2=new SimpleDateFormat("EEEE", Locale.US); 
		  String dayOfWeek=format2.format(dt1);
		  
	      return dayOfWeek;
	  }

}
