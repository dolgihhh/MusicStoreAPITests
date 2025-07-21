package assertions;

import lombok.NoArgsConstructor;
import models.requests.UserRequest;
import models.responses.UserResponse;

import static org.junit.jupiter.api.Assertions.*;

@NoArgsConstructor
public class AssertAuth {
    public void userRequestEqualsResponse(UserRequest request, UserResponse response) {
        assertEquals(request.getUsername(), response.getUsername(), "Пользователь не зарегистрирован с правильным именем пользователя");
        assertEquals(request.getEmail(), response.getEmail(), "Пользователь не зарегистрирован с правильным email");
        assertNotNull(response.getId(), "Пользователь не зарегистрирован с корректным ID");
        assertTrue(response.getIs_active(), "Пользователь не зарегистрирован как активный");
        assertFalse(response.getIs_admin(), "Пользователь зарегистрирован как администратор, хотя не должен быть");
        assertNotNull(response.getCreated_at(), "Пользователь не зарегистрирован с корректной датой создания");
    }
}