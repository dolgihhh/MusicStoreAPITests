package tests;

import assertions.AssertArtist;
import data.ArtistDataFactory;
import data.UserDataFactory;
import io.restassured.response.Response;
import models.requests.ArtistRequest;
import models.requests.UserRequest;
import models.responses.ArtistResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import utils.TestTag;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ArtistsTests extends BaseTest {
    private static AssertArtist assertArtist;
    private static String basicRegisteredUserToken;
    private static final Long NON_EXISTENT_ID = 999999L;

    @BeforeAll
    public static void setUp() {
        assertArtist = new AssertArtist();
        UserRequest basicRegisteredUser = UserDataFactory.generateNewValidUser();
        //TODO почитать про extention JUnit
        System.out.println(basicRegisteredUser);
        ApiChecked.auth().registerUserAndExtract(basicRegisteredUser);
        basicRegisteredUserToken = ApiChecked.auth().loginAndGetToken(basicRegisteredUser);
    }

    @Tags({@Tag(TestTag.POSITIVE), @Tag(TestTag.SMOKE)})
    @Test
    @DisplayName("Создание нового исполнителя")
    public void shouldCreateNewArtist() {
        ArtistRequest artistRequest = ArtistDataFactory.generateValidArtist();

        ArtistResponse artistResponse = ApiChecked.artists().postArtist(artistRequest, adminToken);

        assertArtist.assertOnCreate(artistRequest, artistResponse);
    }

    @Tags({@Tag(TestTag.POSITIVE), @Tag(TestTag.SMOKE)})
    @Test
    @DisplayName("Получение исполнителя по ID")
    public void shouldGetArtistById() {
        ArtistRequest newArtist = ArtistDataFactory.generateValidArtist();

        ArtistResponse createdArtist = ApiChecked.artists().postArtist(newArtist, adminToken);
        ArtistResponse artistResponseFromGet = ApiChecked.artists().getArtist(createdArtist.getId(), basicRegisteredUserToken);

        assertArtist.assertOnGet(createdArtist, artistResponseFromGet);
    }

    @Tag(TestTag.NEGATIVE)
    @Test
    @DisplayName("Получение исполнителя по несуществующему ID")
    public void shouldNotGetArtistById() {
        Response response = ApiUnchecked.artists().getArtist(NON_EXISTENT_ID, basicRegisteredUserToken);

        assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusCode(), "Статус код не соответствует ожиданиям");
    }


    @Tags({@Tag(TestTag.POSITIVE), @Tag(TestTag.SMOKE)})
    @DisplayName("Получение списка исполнителей")
    @Test
    public void shouldGetArtistsList() {
        for (int i = 0; i < 10; i++) {
            ApiChecked.artists().postArtist(ArtistDataFactory.generateValidArtist(), adminToken);
        }

        ArtistResponse createdArtist = ApiChecked.artists().postArtist(ArtistDataFactory.generateValidArtist(),
                adminToken);

        List<ArtistResponse> artists = ApiChecked.artists().getArtists(0, 100, adminToken);

        assertTrue(artists.contains(createdArtist), "Список исполнителей не содержит созданного исполнителя");
        assertTrue(artists.size() >= 11, "Список исполнителей должен содержать как минимум 10 исполнителей");
    }

    @Tag(TestTag.POSITIVE)
    @Test
    @DisplayName("Обновление исполнителя")
    public void shouldUpdateArtist() {
        ArtistResponse createdArtist = ApiChecked.artists().postArtist(ArtistDataFactory.generateValidArtist(), adminToken);

        ArtistResponse updatedArtist = ApiChecked.artists().putArtist(createdArtist.getId(),
                ArtistDataFactory.generateValidArtist(), adminToken);

        ArtistResponse updatedArtistAfterGet = ApiChecked.artists().getArtist(createdArtist.getId(), basicRegisteredUserToken);

        assertArtist.assertOnUpdate(createdArtist, updatedArtist, updatedArtistAfterGet);
    }

    @Tag(TestTag.POSITIVE)
    @Test
    @DisplayName("Удаление существующего исполнителя")
    public void shouldDeleteArtist() {
        ArtistResponse createdArtist = ApiChecked.artists().postArtist(ArtistDataFactory.generateValidArtist(), adminToken);
        ApiChecked.artists().deleteArtist(createdArtist.getId(), adminToken);
        Response response = ApiUnchecked.artists().getArtist(createdArtist.getId(), basicRegisteredUserToken);

        assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusCode(), "Исполнитель не удален, а все еще существует");
    }

    @Tag(TestTag.NEGATIVE)
    @Test
    @DisplayName("Удаление несуществующего исполнителя")
    public void shouldNotDeleteNonExistentArtist() {
        Response response = ApiUnchecked.artists().deleteArtist(NON_EXISTENT_ID, adminToken);

        assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusCode(), "Удаление несуществующего " +
                "исполнителя не должно быть успешным");
    }
}
