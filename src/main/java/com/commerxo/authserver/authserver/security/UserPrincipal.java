package com.commerxo.authserver.authserver.security;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class UserPrincipal implements UserDetails {

    private static final String EMPTY_STRING = "";

    private String id;
    private String username;
    private String password;
    private String emailId;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    private Set<GrantedAuthority> authorities;

    public String getId() {
        return this.id;
    }

    public String getEmailId() {
        return this.emailId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    public static Builder buildWithId(String id){
        if(id == null)
            throw new IllegalArgumentException("Id can't be null!");
        return new Builder(id);
    }

    public static class Builder implements Serializable{

        private String id;
        private String username;
        private String emailId;
        private boolean isAccountNonExpired;
        private boolean isAccountNonLocked;
        private boolean isCredentialsNonExpired;
        private boolean isEnabled;
        private Set<GrantedAuthority> authorities;

        public Builder(String id){
            this.id = id;
        }

        public Builder id(String id){
            this.id = id;
            return this;
        }

        public Builder username(String username){
            this.username =  username;
            return this;
        }

        public Builder emailId(String emailId){
            this.emailId = emailId;
            return this;
        }

        public Builder accountNonExpired(boolean isAccountNonExpired){
            this.isAccountNonExpired = isAccountNonExpired;
            return this;
        }

        public Builder accountNonLocked(boolean isAccountNonLocked){
            this.isAccountNonLocked = isAccountNonLocked;
            return this;
        }

        public Builder credentialsExpired(boolean isCredentialsNonExpired){
            this.isCredentialsNonExpired = isCredentialsNonExpired;
            return this;
        }

        public Builder enabled(boolean isEnabled){
            this.isEnabled = isEnabled;
            return this;
        }

        public Builder authorities(Set<GrantedAuthority> authorities){
            this.authorities = authorities;
            return this;
        }

        public UserPrincipal build(){
            UserPrincipal principal = new UserPrincipal();
            principal.id = this.id;
            principal.username = this.username;
            principal.emailId = this.emailId;
            principal.password = EMPTY_STRING;
            principal.isAccountNonExpired = this.isAccountNonExpired;
            principal.isAccountNonLocked = this.isAccountNonLocked;
            principal.isCredentialsNonExpired = this.isCredentialsNonExpired;
            principal.isEnabled = this.isEnabled;
            principal.authorities = this.authorities != null ? Collections.unmodifiableSet(this.authorities) : Collections.emptySet();
            check(principal);
            return principal;
        }

        private static void check(UserPrincipal principal){

            if(!principal.isEnabled())
                throw new DisabledException("Users account is disabled");

            if(!principal.isAccountNonExpired())
                throw new AccountExpiredException("Users account is expired");

            if(!principal.isAccountNonLocked())
                throw new LockedException("Users account is locked");

            if(!principal.isCredentialsNonExpired())
                throw new CredentialsExpiredException("user account credential is expired");

        }

    }
}
