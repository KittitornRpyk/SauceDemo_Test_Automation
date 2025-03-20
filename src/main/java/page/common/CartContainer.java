package page.common;

import dataclass.ItemData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utility.logger.LoggerUtil;

import java.util.ArrayList;
import java.util.List;

public class CartContainer extends BasePage {

    private final By cartItemElements = By.className("cart_item");

    public CartContainer(WebDriver webDriver) {
        super(webDriver);
    }

    public List<ItemData> getCartItems() {
        List<WebElement> webElements = webDriver.findElements(cartItemElements);
        if (webElements.isEmpty()) {
            return new ArrayList<>();
        }
        List<ItemData> items = new ArrayList<>();
        for (WebElement e : webElements) {
            ItemData item = new ItemData();
            item.setName(e.findElement(By.cssSelector("div[data-test='inventory-item-name']")).getText());
            item.setDescription(e.findElement(By.cssSelector("div[data-test='inventory-item-desc']")).getText());
            item.setPrice(e.findElement(By.cssSelector("div[data-test='inventory-item-price']")).getText());
            items.add(item);
        }
        return items;
    }

    public CartContainer removeFromCart(String itemName) {
        LoggerUtil.logInfo(String.format("Removing '%s' from the cart", itemName));
        List<WebElement> webElements = webDriver.findElements(cartItemElements);
        List<ItemData> itemDataList = getCartItems();
        if (itemDataList.stream().filter(i -> i.getName().equals(itemName)).count() > 0) {
            for (WebElement e : webElements) {
                if (e.findElement(By.cssSelector("div[data-test='inventory-item-name']")).getText().equals(itemName)) {
                    itemDataList.remove(itemDataList.stream().filter(i -> i.getName().equals(itemName)).findFirst().get());
                    click(e.findElement(By.xpath(".//button[contains(normalize-space,'Remove')]")), "Remove button");
                    return this;
                }
            }
            return this;
        } else {
            LoggerUtil.logWarning(String.format("'%s' is not in the cart", itemName));
        }
        return this;
    }

}
