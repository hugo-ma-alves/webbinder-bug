package com.example.webbinderbug;

import org.springframework.web.bind.annotation.RequestParam;

public class SearchCriteria {

    private String name;

    @ParamName(value = "$page")
    private int page;

    @ParamName(value = "$size")
    private int size = 20;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
