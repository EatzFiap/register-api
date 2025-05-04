package com.eatz.infrastructure.persistence.customerAddress;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_CUSTOMER_ADDRESS")
public class CustomerAddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer_address")
    private Long idCustomerAddress;

    @Column(name = "fk_customer")
    private Long customerId;

    @Column(name = "fk_address")
    private Long addressId;

    private String nickname;

    @Column(name = "is_default")
    private boolean isDefault;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public CustomerAddressEntity(Long idCustomerAddress, Long customerId, Long addressId, String nickname, boolean isDefault, LocalDateTime createdAt, LocalDateTime updatedAt, boolean isDeleted) {
        this.idCustomerAddress = idCustomerAddress;
        this.customerId = customerId;
        this.addressId = addressId;
        this.nickname = nickname;
        this.isDefault = isDefault;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }

    public CustomerAddressEntity() {
    }

    public Long getIdCustomerAddress() {
        return idCustomerAddress;
    }

    public void setIdCustomerAddress(Long idCustomerAddress) {
        this.idCustomerAddress = idCustomerAddress;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
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
}
