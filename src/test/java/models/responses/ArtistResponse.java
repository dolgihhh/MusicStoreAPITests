package models.responses;

import lombok.Data;

@Data
public class ArtistResponse {
    private String name;
    private String description;
    private Long id; //TODO удалять все созданные сущности  после тестов(ложить их в список а потом удалять) через api
    //в afterall
}
