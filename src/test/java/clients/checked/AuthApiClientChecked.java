package clients.checked;

import models.requests.UserRequest;
import models.responses.UserResponse;
import config.RequestSpecFactory;
import endpoints.AuthEndpoints;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class AuthApiClientChecked {
    public UserResponse registerUser(UserRequest requestBody) {
        return given()
                .spec(RequestSpecFactory.jsonSpec())
                .body(requestBody)
                .when()
                .post(AuthEndpoints.register())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"))
                .extract()
                .as(UserResponse.class);
    }

    public String loginAndGetToken(UserRequest request) {
        return given()
                .spec(RequestSpecFactory.formUrlEncodedSpec())
                .formParam("username", request.getUsername())
                .formParam("password", request.getPassword())
                .when()
                .post(AuthEndpoints.login())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .path("access_token");
    }

    public UserResponse updateUserRights(String username, String adminToken, boolean setAdmin) {
        Map<String, Object> body = new HashMap<>();
        body.put("is_admin", setAdmin);

        return given()
                .spec(RequestSpecFactory.jsonWithAuthTokenSpec(adminToken))
                .body(body)
                .when()
                .patch(AuthEndpoints.updateUserRights(username))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(UserResponse.class);
    }
}
