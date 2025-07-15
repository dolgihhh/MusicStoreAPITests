package data;

import com.github.javafaker.Faker;
import models.requests.ArtistRequest;
import utils.FakerProvider;

public class ArtistDataFactory {
    private static final Faker faker = FakerProvider.get();

    public static ArtistRequest generateValidArtist() { //TODO сделать фасад (общий класс для генерации данных)
        String name = faker.name().fullName();
        String description = faker.lorem().paragraph(3);

        return new ArtistRequest(name, description);
    }
}
