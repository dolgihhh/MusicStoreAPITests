package tests;

import clients.ApiFacade;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import models.requests.UserRequest;
import org.junit.jupiter.api.BeforeAll;
import utils.ConfigReader;
import utils.DatabaseUtils;
import utils.TestDataReader;

public class BaseTest {
    protected static UserRequest adminUser;
    public static String adminToken;
    protected static ApiFacade apiClient;
    private static boolean initialized = false;

    @BeforeAll
    public static synchronized void setup() {
        if (initialized) return;
        initialized = true;
        RestAssured.baseURI = ConfigReader.get("base.uri");
        RestAssured.basePath = ConfigReader.get("base.path");
        RestAssured.filters(new AllureRestAssured());
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        DatabaseUtils.clearDatabase();
        adminUser = TestDataReader.getAdminData();
        apiClient = new ApiFacade();
        apiClient.auth().registerUser(adminUser);
        adminToken = apiClient.auth().loginAndExtractToken(adminUser);
    }
}

//package tests;
//
//import clients.ApiFacade;
//import io.restassured.RestAssured;
//import lombok.Setter;
//import models.requests.UserRequest;
//import org.junit.jupiter.api.BeforeAll;
//import utils.ConfigReader;
//import utils.TestDataReader;
//
//@Setter
//public class BaseTest {
//    protected static UserRequest adminUser;
//    protected static String adminToken;
//    protected static ApiFacade apiClient;
//
//    @BeforeAll
//    public static void setup() {
//        RestAssured.baseURI = ConfigReader.get("base.uri");
//        RestAssured.basePath = ConfigReader.get("base.path");
//        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
//    }
//}
