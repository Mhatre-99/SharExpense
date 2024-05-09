package org.termProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.termProject.dtos.request.CompleteTransaction;
import org.termProject.dtos.request.SubTransactions;
import org.termProject.dtos.response.ParsedItem;
import org.termProject.dtos.response.ParsedItemsDTO;
import org.termProject.dtos.response.ParsedSubTransactionsDTO;
import org.termProject.dtos.response.ReceiptParseRequest;
import org.termProject.service.CreateTransaction;
import org.termProject.service.ParsingService;
import org.termProject.service.S3Services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private S3Services s3Services;

    @Autowired
    private ParsingService parsingService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CreateTransaction createTransaction;

    @Value(value = "${lambda.url}")
    private String flask_uri;

    private ReceiptParseRequest receiptParseRequest;
    @PostMapping("/parse-file")
    public ResponseEntity<?> addTransaction(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id, @RequestParam("username") String username)//@RequestParam("file") MultipartFile file,
    {
        // Add your logic to handle file storage
        System.out.println("Received file: " + id);
        System.out.println("Received file: " + file.getOriginalFilename());
        System.out.println("Received file: " + username);
        String keyName = s3Services.uploadFile(file, "share-expense-2");
        receiptParseRequest = new ReceiptParseRequest(keyName);
        ResponseEntity<String> apiResponse = restTemplate.postForEntity(flask_uri, receiptParseRequest, String.class);
        ParsedItemsDTO data = parsingService.parseJson(apiResponse.getBody());
        ParsedSubTransactionsDTO parsedSubTransactionsDTO = parsingService.convertToSubTransactions(data);
        System.out.println("parsed data"+ parsedSubTransactionsDTO.toString());
        /*List<ParsedItem> item = new ArrayList<>();
        item.add(new ParsedItem("DISH CLOTHS", 2.50,"667888159581"));
        item.add(new ParsedItem("PILLOW", 4.00,"667888159581"));*/
        //ParsedItemsDTO data = new ParsedItemsDTO();
        //data.setitems(item);

        System.out.println(parsedSubTransactionsDTO.getItems().get(0).getItemName());

        return ResponseEntity.ok(parsedSubTransactionsDTO);
    }

    @PostMapping("/create-transaction")
    public ResponseEntity<?> createTransaction(@RequestBody CompleteTransaction completeTransaction){
        System.out.println("transaction received "+completeTransaction.getSubTransactions());
        List<SubTransactions> subTransactions = completeTransaction.getSubTransactions();
        //System.out.println(subTransactions.get(0));
        createTransaction.createTheTransaction(completeTransaction);
        return ResponseEntity.ok(completeTransaction);
    }



}
