package com.example.android_mobile_banking.response;

import org.json.JSONObject;

public class UserResponse {
    private String response;
    private String status;
    private String message;
    private JSONObject payload;

    public UserResponse() {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getPayload() {
        return payload;
    }

    public void setPayload(JSONObject payload) {
        this.payload = payload;
    }
}
