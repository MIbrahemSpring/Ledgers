package com.mohamed.ledgers.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiError {
	public String status;
	public String type;
	public String message;
	public ErrorSource error;
	public String status_code;

	public ApiError() {
	}

	public ApiError(String status, String type, String message) {
		this.status = status;
		this.type = type;
		this.message = message;
	}

	public ApiError(String status, String type, String message, ErrorSource error) {
		this.status = status;
		this.type = type;
		this.message = message;
		this.error = error;
	}

}
