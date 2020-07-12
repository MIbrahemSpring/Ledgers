package com.mohamed.ledgers.dao;

import java.io.Serializable;

import com.mohamed.ledgers.utils.Enums;

import lombok.Data;

@Data
public class LedgerDTO implements Serializable {

	private static final long serialVersionUID = 1L;

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
