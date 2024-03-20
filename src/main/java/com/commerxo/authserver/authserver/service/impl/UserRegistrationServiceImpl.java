package com.commerxo.authserver.authserver.service.impl;

import com.commerxo.authserver.authserver.common.Roles;
import com.commerxo.authserver.authserver.domain.RegisteredUser;
import com.commerxo.authserver.authserver.domain.Role;
import com.commerxo.authserver.authserver.dto.UserRegistrationRequest;
import com.commerxo.authserver.authserver.exception.ResourceAlreadyExistException;
import com.commerxo.authserver.authserver.repository.JpaUserRegistrationRepository;
import com.commerxo.authserver.authserver.service.RoleService;
import com.commerxo.authserver.authserver.service.UserRegistrationService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final RoleService roleService;
    private final BytesEncryptor bytesEncryptor;
    private final JpaUserRegistrationRepository userRegistrationRepository;

    public UserRegistrationServiceImpl(JpaUserRegistrationRepository userRegistrationRepository,
                                       BytesEncryptor bytesEncryptor,
                                       RoleService roleService) {
        Assert.notNull(roleService, "RoleService can't be null!");
        Assert.notNull(userRegistrationRepository,"JpaUserRegistrationRepository can't be null!");
        this.userRegistrationRepository = userRegistrationRepository;
        this.bytesEncryptor = bytesEncryptor;
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
        registeredUser.setEnabled(true);
        registeredUser.setAccountNonExpired(false);
        registeredUser.setAccountNonLocked(false);
        registeredUser.setCredentialsNonExpired(false);
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
            return this.userRegistrationRepository.findByUsernameAndEnabled(username, true).orElse(null);
        }
        return this.userRegistrationRepository.findByUsernameAndEnabled(username, false).orElse(null);
    }

    @Override
    public void storeMfaSecret(String base32Secret, String username) {
        RegisteredUser registeredUser = this.userRegistrationRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User does not exist with username [ " + username + " ]")
                );
        String encryptedSecret = new String(Hex.encode(this.bytesEncryptor.encrypt(base32Secret.getBytes(StandardCharsets.UTF_8))));
        registeredUser.setMfaSecret(encryptedSecret);
        registeredUser.setMfaRegistered(true);
        this.userRegistrationRepository.saveAndFlush(registeredUser);
    }

    @Override
    public void removeUserInfoMfaRegistered(String username) {
        RegisteredUser registeredUser = this.userRegistrationRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User does not exist with username [ " + username + " ]")
                );
        registeredUser.setMfaRegistered(false);
        registeredUser.setMfaSecret(Strings.EMPTY);
        this.userRegistrationRepository.saveAndFlush(registeredUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RegisteredUser registeredUser = this.userRegistrationRepository.findByUsername(username).orElse(null);
        if(registeredUser == null)
            throw new UsernameNotFoundException("Username does not exist with [ " + username + " ]");

        Set<GrantedAuthority> authorities = new HashSet<>(registeredUser.getRoles().size());
        registeredUser.getRoles().forEach(authority -> {
            authorities.add(new SimpleGrantedAuthority(authority.getName()));
        });


        return registeredUser;
//        return User.builder()
//                .username(registeredUser.getUsername())
//                .password(registeredUser.getPassword())
//                .authorities(authorities)
//                .accountExpired(registeredUser.isAccountNonExpired())
//                .accountLocked(registeredUser.isAccountNonLocked())
//                .credentialsExpired(registeredUser.isCredentialsNonExpired())
//                .disabled(!registeredUser.isEnabled())
//                .build();

    }

}