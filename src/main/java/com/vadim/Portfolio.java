package com.vadim;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
	//static Contract[] contracts;
	static List<Contract> contracts = new ArrayList<Contract>();
	static float cash=100000;
	
	
	static void buyOption(String putOrCall, String name,  int quantity, float price, String expiryDate, float strikePrice, float vix )
	{
		Contract contract = new Option(putOrCall, name, quantity, price, expiryDate, strikePrice);
		contracts.add(contract);
		cash = cash - quantity*price*100;
		cash = cash - contract.getCommission();
		cash = cash - contract.getSlippage(vix);
		
	
	}
	
	static float showCash()
	{
		 
		return cash;
	}
	

}
