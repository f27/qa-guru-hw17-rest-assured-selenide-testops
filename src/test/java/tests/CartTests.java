package tests;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.steps.ApiSteps;
import tests.steps.WebSteps;

import java.util.HashMap;
import java.util.Map;

@Owner("f27")
@DisplayName("Cart tests")
public class CartTests extends TestBase{

    @Test
    @DisplayName("Add simple item to cart test")
    void addSimpleItemToCartTest() {
        WebSteps.setCookies(authCookies);
        ApiSteps.addSimpleItemToCart("/catalog/31/1/1");
        WebSteps.verifyAddToCart("14.1-inch Laptop");
    }

    @Test
    @DisplayName("Add item with details to cart test")
    void addItemWithDetailsToCartTest() {
        WebSteps.setCookies(authCookies);

        Map<String,String> details = new HashMap<String,String>(){{
            put("giftcard_2.RecipientName", "John");
            put("giftcard_2.RecipientEmail", "john_beznogim@yahoo.com");
            put("giftcard_2.SenderName", "Alice");
            put("giftcard_2.SenderEmail", "alice.in.wonderland@gmail.com");
            put("giftcard_2.Message", "Shut up and take my money!");
            put("addtocart_2.EnteredQuantity", "1");
        }};

        ApiSteps.addItemWithDetailsToCart("/details/2/1", details);
        WebSteps.verifyAddToCart("$25 Virtual Gift Card");
    }
}
