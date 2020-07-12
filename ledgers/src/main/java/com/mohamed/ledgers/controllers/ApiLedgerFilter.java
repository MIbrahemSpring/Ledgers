package com.mohamed.ledgers.controllers;

import com.mohamed.ledgers.apiModels.ApiLedgerPeriod;

import lombok.Data;

@Data
public class ApiLedgerFilter {
	private ApiLedgerPeriod period;
	private int limit;
	private int page;
}
