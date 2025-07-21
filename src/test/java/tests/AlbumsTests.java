package tests;


import assertions.AssertAlbum;
import data.AlbumDataFactory;
import data.UserDataFactory;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import models.requests.AlbumRequest;
import models.requests.UserRequest;
import models.responses.AlbumResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import utils.TestTag;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Albums")
public class AlbumsTests extends BaseTest {
    private static AssertAlbum assertAlbum;
    private static String basicRegisteredUserToken;
    private static final Long NON_EXISTENT_ID = 999999L;
    private AlbumDataFactory albumDataFactory = new AlbumDataFactory();

    @BeforeAll
    public static void setUp() {
        assertAlbum = new AssertAlbum();
        UserRequest basicRegisteredUser = UserDataFactory.generateNewValidUser();
        apiClient.auth().registerUserAndExtract(basicRegisteredUser);
        basicRegisteredUserToken = apiClient.auth().loginAndExtractToken(basicRegisteredUser);
    }

    @Tags({@Tag(TestTag.POSITIVE), @Tag(TestTag.SMOKE)})
    @Test
    @DisplayName("Создание нового альбома")
    public void shouldCreateNewAlbum() {
        AlbumRequest albumRequest = albumDataFactory.generateValidAlbum();

        AlbumResponse albumResponse = apiClient.albums().postAlbumAndExtract(albumRequest, adminToken);

        assertAlbum.assertOnCreate(albumRequest, albumResponse);
    }

    @Tags({@Tag(TestTag.POSITIVE), @Tag(TestTag.SMOKE)})
    @Test
    @DisplayName("Получение альбома по ID")
    public void shouldGetAlbumById() {
        AlbumRequest newAlbum = albumDataFactory.generateValidAlbum();

        AlbumResponse createdAlbum = apiClient.albums().postAlbumAndExtract(newAlbum, adminToken);
        AlbumResponse albumResponseFromGet = apiClient.albums().getAlbumAndExtract(createdAlbum.getId(),
                basicRegisteredUserToken);

        //assertAlbum.assertOnGet(createdAlbum, albumResponseFromGet);
        assertThat(createdAlbum)
                .as("Созданный альбом не совпадает с полученным из API")
                .usingRecursiveComparison()
                .isEqualTo(albumResponseFromGet);
    }

    @Tag(TestTag.NEGATIVE)
    @Test
    @DisplayName("Получение альбома по несуществующему ID")
    public void shouldNotGetAlbumById() {
        Response response = apiClient.albums().getAlbum(NON_EXISTENT_ID, basicRegisteredUserToken);

        assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusCode(), "Статус код не соответствует ожиданиям");
    }

    @Tags({@Tag(TestTag.POSITIVE), @Tag(TestTag.SMOKE)})
    @DisplayName("Получение списка альбомов")
    @Test
    public void shouldGetAlbumsList() {
        for (int i = 0; i < 3; i++) {
            apiClient.albums().postAlbum(albumDataFactory.generateValidAlbum(), adminToken);
        }

        AlbumResponse createdAlbum = apiClient.albums().postAlbumAndExtract(albumDataFactory.generateValidAlbum(),
                adminToken);

        List<AlbumResponse> albums = apiClient.albums().getAlbumsAndExtract(null, null, null, null,
                null, null, null, basicRegisteredUserToken);

        assertTrue(albums.contains(createdAlbum), "Список альбомов не содержит созданного альбома");
        assertTrue(albums.size() >= 4, "Список альбомов должен содержать как минимум 4 альбома");
    }

    @Tag(TestTag.POSITIVE)
    @Test
    @DisplayName("Обновление альбома")
    public void shouldUpdateAlbum() {
        AlbumResponse createdAlbum = apiClient.albums().postAlbumAndExtract(albumDataFactory.generateValidAlbum(),
                adminToken);

        AlbumResponse updatedAlbum = apiClient.albums().putAlbumAndExtract(albumDataFactory.generateValidAlbum(),
                createdAlbum.getId(), adminToken);

        AlbumResponse updatedAlbumAfterGet = apiClient.albums().getAlbumAndExtract(createdAlbum.getId(),
                basicRegisteredUserToken);


//        assertThat(createdAlbum)
//                .as("Созданный и обновлённый альбом не должны совпадать")
//                .usingRecursiveComparison()
//                .ignoringFields("id")
//                .isNotEqualTo(updatedAlbum);
//        assertThat(updatedAlbum)
//                .as("Обновлённый альбом и полученный после обновления должны совпадать")
//                .usingRecursiveComparison()
//                .isEqualTo(updatedAlbumAfterGet);
        assertNotEquals(createdAlbum, updatedAlbum,"Созданный и обновлённый альбом не должны совпадать");
        assertEquals(updatedAlbum, updatedAlbumAfterGet, "Обновлённый альбом и полученный после обновления должны совпадать");
    }

    @Tag(TestTag.POSITIVE)
    @Test
    @DisplayName("Удаление существующего альбома")
    public void shouldDeleteAlbum() {
        AlbumResponse createdAlbum = apiClient.albums().postAlbumAndExtract(albumDataFactory.generateValidAlbum(),
                adminToken);
        Response responseDelete = apiClient.albums().deleteAlbum(createdAlbum.getId(), adminToken);
        Response responseGet = apiClient.albums().getAlbum(createdAlbum.getId(), basicRegisteredUserToken);

        assertEquals(HttpStatus.SC_NO_CONTENT, responseDelete.getStatusCode(), "Неправильный статус код при удалении " +
                "альбома");
        assertEquals(HttpStatus.SC_NOT_FOUND, responseGet.getStatusCode(), "Альбом не удален, а все еще существует");
    }

    @Tag(TestTag.NEGATIVE)
    @Test
    @DisplayName("Удаление несуществующего альбома")
    public void shouldNotDeleteNonExistentAlbum() {
        Response response = apiClient.albums().deleteAlbum(NON_EXISTENT_ID, adminToken);
        response.getBody().prettyPrint();
        assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusCode(), "Удаление несуществующего альбома не должно быть успешным");
    }
}