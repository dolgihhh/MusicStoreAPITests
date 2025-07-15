package assertions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import models.requests.UserRequest;
import models.responses.UserResponse;

import static org.junit.jupiter.api.Assertions.*;

@AllArgsConstructor
@NoArgsConstructor
public class AssertAuth {
    //private UserRequest request;

    //public AssertAuth assertThat(UserRequest request) {
    //    return new AssertAuth(request);
    //}

    public void userRequestEqualsResponse(UserRequest request, UserResponse response) {
        assertEquals(request.getUsername(), response.getUsername(), "Пользователь не зарегистрирован с правильным именем пользователя");
        assertEquals(request.getEmail(), response.getEmail(), "Пользователь не зарегистрирован с правильным email");
        assertNotNull(response.getId(), "Пользователь не зарегистрирован с корректным ID");
        assertTrue(response.getIsActive(), "Пользователь не зарегистрирован как активный");
        assertFalse(response.getIsAdmin(), "Пользователь зарегистрирован как администратор, хотя не должен быть");
        assertNotNull(response.getCreatedAt(), "Пользователь не зарегистрирован с корректной датой создания");
    }
}