package com.commerxo.authserver.authserver.dto;

import com.commerxo.authserver.authserver.domain.RegisteredUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class UserRegistrationRequest {

    private String firstName;
    private String lastName;
    private String username;
    private String emailId;
    private String password;

    @NotEmpty(message = "FirstName can't be null!")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotEmpty(message = "LastName can't be null!")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotEmpty(message = "Username can't be null!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty(message = "Email can't be null!")
    @Email(message = "")
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @NotEmpty(message = "password can't be null!")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static RegisteredUser mapToEntity(UserRegistrationRequest registrationRequest){
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setUsername(registrationRequest.getUsername());
        registeredUser.setFirstName(registrationRequest.getFirstName());
        registeredUser.setLastName(registrationRequest.getLastName());
        registeredUser.setEmailId(registrationRequest.getEmailId());
        registeredUser.setPassword(registeredUser.getPassword());
        return registeredUser;
    }
}