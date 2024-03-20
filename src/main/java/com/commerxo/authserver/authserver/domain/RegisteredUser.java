package com.commerxo.authserver.authserver.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
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
public class RegisteredUser implements UserDetails {

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
    private Boolean isAccountNonLocked = false;
    private Boolean isAccountNonExpired = false;
    private Boolean isCredentialsNonExpired = false;
    private Integer loginCount;
    private Integer failedLoginAttempts;
    private String mfaSecret;
    private String mfaKeyId;
    private Boolean mfaEnabled;
    private Boolean mfaRegistered;
    private Boolean securityPhoneNoEnabled;
    private Boolean enabled = false;

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    @Column(name = "username", unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
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

    @Override
    @Column(name = "is_account_locked")
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    @Override
    @Column(name = "is_account_expired")
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    @Override
    @Column(name = "is_credential_expired")
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    @Column(name = "login_count")
    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }


    @Column(name = "mfa_secret")
    public String getMfaSecret() {
        return mfaSecret;
    }

    public void setMfaSecret(String mfaSecret) {
        this.mfaSecret = mfaSecret;
    }

    @Column(name = "mfa_key_id")
    public String getMfaKeyId() {
        return mfaKeyId;
    }

    public void setMfaKeyId(String mfaKeyId) {
        this.mfaKeyId = mfaKeyId;
    }

    @Column(name = "mfa_enabled")
    public Boolean getMfaEnabled() {
        return mfaEnabled;
    }

    public void setMfaEnabled(Boolean mfaEnabled) {
        this.mfaEnabled = mfaEnabled;
    }

    @Column(name = "mfa_registered")
    public Boolean getMfaRegistered() {
        return mfaRegistered;
    }

    public void setMfaRegistered(Boolean mfaRegistered) {
        this.mfaRegistered = mfaRegistered;
    }

    @Column(name = "failed_login_attempt")
    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    @Override
    @Column(name = "is_account_active")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean active) {
        this.enabled = active;
    }

    @Column(name = "security_phone_no_enabled")
    public Boolean getSecurityPhoneNoEnabled() {
        return securityPhoneNoEnabled;
    }

    public void setSecurityPhoneNoEnabled(Boolean securityPhoneNoEnabled) {
        this.securityPhoneNoEnabled = securityPhoneNoEnabled;
    }

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }
}
