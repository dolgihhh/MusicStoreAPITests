package clients.unchecked;

import config.RequestSpecFactory;
import endpoints.ArtistEndpoints;
import io.restassured.response.Response;
import models.requests.ArtistRequest;

import static io.restassured.RestAssured.given;

public class ArtistsApiUnchecked {
    public Response getArtist(Long id, String token) {
        return given()
                .spec(RequestSpecFactory.jsonWithAuthTokenSpec(token))
                .when()
                .get(ArtistEndpoints.getArtist(id));
    }

    public Response getArtists(Integer skip, Integer limit, String token) {
        return given()
                .spec(RequestSpecFactory.jsonWithAuthTokenSpec(token))
                .when()
                .get(ArtistEndpoints.getArtists(skip, limit));
    }

    public Response deleteArtist(Long id, String adminToken) {
        return given()
                .spec(RequestSpecFactory.jsonWithAuthTokenSpec(adminToken))
                .when()
                .delete(ArtistEndpoints.deleteArtist(id));
    }

    public Response postArtist(ArtistRequest requestBody, String adminToken) {
        return given()
                .spec(RequestSpecFactory.jsonWithAuthTokenSpec(adminToken))
                .body(requestBody)
                .when()
                .post(ArtistEndpoints.postArtist());
    }

    public Response putArtist(ArtistRequest requestBody, Long id, String adminToken) {
        return given()
                .spec(RequestSpecFactory.jsonWithAuthTokenSpec(adminToken))
                .body(requestBody)
                .when()
                .put(ArtistEndpoints.putArtist(id));
    }
}
