package com.mohamed.ledgers.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mohamed.ledgers.entities.LedgerEntity;

@Repository
public interface LedgerRepository extends JpaRepository<LedgerEntity, Integer> {
	
	LedgerEntity findByLedgerId(String ledgerId);

	List<LedgerEntity> findbyPeriod(Date from, Date to);

	Page<LedgerEntity> findByPeriodPaging(Date from, Date to, Pageable paging);
}
