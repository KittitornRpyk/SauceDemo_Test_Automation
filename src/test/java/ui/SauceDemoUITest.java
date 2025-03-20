package ui;

import baseclass.UIBaseTest;
import dataprovider.CustomDataProvider;
import dataclass.ItemData;
import dataclass.LoginData;
import dataclass.UserData;
import org.testng.annotations.Test;
import page.*;
import utility.logger.AssertLogger;

import java.util.HashSet;
import java.util.List;

public class SauceDemoUITest extends UIBaseTest {

    @Test(
            testName = "TEST_CASE_01",
            description = "Login and add product to the shopping cart",
            dataProviderClass = CustomDataProvider.class,
            dataProvider = "loginData")
    public void loginAndAddProductTest(LoginData loginData) {
        InventoryPage inventoryPage = loginPage
                .doLogin(loginData.getUsername(), loginData.getPassword())
                .sortInventory("Price (high to low)")
                .verifySortPriceHighToLow()
                .randomAddItemToCart(2);
        inventoryPage.getMenuBar().verifyCartBadgeCount(2);
        List<ItemData> inventoryCartItems = inventoryPage.getInventoryCartItems();

        CartPage cartPage = inventoryPage.navigateToCart();
        List<ItemData> cartItems = cartPage.getCartContainer().getCartItems();
        AssertLogger.assertEquals(new HashSet<>(cartItems), new HashSet<>(inventoryCartItems));

        CheckOutInformationPage checkOutInformationPage = cartPage.clickCheckout();
        UserData userData = UserData.generateRandomData();
        checkOutInformationPage.enterFirstName(userData.getFirstName())
                .enterLastName(userData.getLastName())
                .enterPostalCode(userData.getPostalCode());

        OverviewPage overviewPage = checkOutInformationPage.clickContinueButton();
        List<ItemData> overviewItems = overviewPage.getCartContainer().getCartItems();
        AssertLogger.assertEquals(new HashSet<>(overviewItems), new HashSet<>(cartItems));
        overviewPage.verifyCalculation()
                .logSummary();

        CompletePage completePage = overviewPage.clickFinishButton();
        AssertLogger.assertEquals(completePage.getCompleteText(), "Thank you for your order!");
    }
}
