package com.jsondiff.model;

public class JsonFormatRequest {
    private String json1;
    private String json2;

    public JsonFormatRequest() {
    }

    public JsonFormatRequest(String json1, String json2) {
        this.json1 = json1;
        this.json2 = json2;
    }

    public String getJson1() {
        return json1;
    }

    public void setJson1(String json1) {
        this.json1 = json1;
    }

    public String getJson2() {
        return json2;
    }

    public void setJson2(String json2) {
        this.json2 = json2;
    }
}
