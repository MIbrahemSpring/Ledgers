package com.mohamed.ledgers.exceptions;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.mohamed.ledgers.errors.Errors;
import com.mohamed.ledgers.errors.IErrorServices;
import com.mohamed.ledgers.uiModels.ErrorMessage;

@ControllerAdvice
public class ApiExceptionHandler {

	@Autowired
	private IErrorServices _errorServices;

	// Handling Runtime Exceptions
	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<Object> handleRuntimeException() throws Exception {
		return _errorServices.Error(Errors.Internal_server_error.getCode(), Errors.Internal_server_error.toString(),
				"Internal server error", HttpStatus.BAD_REQUEST);
	}

	// Handling Generic Exceptions
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleGenericException() throws Exception {
		return _errorServices.Error(Errors.Internal_server_error.getCode(), Errors.Internal_server_error.toString(),
				"Something went wrong", HttpStatus.BAD_REQUEST);
	}

	// Handling Method Argument Not Valid Exceptions
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleUserServiceException(MethodArgumentNotValidException ex, WebRequest req) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
		ex.getBindingResult().getAllErrors().forEach(error -> {
			errorMessage.setTimestamp(new Date());
			errorMessage.setMessage(error.getDefaultMessage());
		});
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	// Handling Invalid format Exceptions
	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	public ResponseEntity<Object> handleMessageNotReadableException(HttpMessageNotReadableException ex,
			WebRequest req) {
		return _errorServices.Error(Errors.Invalid_json_request.getCode(), Errors.Invalid_json_request.toString(),
				"value is not accepted", HttpStatus.BAD_REQUEST);
	}

	// Handling Pagination Limit Exception
	@ExceptionHandler(value = PaginationLimitException.class)
	public ResponseEntity<Object> handleApiDataNotFoundException(PaginationLimitException ex, WebRequest req) {
		return _errorServices.Error(Errors.Ledger_Failed.getCode(), Errors.Ledger_Failed.toString(), ex.getMessage(),
				HttpStatus.NOT_FOUND);
	}

	// Handling Invalid format Exceptions
	@ExceptionHandler(value = DataNotFoundException.class)
	public ResponseEntity<Object> handleDataNotFoundException(DataNotFoundException ex, WebRequest req) {
		return _errorServices.Error(Errors.Ledger_Failed.getCode(), Errors.Ledger_Failed.toString(), ex.getMessage(),
				HttpStatus.NOT_FOUND);
	}

}
