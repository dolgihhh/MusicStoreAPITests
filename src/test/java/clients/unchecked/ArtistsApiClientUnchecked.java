package clients.unchecked;

import config.RequestSpecFactory;
import endpoints.ArtistsEndpoints;
import io.restassured.response.Response;

import java.util.ResourceBundle;

import static io.restassured.RestAssured.given;

public class ArtistsApiClientUnchecked {
    public Response getArtist(Long id, String token) {
        return given()
                .spec(RequestSpecFactory.jsonWithAuthTokenSpec(token))
                .when()
                .get(ArtistsEndpoints.getArtist(id));
    }

    public Response deleteArtist(Long id, String adminToken) {
        return given()
                .spec(RequestSpecFactory.jsonWithAuthTokenSpec(adminToken))
                .when()
                .delete(ArtistsEndpoints.deleteArtist(id));
    }
}
