package com.mohamed.ledgers.apiModels;

import lombok.Data;

@Data
public class ApiLedgerFilter {
	private ApiLedgerPeriod period;
	private int limit;
	private int page;
}
