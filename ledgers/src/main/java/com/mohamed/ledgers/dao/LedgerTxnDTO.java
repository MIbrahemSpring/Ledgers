package com.mohamed.ledgers.dao;

import java.io.Serializable;

import lombok.Data;

@Data
public class LedgerTxnDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ledgerId;
	private String txnId;
	private long created;
	private String status;
	private String currency;
	private double amount;

}
