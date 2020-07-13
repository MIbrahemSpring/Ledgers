package com.mohamed.ledgers.apiModels;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class ApiLedgerTxn {
	
	@NotEmpty(message = "Ledger ID is required")
	private String ledgerId;
	
	@Min(value = 0, message = "Amount must be larger than 0")
	private double amount;
	
	@NotEmpty(message = "Currency is required")
	private String currency;
}
