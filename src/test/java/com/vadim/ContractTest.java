package com.vadim;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

public class ContractTest {

	  @Test
	  public void testOption() {

		  Option option = new Option("Put", "SPY", 1, (float)220.76, "23/11/2016", (float)220.7);
		  assertEquals(220.76, (Math.round(option.getPrice()*100.0)/100.0),0);
	  }
	  
	  @Test
	  public void testStock() {

		  Stock stock = new Stock("SPY", 0, (float)220.76);
		  assertEquals(220.76, (Math.round(stock.getPrice()*100.0)/100.0),0);
		  assertEquals("SPY", stock.getName());
	  }
}
