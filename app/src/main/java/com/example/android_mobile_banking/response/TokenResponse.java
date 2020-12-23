package com.example.android_mobile_banking.response;

import com.example.android_mobile_banking.model.Token;

import org.json.JSONArray;

import java.util.List;

public class TokenResponse {

    private String response;
    private String status;
//    private JSONArray payload;
    private List<Token> payload;

    public TokenResponse() {
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Token> getPayload() {
        return payload;
    }

    public void setPayload(List<Token> payload) {
        this.payload = payload;
    }
}
