package com.commerxo.authserver.authserver.dto;

import com.commerxo.authserver.authserver.domain.RegisteredUser;

public class UserRegistrationResponse {

    private String username;
    private String firstName;
    private String lastName;
    private String emailId;
    private Boolean active;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public static UserRegistrationResponse mapFromEntity(RegisteredUser registeredUser){
        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setUsername(registeredUser.getUsername());
        response.setFirstName(registeredUser.getFirstName());
        response.setLastName(registeredUser.getLastName());
        response.setEmailId(registeredUser.getEmailId());
        response.setActive(registeredUser.isActive());
        return response;
    }
}
