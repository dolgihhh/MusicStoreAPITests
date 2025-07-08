package models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private String email;
    private String username;
    private Integer id;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("is_admin")
    private Boolean isAdmin;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime createdAt;
}
