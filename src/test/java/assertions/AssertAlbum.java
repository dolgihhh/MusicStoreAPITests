package assertions;

import models.requests.AlbumRequest;
import models.responses.AlbumResponse;

import static org.junit.jupiter.api.Assertions.*;

public class AssertAlbum {
    public void assertOnCreate(AlbumRequest albumRequest, AlbumResponse albumResponse) {
        assertNotNull(albumResponse.getId(), "Альбом не создан с корректным ID");
        assertEquals(albumRequest.getTitle(), albumResponse.getTitle(), "Альбом не создан с правильным названием");
        assertEquals(albumRequest.getRelease_year(), albumResponse.getRelease_year(), "Год релиза не совпадает");
        assertEquals(albumRequest.getGenre(), albumResponse.getGenre(), "Жанр не совпадает");
        assertEquals(0, albumRequest.getPrice().compareTo(albumResponse.getPrice()), "Цена не совпадает");
        assertEquals(albumRequest.getStock(), albumResponse.getStock(), "Количество на складе не совпадает");
        assertNotNull(albumResponse.getArtist(), "Артист не указан");
    }
}
