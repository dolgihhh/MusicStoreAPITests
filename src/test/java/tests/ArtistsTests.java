package tests;

import assertions.AssertArtist;
import data.ArtistDataFactory;
import data.UserDataFactory;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import models.requests.ArtistRequest;
import models.requests.UserRequest;
import models.responses.ArtistResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import utils.TestTag;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Epic("Artists")
public class ArtistsTests extends BaseTest {
    private static AssertArtist assertArtist;
    private static String basicRegisteredUserToken;
    private static final Long NON_EXISTENT_ID = 999999L;

    @BeforeAll
    public static void setUp() {
        assertArtist = new AssertArtist();
        UserRequest basicRegisteredUser = UserDataFactory.generateNewValidUser();
        //TODO почитать про extention JUnit
        //System.out.println(basicRegisteredUser);
        apiClient.auth().registerUserAndExtract(basicRegisteredUser);
        basicRegisteredUserToken = apiClient.auth().loginAndExtractToken(basicRegisteredUser);
    }

    @Tags({@Tag(TestTag.POSITIVE), @Tag(TestTag.SMOKE)})
    @Test
    @DisplayName("Создание нового исполнителя")
    public void shouldCreateNewArtist() {
        ArtistRequest artistRequest = ArtistDataFactory.generateValidArtist();

        ArtistResponse artistResponse = apiClient.artists().postArtistAndExtract(artistRequest, adminToken);

        assertArtist.assertOnCreate(artistRequest, artistResponse);
    }

    @Tags({@Tag(TestTag.POSITIVE), @Tag(TestTag.SMOKE)})
    @Test
    @DisplayName("Получение исполнителя по ID")
    public void shouldGetArtistById() {
        ArtistRequest newArtist = ArtistDataFactory.generateValidArtist();

        ArtistResponse createdArtist = apiClient.artists().postArtistAndExtract(newArtist, adminToken);
        ArtistResponse artistResponseFromGet = apiClient.artists().getArtistAndExtract(createdArtist.getId(),
                basicRegisteredUserToken);

        //assertArtist.assertOnGet(createdArtist, artistResponseFromGet);
        assertThat(createdArtist)
                .as("Созданный исполнитель я не совпадает с полученным из API")
                .usingRecursiveComparison()
                .isEqualTo(artistResponseFromGet);
    }

    @Tag(TestTag.NEGATIVE)
    @Test
    @DisplayName("Получение исполнителя по несуществующему ID")
    public void shouldNotGetArtistById() {
        Response response = apiClient.artists().getArtist(NON_EXISTENT_ID, basicRegisteredUserToken);

        assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusCode(), "Статус код не соответствует ожиданиям");
    }


    @Tags({@Tag(TestTag.POSITIVE), @Tag(TestTag.SMOKE)})
    @DisplayName("Получение списка исполнителей")
    @Test
    public void shouldGetArtistsList() {
        for (int i = 0; i < 3; i++) {
            apiClient.artists().postArtist(ArtistDataFactory.generateValidArtist(), adminToken);
        }

        ArtistResponse createdArtist = apiClient.artists().postArtistAndExtract(ArtistDataFactory.generateValidArtist(),
                adminToken);

        List<ArtistResponse> artists = apiClient.artists().getArtistsAndExtract(0, 100, adminToken);

        assertTrue(artists.contains(createdArtist), "Список исполнителей не содержит созданного исполнителя");
        assertTrue(artists.size() >= 4, "Список исполнителей должен содержать как минимум 4 исполнителя");
    }

    @Tag(TestTag.POSITIVE)
    @Test
    @DisplayName("Обновление исполнителя")
    public void shouldUpdateArtist() {
        ArtistResponse createdArtist = apiClient.artists().postArtistAndExtract(ArtistDataFactory.generateValidArtist(),
                adminToken);

        ArtistResponse updatedArtist = apiClient.artists().putArtistAndExtract(ArtistDataFactory.generateValidArtist(),
                createdArtist.getId(), adminToken);

        ArtistResponse updatedArtistAfterGet = apiClient.artists().getArtistAndExtract(createdArtist.getId(),
                basicRegisteredUserToken);

        //assertArtist.assertOnUpdate(createdArtist, updatedArtist, updatedArtistAfterGet);
        assertThat(createdArtist)
                .as("Созданный и обновлённый исполнитель не должны совпадать")
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isNotEqualTo(updatedArtist);

        assertThat(updatedArtist)
                .as("Обновлённый исполнитель и полученный после обновления должны совпадать")
                .usingRecursiveComparison()
                .isEqualTo(updatedArtistAfterGet);
}

    @Tag(TestTag.POSITIVE)
    @Test
    @DisplayName("Удаление существующего исполнителя")
    public void shouldDeleteArtist() {
        ArtistResponse createdArtist = apiClient.artists().postArtistAndExtract(ArtistDataFactory.generateValidArtist(),
                adminToken);

        Response responseDelete = apiClient.artists().deleteArtist(createdArtist.getId(), adminToken);

        assertEquals(HttpStatus.SC_NO_CONTENT, responseDelete.getStatusCode(), "Неправильный статус код при удалении " +
                "исполнителя");
    }

    @Tag(TestTag.NEGATIVE)
    @Test
    @DisplayName("Удаление несуществующего исполнителя")
    public void shouldNotDeleteNonExistentArtist() {
        Response response = apiClient.artists().deleteArtist(NON_EXISTENT_ID, adminToken);

        assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusCode(), "Удаление несуществующего " +
                "исполнителя не должно быть успешным");
    }
}
