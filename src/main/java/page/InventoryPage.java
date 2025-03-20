package page;

import lombok.Getter;
import dataclass.ItemData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import page.common.BasePage;
import page.common.MenuBar;
import utility.logger.AssertLogger;
import utility.logger.LoggerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InventoryPage extends BasePage {

    private final By productSortContainer = By.className("product_sort_container");
    private final By inventoryContainerElement = By.id("inventory_container");
    private final By inventoryItemElements = By.cssSelector(".inventory_list > .inventory_item");

    @Getter
    private MenuBar menuBar;
    @Getter
    private List<ItemData> inventoryCartItems = new ArrayList<>();

    public InventoryPage(WebDriver webDriver) {
        super(webDriver);
        verifyPageGenerated(inventoryContainerElement);
        menuBar = new MenuBar(webDriver);
        menuBar.verifyTitleText("Products");
    }

    public CartPage navigateToCart() {
        return menuBar.navigateToCartPage();
    }

    public InventoryPage sortInventory(String option) {
        selectByVisibleText(productSortContainer, option);
        AssertLogger.assertEquals(getSelectedOption(productSortContainer), option);
        return this;
    }

    public InventoryPage verifySortPriceHighToLow() {
        LoggerUtil.logInfo("Verifying price");
        List<ItemData> itemDataList = getInventoryItems();
        if (itemDataList.size() > 1) {
            for (int i = 0; i < itemDataList.size() - 1; i++) {
                AssertLogger.assertTrue(itemDataList.get(i).getPriceBigDecimal()
                                .compareTo(itemDataList.get(i + 1).getPriceBigDecimal()) >= 0,
                        String.format("%s(%s) price is more than or equal as %s(%s)",
                                itemDataList.get(i).getName(),
                                itemDataList.get(i).getPrice(),
                                itemDataList.get(i + 1).getName(),
                                itemDataList.get(i + 1).getPrice()));
            }
        } else {
            LoggerUtil.logWarning("There is no items to be compared");
        }
        return this;
    }

    public List<ItemData> getInventoryItems() {
        List<WebElement> webElements = webDriver.findElements(inventoryItemElements);
        if (webElements.isEmpty()) {
            return new ArrayList<>();
        }
        List<ItemData> itemDataList = new ArrayList<>();
        for (WebElement e : webElements) {
            ItemData item = new ItemData();
            item.setName(e.findElement(By.cssSelector("div[data-test='inventory-item-name']")).getText());
            item.setDescription(e.findElement(By.cssSelector("div[data-test='inventory-item-desc']")).getText());
            item.setPrice(e.findElement(By.cssSelector("div[data-test='inventory-item-price']")).getText());
            itemDataList.add(item);
        }
        return itemDataList;
    }

    public InventoryPage randomAddItemToCart(int itemTypeCount) {
        LoggerUtil.logInfo("Adding random item to the cart");
        if (itemTypeCount <= 0) {
            LoggerUtil.logWarning("No Count to be select");
            return this;
        }
        List<ItemData> itemDataList = getInventoryItems();
        if (itemTypeCount > itemDataList.size()) {
            LoggerUtil.logWarning("Item type count is more than selectable item");
        } else {
            Random random = new Random();
            for (int i = itemDataList.size(); i > itemTypeCount; i--) {
                int randomIndex = random.nextInt(itemDataList.size());
                itemDataList.remove(randomIndex);
            }
        }
        for (ItemData item : itemDataList) {
            addItemToCart(item.getName());
        }
        return this;
    }

    public InventoryPage addItemToCart(String itemName) {
        LoggerUtil.logInfo(String.format("Adding '%s' to the cart", itemName));
        List<WebElement> webElements = webDriver.findElements(inventoryItemElements);
        List<ItemData> itemDataList = getInventoryItems();
        if (inventoryCartItems.stream().filter(i -> i.getName().equals(itemName)).count() > 0) {
            LoggerUtil.logWarning(String.format("Not added since '%s' is already in the cart", itemName));
            return this;
        }
        for (WebElement e : webElements) {
            if (e.findElement(By.cssSelector("div[data-test='inventory-item-name']")).getText().equals(itemName)) {
                inventoryCartItems.add(itemDataList.stream().filter(i -> i.getName().equals(itemName)).findFirst().get());
                click(e.findElement(By.xpath(".//button[contains(normalize-space(),'Add to cart')]")), "Add to cart button");
                return this;
            }
        }
        LoggerUtil.logError("Cannot find any item name match with " + itemName);
        return this;
    }

    public InventoryPage removeItem(String itemName) {
        LoggerUtil.logInfo(String.format("Removing '%s' from the cart", itemName));
        List<WebElement> webElements = webDriver.findElements(inventoryItemElements);
        List<ItemData> itemDataList = getInventoryItems();
        if (inventoryCartItems.stream().filter(i -> i.getName().equals(itemName)).count() > 0) {
            for (WebElement e : webElements) {
                if (e.findElement(By.cssSelector("div[data-test='inventory-item-name']")).getText().equals(itemName)) {
                    inventoryCartItems.remove(itemDataList.stream().filter(i -> i.getName().equals(itemName)).findFirst().get());
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
