package com.commerxo.authserver.authserver.security.mfa;

import com.commerxo.authserver.authserver.domain.RegisteredUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import java.io.IOException;

public class MFAHandler implements AuthenticationSuccessHandler {

    SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    AuthenticationSuccessHandler mfaNotEnabled  = new SavedRequestAwareAuthenticationSuccessHandler();
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final String authority;

    public MFAHandler(String successUrl, String authority){
        SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler =
                new SimpleUrlAuthenticationSuccessHandler(successUrl);
        authenticationSuccessHandler.setAlwaysUseDefaultTargetUrl(true);
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authority = authority;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        if(authentication instanceof UsernamePasswordAuthenticationToken){
            RegisteredUser registeredUser = (RegisteredUser) authentication.getPrincipal();
            if(!registeredUser.getMfaEnabled()){
                mfaNotEnabled.onAuthenticationSuccess(request, response, authentication);
                return;
            }
            saveAuthentication(request, response, new MFAAuthentication(authentication, authority));
            this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
        }
    }

    private void saveAuthentication(HttpServletRequest request,
                                       HttpServletResponse response,
                                       Authentication authentication){
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
        securityContextRepository.saveContext(securityContext, request, response);
    }
}