package org.termProject.dtos;

import java.util.List;

public class ListOFTransactionsDTO {

    private List<TransactionsDTO> transactionsDTOList;

    public ListOFTransactionsDTO(List<TransactionsDTO> transactionsDTOList){
        this.transactionsDTOList = transactionsDTOList;
    }

    public void setTransactionsDTOList(List<TransactionsDTO> transactionsDTOList) {
        this.transactionsDTOList = transactionsDTOList;
    }

    public List<TransactionsDTO> getTransactionsDTOList() {
        return transactionsDTOList;
    }
}
