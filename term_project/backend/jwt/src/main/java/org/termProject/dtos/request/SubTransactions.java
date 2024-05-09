package org.termProject.dtos.request;


import java.util.List;

public class SubTransactions {

    private String itemName;

    private double itemPrice;

    private List<String> membersInvolved;

    public SubTransactions(){
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public List<String> getMembersInvolved() {
        return membersInvolved;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setMembersInvolved(List<String> membersInvolved) {
        this.membersInvolved = membersInvolved;
    }


}
