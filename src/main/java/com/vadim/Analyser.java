package com.vadim;

import java.util.ArrayList;
import java.util.List;

public class Analyser {
	static float furthestExpiryAbove;
	static float furthestExpiryBelow;
	static float modExpiry;
	static List<ProposedTransaction> suggestTransactions()
	{
		// Loop through number of days from 1 to 20 days and for each day find the most removed point.
		for (int i = 1; i < 20; i++)
		{
			// what are the most removed points when expiry is i days away
			furthestExpiryAbove = findFurthestExpiryAbove(i);
			furthestExpiryBelow = findFurthestExpiryBelow(i);
			modExpiry = findModExpiry(i);
			
		}
		
		// Get the most extreme point and build complex position to check if there is profit in a position
		ProposedTransaction proposedTransaction = new ProposedTransaction();
		List<ProposedTransaction> proposedTransactions=new ArrayList();
		proposedTransactions.add(proposedTransaction);
		
		return proposedTransactions;
		
	}
	
	static float findFurthestExpiryAbove(int days)
	{
		
		return 0;
		
	}

	static float findFurthestExpiryBelow(int days)
	{
		return 0;
	}
	
	static float findModExpiry(int days)
	{
		return 0;
	}
}
