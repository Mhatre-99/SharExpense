package org.termProject.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParsedItem {
    @JsonProperty("ITEM")
    private String ITEM;
    @JsonProperty("PRICE")
    private  double PRICE;

    @JsonProperty("PRODUCT_CODE")
    private String PRODUCT_CODE;

    public ParsedItem(){
    }

    public ParsedItem(String ITEM, double PRICE, String PRODUCT_CODE){
        this.ITEM = ITEM;
        this.PRICE = PRICE;
        this.PRODUCT_CODE = PRODUCT_CODE;
    }
    public double getPrice() {
        return PRICE;
    }

    public void setPrice(double PRICE) {
        this.PRICE = PRICE;
    }

    public String getItem() {
        return ITEM;
    }

    public void setItem(String ITEM) {
        this.ITEM = ITEM;
    }

    public String getProductCode() {
        return PRODUCT_CODE;
    }

    public void setProductCode(String PRODUCT_CODE) {
        this.PRODUCT_CODE = PRODUCT_CODE;
    }
}
