package com.eatz.presentation.web.menuItem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MenuItemRequest(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        String description,

        @NotNull(message = "Preço é obrigatório")
        @Positive(message = "Preço deve ser positivo")
        Double price,

        Boolean onlyLocalConsumption,

        String photoBase64,

        String photoContentType,

        @NotNull(message = "ID do restaurante é obrigatório")
        Long restaurantId
) {}