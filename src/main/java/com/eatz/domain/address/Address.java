package com.eatz.domain.address;


import com.eatz.infrastructure.persistence.address.AddressEntity;

public class Address {

    private Long id;
    private String street;
    private String number;
    private String complement;
    private String city;
    private String neighbourhood;
    private String state;
    private String zipCode;
    private String createdAt;
    private String updatedAt;
    private boolean isDeleted;

    public Address() {
    }

    public Address(Long id, String street, String number, String complement, String city, String neighbourhood, String state, String zipCode) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.city = city;
        this.neighbourhood = neighbourhood;
        this.state = state;
        this.zipCode = zipCode;
    }

    public Address(String street, String city, String state, String zipCode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public Address(AddressEntity addressEntity) {
        this.id = addressEntity.getId();
        this.street = addressEntity.getStreet();
        this.number = addressEntity.getNumber();
        this.complement = addressEntity.getComplement();
        this.city = addressEntity.getCity();
        this.neighbourhood = addressEntity.getNeighbourhood();
        this.state = addressEntity.getState();
        this.zipCode = addressEntity.getZipCode();
        this.createdAt = addressEntity.getCreatedAt();
        this.updatedAt = addressEntity.getUpdatedAt();
        this.isDeleted = addressEntity.isDeleted();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

}
