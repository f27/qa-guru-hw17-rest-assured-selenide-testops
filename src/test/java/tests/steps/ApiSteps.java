package tests.steps;

import io.qameta.allure.Step;

import java.util.Map;

import static api.LogFilter.filters;
import static api.spec.RequestSpec.authorizedSpec;
import static endpoints.ApiEndpoints.ADD_TO_CART;
import static io.restassured.RestAssured.given;

public class ApiSteps {

    @Step("(API) Add simple item to cart")
    public static void addSimpleItemToCart(String item) {
        given(authorizedSpec)
                .filter(filters().withCustomTemplates())
                .when()
                .post(ADD_TO_CART.addPath(item))
                .then()
                .statusCode(200);
    }

    @Step("(API) Add item with details to cart")
    public static void addItemWithDetailsToCart(String item, Map<String,String> details) {
        given(authorizedSpec)
                .filter(filters().withCustomTemplates())
                .formParams(details)
                .log().params()
                .when()
                .post(ADD_TO_CART.addPath(item))
                .then()
                .statusCode(200);
    }
}
