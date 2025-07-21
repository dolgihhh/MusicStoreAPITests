package models.responses;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AlbumResponse {
    private String title;
    private Integer release_year;
    private String genre;
    private BigDecimal price;
    private Integer stock;
    private Long id;
    private ArtistResponse artist;
}
