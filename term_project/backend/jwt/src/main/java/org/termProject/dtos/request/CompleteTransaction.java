package org.termProject.dtos.request;

import java.util.List;

public class CompleteTransaction {

    private Long groupId;
    private String name;
    private String initiatedBy;
    private String receiptName;
    private String type;
    private List<SubTransactions> subTransactions;

    public CompleteTransaction(){

    }

    public void setSubTransactions(Long groupId,String name, String initiatedBy, String receiptName, String type, List<SubTransactions> subTransactions) {
        this.subTransactions = subTransactions;
        this.initiatedBy = initiatedBy;
        this.name = name;
        this.receiptName = receiptName;
        this.groupId = groupId;
        this.type = type;
    }

    public List<SubTransactions> getSubTransactions() {
        return subTransactions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInitiatedBy(String initiatedBy) {
        this.initiatedBy = initiatedBy;
    }

    public String getInitiatedBy() {
        return initiatedBy;
    }

    public String getReceiptName() {
        return receiptName;
    }

    public void setReceiptName(String receiptName) {
        this.receiptName = receiptName;
    }

    public void setSubTransactions(List<SubTransactions> subTransactions) {
        this.subTransactions = subTransactions;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
