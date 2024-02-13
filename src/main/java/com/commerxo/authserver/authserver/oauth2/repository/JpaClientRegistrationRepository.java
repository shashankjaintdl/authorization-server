package com.commerxo.authserver.authserver.oauth2.repository;

import com.commerxo.authserver.authserver.oauth2.ClientRepository;
import com.commerxo.authserver.authserver.oauth2.domain.Client;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class JpaClientRegistrationRepository implements RegisteredClientRepository {

    private final ClientRepository clientRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JpaClientRegistrationRepository(ClientRepository clientRepository) {
        Assert.notNull(clientRepository, "ClientRepository can't be null!");
        this.clientRepository = clientRepository;
        ClassLoader classLoader = JpaClientRegistrationRepository.class.getClassLoader();
        List<Module> securityModule = SecurityJackson2Modules.getModules(classLoader);
        this.objectMapper.registerModules(securityModule);
        this.objectMapper.registerModules(new OAuth2AuthorizationServerJackson2Module());
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        Assert.notNull(registeredClient, "registeredClient can't be null!");
        this.clientRepository.save(toEntity(registeredClient));
    }

    @Override
    public RegisteredClient findById(String id) {
        Assert.hasText(id, "Id can't be empty!");
        return this.clientRepository.findById(id).map(this::toObject).orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Assert.hasText(clientId,"clientId can't be empty!");
        return this.clientRepository.findByClientId(clientId).map(this::toObject).orElse(null);
    }

    private RegisteredClient toObject(Client client){
        Set<String> clientAuthenticationMethods =  StringUtils.commaDelimitedListToSet(client.getClientAuthenticationMethod());
        Set<String> clientScopes = StringUtils.commaDelimitedListToSet(client.getScopes());
        Set<String> authorizationGrantTypes = StringUtils.commaDelimitedListToSet(client.getClientAuthorizationGrantType());
        Set<String> redirectUris = StringUtils.commaDelimitedListToSet(client.getRedirectUris());
        Set<String> postLogoutRedirectUris = StringUtils.commaDelimitedListToSet(client.getPostLogoutRedirectUris());

        RegisteredClient.Builder registeredClient = RegisteredClient.withId(client.getId())
                .clientName(client.getClientName())
                .clientId(client.getClientId())
                .clientIdIssuedAt(client.getClientIdIssuedAt())
                .clientSecret(client.getClientSecret())
                .clientSecretExpiresAt(client.getClientSecretExpiredAt())
                .redirectUris(uris -> uris.addAll(redirectUris))
                .postLogoutRedirectUris(uris -> uris.addAll(postLogoutRedirectUris))
                .scopes(scopes -> scopes.addAll(clientScopes))
                .authorizationGrantTypes(grantTypes ->
                        authorizationGrantTypes.forEach(authorizationGrantType ->
                                grantTypes.add(resolveAuthorizationGrantType(authorizationGrantType))
                        )
                )
                .clientAuthenticationMethods(authenticationMethod->
                        clientAuthenticationMethods.forEach(clientAuthenticationMethod -> authenticationMethod.add(resolveClientAuthenticationMethod(clientAuthenticationMethod)))
                )
                .clientSettings(ClientSettings.withSettings(parseMap(client.getClientSettings())).build())
                .tokenSettings(TokenSettings.withSettings(parseMap(client.getTokenSettings())).build());

        return registeredClient.build();
    }

    private Client toEntity(RegisteredClient registeredClient){
        List<String> clientAuthenticationMethods = new ArrayList<>(registeredClient.getClientAuthenticationMethods().size());
        registeredClient.getClientAuthenticationMethods()
                .forEach(clientAuthenticationMethod -> clientAuthenticationMethods.add(clientAuthenticationMethod.getValue()));

        List<String> grantTypes = new ArrayList<>(registeredClient.getAuthorizationGrantTypes().size());
        registeredClient.getAuthorizationGrantTypes()
                .forEach(grantType -> grantTypes.add(grantType.getValue()));

        Client client = new Client();
        client.setId(registeredClient.getId());
        client.setClientId(registeredClient.getClientId());
        client.setClientIdIssuedAt(registeredClient.getClientIdIssuedAt());
        client.setClientSecretExpiredAt(registeredClient.getClientSecretExpiresAt());
        client.setClientSecret(registeredClient.getClientSecret());
        client.setClientName(registeredClient.getClientName());
        client.setScopes(StringUtils.collectionToCommaDelimitedString(registeredClient.getScopes()));
        client.setClientAuthenticationMethod(StringUtils.collectionToCommaDelimitedString(clientAuthenticationMethods));
        client.setClientAuthorizationGrantType(StringUtils.collectionToCommaDelimitedString(grantTypes));
        client.setPostLogoutRedirectUris(StringUtils.collectionToCommaDelimitedString(registeredClient.getPostLogoutRedirectUris()));
        client.setRedirectUris(StringUtils.collectionToCommaDelimitedString(registeredClient.getRedirectUris()));
        client.setClientSettings(writeMap(registeredClient.getClientSettings().getSettings()));
        client.setTokenSettings(writeMap(registeredClient.getTokenSettings().getSettings()));
        return client;
    }

    private String writeMap(Map<String, Object> objectMap){
        try{
            return this.objectMapper.writeValueAsString(objectMap);
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    private Map<String, Object> parseMap(String data)  {
        try {
            return this.objectMapper.readValue(data, new TypeReference<>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    private static ClientAuthenticationMethod resolveClientAuthenticationMethod(String authenticationMethod){
        if(ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue().equals(authenticationMethod)){
            return ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
        } else if (ClientAuthenticationMethod.CLIENT_SECRET_POST.getValue().equals(authenticationMethod)) {
            return ClientAuthenticationMethod.CLIENT_SECRET_POST;
        } else if (ClientAuthenticationMethod.CLIENT_SECRET_JWT.getValue().equals(authenticationMethod)) {
            return ClientAuthenticationMethod.CLIENT_SECRET_JWT;
        } else if (ClientAuthenticationMethod.PRIVATE_KEY_JWT.getValue().equals(authenticationMethod)) {
            return ClientAuthenticationMethod.PRIVATE_KEY_JWT;
        }else if(ClientAuthenticationMethod.NONE.getValue().equals(authenticationMethod)){
            return ClientAuthenticationMethod.NONE;
        }
        return new ClientAuthenticationMethod(authenticationMethod);
    }

    private static AuthorizationGrantType resolveAuthorizationGrantType(String authorizationGrantType){
        if(AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equals(authorizationGrantType)){
            return AuthorizationGrantType.AUTHORIZATION_CODE;
        } else if (AuthorizationGrantType.CLIENT_CREDENTIALS.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.CLIENT_CREDENTIALS;
        }else if(AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(authorizationGrantType)){
            return AuthorizationGrantType.REFRESH_TOKEN;
        } else if (AuthorizationGrantType.DEVICE_CODE.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.DEVICE_CODE;
        } else if (AuthorizationGrantType.JWT_BEARER.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.JWT_BEARER;
        }
        return new AuthorizationGrantType(authorizationGrantType);
    }
}