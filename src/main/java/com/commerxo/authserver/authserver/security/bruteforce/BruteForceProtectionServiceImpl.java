package com.commerxo.authserver.authserver.security.bruteforce;

import com.commerxo.authserver.authserver.domain.RegisteredUser;
import com.commerxo.authserver.authserver.repository.JpaUserRegistrationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class BruteForceProtectionServiceImpl implements BruteForceProtectionService{

    private static final Logger logger = LoggerFactory.getLogger(BruteForceProtectionServiceImpl.class);

    @Value("${app.security.login.max-count}")
    private int failedMaxCount;

    private final JpaUserRegistrationRepository userRegistrationRepository;

    public BruteForceProtectionServiceImpl(JpaUserRegistrationRepository userRegistrationRepository) {
        this.userRegistrationRepository = userRegistrationRepository;
    }

    @Override
    public void registerLoginFailure(String username) {
        RegisteredUser registeredUser = this.userRegistrationRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(""));
        int failedCount = registeredUser.getFailedLoginAttempts();
        if(failedCount > failedMaxCount){
            registeredUser.setAccountLocked(false);
        }else{
            registeredUser.setFailedLoginAttempts(failedCount + 1);
        }
        this.userRegistrationRepository.save(registeredUser);
    }

    @Override
    public void resetBruteForceCounter(String username) {
        RegisteredUser registeredUser = this.userRegistrationRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(""));
        registeredUser.setAccountLocked(false);
        registeredUser.setFailedLoginAttempts(0);
        this.userRegistrationRepository.save(registeredUser);
    }

    @Override
    public boolean isBruteForceAttack(String username) {
        RegisteredUser registeredUser = this.userRegistrationRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(""));
        return registeredUser.getFailedLoginAttempts() >= 3;
    }

}