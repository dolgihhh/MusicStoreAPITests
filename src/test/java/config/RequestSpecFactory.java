package config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public class RequestSpecFactory {

    private static RequestSpecification jsonSpec;
    private static RequestSpecification formUrlEncodedSpec;

    public static RequestSpecification jsonSpec() {
        if (jsonSpec == null) {
            jsonSpec = new RequestSpecBuilder()
                    .setContentType(JSON)
                    .setAccept(JSON)
                    .build();
        }
        return jsonSpec;
    }

    public static RequestSpecification jsonWithAuthTokenSpec(String token) {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .setAccept(JSON)
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }

    public static RequestSpecification formUrlEncodedSpec() {
        if (formUrlEncodedSpec == null) {
            formUrlEncodedSpec = new RequestSpecBuilder()
                    .setContentType("application/x-www-form-urlencoded; charset=UTF-8")
                    .build();
        }
        return formUrlEncodedSpec;
    }
}