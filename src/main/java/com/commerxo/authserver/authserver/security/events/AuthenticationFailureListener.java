package com.commerxo.authserver.authserver.security.events;

import com.commerxo.authserver.authserver.security.bruteforce.BruteForceProtectionService;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AbstractAuthenticationFailureEvent> {

    private final BruteForceProtectionService bruteForceProtectionService;

    public AuthenticationFailureListener(BruteForceProtectionService bruteForceProtectionService) {
        this.bruteForceProtectionService = bruteForceProtectionService;
    }

    @Override
    public void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
        System.out.println("Fail");
    }
}