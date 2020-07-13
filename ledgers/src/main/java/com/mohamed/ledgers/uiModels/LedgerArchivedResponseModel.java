package com.mohamed.ledgers.uiModels;

import lombok.Data;

@Data
public class LedgerArchivedResponseModel {

	private String ledgerId;
	private String message;

	public LedgerArchivedResponseModel() {

	}

	public LedgerArchivedResponseModel(String ledgerId, String message) {
		this.ledgerId = ledgerId;
		this.message = message;
	}

}
