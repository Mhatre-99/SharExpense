package org.termProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.termProject.model.Transactions;
import org.termProject.model.User;
import org.termProject.model.UserGroup;
import org.termProject.repository.SEGroupRepository;
import org.termProject.repository.TransactionsRepository;
import org.termProject.repository.UserGroupRepository;
import org.termProject.repository.UserRepository;

import java.util.*;

import static java.lang.Math.abs;

@Service
public class GroupService {

    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private TransactionsRepository transactionsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SEGroupRepository seGroupRepository;
    private double[] amount;
    private List<String> allMembers;
    public GroupService(){}

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
        System.out.println("All members in the group "+allMembers);
        System.out.println("amount per user "+ Arrays.toString(amount));
        List<String> balance = new ArrayList<>();
        while(!checkIfAllZero()){
            String payment;

            int indexOfLowest = findSmallest(amount);
            int indexOfLargest = findLargest(amount);
            double lowest = amount[indexOfLowest];
            double largest = amount[indexOfLargest];
            System.out.println(indexOfLargest+" "+ indexOfLowest);
            if (abs(lowest) > abs(largest)){
                System.out.println("After subtraction "+lowest + largest);
                double equa;
                if (largest+lowest > -0.00001){
                    equa =0.0;
                }else {
                    equa = largest + lowest;
                }
                this.amount[indexOfLowest] = equa;
                this.amount[indexOfLargest] = 0;
                payment = String.format("%s has to pay %.2f to %s", allMembers.get(indexOfLowest), largest ,allMembers.get(indexOfLargest));


            } else {
                double equa;
                if (largest+lowest < 0.01){
                    equa =0.0;
                }else {
                    equa = largest + lowest;
                }
                this.amount[indexOfLargest] = equa;
                this.amount[indexOfLowest] = 0;
                payment = String.format("%s has to pay %.2f to %s", allMembers.get(indexOfLowest), abs(lowest) ,allMembers.get(indexOfLargest));

            }
            balance.add(payment);

        }
        System.out.println(balance.toString());
        return balance;
    }

    public Double getPerUserBalance(Long userId, Long groupId){
        UserGroup ug = userGroupRepository.findAmountbyUserIdAndGroupId(groupId, userId);
        return ug.getAmount();
    }

    public List<String> getBalance(Long groupId){
        this.allMembers =  new ArrayList<>();
        Set<User> user = userGroupRepository.findUserByGroupId(groupId);

        for (User u: user){
            this.allMembers.add(u.getUsername());
        }
        this.amount = new double[this.allMembers.size()];
        int count = 0;
        for (String u: this.allMembers){
            Optional<User> username = userRepository.findByUsername(u);
            UserGroup ug = userGroupRepository.findAmountbyUserIdAndGroupId(groupId, username.get().getId());
            this.amount[count] = ug.getAmount();
            count++;
        }

        System.out.println("All members "+this.allMembers.toString());
        System.out.println("All balances "+ Arrays.toString(this.amount));

        List<String> bal = calculateBalance();
        return bal;
    }

    public List<Transactions> getTransactionOfGroup(Long groupId){
        System.out.println("In get transactions "+ groupId);
        List<Transactions> transactions;
        if (!transactionsRepository.findTransactionsByGroupId(groupId).get().isEmpty()) {
            transactions = new ArrayList<>(transactionsRepository.findTransactionsByGroupId(groupId).get());
        } else{
            transactions = null;
        }
        return transactions;
    }

}
