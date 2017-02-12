package com.vadim;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

public class PortfolioTest {

	  @Test
	  public void testBuying() {

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
		  Portfolio.buyStock("SPY", 10, (float)100, 12);
		  assertEquals(2, Portfolio.contracts.size());
		  assertEquals(100, (Math.round(Portfolio.contracts.get(1).getPrice()*100.0)/100.0), 0);
		  howMuchCashDoesPortfolioHave = Portfolio.showCash();
		  assertEquals(98794.9, (howMuchCashDoesPortfolioHave*100.0)/100.0,0.1); // 0.1 is the delta/max difference between two double numbers

		  
	  }
	  
	  

}

