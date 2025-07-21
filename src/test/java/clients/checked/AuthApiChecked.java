package clients.checked;

import clients.unchecked.AuthApiUnchecked;
import models.requests.UserRequest;
import models.responses.UserResponse;
import org.apache.http.HttpStatus;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class AuthApiChecked extends AuthApiUnchecked {
    public UserResponse registerUserAndExtract(UserRequest requestBody) {
        return registerUser(requestBody)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"))
                .extract()
                .as(UserResponse.class);
    }

    public String loginAndExtractToken(UserRequest request) {
        return loginUser(request).then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .path("access_token");
    }

    public UserResponse updateUserRightsAndExtract(String username, String adminToken, boolean setAdmin) {
        return updateUserRights(username, adminToken, setAdmin)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(UserResponse.class);
    }
}
