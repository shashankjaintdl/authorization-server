package com.commerxo.authserver.authserver.web;

import com.commerxo.authserver.authserver.domain.RegisteredUser;
import com.commerxo.authserver.authserver.security.mfa.CodeStore;
import com.commerxo.authserver.authserver.security.mfa.MFAAuthentication;
import com.commerxo.authserver.authserver.security.mfa.MFAHandler;
import com.commerxo.authserver.authserver.service.AuthenticatorService;
import com.commerxo.authserver.authserver.service.UserRegistrationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Controller
public class LoginEndpoint {

    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    private final AuthenticationFailureHandler registrationFailureHandler =
            new SimpleUrlAuthenticationFailureHandler("/registration?error");

    private final AuthenticationFailureHandler authenticationFailureHandler =
            new SimpleUrlAuthenticationFailureHandler("/authentication?error");

    private final AuthenticationFailureHandler phoneNoAuthenticationFailureHandler =
            new SimpleUrlAuthenticationFailureHandler("/registration/phone-no?error");

    private final AuthenticationSuccessHandler phoneNoAuthenticationSuccessHandler
            = new MFAHandler("/registration/phone-no", "ROLE_PHONE_NO_REQUIRED");

    private final AuthenticatorService authenticatorService;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final PasswordEncoder passwordEncoder;
    private final CodeStore codeStore;
    private final UserRegistrationService userDetailsService;
    private String generatedCode = "";

    public LoginEndpoint(CodeStore codeStore,
                         PasswordEncoder passwordEncoder,
                         AuthenticatorService authenticatorService,
                         UserRegistrationService userDetailsService,
                         AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.codeStore = codeStore;
        this.passwordEncoder = passwordEncoder;
        this.authenticatorService = authenticatorService;
        this.userDetailsService = userDetailsService;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registration")
    public String authenticatorRegistration(@CurrentSecurityContext SecurityContext context,
                                            Model model){
        String base32Secret = this.authenticatorService.generateSecret();
        try {
            generatedCode = this.authenticatorService.getCode(base32Secret);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        codeStore.saveAll(generatedCode, base32Secret);
        model.addAttribute("qrImage",
                this.authenticatorService.generateQrImageUrl(getUser(context).getMfaKeyId(), base32Secret)
        );
        return "mfa/registration";
    }

    @PostMapping("/registration")
    public void validateRegistration(@RequestParam(name = "code") String code,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     @CurrentSecurityContext SecurityContext context) throws ServletException, IOException, GeneralSecurityException {
        if(code.equals(authenticatorService.getCode(codeStore.getBase32Secret()))){
            this.userDetailsService.storeMfaSecret(codeStore.getBase32Secret(), getUser(context).getUsername());
            if(!getUser(context).getSecurityPhoneNoEnabled()) {
                this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, getAuthentication(request, response));
                return;
            }
            this.phoneNoAuthenticationSuccessHandler.onAuthenticationSuccess(request, response, getAuthentication(request, response));
        }
        this.userDetailsService.removeUserInfoMfaRegistered(getUser(context).getUsername());
        this.registrationFailureHandler.onAuthenticationFailure(request, response, new BadCredentialsException("bad credentials"));
    }

    @GetMapping("/authenticator")
    public String authenticator(@CurrentSecurityContext SecurityContext context) {
        if(!getUser(context).getMfaRegistered()){
            return "redirect:/registration";
        }
        return "mfa/authenticator";
    }

    @PostMapping("/authenticator")
    public void validateAuthenticator(@RequestParam(name = "code") String code,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      @CurrentSecurityContext SecurityContext context) throws GeneralSecurityException, ServletException, IOException {
        if(this.authenticatorService.check(getUser(context).getMfaSecret(), code)){
            this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, getAuthentication(request, response));
            return;
        }
        this.authenticationFailureHandler.onAuthenticationFailure(request, response, new BadCredentialsException("bad credentials"));
    }

    private Authentication getAuthentication(HttpServletRequest request,
                                             HttpServletResponse response){
        SecurityContext context = SecurityContextHolder.getContext();
        MFAAuthentication mfaAuthentication = (MFAAuthentication) context.getAuthentication();
        context.setAuthentication(mfaAuthentication.getPrimaryAuthentication());
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);
        return mfaAuthentication.getPrimaryAuthentication();
    }

    private RegisteredUser getUser(SecurityContext context) {
        MFAAuthentication mfaAuthentication = (MFAAuthentication) context.getAuthentication();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                (UsernamePasswordAuthenticationToken) mfaAuthentication.getPrimaryAuthentication();
        return  (RegisteredUser) usernamePasswordAuthenticationToken.getPrincipal();
    }

}