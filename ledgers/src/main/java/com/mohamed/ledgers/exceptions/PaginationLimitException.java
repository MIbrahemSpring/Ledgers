package com.mohamed.ledgers.exceptions;

public class PaginationLimitException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PaginationLimitException(String message) {
		super(message);
	}

}
