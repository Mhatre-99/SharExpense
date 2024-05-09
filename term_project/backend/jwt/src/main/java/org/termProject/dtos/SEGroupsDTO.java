package org.termProject.dtos;

import org.hibernate.Transaction;
import org.termProject.model.Transactions;
import org.termProject.dtos.TransactionsDTO;

import java.util.Date;
import java.util.List;

public class SEGroupsDTO {


    private Long id;
    private String name;
    private String createdBy;
    private Double amount;
    private String amountType;
    private List<String> balances;
    private List<TransactionsDTO> transactionsDTOS;
    private Date date_created;

    public SEGroupsDTO() {

    }

    public SEGroupsDTO(Long id, String name, String createdBy, Date dateCreated, List<String> balances, List<TransactionsDTO> transactionsDTOS) {
        this.id = id;
        this.createdBy = createdBy;
        this.name = name;
        this.date_created = dateCreated;
        this.balances = balances;
        this.transactionsDTOS = transactionsDTOS;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public Date getDate_created() {
        return date_created;
    }

    public List<String> getBalances() {
        return balances;
    }

    public void setBalances(List<String> balances) {
        this.balances = balances;
    }

    public List<TransactionsDTO> getTransactionsDTOS() {
        return transactionsDTOS;
    }

    public void setTransactionsDTOS(List<TransactionsDTO> transactionsDTOS) {
        this.transactionsDTOS = transactionsDTOS;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }
}
