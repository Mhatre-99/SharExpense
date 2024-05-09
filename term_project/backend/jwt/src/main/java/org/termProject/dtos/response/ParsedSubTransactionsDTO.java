package org.termProject.dtos.response;

import org.termProject.dtos.request.SubTransactions;

import java.util.List;

public class ParsedSubTransactionsDTO {

    private List<SubTransactions> items;

    public ParsedSubTransactionsDTO(List<SubTransactions> items){
        this.items = items;
    }

    public List<SubTransactions> getItems() {
        return items;
    }

    public void setItems(List<SubTransactions> items) {
        this.items = items;
    }
}
