package com.commerxo.authserver.authserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity
@Table(
        name = "role",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "role_unique",
                        columnNames = {"role_name"}
                )
        }
)
public class Role {

    private String id;
    private String name;
    private String clientName;
    private String description;
    private Boolean active;
    private List<RegisteredUser> users;

    @Id
    @UuidGenerator
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "client_name")
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Column(name = "description", length = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "active")
    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Column(name = "role_name", length = 30, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "roles"
    )
    @JsonIgnore
    public List<RegisteredUser> getUsers() {
        return users;
    }

    public void setUsers(List<RegisteredUser> users) {
        this.users = users;
    }
}
