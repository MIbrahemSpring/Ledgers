package com.mohamed.ledgers.services;

import java.util.List;

import com.mohamed.ledgers.controllers.ApiLedgerFilter;
import com.mohamed.ledgers.dao.LedgerDTO;

public interface ILedgerService {

	LedgerDTO createLedger(LedgerDTO ledgerDto);

	LedgerDTO getLedger(String ledgerId);

	List<LedgerDTO> getLedgers(ApiLedgerFilter filters);
}
