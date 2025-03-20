package page;

import dataclass.ItemData;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import page.common.BasePage;
import page.common.CartContainer;
import page.common.MenuBar;
import utility.DataUtil;
import utility.logger.AssertLogger;
import utility.logger.LoggerUtil;

import java.math.BigDecimal;
import java.util.List;

public class OverviewPage extends BasePage {

    private final By subTotalText = By.className("summary_subtotal_label");
    private final By taxText = By.className("summary_tax_label");
    private final By summaryTotal = By.className("summary_total_label");
    private final By cancelButton = By.id("cancel");
    private final By finishButton = By.id("finish");

    @Getter
    private MenuBar menuBar;
    @Getter
    private CartContainer cartContainer;

    public OverviewPage(WebDriver webDriver) {
        super(webDriver);
        verifyPageGenerated(finishButton);
        menuBar = new MenuBar(webDriver);
        cartContainer = new CartContainer(webDriver);
        menuBar.verifyTitleText("Checkout: Overview");
    }

    public String getSubTotalText() {
        return getText(subTotalText);
    }

    public BigDecimal getSubTotalValue() {
        return DataUtil.convertPriceStringToBigDecimal(getText(subTotalText, false).split(":")[1]);
    }

    public String getTaxText() {
        return getText(taxText);
    }

    public BigDecimal getTaxValue() {
        return DataUtil.convertPriceStringToBigDecimal(getText(taxText, false).split(":")[1]);
    }

    public String getSummaryTotalText() {
        return getText(summaryTotal);
    }

    public BigDecimal getSummaryTotalValue() {
        return DataUtil.convertPriceStringToBigDecimal(getText(summaryTotal, false).split(":")[1]);
    }

    public OverviewPage verifyCalculation() {
        LoggerUtil.logInfo("Verifying Calculation");
        List<ItemData> overviewItems = getCartContainer().getCartItems();
        if (overviewItems.isEmpty()) return this;
        BigDecimal subTotal = BigDecimal.valueOf(0);
        for (ItemData itemData : overviewItems) {
            subTotal = subTotal.add(itemData.getPriceBigDecimal());
        }
        AssertLogger.assertEquals(getSubTotalValue(), subTotal);
        AssertLogger.assertEquals(getSummaryTotalValue(), subTotal.add(getTaxValue()));
        return this;
    }

    public OverviewPage logSummary() {
        StringBuilder log = new StringBuilder("--------------------- Summary ---------------------\n");
        List<ItemData> overviewItems = getCartContainer().getCartItems();
        if (!overviewItems.isEmpty()) {
            int itemNumber = 1;
            for (ItemData itemData : overviewItems) {
                log.append(String.format("%s) %s | Price: %s\n", itemNumber++, itemData.getName(), itemData.getPrice()));
            }
        }
        log.append("Price Total\n");
        log.append(getSubTotalText().concat("\n"));
        log.append(getTaxText().concat("\n"));
        log.append(getSummaryTotalText().concat("\n"));
        LoggerUtil.logInfo(log.toString());
        return this;
    }

    public InventoryPage clickCancelButton() {
        click(cancelButton);
        return new InventoryPage(webDriver);
    }

    public CompletePage clickFinishButton() {
        click(finishButton);
        return new CompletePage(webDriver);
    }


}
