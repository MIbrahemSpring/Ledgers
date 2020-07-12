package com.mohamed.ledgers.services;

import javax.servlet.http.HttpServletRequest;

import com.mohamed.ledgers.apiModels.ApiHeaders;

public interface IUtilities {
	ApiHeaders GetHeaderData(HttpServletRequest request);
	ApiHeaders GetSessionHeaderData(String Session);
	String generateRandomString(int length, String prefix);
}
