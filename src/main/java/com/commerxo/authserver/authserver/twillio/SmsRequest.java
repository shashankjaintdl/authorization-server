package com.commerxo.authserver.authserver.twillio;

public class SmsRequest {

    private String phoneNo;
    private String message;

    public SmsRequest(String phoneNo, String message) {
        this.phoneNo = phoneNo;
        this.message = message;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
