package page.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import page.CartPage;
import page.InventoryPage;
import page.LoginPage;
import utility.logger.AssertLogger;
import utility.logger.LoggerUtil;

public class MenuBar extends BasePage {
    private final By titleText = By.className("title");
    private final By burgerMenu = By.id("react-burger-menu-btn");
    private final By allItemLink = By.id("inventory_sidebar_link");
    private final By logoutLink = By.id("logout_sidebar_link");
    private final By closeBurgerButton = By.id("react-burger-cross-btn");
    private final By cartButton = By.className("shopping_cart_link");
    private final By cartBadge = By.className("shopping_cart_badge");

    public MenuBar(WebDriver webDriver) {
        super(webDriver);
    }

    public CartPage navigateToCartPage() {
        click(cartButton);
        return new CartPage(webDriver);
    }

    public String getTitleText() {
        return getText(titleText);
    }

    public void verifyTitleText(String expectedTitle) {
        LoggerUtil.logInfo("Verifying page title");
        AssertLogger.assertEquals(getTitleText(), expectedTitle);
    }

    public int getCartBadgeCount() {
        return Integer.parseInt(getText(cartBadge));
    }

    public void verifyCartBadgeCount(int expected) {
        AssertLogger.assertEquals(getCartBadgeCount(), expected);
    }

    public InventoryPage navigateToInventoryPage() {
        click(burgerMenu);
        click(allItemLink);
        return new InventoryPage(webDriver);
    }

    public LoginPage logout() {
        click(burgerMenu);
        click(logoutLink);
        return new LoginPage(webDriver);
    }

    public void closeBurger() {
        click(closeBurgerButton);
    }
}
