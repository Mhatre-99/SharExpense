package org.termProject.dtos.response;

import java.util.List;
import java.util.Map;

public class ParsedItemsDTO {

    private List<ParsedItem> items;

    public ParsedItemsDTO() {
    }

    public List<ParsedItem> getitems() {
        return items;
    }

    public void setitems(List<ParsedItem> items) {
        this.items = items;
    }
}
