package org.termProject.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
public class UserGroup {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private SEGroups group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "amount")
    private double amount;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public UserGroup(){

    }

    public UserGroup(SEGroups group, User user, int amount){
        this.amount = amount;
        this.group = group;
        this.user = user;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public SEGroups getGroup() {
        return group;
    }

    public Long getId() {
        return id;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setGroup(SEGroups group) {
        this.group = group;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
