package com.mohamed.ledgers.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mohamed.ledgers.apiModels.ApiLedgerFilter;
import com.mohamed.ledgers.dao.LedgerDTO;
import com.mohamed.ledgers.dao.LedgerTxnDTO;
import com.mohamed.ledgers.entities.LedgerEntity;
import com.mohamed.ledgers.entities.LedgerTxnEntity;
import com.mohamed.ledgers.exceptions.DataNotFoundException;
import com.mohamed.ledgers.exceptions.LedgerAlreadyArchivedException;
import com.mohamed.ledgers.exceptions.LedgerArchivedException;
import com.mohamed.ledgers.exceptions.PaginationLimitException;
import com.mohamed.ledgers.repositories.LedgerRepository;
import com.mohamed.ledgers.repositories.LedgerTxnRepository;
import com.mohamed.ledgers.utils.Enums;

@Service
public class LedgerService implements ILedgerService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private LedgerRepository ledgerRepo;

	@Autowired
	private LedgerTxnRepository ledgerTxnRepo;

	@Autowired
	private IUtilities utilities;

	@Override
	public LedgerDTO createLedger(LedgerDTO ledgerDto) {
		LedgerEntity ledgerEntity = modelMapper.map(ledgerDto, LedgerEntity.class);
		ledgerEntity.setCreated(new Date());
		ledgerEntity.setStatus(Enums.Status.Active.toString());
		ledgerEntity.setLedgerId(utilities.generateRandomString(22, "ledger_"));
		ledgerEntity.setBalance(0);

		LedgerEntity savedEntity = ledgerRepo.save(ledgerEntity);
		LedgerDTO returnDto = modelMapper.map(savedEntity, LedgerDTO.class);
		return returnDto;
	}

	@Override
	public LedgerDTO getLedger(String ledgerId) {
		LedgerEntity ledgerEntity = ledgerRepo.findByLedgerId(ledgerId);
		if (ledgerEntity == null)
			throw new DataNotFoundException("Ledger not found");
		LedgerDTO ledgerDto = modelMapper.map(ledgerEntity, LedgerDTO.class);
		return ledgerDto;
	}

	@Override
	public List<LedgerDTO> getLedgers(ApiLedgerFilter filters) {
		List<LedgerDTO> filteredEntries = new ArrayList<>();
		List<LedgerEntity> allEntries = new ArrayList<>();

		Integer limit = filters.getLimit();
		Integer page = filters.getPage();
		Date dateFrom = filters.getPeriod().getDate().getFrom();
		Date dateTo = filters.getPeriod().getDate().getTo();

		if (page > 0)
			page--;

		if (limit < 0)
			throw new PaginationLimitException("Limit must be greater than 0");

		// if no limit
		if (limit == 0) {
			if (dateFrom == null || dateTo == null)
				allEntries = ledgerRepo.findAll();
			else
				allEntries = ledgerRepo.findbyPeriod(dateFrom, dateTo);

		}

		// if there is a limit provided
		if (limit > 0) {
			Pageable paging = PageRequest.of(page, limit);
			Page<LedgerEntity> pages;
			if (dateFrom == null || dateTo == null)
				pages = ledgerRepo.findAll(paging);
			else
				pages = ledgerRepo.findByPeriodPaging(dateFrom, dateTo, paging);

			if (pages.hasContent())
				allEntries = pages.getContent();

		}

		for (LedgerEntity ee : allEntries) {
			LedgerDTO entryDto = new LedgerDTO();
			modelMapper.map(ee, entryDto);
			filteredEntries.add(entryDto);
		}

		return filteredEntries;
	}

	@Override
	public LedgerTxnDTO createLedgerTxn(LedgerTxnDTO ledgerTxnDto, String txnType) {
		// validate ledger is valid
		String ledgerId = ledgerTxnDto.getLedgerId();
		LedgerEntity ledgerEntity = ledgerRepo.findByLedgerId(ledgerId);
		if (ledgerEntity == null)
			throw new DataNotFoundException("Ledger not found");

		if (ledgerEntity.getStatus().equalsIgnoreCase("Archived"))
			throw new LedgerArchivedException("This ledger is archived, Please re-activate the ledger.");
		String type = ledgerEntity.getType().toString();

		switch (type) {
		case "Assets":
			if (txnType.equalsIgnoreCase("debit"))
				ledgerEntity.setBalance(ledgerEntity.getBalance() + ledgerTxnDto.getAmount());
			if (txnType.equalsIgnoreCase("credit"))
				ledgerEntity.setBalance(ledgerEntity.getBalance() - ledgerTxnDto.getAmount());
			else
				ledgerEntity.setBalance(ledgerEntity.getBalance());
			break;
		case "Liabilities":
			if (txnType.equalsIgnoreCase("debit"))
				ledgerEntity.setBalance(ledgerEntity.getBalance() - ledgerTxnDto.getAmount());
			if (txnType.equalsIgnoreCase("credit"))
				ledgerEntity.setBalance(ledgerEntity.getBalance() + ledgerTxnDto.getAmount());
			else
				ledgerEntity.setBalance(ledgerEntity.getBalance());
			break;
		case "Revenues":
			if (txnType.equalsIgnoreCase("debit"))
				ledgerEntity.setBalance(ledgerEntity.getBalance() - ledgerTxnDto.getAmount());
			if (txnType.equalsIgnoreCase("credit"))
				ledgerEntity.setBalance(ledgerEntity.getBalance() + ledgerTxnDto.getAmount());
			else
				ledgerEntity.setBalance(ledgerEntity.getBalance());
			break;
		case "Expenses":
			if (txnType.equalsIgnoreCase("debit"))
				ledgerEntity.setBalance(ledgerEntity.getBalance() + ledgerTxnDto.getAmount());
			if (txnType.equalsIgnoreCase("credit"))
				ledgerEntity.setBalance(ledgerEntity.getBalance() - ledgerTxnDto.getAmount());
			else
				ledgerEntity.setBalance(ledgerEntity.getBalance());
			break;
		case "Capital":
			if (txnType.equalsIgnoreCase("debit"))
				ledgerEntity.setBalance(ledgerEntity.getBalance() - ledgerTxnDto.getAmount());
			if (txnType.equalsIgnoreCase("credit"))
				ledgerEntity.setBalance(ledgerEntity.getBalance() + ledgerTxnDto.getAmount());
			else
				ledgerEntity.setBalance(ledgerEntity.getBalance());
			break;
		case "Retained_Earnings":
			if (txnType.equalsIgnoreCase("debit"))
				ledgerEntity.setBalance(ledgerEntity.getBalance() - ledgerTxnDto.getAmount());
			if (txnType.equalsIgnoreCase("credit"))
				ledgerEntity.setBalance(ledgerEntity.getBalance() + ledgerTxnDto.getAmount());
			else
				ledgerEntity.setBalance(ledgerEntity.getBalance());
			break;
		case "Dividends":
			if (txnType.equalsIgnoreCase("debit"))
				ledgerEntity.setBalance(ledgerEntity.getBalance() + ledgerTxnDto.getAmount());
			if (txnType.equalsIgnoreCase("credit"))
				ledgerEntity.setBalance(ledgerEntity.getBalance() - ledgerTxnDto.getAmount());
			else
				ledgerEntity.setBalance(ledgerEntity.getBalance());
			break;
		default:
			ledgerEntity = new LedgerEntity();
			break;
		}

		ledgerEntity = ledgerRepo.save(ledgerEntity);

		// save the txn into the DB
		LedgerTxnEntity ledgerTxnEntity = new LedgerTxnEntity.LedgerTxnEntityBuilder()
				.setAmount(ledgerTxnDto.getAmount()).setCreated(new Date()).setCurrency(ledgerTxnDto.getCurrency())
				.setTxnId(txnType.equalsIgnoreCase("debit") ? utilities.generateRandomString(22, "debit_")
						: utilities.generateRandomString(22, "credit_"))
				.setLedgerId(ledgerId).setStatus(Enums.Status.Success.toString()).build();

		LedgerTxnEntity savedTxnEntity = ledgerTxnRepo.save(ledgerTxnEntity);

		return modelMapper.map(savedTxnEntity, LedgerTxnDTO.class);
	}

	@Override
	public LedgerDTO archiveLedger(String ledgerId) {
		LedgerEntity ledgerEntity = ledgerRepo.findByLedgerId(ledgerId);
		if (ledgerEntity == null)
			throw new DataNotFoundException("Ledger not found");
		if (ledgerEntity.getStatus().equalsIgnoreCase(Enums.Status.Archived.toString()))
			throw new LedgerAlreadyArchivedException("This ledger is Already archived.");
		ledgerEntity.setStatus(Enums.Status.Archived.toString());
		LedgerEntity archivedLedgerEntity = ledgerRepo.save(ledgerEntity);
		LedgerDTO ledgerDto = modelMapper.map(archivedLedgerEntity, LedgerDTO.class);
		return ledgerDto;
	}

	@Override
	public LedgerDTO activateLedger(String ledgerId) {
		LedgerEntity ledgerEntity = ledgerRepo.findByLedgerId(ledgerId);
		if (ledgerEntity == null)
			throw new DataNotFoundException("Ledger not found");
		if (ledgerEntity.getStatus().equalsIgnoreCase(Enums.Status.Active.toString()))
			throw new LedgerAlreadyArchivedException("This ledger is Already Active.");
		ledgerEntity.setStatus(Enums.Status.Active.toString());
		LedgerEntity archivedLedgerEntity = ledgerRepo.save(ledgerEntity);
		LedgerDTO ledgerDto = modelMapper.map(archivedLedgerEntity, LedgerDTO.class);
		return ledgerDto;
	}

}
