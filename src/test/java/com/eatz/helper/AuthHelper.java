package com.eatz.helper;

import com.eatz.shared.dto.LoginRequest;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class AuthHelper {

    public static RequestSpecification authenticatedRestaurantUserRequest(String email, String password) {
        String token =
                given()
                    .contentType("application/json")
                    .body(new LoginRequest(email, password))
                .when()
                    .post("/restaurant-users/login")
                .then()
                    .statusCode(200)
                    .extract()
                    .path("token");

        return given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json");
    }

}
