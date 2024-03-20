package com.commerxo.authserver.authserver.security.mfa;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class MFAAuthentication  extends AnonymousAuthenticationToken {

    private final Authentication primaryAuthentication;

    public MFAAuthentication(Authentication authentication, String authorities) {
        super("anonymous", "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS", authorities));
        this.primaryAuthentication = authentication;
    }

    public Authentication getPrimaryAuthentication(){
        return this.primaryAuthentication;
    }
}
