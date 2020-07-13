package com.mohamed.ledgers.services;

import java.util.List;

import com.mohamed.ledgers.apiModels.ApiLedgerFilter;
import com.mohamed.ledgers.dao.LedgerDTO;
import com.mohamed.ledgers.dao.LedgerTxnDTO;

public interface ILedgerService {

	LedgerDTO createLedger(LedgerDTO ledgerDto);

	LedgerDTO getLedger(String ledgerId);

	List<LedgerDTO> getLedgers(ApiLedgerFilter filters);
	
	LedgerTxnDTO createLedgerTxn(LedgerTxnDTO ledgerTxnDto, String txnType);
	
	LedgerDTO archiveLedger(String ledgerId);
}
