package org.termProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.termProject.dtos.ListOFTransactionsDTO;
import org.termProject.dtos.SEGroupsDTO;
import org.termProject.dtos.TransactionsDTO;
import org.termProject.dtos.request.AddGroup;
import org.termProject.model.SEGroups;
import org.termProject.model.Transactions;
import org.termProject.model.User;
import org.termProject.repository.SEGroupRepository;
import org.termProject.repository.UserGroupRepository;
import org.termProject.repository.UserRepository;
import org.termProject.model.UserGroup;
import org.termProject.service.CreateTransaction;
import org.termProject.service.GroupService;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("/api")
public class GroupController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SEGroupRepository seGroupRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private CreateTransaction createTransaction;

    @Autowired
    private GroupService groupService;

    @GetMapping("/check-username/{username}")
    public ResponseEntity<?> getUserById(@PathVariable String username) {
        System.out.println(username);
        if (userRepository.existsByUsername(username)){
            return ResponseEntity.ok(1);
        }else {
            return ResponseEntity.ok(0);
        }

    }

    @PostMapping("/add-group")
    public ResponseEntity<?> addGroup(@RequestBody AddGroup addGroup){
        System.out.println("username "+addGroup.getGroupName());
        System.out.println(addGroup.getMembers());
        SEGroups seGroups = new SEGroups(addGroup.getGroupName(), addGroup.getCreatedBy(), new Date());
        Set<User> newUser= new HashSet<>();
        for(String s: addGroup.getMembers()){
            User user = userRepository.findByUsername(s).orElseThrow(() -> new RuntimeException("Error: Username not found." + s));
            newUser.add(user);
        }
        seGroupRepository.save(seGroups);
        for(User u: newUser ){
            UserGroup ug = new UserGroup(seGroups,u,0);
            userGroupRepository.save(ug);
        }

        return ResponseEntity.ok("received information");

    }

    @GetMapping("/groups")
    public ResponseEntity<Set<SEGroupsDTO>> findGroupsByusername(Authentication authentication){
        String username = authentication.getName();
        System.out.println("username : "+username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Error: Username not found." + username));
        Set<SEGroups> groups = userGroupRepository.findGroupByUserId(user.getId());
        System.out.println(groups);
        Set<SEGroupsDTO> groupsDTO = new HashSet<>();
        for (SEGroups group : groups) {
            SEGroupsDTO o = convertToDto(group, null, null);
            Double amount = groupService.getPerUserBalance(user.getId(), group.getId());
            o.setAmount(Math.abs(amount));
            if(amount <0){
                o.setAmountType("You owe");
            } else {
                o.setAmountType("You are owed");
            }
            groupsDTO.add(o);
        }
        return ResponseEntity.ok(groupsDTO);
    }

    private SEGroupsDTO convertToDto(SEGroups group, List<String> balances, List<TransactionsDTO> transactionsDTOS) {
        return new SEGroupsDTO(group.getId(), group.getName(), group.getCreatedBy(), group.getDate_created(), balances, transactionsDTOS);
    }

    private TransactionsDTO convertToTransactionDto(Transactions transaction) {
        return new TransactionsDTO(transaction.getName(), transaction.getInitiatedBy(), transaction.getAmount(), transaction.getReceiptLink(), transaction.getTransactionType());
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<SEGroupsDTO> findGroupById(@PathVariable Long id){
        System.out.println("Group id "+id);
        SEGroups seGroups = seGroupRepository.findSEGroupsById(id).orElseThrow(() -> new RuntimeException("Error: Username not found." + id));
        System.out.println(seGroups.getName()+" "+ seGroups.getId());

        List<String> balances = groupService.getBalance(id);

        System.out.println("Got DTO now trnasction");
        List<Transactions> transactions = groupService.getTransactionOfGroup(seGroups.getId());
        SEGroupsDTO seGroupsDTO;
        if(transactions != null){
        List<TransactionsDTO> transactionsDTO = new ArrayList<>();
        for(Transactions t : transactions){
            TransactionsDTO tdto = convertToTransactionDto(t);
            transactionsDTO.add(tdto);
        }
        ListOFTransactionsDTO listOFTransactionsDTO = new ListOFTransactionsDTO(transactionsDTO);
        System.out.println(transactions);
        seGroupsDTO = convertToDto(seGroups, balances, transactionsDTO);}
        else{
            seGroupsDTO = convertToDto(seGroups, balances, null);
        }

        return ResponseEntity.ok(seGroupsDTO);
    }

    @GetMapping("/group-user/{id}")
    public ResponseEntity<?> getUserByGroup(@PathVariable Long id){
        List<User> u = new ArrayList<>(userGroupRepository.findUserByGroupId(id));
        List<String> usernames = new ArrayList<>();
        for(User us: u){
            usernames.add(us.getUsername());
        }
        return ResponseEntity.ok( usernames);
    }
}

