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

import com.mohamed.ledgers.controllers.ApiLedgerFilter;
import com.mohamed.ledgers.dao.LedgerDTO;
import com.mohamed.ledgers.entities.LedgerEntity;
import com.mohamed.ledgers.exceptions.DataNotFoundException;
import com.mohamed.ledgers.exceptions.PaginationLimitException;
import com.mohamed.ledgers.repositories.LedgerRepository;
import com.mohamed.ledgers.utils.Enums;

@Service
public class LedgerService implements ILedgerService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private LedgerRepository ledgerRepo;

	@Autowired
	private IUtilities utilities;

	@Override
	public LedgerDTO createLedger(LedgerDTO ledgerDto) {
		LedgerEntity ledgerEntity = modelMapper.map(ledgerDto, LedgerEntity.class);
		ledgerEntity.setCreated(new Date());
		ledgerEntity.setStatus(Enums.Status.Success.toString());
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

}
