package clients.unchecked;

import config.RequestSpecFactory;
import endpoints.AuthEndpoints;
import io.restassured.response.Response;
import models.requests.UserRequest;
import models.responses.UserResponse;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthApiClientUnchecked {
    public Response registerUser(UserRequest requestBody) {
        return given()
                .spec(RequestSpecFactory.jsonSpec())
                .body(requestBody)
                .when()
                .post(AuthEndpoints.register());
    }

    public Response loginUser(UserRequest request) {
        return given()
                .spec(RequestSpecFactory.formUrlEncodedSpec())
                .formParam("username", request.getUsername())
                .formParam("password", request.getPassword())
                .when()
                .post(AuthEndpoints.login());
    }

    public Response updateUserRights(String username, String token, boolean setAdmin) {
        Map<String, Object> body = new HashMap<>();
        body.put("is_admin", setAdmin);

        return given()
                .spec(RequestSpecFactory.jsonWithAuthTokenSpec(token))
                .body(body)
                .when()
                .patch(AuthEndpoints.updateUserRights(username));
    }
}