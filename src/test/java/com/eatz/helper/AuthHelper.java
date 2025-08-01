package com.eatz.helper;

import com.eatz.shared.dto.LoginRequest;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class AuthHelper {

    public static RequestSpecification authenticatedRequest(String email, String password, String endpoint) {
        String token =
                given()
                    .contentType("application/json")
                    .body(new LoginRequest(email, password))
                .when()
                    .post(endpoint)
                .then()
                    .statusCode(200)
                    .extract()
                    .path("token");

        return given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json");
    }

}
