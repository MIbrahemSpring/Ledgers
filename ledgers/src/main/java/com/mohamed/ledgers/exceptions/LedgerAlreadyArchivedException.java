package com.mohamed.ledgers.exceptions;

public class LedgerAlreadyArchivedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LedgerAlreadyArchivedException(String message) {
		super(message);
	}

}
