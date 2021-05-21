package api.spec;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class RequestSpec {
    public static RequestSpecification spec = RestAssured
            .given()
            .log().uri()
            .contentType("application/x-www-form-urlencoded; charset=UTF-8");

    public static RequestSpecification authorizedSpec = RestAssured
            .given(spec);
}
