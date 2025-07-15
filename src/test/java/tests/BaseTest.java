package tests;

import clients.CheckedApiFacade;
import clients.UncheckedApiFacade;
import io.restassured.RestAssured;
import models.requests.UserRequest;
import org.junit.jupiter.api.BeforeAll;
import utils.ConfigReader;
import utils.DatabaseUtils;
import utils.TestDataReader;

public class BaseTest {
    protected static UserRequest adminUser;
    protected static String adminToken;
    protected static CheckedApiFacade ApiChecked;
    protected static UncheckedApiFacade ApiUnchecked;
    private static boolean initialized = false;

    @BeforeAll
    public static synchronized void setup() {
        if (initialized) return;
        initialized = true;
        RestAssured.baseURI = ConfigReader.get("base.uri");
        RestAssured.basePath = ConfigReader.get("base.path");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        DatabaseUtils.clearDatabase();
        adminUser = TestDataReader.getAdminData();
        ApiChecked = new CheckedApiFacade();
        ApiUnchecked = new UncheckedApiFacade();
        ApiChecked.auth().registerUserAndExtract(adminUser);
        adminToken = ApiChecked.auth().loginAndGetToken(adminUser);
    }
}
