package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import page.common.BasePage;

public class ProductPage extends BasePage {

    private By productNameText = By.xpath("//div[@data-test='inventory-item-name']");
    private By productDescriptionText = By.xpath("//div[@data-test='inventory-item-desc']");
    private By productPriceText = By.xpath("//div[@data-test='inventory-item-price']");

    private By addToCartButton = By.id("add-to-cart");
    private By backToProductsButton = By.id("back-to-products");

    public ProductPage(WebDriver webDriver) {
        super(webDriver);
    }

    public String getProductName() {
        return getText(productNameText);
    }

    public String getProductDescription() {
        return getText(productDescriptionText);
    }

    public String getProductPrice() {
        return getText(productPriceText);
    }

}
