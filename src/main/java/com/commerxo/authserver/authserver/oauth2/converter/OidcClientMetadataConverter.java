package com.commerxo.authserver.authserver.oauth2.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.oidc.OidcClientRegistration;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcClientConfigurationAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcClientRegistrationAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.oidc.converter.OidcClientRegistrationRegisteredClientConverter;
import org.springframework.security.oauth2.server.authorization.oidc.converter.RegisteredClientOidcClientRegistrationConverter;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class OidcClientMetadataConverter  {

    private static final List<String> clientMetadata = List.of("logo_uri", "contacts");

    public static Consumer<List<AuthenticationProvider>> oidcClientMetadataConfigurer(){
        return (authenticationProviders) -> {
            OidcClientRegistrationConverter oidcClientRegistrationConverter =
                    new OidcClientRegistrationConverter(clientMetadata);
            OidcRegisteredClientConverter oidcRegisteredClientConverter =
                    new OidcRegisteredClientConverter(clientMetadata);

            authenticationProviders.forEach(authenticationProvider -> {
                if(authenticationProvider instanceof OidcClientRegistrationAuthenticationProvider provider){
                    provider.setRegisteredClientConverter(oidcRegisteredClientConverter);
                    provider.setClientRegistrationConverter(oidcClientRegistrationConverter);
                }
                if(authenticationProvider instanceof OidcClientConfigurationAuthenticationProvider provider){
                    provider.setClientRegistrationConverter(oidcClientRegistrationConverter);
                }
            });
        };
    }

    private static class OidcRegisteredClientConverter implements Converter<OidcClientRegistration, RegisteredClient> {
        private final List<String> clientMetadata;
        private final OidcClientRegistrationRegisteredClientConverter delegate;

        private OidcRegisteredClientConverter(List<String> clientMetadata) {
            this.clientMetadata = clientMetadata;
            this.delegate = new OidcClientRegistrationRegisteredClientConverter();
        }

        @Override
        @SuppressWarnings("all")
        public RegisteredClient convert(OidcClientRegistration clientRegistration) {
            RegisteredClient registeredClient = this.delegate.convert(clientRegistration);
            ClientSettings.Builder clientSettingBuilder = ClientSettings.withSettings(
                    registeredClient.getClientSettings().getSettings());
            if (!CollectionUtils.isEmpty(this.clientMetadata)) {
                clientRegistration.getClaims()
                        .forEach((claim, value) -> {
                            if (this.clientMetadata.contains(claim)) {
                                clientSettingBuilder.setting(claim, value);
                            }
                        });
            }
            return RegisteredClient.from(registeredClient)
                    .clientSettings(clientSettingBuilder.build())
                    .build();

        }
    }

    private static class OidcClientRegistrationConverter implements Converter<RegisteredClient, OidcClientRegistration>{

        private final List<String> clientMetadata;
        private final RegisteredClientOidcClientRegistrationConverter delegate;

        private OidcClientRegistrationConverter(List<String> clientMetadata) {
            this.clientMetadata = clientMetadata;
            this.delegate = new RegisteredClientOidcClientRegistrationConverter();
        }

        @Override
        @SuppressWarnings("all")
        public OidcClientRegistration convert(RegisteredClient registeredClient) {
            OidcClientRegistration clientRegistration = this.delegate.convert(registeredClient);
            Map<String, Object> claims = new HashMap<>(clientRegistration.getClaims());
            if(!CollectionUtils.isEmpty(this.clientMetadata)){
                ClientSettings clientSettings = registeredClient.getClientSettings();
                claims.putAll(
                        this.clientMetadata.stream()
                                .filter(metadata -> clientSettings.getSetting(metadata) != null)
                                .collect(Collectors.toMap(Function.identity(), clientSettings::getSetting))
                );
            }
            return OidcClientRegistration.withClaims(claims).build();
        }
    }
}