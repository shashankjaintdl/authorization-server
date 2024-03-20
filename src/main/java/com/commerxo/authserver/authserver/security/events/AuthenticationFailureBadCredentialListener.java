package com.commerxo.authserver.authserver.security.events;

import com.commerxo.authserver.authserver.security.bruteforce.BruteForceProtectionService;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureBadCredentialListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final BruteForceProtectionService bruteForceProtectionService;

    public AuthenticationFailureBadCredentialListener(BruteForceProtectionService bruteForceProtectionService) {
        this.bruteForceProtectionService = bruteForceProtectionService;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
//        if(event.getAuthentication() != null)
//            this.bruteForceProtectionService.registerLoginFailure(event.getAuthentication().getName());
    }
}