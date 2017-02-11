package com.vadim;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
	//static Contract[] contracts;
	static List<Contract> contracts = new ArrayList<Contract>();
	static float cash;
	
	
	static void buyOption(String putOrCall, String name,  int quantity, float price, String expiryDate, float strikePrice )
	{
		Contract contract = new Option(putOrCall, name, quantity, price, expiryDate, strikePrice);
		contracts.add(contract);
		
	}
	

}
