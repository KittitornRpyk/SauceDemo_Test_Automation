package page;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import page.common.BasePage;
import page.common.MenuBar;

public class CompletePage extends BasePage {

    private By completeHeaderText = By.className("complete-header");
    private By backHomeButton = By.id("back-to-products");

    @Getter
    private MenuBar menuBar;

    public CompletePage(WebDriver webDriver) {
        super(webDriver);
        verifyPageGenerated(completeHeaderText);
        menuBar = new MenuBar(webDriver);
        menuBar.verifyTitleText("Checkout: Complete!");
    }

    public String getCompleteText() {
        return getText(completeHeaderText);
    }

    public InventoryPage clickBackHomeButton() {
        click(backHomeButton);
        return new InventoryPage(webDriver);
    }


}
