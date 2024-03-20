package com.commerxo.authserver.authserver.security.events;

import com.commerxo.authserver.authserver.security.bruteforce.BruteForceProtectionService;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final BruteForceProtectionService bruteForceProtectionService;

    public AuthenticationSuccessListener(BruteForceProtectionService bruteForceProtectionService) {
        this.bruteForceProtectionService = bruteForceProtectionService;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        if(event.getSource() instanceof UsernamePasswordAuthenticationToken)
           this.bruteForceProtectionService.resetBruteForceCounter(event.getAuthentication().getName());
    }
}
