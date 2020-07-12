package com.mohamed.ledgers.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface IErrorServices {

	ResponseEntity<Object> Error(int code, String error, String description, HttpStatus httpStatus);
}
