package com.eatz.domain.customer.enums;

public enum UserRole {
    ADMIN("Administrador"),
    CUSTOMER("Cliente"),
    RESTAURANT ("Restaurante");

    private final String description;

    UserRole(String descricao) {
        this.description = descricao;
    }

    public String getDescricao() {
        return description;
    }
}