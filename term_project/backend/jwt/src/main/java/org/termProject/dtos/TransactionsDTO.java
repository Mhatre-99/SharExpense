package org.termProject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionsDTO {


    private final String name;
    private final String initiatedBy;
    private final Double amount;
    private final String receiptLink;
    private final String transactionType;

    public TransactionsDTO(String name, String initiatedBy, Double amount, String receiptLink, String transactionType) {
        this.name = name;
        this.initiatedBy = initiatedBy;
        this.amount = amount;
        this.receiptLink = receiptLink;
        this.transactionType = transactionType;
    }

    public String getInitiatedBy() {
        return initiatedBy;
    }

    public String getName() {
        return name;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getReceiptLink() {
        return receiptLink;
    }

    public Double getAmount() {
        return amount;
    }


}
