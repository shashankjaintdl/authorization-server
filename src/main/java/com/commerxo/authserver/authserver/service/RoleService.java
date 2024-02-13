package com.commerxo.authserver.authserver.service;

import com.commerxo.authserver.authserver.common.APIResponse;
import com.commerxo.authserver.authserver.domain.Role;
import com.commerxo.authserver.authserver.dto.RoleCreateRequest;

import java.util.List;

public interface RoleService {

    void create(RoleCreateRequest roleCreateRequest);

    List<Role> getAllByClientName(String clientName);

    Role getByRoleName(String roleName);


}
