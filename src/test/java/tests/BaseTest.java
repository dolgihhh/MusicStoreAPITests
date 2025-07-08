package tests;

import clients.checked.*;
import clients.unchecked.UncheckedApiFacade;
import io.restassured.RestAssured;
import models.requests.UserRequest;
import org.junit.jupiter.api.BeforeAll;
import utils.DatabaseUtils;
import utils.TestDataReader;

public class BaseTest {
    protected static UserRequest adminUser;
    protected static UserRequest basicRegisteredUser;
    protected static String adminToken;
    protected static String basicRegisteredUserToken;
    protected static CheckedApiFacade checkedAPI;
    protected static UncheckedApiFacade uncheckedAPI;
    protected static final Long NON_EXISTENT_ID = 999999L;
    private static boolean initialized = false;

    @BeforeAll
    public static synchronized void setup() {
        if (initialized) return;
        initialized = true;

        RestAssured.baseURI = "http://localhost:8000";
        RestAssured.basePath = "/api";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        DatabaseUtils.clearDatabase();
        adminUser = TestDataReader.getAdminData();
        basicRegisteredUser = TestDataReader.getBasicUserData();
        checkedAPI = new CheckedApiFacade();
        uncheckedAPI = new UncheckedApiFacade();
        checkedAPI.auth().registerUser(adminUser);
        checkedAPI.auth().registerUser(basicRegisteredUser);
        adminToken = checkedAPI.auth().loginAndGetToken(adminUser);
        basicRegisteredUserToken = checkedAPI.auth().loginAndGetToken(basicRegisteredUser);
    }
}
