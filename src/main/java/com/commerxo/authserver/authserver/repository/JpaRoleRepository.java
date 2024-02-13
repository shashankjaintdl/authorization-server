package com.commerxo.authserver.authserver.repository;

import com.commerxo.authserver.authserver.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaRoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByName(String name);

    List<Role> findAllByClientName(String clientName);

}