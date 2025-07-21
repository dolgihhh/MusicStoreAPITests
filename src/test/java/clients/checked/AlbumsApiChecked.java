package clients.checked;

import clients.unchecked.AlbumsApiUnchecked;
import enums.SortBy;
import models.requests.AlbumRequest;
import models.responses.AlbumResponse;
import org.apache.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

public class AlbumsApiChecked extends AlbumsApiUnchecked {
    public AlbumResponse postAlbumAndExtract(AlbumRequest requestBody, String adminToken) {
        return postAlbum(requestBody, adminToken)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(AlbumResponse.class);
    }

    public AlbumResponse getAlbumAndExtract(Long id, String token) {
        return getAlbum(id, token)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(AlbumResponse.class);
    }

    public List<AlbumResponse> getAlbumsAndExtract(Integer skip, Integer limit, String search, String genre,
                                                    BigDecimal minPrice, BigDecimal maxPrice, SortBy sortBy, String token) {
        return getAlbums(skip, limit, search, genre, minPrice, maxPrice, sortBy, token)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .jsonPath()
                .getList("", AlbumResponse.class);
    }

    public AlbumResponse putAlbumAndExtract(AlbumRequest requestBody, Long id, String adminToken) {
        return putAlbum(requestBody, id, adminToken)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(AlbumResponse.class);
    }
}
