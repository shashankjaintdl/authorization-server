package com.commerxo.authserver.authserver.web;

import com.commerxo.authserver.authserver.common.APIResponse;
import com.commerxo.authserver.authserver.dto.RoleCreateRequest;
import com.commerxo.authserver.authserver.dto.RoleUpdateRequest;
import com.commerxo.authserver.authserver.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.commerxo.authserver.authserver.common.WebConstants.Role;

@RestController
@RequestMapping(Role.ROLE)
public class RolesEndpoint {


    private final RoleService roleService;

    public RolesEndpoint(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(
            path = Role.CREATE_ROLE,
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponse<String>> createRole(@RequestBody @Valid RoleCreateRequest roleCreateRequest){
        this.roleService.create(roleCreateRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new APIResponse<>(HttpStatus.CREATED, "Role created successfully!"));
    }

    @RequestMapping(
            path = Role.UPDATE_ROLE,
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponse<String>> editRole(@RequestBody @Valid RoleUpdateRequest updateRequest){

        return null;
    }

}