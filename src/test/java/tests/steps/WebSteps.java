package tests.steps;

import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;
import tests.pages.CartPage;

import java.util.Map;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static endpoints.UiEndpoints.*;

public class WebSteps {

    @Step("(UI) Set cookies")
    public static void setCookies(Map<String, String> cookiesMap) {
        open(FAVICON.getPath());
        cookiesMap.forEach((k, v) -> getWebDriver().manage().addCookie(new Cookie(k, v)));
    }

    @Step("(UI) Verify that item added to cart")
    public static void verifyAddToCart(String itemName) {
        open(CART.getPath(), CartPage.class)
                .itemInCart(itemName);
    }
}
