package models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private String email;
    private String username;
    private Long id;

    //@JsonProperty("is_active")
    private Boolean is_active;

    //@JsonProperty("is_admin")
    private Boolean is_admin;

    //@JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime created_at;
}
