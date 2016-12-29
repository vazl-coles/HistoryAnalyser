package com.vadim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;


public class HistoricalTradeResultTest 
{
	private static float straddlePrice = 0;
	private static float priceAtExpiry = 0;
	private static float strikePrice = 0;
	
	private static HistoricalTradeResult historicalTradeResult;

	@BeforeClass
	public static void initHistoricalTradeResult() {
		historicalTradeResult = new HistoricalTradeResult();
	}

	@Before
	public void beforeEachTest() {
		System.out.println("This is executed before each Test");
	}

	@After
	public void afterEachTest() {
		System.out.println("This is executed after each Test");
	}

	@Test
	public void testCalculateProfit() {
		straddlePrice = 2;
		priceAtExpiry = 220;
		strikePrice = 219;
		historicalTradeResult.calculateProfit(straddlePrice, priceAtExpiry, strikePrice);

		assertEquals(100, historicalTradeResult.getTotalProfit(),0);
		
		straddlePrice = (float)1.5;
		priceAtExpiry = 222;
		strikePrice = 223;
		historicalTradeResult.calculateProfit(straddlePrice, priceAtExpiry, strikePrice);
		
		assertEquals(150, historicalTradeResult.getTotalProfit(),0);
		
		assertEquals(75, historicalTradeResult.getAverageProfit(),0);
		
		assertEquals(2, historicalTradeResult.getNumberOfTrades(),0);
		
		assertEquals(2, historicalTradeResult.getNumberOfSuccessfulTrades(),0);
		
		assertEquals(0, historicalTradeResult.getNumberOfFailures(),0);
		
		straddlePrice = (float)1;
		priceAtExpiry = 219;
		strikePrice = 223;
		historicalTradeResult.calculateProfit(straddlePrice, priceAtExpiry, strikePrice);
		
		assertEquals(-150, historicalTradeResult.getTotalProfit(),0);
		
		assertEquals(3, historicalTradeResult.getNumberOfTrades(),0);
		
		assertEquals(-50, historicalTradeResult.getAverageProfit(),0);
		
		assertEquals(2, historicalTradeResult.getNumberOfSuccessfulTrades(),0);
		
		assertEquals(1, historicalTradeResult.getNumberOfFailures(),0);
		
		assertEquals(67, historicalTradeResult.getSuccessRate(),0);
		
		assertEquals(300, historicalTradeResult.getBiggestDrowdown(),0);
		
		straddlePrice = (float)4;
		priceAtExpiry = 228;
		strikePrice = 218;
		historicalTradeResult.calculateProfit(straddlePrice, priceAtExpiry, strikePrice);
		assertEquals(600, historicalTradeResult.getBiggestDrowdown(),0);
	}

	@Test
	public void testSomething() {

	}
	

}

