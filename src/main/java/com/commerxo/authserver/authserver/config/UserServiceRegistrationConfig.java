package com.commerxo.authserver.authserver.config;

import com.commerxo.authserver.authserver.repository.JpaUserRegistrationRepository;
import com.commerxo.authserver.authserver.service.UserRegistrationService;
import com.commerxo.authserver.authserver.service.impl.UserRegistrationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceRegistrationConfig {

    private final JpaUserRegistrationRepository userRegistrationRepository;
//    private final

    public UserServiceRegistrationConfig(JpaUserRegistrationRepository userRegistrationRepository) {
        this.userRegistrationRepository = userRegistrationRepository;
    }

//    @Bean
//    public UserRegistrationService userRegistrationService(){
//        return new UserRegistrationServiceImpl(userRegistrationRepository, roleService);
//    }
}
