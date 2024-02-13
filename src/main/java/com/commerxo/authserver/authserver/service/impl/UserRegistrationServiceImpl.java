package com.commerxo.authserver.authserver.service.impl;

import com.commerxo.authserver.authserver.domain.RegisteredUser;
import com.commerxo.authserver.authserver.domain.Role;
import com.commerxo.authserver.authserver.dto.UserRegistrationRequest;
import com.commerxo.authserver.authserver.exception.ResourceAlreadyExistException;
import com.commerxo.authserver.authserver.repository.JpaUserRegistrationRepository;
import com.commerxo.authserver.authserver.security.UserPrincipal;
import com.commerxo.authserver.authserver.service.RoleService;
import com.commerxo.authserver.authserver.service.UserRegistrationService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    public static final String DEFAULT_ROLE = "user";

    private final JpaUserRegistrationRepository userRegistrationRepository;
    private final RoleService roleService;

    public UserRegistrationServiceImpl(JpaUserRegistrationRepository userRegistrationRepository, RoleService roleService) {
        Assert.notNull(userRegistrationRepository,"JpaUserRegistrationRepository can't be null!");
        Assert.notNull(roleService, "RoleService can't be null!");
        this.userRegistrationRepository = userRegistrationRepository;
        this.roleService = roleService;
    }

    @Override
    public RegisteredUser createUser(UserRegistrationRequest registrationRequest) {
        RegisteredUser existUser = this.userRegistrationRepository.findByUsername(registrationRequest.getUsername()).orElse(null);
        if(existUser != null)
            throw new ResourceAlreadyExistException("");

        Role role = this.roleService.getByRoleName(DEFAULT_ROLE);
        if(role == null)
            throw new IllegalStateException("");

        RegisteredUser registeredUser = UserRegistrationRequest.mapToEntity(registrationRequest);
        registeredUser.setActive(true);
        registeredUser.setAccountExpired(false);
        registeredUser.setAccountLocked(false);
        registeredUser.setCredentialExpired(false);
        registeredUser.setEmailVerified(false);
        registeredUser.setPhoneNoVerified(false);
        registeredUser.setRoles(Set.of(role));

        return this.userRegistrationRepository.save(registeredUser);

    }

    @Override
    public RegisteredUser getByUsername(String username, boolean active) {
        if(!StringUtils.hasText(username))
            throw new IllegalArgumentException("");
        if(active){
            return this.userRegistrationRepository.findByUsernameAndActive(username, true).orElse(null);
        }
        return this.userRegistrationRepository.findByUsernameAndActive(username, false).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RegisteredUser registeredUser = this.userRegistrationRepository.findByUsername(username).orElse(null);
        if(registeredUser == null)
            throw new UsernameNotFoundException("");

        Set<GrantedAuthority> authorities = new HashSet<>(registeredUser.getRoles().size());
        registeredUser.getRoles().forEach(authority -> {
            authorities.add(new SimpleGrantedAuthority(authority.getName()));
        });

        return UserPrincipal.buildWithId(registeredUser.getId())
                .username(registeredUser.getUsername())
                .emailId(registeredUser.getEmailId())
                .authorities(authorities)
                .enabled(registeredUser.isActive())
                .accountNonExpired(registeredUser.isAccountExpired())
                .accountNonLocked(registeredUser.isAccountLocked())
                .build();
    }


}
