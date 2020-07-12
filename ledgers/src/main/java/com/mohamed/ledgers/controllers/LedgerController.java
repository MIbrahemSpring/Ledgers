package com.mohamed.ledgers.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohamed.ledgers.apiModels.ApiHeaders;
import com.mohamed.ledgers.apiModels.ApiLedger;
import com.mohamed.ledgers.dao.LedgerDTO;
import com.mohamed.ledgers.errors.Errors;
import com.mohamed.ledgers.errors.IErrorServices;
import com.mohamed.ledgers.services.ILedgerService;
import com.mohamed.ledgers.services.IUtilities;
import com.mohamed.ledgers.uiModels.LedgerResponseModel;

@RestController
@RequestMapping("/ledger")
public class LedgerController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ObjectMapper objMapper;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IUtilities _utilities;

	@Autowired
	private IErrorServices _errorServices;

	@Autowired
	private ILedgerService _ledgerService;

	/**
	 * Create a new Ledger
	 * 
	 * @param apiLedger
	 * @return LedgerResponseModel
	 */
	@PostMapping()
	public ResponseEntity<Object> createLedger(@Valid @RequestBody ApiLedger apiLedger) {
		logger.info("===================== start creating a ledger =====================");

		// get request Header Data
		ApiHeaders headers = _utilities.GetHeaderData(request);
		if (headers != null && headers.getStatus_code() == null) {
			ApiHeaders sessionHead = _utilities.GetSessionHeaderData(headers.getSession_token());
			if (sessionHead == null) {
				return _errorServices.Error(Errors.Invalid_API_Key.getCode(), Errors.Invalid_API_Key.toString(),
						"Invalid Api key", HttpStatus.UNAUTHORIZED);
			} else if (sessionHead != null && sessionHead.getStatus_code() != null) {
				return _errorServices.Error(Errors.Invalid_API_Key.getCode(), Errors.Invalid_API_Key.toString(),
						"Invalid session token", HttpStatus.UNAUTHORIZED);
			} else if (!sessionHead.getApi_key().equals(headers.getAuthorization())) {
				return _errorServices.Error(Errors.Invalid_API_Key.getCode(), Errors.Invalid_API_Key.toString(),
						"Api key doesnt match with session token", HttpStatus.UNAUTHORIZED);
			} else if (!sessionHead.getPayload_data().equals(headers.getPayload_data())) {
				return _errorServices.Error(Errors.Invalid_API_Key.getCode(), Errors.Invalid_API_Key.toString(),
						"Api key doesnt match with payload Data", HttpStatus.UNAUTHORIZED);
			} else {

				// fill the LedgerDTO from the APILedger request body
				LedgerDTO ledgerDto = modelMapper.map(apiLedger, LedgerDTO.class);

				// create a new ledger
				LedgerDTO storedLedger = _ledgerService.createLedger(ledgerDto);

				// fill the response
				LedgerResponseModel response = modelMapper.map(storedLedger, LedgerResponseModel.class);

				// return the response
				try {
					return new ResponseEntity<>(objMapper.writeValueAsString(response), HttpStatus.OK);
				} catch (JsonProcessingException e) {
					logger.error("-------------------- Error while creating a new ledger --------------------");
					logger.error(e.getMessage());
					return _errorServices.Error(Errors.Ledger_Failed.getCode(), Errors.Ledger_Failed.toString(),
							"Failed to create a new Ledger", HttpStatus.BAD_REQUEST);
				}
			}
		} else
			return _errorServices.Error(Errors.Headers_values_missing.getCode(),
					Errors.Headers_values_missing.toString(), "Header values are missing", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/{ledgerId}")
	public ResponseEntity<Object> getLedger(@PathVariable String ledgerId) {
		logger.info("===================== start getting a ledger =====================");

		// get request Header Data
		ApiHeaders headers = _utilities.GetHeaderData(request);
		if (headers != null && headers.getStatus_code() == null) {
			ApiHeaders sessionHead = _utilities.GetSessionHeaderData(headers.getSession_token());
			if (sessionHead == null) {
				return _errorServices.Error(Errors.Invalid_API_Key.getCode(), Errors.Invalid_API_Key.toString(),
						"Invalid Api key", HttpStatus.UNAUTHORIZED);
			} else if (sessionHead != null && sessionHead.getStatus_code() != null) {
				return _errorServices.Error(Errors.Invalid_API_Key.getCode(), Errors.Invalid_API_Key.toString(),
						"Invalid session token", HttpStatus.UNAUTHORIZED);
			} else if (!sessionHead.getApi_key().equals(headers.getAuthorization())) {
				return _errorServices.Error(Errors.Invalid_API_Key.getCode(), Errors.Invalid_API_Key.toString(),
						"Api key doesnt match with session token", HttpStatus.UNAUTHORIZED);
			} else if (!sessionHead.getPayload_data().equals(headers.getPayload_data())) {
				return _errorServices.Error(Errors.Invalid_API_Key.getCode(), Errors.Invalid_API_Key.toString(),
						"Api key doesnt match with payload Data", HttpStatus.UNAUTHORIZED);
			} else {

				// get ledger from the DB
				LedgerDTO ledgerDto = _ledgerService.getLedger(ledgerId);

				// fill the response
				LedgerResponseModel response = modelMapper.map(ledgerDto, LedgerResponseModel.class);

				// return the response
				try {
					return new ResponseEntity<>(objMapper.writeValueAsString(response), HttpStatus.OK);
				} catch (JsonProcessingException e) {
					logger.error("-------------------- Error while getting a new ledger --------------------");
					logger.error(e.getMessage());
					return _errorServices.Error(Errors.Ledger_Failed.getCode(), Errors.Ledger_Failed.toString(),
							"Failed to get a Ledger", HttpStatus.BAD_REQUEST);
				}
			}
		} else
			return _errorServices.Error(Errors.Headers_values_missing.getCode(),
					Errors.Headers_values_missing.toString(), "Header values are missing", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/list")
	public ResponseEntity<Object> getLedgers(@Valid @RequestBody ApiLedgerFilter filters) {
		logger.info("===================== start getting list of ledgers =====================");
		// get request Header Data
		ApiHeaders headers = _utilities.GetHeaderData(request);
		if (headers != null && headers.getStatus_code() == null) {
			ApiHeaders sessionHead = _utilities.GetSessionHeaderData(headers.getSession_token());
			if (sessionHead == null) {
				return _errorServices.Error(Errors.Invalid_API_Key.getCode(), Errors.Invalid_API_Key.toString(),
						"Invalid Api key", HttpStatus.UNAUTHORIZED);
			} else if (sessionHead != null && sessionHead.getStatus_code() != null) {
				return _errorServices.Error(Errors.Invalid_API_Key.getCode(), Errors.Invalid_API_Key.toString(),
						"Invalid session token", HttpStatus.UNAUTHORIZED);
			} else if (!sessionHead.getApi_key().equals(headers.getAuthorization())) {
				return _errorServices.Error(Errors.Invalid_API_Key.getCode(), Errors.Invalid_API_Key.toString(),
						"Api key doesnt match with session token", HttpStatus.UNAUTHORIZED);
			} else if (!sessionHead.getPayload_data().equals(headers.getPayload_data())) {
				return _errorServices.Error(Errors.Invalid_API_Key.getCode(), Errors.Invalid_API_Key.toString(),
						"Api key doesnt match with payload Data", HttpStatus.UNAUTHORIZED);
			} else {

				// get the list of ledgerDto from the DB
				List<LedgerDTO> ledgerDtos = _ledgerService.getLedgers(filters);

				if (ledgerDtos.size() < 0 || ledgerDtos == null || ledgerDtos.isEmpty())
					return _errorServices.Error(Errors.Ledger_Failed.getCode(), Errors.Ledger_Failed.toString(),
							"No ledgers found", HttpStatus.NOT_FOUND);

				// fill the EntryResponse list
				List<LedgerResponseModel> ledgerResponseList = new ArrayList<>();
				for (LedgerDTO entry : ledgerDtos) {
					LedgerResponseModel ledgerResponse = new LedgerResponseModel();
					modelMapper.map(entry, ledgerResponse);
					ledgerResponseList.add(ledgerResponse);
				}
				// return response
				try {
					logger.info("-------------------- End get list of ledgers --------------------");
					return new ResponseEntity<Object>(objMapper.writeValueAsString(ledgerResponseList), HttpStatus.OK);
				} catch (JsonProcessingException e) {
					logger.error("-------------------- Error while  archive the entry --------------------");
					logger.error(e.getMessage());
					return _errorServices.Error(Errors.Ledger_Failed.getCode(), Errors.Ledger_Failed.toString(),
							"Failed to get list of ledgers", HttpStatus.BAD_REQUEST);
				}
			}
		} else
			return _errorServices.Error(Errors.Headers_values_missing.getCode(),
					Errors.Headers_values_missing.toString(), "Header values are missing", HttpStatus.BAD_REQUEST);
	}
}
