package com.mohamed.ledgers.errors;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ErrorServices implements IErrorServices {

	@Autowired
	private ObjectMapper objMapper;

	public ResponseEntity<Object> Error(int code, String error, String description, HttpStatus httpStatus) {
		ApiErrors apiError = new ApiErrors();
		ApiErrors.ApiError err = apiError.new ApiError(String.valueOf(code), error, description);
		List<ApiErrors.ApiError> errors = new ArrayList<ApiErrors.ApiError>();
		errors.add(err);
		apiError.errors = errors;
		try {
			return new ResponseEntity<>(objMapper.writeValueAsString(apiError), httpStatus);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
			return Error(Errors.Internal_server_error.getCode(), Errors.Internal_server_error.toString(),
					"Failed internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
