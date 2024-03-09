package com.commerxo.authserver.authserver.common;

public enum Roles {

    SUPER_ADMIN("super_user"),

    ADMIN("admin"),

    USER("user");

    private final String role;

    Roles(String role) {
        this.role = role;
    }

    public String getValue(){
        return this.role;
    }

}