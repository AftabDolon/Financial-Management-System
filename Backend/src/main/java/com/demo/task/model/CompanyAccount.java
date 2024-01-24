package com.demo.task.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "SEC_COMPANY_BALANCE")
public class CompanyAccount extends BaseEntity {
    private Integer amount;
    @Column(name = "CURRENT_AMOUNT")
    private Integer currentAmount;
    private String date;
    private Long transactionTypeId;
    private String description;
    @Column(name = "TXN_ID", unique = true)
    private String txnId;
    @Column(name = "BANK_ACC")
    private String bankId;
    @Column(name = "INSERT_BY")
    private String insertBy;
    @Column(name = "INSERT_DATE")
    private String insertDate;
    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Transient
    private String transactionType;

    public CompanyAccount() {
    }

    public CompanyAccount(Tuple tuple) {
        bankId = tuple.get("BANK_ACC", String.class);
        date = tuple.get("date", String.class);
        description = tuple.get("description", String.class);
        txnId = tuple.get("TXN_ID", String.class);
        amount = tuple.get("amount", Integer.class);
        currentAmount = tuple.get("CURRENT_AMOUNT", Integer.class);
        transactionType = tuple.get("TRANSACTION_TYPE", String.class);
    }
}
