package com.eatz.domain.user.enums;

public enum UserRole {
    ADMIN("Administrador"),
    COSTUMER ("Cliente"),
    RESTAURANT ("Restaurante");

    private final String description;

    UserRole(String descricao) {
        this.description = descricao;
    }

    public String getDescricao() {
        return description;
    }
}