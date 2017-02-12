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
		  float howMuchCashDoesPortfolioHave;
		  assertEquals(0, Portfolio.contracts.size());
		  //System.out.print("Initial cash :" +  Portfolio.showCash() );
		  Portfolio.buyOption("Put", "SPY", 1, (float)2, "23/11/2016", (float)220.7, 12);
		  howMuchCashDoesPortfolioHave = Portfolio.showCash();
		  //System.out.println("Current cash:" + howMuchCashDoesPortfolioHave );
		  assertEquals(99796.9, (howMuchCashDoesPortfolioHave*100.0)/100.0,0.1); // 0.1 is the delta/max difference between two double numbers
		  assertEquals(1, Portfolio.contracts.size());
		  assertEquals(2, (Math.round(Portfolio.contracts.get(0).getPrice()*100.0)/100.0), 0);
	  }
	  

}

