package com.vadim;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StraddlePriceCalculatorTest {
	
	  @Test
	  public void testGetStraddlePrice() {
			float currentPrice = 220;
			float strikePrice = 220;
			int numberOfDaysBeforeExpiry = 2;
			float vix = 13;
			float straddlePrice=StraddlePriceCalculator.getStraddlePrice(currentPrice, strikePrice, numberOfDaysBeforeExpiry, vix);
			
			assertEquals(1.6, (Math.round(straddlePrice*100.0)/100.0),0);
			
			float distanceBetweenPrices = StraddlePriceCalculator.calcDistanceBetweenPrices((float)224.4,(float)223);
			
			assertEquals(0.63, (Math.round(distanceBetweenPrices*100.0)/100.0),0);
			
			currentPrice = (float)224.4;
			strikePrice = 223;
			numberOfDaysBeforeExpiry = 2;
			vix = 13;
			straddlePrice=StraddlePriceCalculator.getStraddlePrice(currentPrice, strikePrice, numberOfDaysBeforeExpiry, vix);
			
			assertEquals(1.9, (Math.round(straddlePrice*100.0)/100.0),0);
	  }

}
