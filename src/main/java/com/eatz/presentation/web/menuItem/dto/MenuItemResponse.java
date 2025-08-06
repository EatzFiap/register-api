package com.eatz.presentation.web.menuItem.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(name = "MenuItemResponse", description = "Resposta com dados do item de menu")
public class MenuItemResponse {

        private Long id;
        private String name;
        private String description;
        private Double price;
        private Boolean onlyLocalConsumption;
        private String photoUrl;
        private Long restaurantId;

        @Schema(type = "string", format = "date-time", example = "2025-07-15T20:00:00")
        private LocalDateTime createdAt;

        @Schema(type = "string", format = "date-time", example = "2025-07-15T20:00:00")
        private LocalDateTime updatedAt;

        public MenuItemResponse() {}

        public MenuItemResponse(Long id, String name, String description, Double price,
                                Boolean onlyLocalConsumption, String photoUrl,
                                Long restaurantId, LocalDateTime createdAt, LocalDateTime updatedAt) {
                this.id = id;
                this.name = name;
                this.description = description;
                this.price = price;
                this.onlyLocalConsumption = onlyLocalConsumption;
                this.photoUrl = photoUrl;
                this.restaurantId = restaurantId;
                this.createdAt = createdAt;
                this.updatedAt = updatedAt;
        }

        public Long getId() {
                return id;
        }

        public String getName() {
                return name;
        }

        public String getDescription() {
                return description;
        }

        public Double getPrice() {
                return price;
        }

        public Boolean getOnlyLocalConsumption() {
                return onlyLocalConsumption;
        }

        public String getPhotoUrl() {
                return photoUrl;
        }

        public Long getRestaurantId() {
                return restaurantId;
        }

        public LocalDateTime getCreatedAt() {
                return createdAt;
        }

        public LocalDateTime getUpdatedAt() {
                return updatedAt;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public void setName(String name) {
                this.name = name;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public void setPrice(Double price) {
                this.price = price;
        }

        public void setOnlyLocalConsumption(Boolean onlyLocalConsumption) {
                this.onlyLocalConsumption = onlyLocalConsumption;
        }

        public void setPhotoUrl(String photoUrl) {
                this.photoUrl = photoUrl;
        }

        public void setRestaurantId(Long restaurantId) {
                this.restaurantId = restaurantId;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
        }

        public void setUpdatedAt(LocalDateTime updatedAt) {
                this.updatedAt = updatedAt;
        }
}
