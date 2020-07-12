package com.mohamed.ledgers.uiModels;

import com.mohamed.ledgers.utils.Enums;

import lombok.Data;

@Data
public class LedgerResponseModel {
	private String ledgerId;
	private long created;
	private Enums.Status status;
	private String name;
	private String description;
	private Enums.LedgerType type;
	private String country;
	private Enums.LedgerCurrency currency;
	private double balance;
}
