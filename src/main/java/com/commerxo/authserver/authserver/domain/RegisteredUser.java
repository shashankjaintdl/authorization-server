package com.commerxo.authserver.authserver.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;

@Entity
@Table(
        name = "registered_user",
        uniqueConstraints = @UniqueConstraint(
                name = "user_unique",
                columnNames = {"username"}
        )
)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class RegisteredUser {

    private String id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String emailId;
    private String phoneNo;
    private Set<Role> roles;
    private Boolean isEmailVerified =  false;
    private Boolean isPhoneNoVerified =  false;
    private Boolean isAccountLocked = false;
    private Boolean isAccountExpired = false;
    private Boolean isCredentialExpired = false;
    private Integer loginCount;
    private Integer failedLoginAttempts;
    private Boolean active = false;

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "username", unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", unique = true)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Enumerated(EnumType.STRING)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Column(name = "email_id", nullable = false)
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Column(name = "phone_no")
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.ALL
            })
    @JoinTable(
            name = "user_role",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id")
            }
    )
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Column(name = "is_email_verified")
    public Boolean isEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    @Column(name = "is_phone_verified")
    public Boolean isPhoneNoVerified() {
        return isPhoneNoVerified;
    }

    public void setPhoneNoVerified(Boolean phoneNoVerified) {
        isPhoneNoVerified = phoneNoVerified;
    }

    @Column(name = "is_account_locked")
    public Boolean isAccountLocked() {
        return isAccountLocked;
    }

    public void setAccountLocked(Boolean accountLocked) {
        isAccountLocked = accountLocked;
    }

    @Column(name = "is_account_expired")
    public Boolean isAccountExpired() {
        return isAccountExpired;
    }

    public void setAccountExpired(Boolean accountExpired) {
        isAccountExpired = accountExpired;
    }

    @Column(name = "is_credential_expired")
    public Boolean isCredentialExpired() {
        return isCredentialExpired;
    }

    public void setCredentialExpired(Boolean credentialExpired) {
        isCredentialExpired = credentialExpired;
    }

    @Column(name = "login_count")
    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    @Column(name = "failed_login_attempt")
    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    @Column(name = "is_account_active")
    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
