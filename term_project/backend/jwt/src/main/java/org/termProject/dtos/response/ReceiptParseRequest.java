package org.termProject.dtos.response;

public class ReceiptParseRequest {

    private String fileName;

    public ReceiptParseRequest(String fileName){
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
