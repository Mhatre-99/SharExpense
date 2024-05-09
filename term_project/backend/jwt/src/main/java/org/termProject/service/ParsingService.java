package org.termProject.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.termProject.dtos.request.SubTransactions;
import org.termProject.dtos.response.ParsedItem;
import org.termProject.dtos.response.ParsedItemsDTO;
import org.termProject.dtos.response.ParsedSubTransactionsDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParsingService {

    private String json;

    private ParsedItemsDTO parsedItemsDTO;
    public ParsingService(){

    }

    public void setJson(String json) {
        this.json = json;
    }

    public ParsedItemsDTO parseJson(String json){
        setJson(json);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ParsedItemsDTO parsedItemsDTO = objectMapper.readValue(json, ParsedItemsDTO.class);
            System.out.println("Parsed Item "+parsedItemsDTO);
            return parsedItemsDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parsedItemsDTO;
    }

    public ParsedSubTransactionsDTO convertToSubTransactions(ParsedItemsDTO parsedItemsDTO){
        List<SubTransactions> subTransactionsList = new ArrayList<>();
        List<String> mem = new ArrayList<>();
        mem.add("everyone");
        for(ParsedItem parsedItem: parsedItemsDTO.getitems()){
           SubTransactions subTransactions = new SubTransactions();
           subTransactions.setItemName(parsedItem.getItem());
           subTransactions.setItemPrice(parsedItem.getPrice());
           subTransactions.setMembersInvolved(mem);
           subTransactionsList.add(subTransactions);
        }
        return new ParsedSubTransactionsDTO(subTransactionsList);
    }
}
