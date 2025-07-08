package assertions;

import models.requests.UserRequest;
import models.responses.UserResponse;

import static org.junit.jupiter.api.Assertions.*;

public class AssertAuth {

    public void assertUserRegister(UserRequest request, UserResponse response) {
        assertEquals(request.getUsername(), response.getUsername(), "Пользователь не зарегистрирован с правильным именем пользователя");
        assertEquals(request.getEmail(), response.getEmail(), "Пользователь не зарегистрирован с правильным email");
        assertNotNull(response.getId(), "Пользователь не зарегистрирован с корректным ID");
        assertTrue(response.getIsActive(), "Пользователь не зарегистрирован как активный");
        assertFalse(response.getIsAdmin(), "Пользователь зарегистрирован как администратор, хотя не должен быть");
        assertNotNull(response.getCreatedAt(), "Пользователь не зарегистрирован с корректной датой создания");
    }
}