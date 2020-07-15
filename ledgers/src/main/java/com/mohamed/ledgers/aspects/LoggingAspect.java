package com.mohamed.ledgers.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// adding logging before createLedger
	@Before("execution (* com.mohamed.ledgers.controllers.LedgerController.createLedger(..))")
	public void logBeforeCreatingLedger() {
		logger.info("==================== CREATING A NEW LEDGER ====================");
	}

	// adding logging before getLedger
	@Before("execution (* com.mohamed.ledgers.controllers.LedgerController.getLedger(..))")
	public void logBeforeGettingLedger() {
		logger.info("==================== GETTING A SINGLE LEDGER FROM THE DATABASE ====================");
	}

	// adding logging before getLedgers
	@Before("execution (* com.mohamed.ledgers.controllers.LedgerController.getLedgers(..))")
	public void logBeforeGettingLedgers() {
		logger.info("==================== GETTING A LIST OF LEDGER FROM THE DATABASE ====================");
	}

	// adding logging before debitLedger
	@Before("execution (* com.mohamed.ledgers.controllers.LedgerController.debitLedger(..))")
	public void logBeforeDebitLedger() {
		logger.info("==================== DEBIT A LEDGER ====================");
	}

	// adding logging before creditLedger
	@Before("execution (* com.mohamed.ledgers.controllers.LedgerController.creditLedger(..))")
	public void logBeforeCreditLedger() {
		logger.info("==================== CREDIT A LEDGER ====================");
	}

	// adding logging before archiveLedger
	@Before("execution (* com.mohamed.ledgers.controllers.LedgerController.archiveLedger(..))")
	public void logBeforeArchiveLedger() {
		logger.info("==================== ARCHIVE A LEDGER ====================");
	}
}
