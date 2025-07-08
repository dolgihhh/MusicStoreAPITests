package data;

import models.requests.UserRequest;
import com.github.javafaker.Faker;
import utils.FakerProvider;

public class UserDataFactory {
    private static final Faker faker = FakerProvider.get();

    public static UserRequest generateNewValidUser() {
        String username = getValidUsername();
        String password = faker.internet().password(8, 51);

        return new UserRequest(
                faker.internet().emailAddress(),
                username,
                password
        );
    }

    public static UserRequest generateUser(boolean isEmailValid,  boolean isUsernameValid, int usernameLength,
                                           int passwordLength) {
        if (usernameLength < 1 || passwordLength < 1) {
            throw new IllegalArgumentException("Username and password length must be at least 2 characters for " +
                    "generation.");
        }
        String username = isUsernameValid ? getValidUsername(usernameLength) : getInvalidUsername(usernameLength);
        String password = faker.internet().password(passwordLength, passwordLength + 1);
        String email = isEmailValid ? faker.internet().emailAddress() : faker.letterify("??????????");

        return UserRequest.builder()
                .email(email)
                .password(password)
                .username(username)
                .build();
    }

    private static String getValidUsername() {

        return faker.regexify("[a-zA-Z0-9_-]{3,50}");
    }

    private static String getValidUsername(int usernameLength) {

        return faker.regexify("[a-zA-Z0-9_-]{" + usernameLength + "}");
    }

    private static String getInvalidUsername(int usernameLength) {
        int lastLength = usernameLength - 1;

        return faker.regexify("[^a-zA-Z0-9_-]{1}[a-zA-Z0-9_-]{" + lastLength + "}");
    }
}
