package clients.unchecked;

import config.RequestSpecFactory;
import endpoints.AlbumEndpoints;
import enums.SortBy;
import io.restassured.response.Response;
import models.requests.AlbumRequest;
import java.math.BigDecimal;

import static io.restassured.RestAssured.given;

public class AlbumsApiUnchecked {
    public Response getAlbum(Long id, String token) {
        return given()
                .spec(RequestSpecFactory.jsonWithAuthTokenSpec(token))
                .when()
                .get(AlbumEndpoints.getAlbum(id));
    }

    public Response getAlbums(Integer skip, Integer limit, String search, String genre,
                              BigDecimal minPrice, BigDecimal maxPrice, SortBy sortBy, String token) {
        return given()
                .spec(RequestSpecFactory.jsonWithAuthTokenSpec(token))
                .when()
                .get(AlbumEndpoints.getAlbums(skip, limit, search, genre, minPrice, maxPrice, sortBy));
    }


    public Response postAlbum(AlbumRequest albumRequest, String adminToken) {
        return given()
                .spec(RequestSpecFactory.jsonWithAuthTokenSpec(adminToken))
                .body(albumRequest)
                .when()
                .post(AlbumEndpoints.postAlbum());
    }

    public Response putAlbum(AlbumRequest albumRequest, Long id, String adminToken) {
        return given()
                .spec(RequestSpecFactory.jsonWithAuthTokenSpec(adminToken))
                .body(albumRequest)
                .when()
                .put(AlbumEndpoints.putAlbum(id));
    }

    public Response deleteAlbum(Long id, String adminToken) {
        return given()
                .spec(RequestSpecFactory.jsonWithAuthTokenSpec(adminToken))
                .log().all()
                .when()
                .delete(AlbumEndpoints.deleteAlbum(id));
    }
}
