package com.eatz.infrastructure.persistence.customer;

import com.eatz.infrastructure.persistence.address.AddressEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class CustomerEntity {

    @Id
    private UUID id;

    private String name;
    private String email;
    private String login;
    private String password;
    private String lastUpdated;
    private boolean isActive;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private AddressEntity address;

    public CustomerEntity(String name, String email, String login, String password, String lastUpdated, AddressEntity address) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.lastUpdated = lastUpdated;
        this.address = address;
    }

    public CustomerEntity() {
    }

    // Getters and setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public AddressEntity getAddress() {
        return address;
    }
    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }

}
