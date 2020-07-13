package com.mohamed.ledgers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mohamed.ledgers.entities.LedgerTxnEntity;

@Repository
public interface LedgerTxnRepository extends JpaRepository<LedgerTxnEntity, Integer> {

}
