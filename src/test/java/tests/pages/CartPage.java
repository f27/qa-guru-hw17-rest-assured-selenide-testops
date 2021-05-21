package tests.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class CartPage {
    private static final SelenideElement cartTable = $(".cart");

    public void itemInCart(String itemName) {
        cartTable.shouldHave(text(itemName));
    }
}
