package com.commerxo.authserver.authserver.dto;

import jakarta.validation.constraints.NotEmpty;

public class RoleUpdateRequest {

    private String name;
    private String description;
    private String clientName;
    private Boolean active = false;

    @NotEmpty(message = "")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotEmpty(message = "")
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
