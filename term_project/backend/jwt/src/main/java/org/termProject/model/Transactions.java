package org.termProject.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "transactions")
public class Transactions {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String initiatedBy;

    @NotBlank
    private Double amount;

    private String receiptLink;

    @NotBlank
    private String transactionType;

    public Transactions() {

    }
    @ManyToMany(mappedBy = "transactions")
    private Set<User> users = new HashSet<>();

    @ManyToMany(mappedBy = "transactions")
    private Set<SEGroups> seGroups = new HashSet<>();

    public Transactions(String name, String initiatedBy, Double amount, String receiptLink, String transactionType) {
        this.name = name;
        this.initiatedBy = initiatedBy;
        this.amount = amount;
        this.receiptLink = receiptLink;
        this.transactionType = transactionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getInitiatedBy() {
        return initiatedBy;
    }

    public void setInitiatedBy(String initiatedBy) {
        this.initiatedBy = initiatedBy;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getReceiptLink() {
        return receiptLink;
    }

    public void setReceiptLink(String receiptLink) {
        this.receiptLink = receiptLink;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<SEGroups> getSeGroups() {
        return seGroups;
    }

    public void setSeGroups(Set<SEGroups> seGroups) {
        this.seGroups = seGroups;
    }


    public Set<User> getUsers() {
        return users;
    }
}
