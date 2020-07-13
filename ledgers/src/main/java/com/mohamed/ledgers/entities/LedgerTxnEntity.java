package com.mohamed.ledgers.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = Include.NON_NULL)
@NoArgsConstructor
@Entity
@Table(name = "ledgers_txn")
public class LedgerTxnEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	private String ledgerId;
	private String txnId;
	@Temporal(TemporalType.DATE)
	private Date created;
	private String status;
	private String currency;
	private double amount;

	private LedgerTxnEntity(LedgerTxnEntityBuilder builder) {
		this.ledgerId = builder.ledgerId;
		this.txnId = builder.txnId;
		this.created = builder.created;
		this.status = builder.status;
		this.currency = builder.currency;
		this.amount = builder.amount;
	}

	public static class LedgerTxnEntityBuilder {
		private String ledgerId;
		private String txnId;
		private Date created;
		private String status;
		private String currency;
		private double amount;

		public LedgerTxnEntityBuilder setLedgerId(String ledgerId) {
			this.ledgerId = ledgerId;
			return this;
		}

		public LedgerTxnEntityBuilder setTxnId(String txnId) {
			this.txnId = txnId;
			return this;
		}

		public LedgerTxnEntityBuilder setCreated(Date created) {
			this.created = created;
			return this;
		}

		public LedgerTxnEntityBuilder setStatus(String status) {
			this.status = status;
			return this;
		}

		public LedgerTxnEntityBuilder setCurrency(String currency) {
			this.currency = currency;
			return this;
		}

		public LedgerTxnEntityBuilder setAmount(double amount) {
			this.amount = amount;
			return this;
		}

		public LedgerTxnEntity build() {
			return new LedgerTxnEntity(this);
		}

	}
}
