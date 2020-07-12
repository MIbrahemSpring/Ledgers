package com.mohamed.ledgers.apiModels;

import lombok.Data;

@Data
public class ApiHeaders {
	private String authorization;
	private String session_token;
	private String payload_data;
	private String status_code;
	public String api_key;
}
