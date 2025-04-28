package com.eatz.domain.restaurantuser.enums;

public enum RestaurantRole {
    ADMIN("Administrador"),
    CUSTOMER("Cliente"),
    RESTAURANT ("Restaurante");

    private final String description;

    RestaurantRole(String descricao) {
        this.description = descricao;
    }

    public String getDescricao() {
        return description;
    }
}