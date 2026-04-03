package com.jsondiff.model;

public class JsonFormatResponse {
    private boolean success;
    private String formatted1;
    private String formatted2;
    private String error;

    public JsonFormatResponse() {
    }

    public static JsonFormatResponse success(String formatted1, String formatted2) {
        JsonFormatResponse response = new JsonFormatResponse();
        response.setSuccess(true);
        response.setFormatted1(formatted1);
        response.setFormatted2(formatted2);
        response.setError(null);
        return response;
    }

    public static JsonFormatResponse error(String errorMessage) {
        JsonFormatResponse response = new JsonFormatResponse();
        response.setSuccess(false);
        response.setFormatted1(null);
        response.setFormatted2(null);
        response.setError(errorMessage);
        return response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFormatted1() {
        return formatted1;
    }

    public void setFormatted1(String formatted1) {
        this.formatted1 = formatted1;
    }

    public String getFormatted2() {
        return formatted2;
    }

    public void setFormatted2(String formatted2) {
        this.formatted2 = formatted2;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
