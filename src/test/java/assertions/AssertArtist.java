package assertions;

import models.requests.ArtistRequest;
import models.responses.ArtistResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AssertArtist {
    public void assertOnCreate(ArtistRequest request, ArtistResponse response) {
        assertNotNull(response.getId(), "Исполнитель не создан с корректным ID");
        assertEquals(request.getName(), response.getName(), "Исполнитель не создан с правильным именем");
        assertEquals(request.getDescription(), response.getDescription(), "Исполнитель не создан с правильным описанием");
    }

    public void assertOnGet(ArtistResponse expected, ArtistResponse actual) {
        assertNotNull(actual, "Исполнитель не найден");
        assertEquals(expected.getId(), actual.getId(), "Полученный исполнитель имеет неправильный ID");
        assertEquals(expected.getName(), actual.getName(), "Полученный исполнитель имеет неправильное имя");
        assertEquals(expected.getDescription(), actual.getDescription(), "Полученный исполнитель имеет неправильное описание");
    }

    public void assertOnUpdate(ArtistResponse expected, ArtistResponse actual, ArtistResponse actualAfterGet) {
        assertNotNull(actual, "Исполнитель не обновлен");
        assertNotEquals(expected.getName(), actual.getName(), "Имя исполнителя не обновлено");
        assertNotEquals(expected.getDescription(), actual.getDescription(), "Описание исполнителя не обновлено");
        assertEquals(actual, actualAfterGet, "Исполнитель после обновления не совпадает с полученным из API");
    }
}
