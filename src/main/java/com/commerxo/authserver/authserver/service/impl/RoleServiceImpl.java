package com.commerxo.authserver.authserver.service.impl;

import com.commerxo.authserver.authserver.common.APIResponse;
import com.commerxo.authserver.authserver.domain.Role;
import com.commerxo.authserver.authserver.dto.RoleCreateRequest;
import com.commerxo.authserver.authserver.exception.ResourceAlreadyExistException;
import com.commerxo.authserver.authserver.repository.JpaRoleRepository;
import com.commerxo.authserver.authserver.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public  class RoleServiceImpl implements RoleService {

    private final JpaRoleRepository roleRepository;

    public RoleServiceImpl(JpaRoleRepository roleRepository) {
        Assert.notNull(roleRepository,"JpaRoleRepository can't be null!");
        this.roleRepository = roleRepository;
    }

    @Override
    public void create(RoleCreateRequest roleCreateRequest) {
        Role existingRole = this.roleRepository.findByName(roleCreateRequest.getName()).orElse(null);
        if(existingRole != null)
            throw new ResourceAlreadyExistException("");

        Role newRole = RoleCreateRequest.mapToEntity(roleCreateRequest);

        newRole = this.roleRepository.save(newRole);

        if(!StringUtils.hasText(newRole.getId()))
            throw new IllegalStateException("");
    }

    @Override
    public List<Role> getAllByClientName(String clientName) {
        if(!StringUtils.hasText(clientName))
            throw new IllegalArgumentException("");

        return this.roleRepository
                .findAllByClientName(clientName);
    }

    @Override
    public Role getByRoleName(String roleName) {
        if(StringUtils.hasText(roleName))
            throw new IllegalArgumentException("");
        return this.roleRepository.findByName(roleName).orElse(null);
    }
}