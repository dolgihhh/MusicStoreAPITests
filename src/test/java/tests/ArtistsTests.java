package tests;

import assertions.AssertArtist;
import data.ArtistDataFactory;
import io.restassured.response.Response;
import models.requests.ArtistRequest;
import models.responses.ArtistResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import utils.TestTag;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ArtistsTests extends BaseTest {
    private static AssertArtist assertArtist;

    @BeforeAll
    public static void setUp() {
        assertArtist = new AssertArtist();
    }

    @Tags({@Tag(TestTag.POSITIVE), @Tag(TestTag.SMOKE)})
    @Test
    @DisplayName("Создание нового исполнителя")
    public void shouldCreateNewArtist() {
        ArtistRequest artistRequest = ArtistDataFactory.generateValidArtist();

        ArtistResponse artistResponse = checkedAPI.artists().postArtist(artistRequest, adminToken);

        assertArtist.assertOnCreate(artistRequest, artistResponse);
    }

    @Tags({@Tag(TestTag.POSITIVE), @Tag(TestTag.SMOKE)})
    @Test
    @DisplayName("Получение исполнителя по ID")
    public void shouldGetArtistById() {
        ArtistRequest newArtist = ArtistDataFactory.generateValidArtist();

        ArtistResponse createdArtist = checkedAPI.artists().postArtist(newArtist, adminToken);
        ArtistResponse artistResponseFromGet = checkedAPI.artists().getArtist(createdArtist.getId(), basicRegisteredUserToken);

        assertArtist.assertOnGet(createdArtist, artistResponseFromGet);
    }

    @Tag(TestTag.NEGATIVE)
    @Test
    @DisplayName("Получение исполнителя по несуществующему ID")
    public void shouldNotGetArtistById() {
        Response response = uncheckedAPI.artists().getArtist(NON_EXISTENT_ID, basicRegisteredUserToken);
        //ErrorResponse errorResponse = response.as(ErrorResponse.class);

        //assertEquals("Artist not found", errorResponse.getDetail());
        assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusCode(), "Статус код не соответствует ожиданиям");
    }


    @Tags({@Tag(TestTag.POSITIVE), @Tag(TestTag.SMOKE)})
    @DisplayName("Получение списка исполнителей")
    @Test
    public void shouldGetArtistsList() {
        for (int i = 0; i < 10; i++) {
            checkedAPI.artists().postArtist(ArtistDataFactory.generateValidArtist(), adminToken);
        }

        ArtistResponse createdArtist = checkedAPI.artists().postArtist(ArtistDataFactory.generateValidArtist(),
                adminToken);

        List<ArtistResponse> artists = checkedAPI.artists().getArtists(0, 100, adminToken);

        assertTrue(artists.contains(createdArtist), "Список исполнителей не содержит созданного исполнителя");
        assertTrue(artists.size() >= 11, "Список исполнителей должен содержать как минимум 10 исполнителей");
    }

    @Tag(TestTag.POSITIVE)
    @Test
    @DisplayName("Обновление исполнителя")
    public void shouldUpdateArtist() {
        ArtistResponse createdArtist = checkedAPI.artists().postArtist(ArtistDataFactory.generateValidArtist(), adminToken);

        ArtistResponse updatedArtist = checkedAPI.artists().putArtist(createdArtist.getId(),
                ArtistDataFactory.generateValidArtist(), adminToken);

        ArtistResponse updatedArtistAfterGet = checkedAPI.artists().getArtist(createdArtist.getId(), basicRegisteredUserToken);

        assertArtist.assertOnUpdate(createdArtist, updatedArtist, updatedArtistAfterGet);
    }

    @Tag(TestTag.POSITIVE)
    @Test
    @DisplayName("Удаление существующего исполнителя")
    public void shouldDeleteArtist() {
        ArtistResponse createdArtist = checkedAPI.artists().postArtist(ArtistDataFactory.generateValidArtist(), adminToken);
        checkedAPI.artists().deleteArtist(createdArtist.getId(), adminToken);
        Response response = uncheckedAPI.artists().getArtist(createdArtist.getId(), basicRegisteredUserToken);

        assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusCode(), "Исполнитель не удален, а все еще существует");
    }

    @Tag(TestTag.NEGATIVE)
    @Test
    @DisplayName("Удаление несуществующего исполнителя")
    public void shouldNotDeleteNonExistentArtist() {
        Response response = uncheckedAPI.artists().deleteArtist(NON_EXISTENT_ID, adminToken);

        assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusCode(), "Удаление несуществующего " +
                "исполнителя не должно быть успешным");
    }
}
