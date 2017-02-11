package com.vadim;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

public class PortfolioTest {

	  @Test
	  public void testBuyOption() {

		  //new Portfolio();
		  assertEquals(0, Portfolio.contracts.size());
		  Portfolio.buyOption("Put", "SPY", 1, (float)220.76, "23/11/2016", (float)220.7);
		  assertEquals(1, Portfolio.contracts.size());
		  assertEquals(220.76, (Math.round(Portfolio.contracts.get(0).getPrice()*100.0)/100.0),0);
	  }
	  

}

