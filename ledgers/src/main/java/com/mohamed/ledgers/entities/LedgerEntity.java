package com.mohamed.ledgers.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "ledgers")
@NamedQueries({
		@NamedQuery(name = "LedgerEntity.findbyPeriod", query = "SELECT e FROM LedgerEntity e WHERE e.created between ?1 and ?2"),
		@NamedQuery(name = "LedgerEntity.findByPeriodPaging", query = "SELECT e FROM LedgerEntity e WHERE e.created between ?1 and ?2") })
public class LedgerEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "ledger_id")
	private String ledgerId;
	@Temporal(TemporalType.DATE)
	private Date created;
	private String status;
	private String name;
	private String description;
	private String type;
	private String country;
	private String currency;
	private double balance;

	private LedgerEntity(LedgerEntityBuilder builder) {
		this.ledgerId = builder.ledgerId;
		this.created = builder.created;
		this.status = builder.status;
	}

	public static class LedgerEntityBuilder {

		private String ledgerId;
		private Date created;
		private String status;

		public LedgerEntityBuilder setLedgerId(String ledgerId) {
			this.ledgerId = ledgerId;
			return this;
		}

		public LedgerEntityBuilder setCreated(Date created) {
			this.created = created;
			return this;
		}

		public LedgerEntityBuilder setStatus(String status) {
			this.status = status;
			return this;
		}

		public LedgerEntity build() {
			return new LedgerEntity(this);
		}
	}

}
