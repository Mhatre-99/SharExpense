package org.termProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.termProject.dtos.request.CompleteTransaction;
import org.termProject.dtos.request.SubTransactions;
import org.termProject.model.SEGroups;
import org.termProject.model.Transactions;
import org.termProject.model.User;
import org.termProject.model.UserGroup;
import org.termProject.repository.SEGroupRepository;
import org.termProject.repository.TransactionsRepository;
import org.termProject.repository.UserGroupRepository;
import org.termProject.repository.UserRepository;

import java.util.*;

import static java.lang.Math.*;

@Service
public class CreateTransaction {


    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private TransactionsRepository transactionsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SEGroupRepository seGroupRepository;
    private Transactions transactions;
    private List<String> allMembers;

    private double[] amount;
    private double totalAmount;
    private CompleteTransaction completeTransaction;

    public CreateTransaction(){

    }



    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public List<String> getAllMembers() {
        return allMembers;
    }

    public double[] getAmount() {
        return amount;
    }

    private void setAmountInDatabaseDummy(){
        System.out.println("Group ID: "+completeTransaction.getGroupId());
        for(String u: allMembers ) {
            Optional<User> user = userRepository.findByUsername(u);
            System.out.println("User id being updated "+user.get().getId());
            UserGroup ug = userGroupRepository.findAmountbyUserIdAndGroupId(completeTransaction.getGroupId(), user.get().getId());
            if (u.equals("sm")){ug.setAmount(15.0);}
            if (u.equals("nm")){ug.setAmount(-9.0);}
            if(u.equals("pm")){ug.setAmount(-6.0);}
            userGroupRepository.save(ug);
            /*int indexOfUser = allMembers.indexOf(u);
            this.amount[indexOfUser] = ug.getAmount();*/
        }
    }

    private void setAmountInDatabase(){
        System.out.println("Group ID: "+completeTransaction.getGroupId());
        for(String u: allMembers ) {
            Optional<User> user = userRepository.findByUsername(u);
            Long userId = user.get().getId();
            int indexOfUser = allMembers.indexOf(u);
            System.out.println("User id being updated "+ userId);
            UserGroup ug = userGroupRepository.findAmountbyUserIdAndGroupId(completeTransaction.getGroupId(), userId);
            ug.setAmount(amount[indexOfUser]);
            userGroupRepository.save(ug);
            /*int indexOfUser = allMembers.indexOf(u);
            this.amount[indexOfUser] = ug.getAmount();*/
        }
    }

    public void setAmount() {
        this.amount = new double[allMembers.size()];
        for(String u: allMembers ) {
            Optional<User> user = userRepository.findByUsername(u);
            UserGroup ug = userGroupRepository.findAmountbyUserIdAndGroupId(completeTransaction.getGroupId(), user.get().getId());
            int indexOfUser = allMembers.indexOf(u);
            this.amount[indexOfUser] = ug.getAmount();
        }
    }

    public void setAllMembers(List<String> allMembers) {
        this.allMembers = allMembers;
    }

    public void printAllMembers(){
        for(String u: this.allMembers){
            System.out.println("username "+ u);
        }
    }
    private void getAllMembersFromDB(){
        Optional<User> userId = userRepository.findByUsername(completeTransaction.getInitiatedBy());
        Set<User> usersInvolved = userGroupRepository.findUserByGroupId(completeTransaction.getGroupId());
        List<String> members = new ArrayList<>();
        for (User u: usersInvolved){
            members.add(u.getUsername());
        }
        setAllMembers(members);

        //printAllMembers();
        //System.out.println("initiatedBy "+userId);
    }

    public int giveNumberOfUsers(SubTransactions subTransactions){
        return subTransactions.getMembersInvolved().size();
    }

    public double[] calculateTheAmountPerSubTransaction(SubTransactions subTransactions){
        int size = this.allMembers.size();
        double[] subAmount = new double[size];
        int initiatedByIndex = this.allMembers.indexOf(this.completeTransaction.getInitiatedBy());
        subAmount[initiatedByIndex] += subTransactions.getItemPrice();
        List<String> membersInvolved = subTransactions.getMembersInvolved();
        System.out.println("Amount for initiated by "+ Arrays.toString(subAmount));
        if (membersInvolved.get(0).equals("everyone")){
            double amount = subTransactions.getItemPrice()/size;
            for(int i = 0; i<size;i++){
                subAmount[i] = subAmount[i]-amount;
            }
        } else {
            double amount = subTransactions.getItemPrice()/(membersInvolved.size());
            System.out.println("sub amount "+amount);
            for (String m : membersInvolved) {
                System.out.println("membersInvolved : " + m);
                int indexOfMember = allMembers.indexOf(m);
                subAmount[indexOfMember] -= amount;
            }
        }
        return subAmount;
    }

    private void calculateAmountForTransaction(){
        System.out.println("All members in the group "+this.allMembers.toString());
        setAmount();
        System.out.println("Previous balance "+Arrays.toString(this.amount));

        for(SubTransactions s: completeTransaction.getSubTransactions()){
            this.totalAmount += s.getItemPrice();
            System.out.println("members involved "+ s.getMembersInvolved());
            System.out.println("Item Price "+s.getItemPrice());
            double[] subAmount = calculateTheAmountPerSubTransaction(s);
            System.out.println("Divided sub amounts "+Arrays.toString(subAmount));
            for (int i=0; i< allMembers.size(); i++){
                amount[i]+=subAmount[i];
            }
        }
        setAmountInDatabase();
        System.out.println("Total transaction amount = " + this.totalAmount);

    }

    private void addTransactiontoDB(){
        Transactions transactions = new Transactions(completeTransaction.getName(),
                completeTransaction.getInitiatedBy(), this.totalAmount, completeTransaction.getReceiptName(), completeTransaction.getType());
        Optional<User> user = userRepository.findByUsername(completeTransaction.getInitiatedBy());
        user.get().addTransaction(transactions);
        Optional<SEGroups> seGroups = seGroupRepository.findSEGroupsById(completeTransaction.getGroupId());
        seGroups.get().addTransaction(transactions);
        transactionsRepository.save(transactions);
    }

    private boolean checkIfAllZero(){
        //System.out.println("checking for zeros "+ Arrays.toString(this.amount));
        for(double d: this.amount) {
            if (d!=0.0){
                return false;
            }

        }
        return true;
    }

    private int findSmallest(double[] arr){
        double smallest = arr[0];
        int count = 0;
        int indexOfSmallest = 0;
        for(double d: arr){

            if (smallest > d){
                smallest = d;
                indexOfSmallest=count;
            }
            count +=1;
        }
        return indexOfSmallest;
    }

    private int findLargest(double[] arr){
        double largest = arr[0];
        int count = 0;
        int indexOfLargest = 0;
        for(double d: arr){

            if (largest < d){
                largest = d;
                indexOfLargest=count;
            }
            count +=1;
        }
        return indexOfLargest;
    }



    public List<String> calculateBalance(){
        getAllMembersFromDB();
        System.out.println("All members in the group "+allMembers);
        setAmount();
        System.out.println("amount per user "+ Arrays.toString(amount));
        List<String> balance = new ArrayList<>();
        while(!checkIfAllZero()){
            String payment;

            int indexOfLowest = findSmallest(amount);
            int indexOfLargest = findLargest(amount);
            double lowest = amount[indexOfLowest];
            double largest = amount[indexOfLargest];
            if (abs(lowest) > abs(largest)){
                this.amount[indexOfLowest] = lowest + largest;
                this.amount[indexOfLargest] = 0;
                payment = String.format("%s has to pay %.2f to %s", allMembers.get(indexOfLowest), largest ,allMembers.get(indexOfLargest));


            } else {
                this.amount[indexOfLargest] = largest+lowest;
                this.amount[indexOfLowest] = 0;
                payment = String.format("%s has to pay %.2f to %s", allMembers.get(indexOfLowest), abs(lowest) ,allMembers.get(indexOfLargest));

            }
            balance.add(payment);

        }
        System.out.println(balance.toString());
        return balance;
    }

    public void settleTransaction(){
        System.out.println("All members in the group "+allMembers);
        setAmount();
        System.out.println("amount per user "+ Arrays.toString(amount));
        int userIndex = allMembers.indexOf(completeTransaction.getInitiatedBy());
        double userAmount = amount[userIndex];
        this.totalAmount = abs(userAmount);
        while(userAmount!=0){
            System.out.println("Amount after payment "+ Arrays.toString(amount));
            int indexOfLargest = findLargest(amount);
            double largest = amount[indexOfLargest];
            if (abs(userAmount) > abs(largest)){
                double equa;
                if (userAmount + largest > -0.0001){
                    equa = 0.0;
                }else{
                    equa = userAmount + largest;
                }
                this.amount[userIndex] = equa;
                this.amount[indexOfLargest] = 0;
                userAmount = equa;
            } else {
                double equa;
                if (userAmount + largest < 0.0001){
                    equa = 0.0;
                }else{
                    equa = userAmount + largest;
                }
                this.amount[indexOfLargest] = equa;
                this.amount[userIndex] = 0;
                userAmount = 0;
            }
        }

        System.out.println("Amount after payment "+ Arrays.toString(amount));
        addTransactiontoDB();
        setAmountInDatabase();

    }

    public void createTheTransaction(CompleteTransaction completeTransaction) {
        this.completeTransaction = completeTransaction;
        getAllMembersFromDB();
        if(completeTransaction.getType() .equals("lent")){
            System.out.println("Amount type is lent");
            calculateAmountForTransaction();
            addTransactiontoDB();
            setAmountInDatabase();
            this.totalAmount= this.totalAmount*0;
        }
        if (completeTransaction.getType().equals("payback")){settleTransaction();
        this.totalAmount = this.totalAmount*0;}
        //calculateBalance();
        //System.out.println(Arrays.toString(calculateAmountForTransaction()));
        //addTransactiontoDB();
        //setAmountInDatabase();
    }
}
