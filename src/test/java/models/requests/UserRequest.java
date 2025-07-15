package models.requests;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserRequest {
    private String email;
    private String username;
    private String password;
}
