package com.commerxo.authserver.authserver.oauth2.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "client")
public class Client {

    private String id;
    private String clientId;
    private String clientName;
    private String clientSecret;
    private Instant clientIdIssuedAt;
    private Instant clientSecretExpiredAt;
    private String clientAuthenticationMethod;
    private String clientAuthorizationGrantType;
    private String redirectUris;
    private String postLogoutRedirectUris;
    private String scopes;
    private String clientSettings;
    private String tokenSettings;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Instant getClientIdIssuedAt() {
        return clientIdIssuedAt;
    }

    public void setClientIdIssuedAt(Instant clientIdIssuedAt) {
        this.clientIdIssuedAt = clientIdIssuedAt;
    }

    public Instant getClientSecretExpiredAt() {
        return clientSecretExpiredAt;
    }

    public void setClientSecretExpiredAt(Instant clientSecretExpiredAt) {
        this.clientSecretExpiredAt = clientSecretExpiredAt;
    }

    @Column(length = 1000)
    public String getClientAuthorizationGrantType() {
        return clientAuthorizationGrantType;
    }

    public void setClientAuthorizationGrantType(String clientAuthorizationGrantType) {
        this.clientAuthorizationGrantType = clientAuthorizationGrantType;
    }

    @Column(length = 1000)
    public String getClientAuthenticationMethod() {
        return clientAuthenticationMethod;
    }

    public void setClientAuthenticationMethod(String clientAuthenticationMethod) {
        this.clientAuthenticationMethod = clientAuthenticationMethod;
    }

    @Column(length = 1000)
    public String getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(String redirectUris) {
        this.redirectUris = redirectUris;
    }

    @Column(length = 1000)
    public String getPostLogoutRedirectUris() {
        return postLogoutRedirectUris;
    }

    public void setPostLogoutRedirectUris(String postLogoutRedirectUris) {
        this.postLogoutRedirectUris = postLogoutRedirectUris;
    }

    @Column(length = 1000)
    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    @Column(length = 2000)
    public String getClientSettings() {
        return clientSettings;
    }

    public void setClientSettings(String clientSettings) {
        this.clientSettings = clientSettings;
    }

    @Column(length = 2000)
    public String getTokenSettings() {
        return tokenSettings;
    }

    public void setTokenSettings(String tokenSettings) {
        this.tokenSettings = tokenSettings;
    }
}