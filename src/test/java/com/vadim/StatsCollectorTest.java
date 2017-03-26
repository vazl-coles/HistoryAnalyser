package com.vadim;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StatsCollectorTest {
	
	  @Test
	  public void testInnerClassesofStats() {
		  
		  StatsCollector.init();
		  
		  // Call test method to populate the array
		  StatsCollector.updateStatsTest(1);
		  //StatsCollector.displayExtremeValues(1);
		  //System.out.println("Highest " + StatsCollector.getHighest(1));
		  //System.out.println("Lowest " + StatsCollector.getLowest(1));
		  assertEquals(-5, StatsCollector.getLowest(1));
		  assertEquals(5, StatsCollector.getHighest(1));
		  
		// Call test method to populate the array
		  StatsCollector.updateStatsTest(2);
		  assertEquals(-10, StatsCollector.getLowest(2));
		  assertEquals(10, StatsCollector.getHighest(2));
		  //StatsCollector.displayExtremeValues(2);
		  
		  // 3 days before expiry is subscript = 2
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(1);
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(1);
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(1);
		  
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(2);
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(2);
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(2);
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(2);
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(2);
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(2);
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(2);
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(2);
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(2);
		  
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(3);
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(3);
		  
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(0);
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(0);
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(0);
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(0);
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(0);
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(0);
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(0);
		  
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(-1);
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(-1);
		  StatsCollector.daysBeforeExpiryArray[2].updateStatsEntry(-1);
		  
		  //StatsCollector.daysBeforeExpiryArray[3].displayAll();
		  //assertEquals(10, StatsCollector.getHighest(3));
		  assertEquals(27, StatsCollector.getMod(3)); // The entry 27 (2% gain) has the highest total
		  
		  assertEquals(2, StatsCollector.calculatePercentageFromSubscript(3, 27), 0); // The entry 27 (2% gain)
		  
		  // Get total which would be used for probability calculation
		  assertEquals(24, StatsCollector.getTotalForAllEntries(3));
		  
		  // what is the probability that there will be 2% gain in 3 days
		  // There were 9 entries for 2% gain, so 9 should be divided by 24 which is total of all entries (3 days away) 
		  assertEquals(0.375, StatsCollector.getProbabilityOfPercentageChange(3, 2), 0);
		  
		  
		  /*
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
*/
		  
	  }

}
