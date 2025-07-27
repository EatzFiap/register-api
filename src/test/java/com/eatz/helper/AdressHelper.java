package com.eatz.helper;

import com.eatz.domain.address.Address;

public abstract class AdressHelper {

    public static Address createAddress() {
        Address address = new Address();
        address.setStreet("Rua das Flores");
        address.setNumber("123");
        address.setCity("São Paulo");
        address.setState("SP");
        address.setZipCode("12345-678");
        return address;
    }

}
