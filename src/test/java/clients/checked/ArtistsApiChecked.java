package clients.checked;

import clients.unchecked.ArtistsApiUnchecked;
import models.requests.ArtistRequest;
import models.responses.ArtistResponse;
import org.apache.http.HttpStatus;
import java.util.List;


public class ArtistsApiChecked extends ArtistsApiUnchecked {
    public ArtistResponse postArtistAndExtract(ArtistRequest requestBody, String adminToken) {
        return postArtist(requestBody, adminToken)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(ArtistResponse.class);
    }

    public ArtistResponse getArtistAndExtract(Long id, String token) {
        return getArtist(id, token)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(ArtistResponse.class);
    }

    public List<ArtistResponse> getArtistsAndExtract(Integer skip, Integer limit, String token) {
        return getArtists(skip, limit, token)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .jsonPath()
                .getList("", ArtistResponse.class);
    }

    public ArtistResponse putArtistAndExtract(ArtistRequest requestBody, Long id, String adminToken) {
        return putArtist(requestBody, id, adminToken)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(ArtistResponse.class);
    }
}
