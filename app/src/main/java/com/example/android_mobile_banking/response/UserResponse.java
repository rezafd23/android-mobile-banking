package com.example.android_mobile_banking.response;

import com.example.android_mobile_banking.model.Mutasi;

import org.json.JSONObject;

import java.util.List;

public class UserResponse {
    private String response;
    private String status;
    private String message;
    private JSONObject payload;
    private List<Mutasi> mutasiList;

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

    public List<Mutasi> getMutasiList() {
        return mutasiList;
    }

    public void setMutasiList(List<Mutasi> mutasiList) {
        this.mutasiList = mutasiList;
    }
}
