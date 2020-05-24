package com.bluto.utils.ocr;

public enum BltOcrProvider {

    OCR_PROVIDER_BAIDU("baidu");

    private String name;

    BltOcrProvider(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
