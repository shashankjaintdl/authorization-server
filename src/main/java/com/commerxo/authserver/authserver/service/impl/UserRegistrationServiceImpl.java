package com.commerxo.authserver.authserver.service.impl;

import com.commerxo.authserver.authserver.common.Roles;
import com.commerxo.authserver.authserver.domain.RegisteredUser;
import com.commerxo.authserver.authserver.domain.Role;
import com.commerxo.authserver.authserver.dto.UserRegistrationRequest;
import com.commerxo.authserver.authserver.exception.ResourceAlreadyExistException;
import com.commerxo.authserver.authserver.repository.JpaUserRegistrationRepository;
import com.commerxo.authserver.authserver.service.RoleService;
import com.commerxo.authserver.authserver.service.UserRegistrationService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

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
            throw new ResourceAlreadyExistException("User already exist with username => [ " + registrationRequest.getUsername() + " ]");

        Role role = this.roleService.getByRoleName(Roles.USER.getValue());
        if(role == null)
            throw new IllegalStateException("Roles does not exist");

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
            throw new IllegalArgumentException("username");
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

        return User.builder()
                .username(registeredUser.getUsername())
                .password(registeredUser.getPassword())
                .authorities(authorities)
                .accountExpired(registeredUser.isAccountExpired())
                .accountLocked(registeredUser.isAccountLocked())
                .credentialsExpired(registeredUser.isCredentialExpired())
                .disabled(!registeredUser.isActive())
                .build();
                //        return UserPrincipal.buildWithId(registeredUser.getId())
//                .username(registeredUser.getUsername())
//                .password(registeredUser.getPassword())
//                .emailId(registeredUser.getEmailId())
//                .authorities(authorities)
//                .enabled(registeredUser.isActive())
//                .accountExpired(registeredUser.isAccountExpired())
//                .accountLocked(registeredUser.isAccountLocked())
//                .authorities(authorities)
//                .enabled(registeredUser.isActive())
//                .build();
    }


}
