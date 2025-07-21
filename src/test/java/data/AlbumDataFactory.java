package data;

import clients.ApiFacade;
import com.github.javafaker.Faker;
import models.requests.AlbumRequest;
import models.requests.ArtistRequest;
import models.responses.ArtistResponse;
import utils.FakerProvider;

import java.math.BigDecimal;

import static tests.BaseTest.adminToken;

public class AlbumDataFactory {
    private static final ApiFacade apiClient = new ApiFacade();
    private static final Faker faker = FakerProvider.get();

    public AlbumRequest generateValidAlbum() {
        String title = faker.book().title();
        title = title.substring(0, Math.min(title.length(), 200));
        Integer release_year = faker.number().numberBetween(1900, 2100);
        String genre = faker.music().genre();
        genre = genre.substring(0, Math.min(genre.length(), 50));
        BigDecimal price = BigDecimal.valueOf(faker.number().numberBetween(0, 10000));
        Integer stock = faker.number().numberBetween(0, 10000);

        // Creating a valid artist for the album
        ArtistRequest artist = ArtistDataFactory.generateValidArtist();
        ArtistResponse artistResponse = apiClient.artists().postArtistAndExtract(artist, adminToken);

        return AlbumRequest.builder()
                .title(title)
                .release_year(release_year)
                .genre(genre)
                .price(price)
                .stock(stock)
                .artist_id(artistResponse.getId())
                .build();
    }
}
