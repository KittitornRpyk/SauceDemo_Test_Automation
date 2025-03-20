package page;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import page.common.BasePage;
import page.common.MenuBar;

public class CheckOutInformationPage extends BasePage {

    private final By firstNameInput = By.id("first-name");
    private final By lastNameInput = By.id("last-name");
    private final By postalCode = By.id("postal-code");
    private final By cancelButton = By.id("cancel");
    private final By continueButton = By.id("continue");

    @Getter
    private MenuBar menuBar;

    public CheckOutInformationPage(WebDriver webDriver) {
        super(webDriver);
        verifyPageGenerated(firstNameInput);
        menuBar = new MenuBar(webDriver);
        menuBar.verifyTitleText("Checkout: Your Information");
    }

    public CheckOutInformationPage enterFirstName(String text) {
        typeText(firstNameInput, text);
        return this;
    }

    public CheckOutInformationPage enterLastName(String text) {
        typeText(lastNameInput, text);
        return this;
    }

    public CheckOutInformationPage enterPostalCode(String text) {
        typeText(postalCode, text);
        return this;
    }

    public OverviewPage clickContinueButton() {
        click(continueButton);
        return new OverviewPage(webDriver);
    }

    public CheckOutInformationPage clickCancelButton() {
        click(cancelButton);
        return new CheckOutInformationPage(webDriver);
    }

}
