package com.eatz.domain.user;


import com.eatz.domain.address.Address;

import java.util.UUID;

public class User {

    private UUID id;

    private String name;
    private String email;
    private String login;
    private String password;
    private String lastUpdated;
    private boolean isActive;

    private Address address;

    public User(UUID id, String name, String email, String login, String password, String lastUpdated, Address address, boolean isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.lastUpdated = lastUpdated;
        this.address = address;
        this.isActive = isActive;
    }

    public User() {
    }

    public User(UUID id, String name, String email, String login, String password, boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.isActive = active;
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

    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }
}
