package models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumRequest {
    private String title;
    private Integer release_year;
    private String genre;
    private BigDecimal price;
    private Integer stock;
    private Long artist_id;
}
