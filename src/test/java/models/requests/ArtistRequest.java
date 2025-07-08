package models.requests;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistRequest {
    private String name;
    private String description;
}
