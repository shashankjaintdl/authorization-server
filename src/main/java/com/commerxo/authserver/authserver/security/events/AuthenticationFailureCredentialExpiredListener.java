package com.commerxo.authserver.authserver.security.events;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureCredentialsExpiredEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureCredentialExpiredListener implements ApplicationListener<AuthenticationFailureCredentialsExpiredEvent> {

    @Override
    public void onApplicationEvent(AuthenticationFailureCredentialsExpiredEvent event) {

    }

}