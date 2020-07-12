package com.mohamed.ledgers.utils;

public class Enums {

	public enum LedgerType {
		Assets, Liabilities, Revenues, Expenses, Capital, Retained_Earnings, Dividends
	}

	public enum LedgerCurrency {
		EGP, USD, KWD, EUR;
	}
	
	public enum Status {
		Active,Archived,Deleted,Success,Failed;
	}
}
