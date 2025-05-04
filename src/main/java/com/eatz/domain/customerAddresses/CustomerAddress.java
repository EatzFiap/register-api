package com.eatz.domain.customerAddresses;



public class CustomerAddress {
    private Long id;
    private Long customerId;
    private Long addressId;
    private String nickname;
    private boolean isDefault;
    private boolean isDeleted;

    public CustomerAddress() { }

    public CustomerAddress(Long id, Long customerId, Long addressId, String nickname, boolean isDefault, boolean isDeleted) {
        this.id = id;
        this.customerId = customerId;
        this.addressId = addressId;
        this.nickname = nickname;
        this.isDefault = isDefault;
        this.isDeleted = isDeleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}