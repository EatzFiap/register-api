package com.eatz.infrastructure.persistence.restaurantUser;

import com.eatz.infrastructure.persistence.address.AddressEntity;
import com.eatz.infrastructure.persistence.entity.RestaurantUserTypeEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_RESTAURANT_USER")
public class RestaurantUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_restaurant_user")
    private Long id;

    @Column(name = "role")
    private String role;

    private String name;
    private String email;
    private String password;
    private String cpf;
    private String phone;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "profile_image_url", length = 500)
    private String profileImageUrl;

    @Column(name = "fk_restaurant")
    private Long restaurantId;

    @Column(name = "fk_restaurant_user_type", insertable = false, updatable = false)
    private Long restaurantUserTypeId;

    @Column(name = "fk_address", insertable=false, updatable=false)
    private Long addressId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_address")
    private AddressEntity address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_restaurant_user_type")
    private RestaurantUserTypeEntity restaurantUserType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public Long getRestaurantUserTypeId() {
        return restaurantUserTypeId;
    }

    public void setRestaurantUserTypeId(Long restaurantUserTypeId) {
        this.restaurantUserTypeId = restaurantUserTypeId;
    }

    public RestaurantUserTypeEntity getRestaurantUserType() {
        return restaurantUserType;
    }

    public void setRestaurantUserType(RestaurantUserTypeEntity restaurantUserType) {
        this.restaurantUserType = restaurantUserType;
    }

}