package page;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import page.common.BasePage;
import page.common.CartContainer;
import page.common.MenuBar;

public class CartPage extends BasePage {

    private final By cartListContainer = By.id("cart_contents_container");
    private final By continueShoppingButton = By.id("continue-shopping");
    private final By checkOutButton = By.id("checkout");

    @Getter
    private MenuBar menuBar;
    @Getter
    private CartContainer cartContainer;

    public CartPage(WebDriver webDriver) {
        super(webDriver);
        verifyPageGenerated(cartListContainer);
        menuBar = new MenuBar(webDriver);
        menuBar.verifyTitleText("Your Cart");
        cartContainer = new CartContainer(webDriver);
    }

    public InventoryPage clickContinueShopping() {
        click(continueShoppingButton);
        return new InventoryPage(webDriver);
    }

    public CheckOutInformationPage clickCheckout() {
        click(checkOutButton);
        return new CheckOutInformationPage(webDriver);
    }

}
