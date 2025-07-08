package clients.checked;

import config.RequestSpecFactory;
import endpoints.ArtistsEndpoints;
import models.requests.ArtistRequest;
import models.responses.ArtistResponse;
import org.apache.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ArtistsApiClientChecked {
    public ArtistResponse postArtist(ArtistRequest requestBody, String adminToken) {
        return given()
                .spec(RequestSpecFactory.jsonWithAuthTokenSpec(adminToken))
                .body(requestBody)
                .when()
                .post(ArtistsEndpoints.postArtist())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(ArtistResponse.class);
    }

    public ArtistResponse getArtist(Long id, String token) {
        return given()
                .spec(RequestSpecFactory.jsonWithAuthTokenSpec(token))
                .when()
                .get(ArtistsEndpoints.getArtist(id))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(ArtistResponse.class);
    }

    public List<ArtistResponse> getArtists(int skip, int limit, String token) {
        return given()
                .spec(RequestSpecFactory.jsonWithAuthTokenSpec(token))
                .when()
                .get(ArtistsEndpoints.getArtists(skip, limit))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .jsonPath()
                .getList("", ArtistResponse.class);
    }

    public ArtistResponse putArtist(Long id, ArtistRequest requestBody, String adminToken) {
        return given()
                .spec(RequestSpecFactory.jsonWithAuthTokenSpec(adminToken))
                .body(requestBody)
                .when()
                .put(ArtistsEndpoints.putArtist(id))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(ArtistResponse.class);
    }

    public void deleteArtist(Long id, String adminToken) {
        given()
                .spec(RequestSpecFactory.jsonWithAuthTokenSpec(adminToken))
                .when()
                .delete(ArtistsEndpoints.deleteArtist(id))
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
