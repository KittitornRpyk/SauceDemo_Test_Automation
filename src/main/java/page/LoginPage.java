package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import page.common.BasePage;
import utility.logger.AssertLogger;

public class LoginPage extends BasePage {

    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By submitButton = By.id("login-button");
    private final By errorMessage = By.xpath("//h3[@data-test='error']");

    public LoginPage(WebDriver webDriver) {
        super(webDriver);
        verifyPageGenerated(usernameInput);
    }

    public LoginPage(WebDriver webDriver, boolean isLogReportHtml) {
        super(webDriver, isLogReportHtml);
        verifyPageGenerated(usernameInput, isLogReportHtml);
    }

    public LoginPage enterUsername(String username) {
        typeText(usernameInput, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        typeText(passwordInput, password);
        return this;
    }

    public LoginPage clickSubmitButton() {
        click(submitButton);
        return this;
    }

    public InventoryPage clickSubmitButtonAndNavigateToInventoryPage() {
        clickSubmitButton();
        return new InventoryPage(webDriver);
    }

    public InventoryPage doLogin(String username, String password) {
        enterUsername(username)
                .enterPassword(password);
        return clickSubmitButtonAndNavigateToInventoryPage();
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public LoginPage assertErrorMessage(String expectedMessage) {
        AssertLogger.assertEquals(getErrorMessage(), expectedMessage);
        return this;
    }

}
