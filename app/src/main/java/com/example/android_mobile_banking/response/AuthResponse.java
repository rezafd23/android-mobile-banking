package com.example.android_mobile_banking.response;

import org.json.JSONObject;

public class AuthResponse {

    private String response;
    private String status;
    private String payload;
    private String otp;
    private String otpId;
    private String access_token;
    private JSONObject payloadData;

    public AuthResponse() {
    }

    public JSONObject getPayloadData() {
        return payloadData;
    }

    public void setPayloadData(JSONObject payloadData) {
        this.payloadData = payloadData;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
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

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getOtpId() {
        return otpId;
    }

    public void setOtpId(String otpId) {
        this.otpId = otpId;
    }
}
