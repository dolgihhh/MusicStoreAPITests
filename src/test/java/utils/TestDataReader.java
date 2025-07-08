package utils;

import lombok.experimental.UtilityClass;
import models.requests.UserRequest;

@UtilityClass
public class TestDataReader {
    public final String TEST_DATA_PATH = JsonReader.RESOURCES_PATH + "testdata/";
    private final String ADMIN_CRED_DATA_PATH = TEST_DATA_PATH + "adminCred.json";
    private final String BASIC_USER_DATA_PATH = TEST_DATA_PATH + "basicUserCred.json";

    public UserRequest getAdminData() {
        return JsonReader.deserializeJson(ADMIN_CRED_DATA_PATH, UserRequest.class);
    }

    public UserRequest getBasicUserData() {
        return JsonReader.deserializeJson(BASIC_USER_DATA_PATH, UserRequest.class);
    }
}
