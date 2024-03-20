package com.commerxo.authserver.authserver.security.events;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureLockedEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureLockedListener implements ApplicationListener<AuthenticationFailureLockedEvent> {
    @Override
    public void onApplicationEvent(AuthenticationFailureLockedEvent event) {

    }
}
