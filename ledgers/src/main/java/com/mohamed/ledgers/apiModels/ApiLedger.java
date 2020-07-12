package com.mohamed.ledgers.apiModels;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.mohamed.ledgers.utils.Enums;

import lombok.Data;

@Data
public class ApiLedger {

	@NotEmpty(message = "name is required")
	private String name;
	
	private String description;
	
	@NotNull(message = "type is required")
	private Enums.LedgerType type;
	
	@NotEmpty(message = "country is required")
	private String country;
	
	@NotNull(message = "currency is required")
	private Enums.LedgerCurrency currency;

}
