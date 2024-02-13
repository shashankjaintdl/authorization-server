package com.commerxo.authserver.authserver.service;

import com.commerxo.authserver.authserver.domain.RegisteredUser;
import com.commerxo.authserver.authserver.dto.UserRegistrationRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserRegistrationService extends UserDetailsService {

    RegisteredUser createUser(UserRegistrationRequest registrationRequest);

    RegisteredUser getByUsername(String username, boolean active);
}
