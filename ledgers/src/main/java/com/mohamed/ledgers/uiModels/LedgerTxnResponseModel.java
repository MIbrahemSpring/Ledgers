package com.mohamed.ledgers.uiModels;

import lombok.Data;

@Data
public class LedgerTxnResponseModel {
	private String ledgerId;
	private String txnId;
	private long created;
	private String status;
	private String currency;
	private double amount;
}
