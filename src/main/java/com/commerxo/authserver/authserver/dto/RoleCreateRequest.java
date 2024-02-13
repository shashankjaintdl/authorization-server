package com.commerxo.authserver.authserver.dto;

import com.commerxo.authserver.authserver.domain.Role;
import jakarta.validation.constraints.NotEmpty;

public class RoleCreateRequest {

    private String name;
    private String description;
    private Boolean active =  false;

    @NotEmpty(message = "Role Name can't be null!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty(message = "Description can't be null!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public static Role mapToEntity(RoleCreateRequest roleCreateRequest){
        Role role = new Role();
        role.setName(roleCreateRequest.getName());
        role.setDescription(roleCreateRequest.getDescription());
        role.setActive(roleCreateRequest.getActive());
        return role;
    }
}