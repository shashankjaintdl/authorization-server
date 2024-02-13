package com.commerxo.authserver.authserver.repository;

import com.commerxo.authserver.authserver.domain.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRegistrationRepository extends JpaRepository<RegisteredUser, String> {

    Optional<RegisteredUser> findByUsername(String username);

    Optional<RegisteredUser> findByUsernameAndActive(String username, Boolean active);
}