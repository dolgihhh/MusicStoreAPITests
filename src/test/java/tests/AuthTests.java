package tests;

import assertions.AssertAuth;
import data.UserDataFactory;
import io.restassured.response.Response;
import models.requests.UserRequest;
import models.responses.UserResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utils.FakerProvider;
import utils.TestTag;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Tag(TestTag.AUTH)
public class AuthTests extends BaseTest {
    private static final int VALID_USERNAME_LENGTH = 15;
    private static final int VALID_PASSWORD_LENGTH = 15;
    private static AssertAuth assertAuth;

    @BeforeAll
    public static void setUp() {
        assertAuth = new AssertAuth();
    }

    @Tags({@Tag(TestTag.POSITIVE), @Tag(TestTag.SMOKE)})
    @DisplayName("Регистрация нового пользователя")
    @MethodSource("validUserProvider")
    @ParameterizedTest
    public void shouldRegisterNewUserSuccessfully(UserRequest request) {
        UserResponse response = checkedAPI.auth().registerUser(request);

        assertAuth.assertUserRegister(request, response);
    }

    @Tag(TestTag.NEGATIVE)
    @DisplayName("Регистрация нового пользователя c невалидными данными")
    @MethodSource("invalidUserProvider")
    @ParameterizedTest
    public void shouldNotRegisterUserWithInvalidCredentials(UserRequest request) {
        Response response = uncheckedAPI.auth().registerUser(request);

        assertEquals(HttpStatus.SC_UNPROCESSABLE_ENTITY, response.getStatusCode());
    }


    @Tag(TestTag.NEGATIVE)
    @DisplayName("Регистрация нового пользователя с уже существующим email и username")
    @Test
    public void shouldNotRegisterUserWithExistingCredentials() {
        UserRequest request = basicRegisteredUser;

        Response response = uncheckedAPI.auth().registerUser(request);

        assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatusCode());
    }


    @Tags({@Tag(TestTag.POSITIVE), @Tag(TestTag.SMOKE)})
    @DisplayName("Авторизация пользователя c валидными данными")
    @Test
    public void shouldLoginUserSuccessfully() {
        UserRequest request = basicRegisteredUser;

        String token = checkedAPI.auth().loginAndGetToken(request);

        assertNotNull(token, "Токен не получен при авторизации пользователя");
    }

    @Tag(TestTag.NEGATIVE)
    @DisplayName("Авторизация незарегистрированного пользователя")
    @Test
    public void shouldNotLoginUnregisteredUser() {
        UserRequest request = UserDataFactory.generateNewValidUser();

        Response response = uncheckedAPI.auth().loginUser(request);

        assertEquals(HttpStatus.SC_UNAUTHORIZED, response.getStatusCode(),"Ошибка аутентификации");
    }

    @Tag(TestTag.NEGATIVE)
    @DisplayName("Авторизация пользователя с неверным паролем")
    @Test
    public void shouldNotLoginWithWrongPassword() {
        UserRequest request = basicRegisteredUser;
        request.setPassword(FakerProvider.get().internet().password());

        Response response = uncheckedAPI.auth().loginUser(request);

        assertEquals(HttpStatus.SC_UNAUTHORIZED, response.getStatusCode(),"Ошибка аутентификации");
    }

    @DisplayName("Обновление прав пользователя админом")
    @Test
    @Tag(TestTag.POSITIVE)
    public void shouldUpdateUserRights() {
        String usernameToUpdate = basicRegisteredUser.getUsername();

        UserResponse updatedUser = checkedAPI.auth().updateUserRights(usernameToUpdate, adminToken, true);

        assertTrue(updatedUser.getIsAdmin(), "Права пользователя не обновлены на admin");
    }

    @DisplayName("Обновление прав пользователя обычным зарегистрированным пользователем")
    @Test
    @Tags({@Tag(TestTag.NEGATIVE)})
    public void shouldNotUpdateUserRights() {
        checkedAPI.auth().updateUserRights(basicRegisteredUser.getUsername(), adminToken, false);
        UserResponse newUser = checkedAPI.auth().registerUser(UserDataFactory.generateNewValidUser());

        Response response = uncheckedAPI.auth().updateUserRights(newUser.getUsername(), basicRegisteredUserToken, true);

        assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusCode(), "Обычный пользователь не должен" +
                " иметь права обновлять права других пользователей");
    }

    @DisplayName("Обновление прав пользователя без токена авторизации")
    @Test
    @Tags({@Tag(TestTag.NEGATIVE)})
    public void shouldNotUpdateUserRightsWithoutToken() {
        Response response = uncheckedAPI.auth().updateUserRights(basicRegisteredUser.getUsername(), "",
                true);

        assertEquals(HttpStatus.SC_UNAUTHORIZED, response.getStatusCode(), "Доступ без токена не должен " +
                "быть разрешен");
    }


    static Stream<UserRequest> validUserProvider() {
        return Stream.generate(UserDataFactory::generateNewValidUser)
                .limit(3);
    }

    static Stream<UserRequest> invalidUserProvider() {
        return Stream.of(
                UserDataFactory.generateUser(false, true, VALID_USERNAME_LENGTH, VALID_PASSWORD_LENGTH),
                UserDataFactory.generateUser(true, true, 2, VALID_PASSWORD_LENGTH),
                UserDataFactory.generateUser(true, true, 51, VALID_PASSWORD_LENGTH),
                UserDataFactory.generateUser(true, false, VALID_USERNAME_LENGTH, VALID_PASSWORD_LENGTH),
                UserDataFactory.generateUser(true, true, VALID_USERNAME_LENGTH, 7),
                UserDataFactory.generateUser(true, true, VALID_USERNAME_LENGTH, 51)
        );
    }

}
