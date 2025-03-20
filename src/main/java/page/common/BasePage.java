package page.common;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import utility.WebDriverInstanceManager;
import utility.logger.AssertLogger;
import utility.logger.LoggerUtil;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver webDriver;

    public BasePage(WebDriver webDriver) {
        this(webDriver, true);
    }

    public BasePage(WebDriver webDriver, boolean isLogReportHtml) {
        this.webDriver = webDriver;
        LoggerUtil.logInfo(String.format("----------------- Navigating to '%s' -----------------", this.getClass().getName()),
                isLogReportHtml);
    }

    public void verifyPageGenerated(By locator) {
        waitUntilElementVisible(locator);
        AssertLogger.assertTrue(getWebElement(locator).isDisplayed(),
                String.format("Verify if element '%s' is appear", getElementLocator(locator)));
    }

    public void verifyPageGenerated(By locator, boolean isLogReportHtml) {
        waitUntilElementVisible(locator);
        AssertLogger.assertTrue(getWebElement(locator).isDisplayed(),
                String.format("Verify if element '%s' is appear", getElementLocator(locator)),
                isLogReportHtml);
    }

    protected WebElement getWebElement(By locator) {
        return webDriver.findElement(locator);
    }

    protected List<WebElement> getWebElements(By locator) {
        return webDriver.findElements(locator);
    }

    protected void waitUntilElementVisible(By locator) {
        WebDriverInstanceManager.getWait().ignoring(NoSuchElementException.class)
                .pollingEvery(Duration.ofSeconds(1))
                .until(ExpectedConditions.visibilityOf(webDriver.findElement(locator)));
    }

    protected void waitUntilElementVisible(WebElement element) {
        WebDriverInstanceManager.getWait().ignoring(NoSuchElementException.class)
                .pollingEvery(Duration.ofSeconds(1))
                .until(ExpectedConditions.visibilityOf(element));
    }

    protected void click(By locator) {
        WebElement element = webDriver.findElement(locator);
        LoggerUtil.logInfo(String.format("Clicking element by %s", getElementLocator(locator)));
        element.click();
    }

    protected void click(WebElement element, String name) {
        LoggerUtil.logInfo(String.format("Clicking element '%s'", name));
        element.click();
    }

    protected void typeText(By locator, String text) {
        WebElement element = webDriver.findElement(locator);
        LoggerUtil.logInfo(String.format("Typing '%s' to element by %s", text, getElementLocator(locator)));
        element.sendKeys(text);
    }

    protected void sendKeys(By locator, CharSequence... keys) {
        WebElement element = webDriver.findElement(locator);
        LoggerUtil.logInfo(String.format("Sending Keys '%s' to element by %s", keys, getElementLocator(locator)));
        element.sendKeys(keys);
    }

    protected void selectByVisibleText(By locator, String text) {
        LoggerUtil.logInfo(String.format("Selecting '%s' from dropdown element by '%s'", text, getElementLocator(locator)));
        Select select = new Select(webDriver.findElement(locator));
        select.selectByVisibleText(text);
    }

    protected String getSelectedOption(By locator) {
        LoggerUtil.logInfo(String.format("Getting selected option by %s", getElementLocator(locator)));
        Select select = new Select(webDriver.findElement(locator));
        return select.getFirstSelectedOption().getText();
    }

    protected String getText(By locator) {
        return getText(locator, true);
    }

    protected String getText(By locator, boolean isLog) {
        WebElement element = webDriver.findElement(locator);
        if (isLog) LoggerUtil.logInfo(String.format("Getting element text by %s", getElementLocator(locator)));
        return element.getText();
    }

    private static String getElementLocator(By locator) {
        if (locator instanceof By.ById) {
            return "ID: " + ((By.ById) locator).toString().split(":")[1].trim();
        } else if (locator instanceof By.ByName) {
            return "Name: " + ((By.ByName) locator).toString().split(":")[1].trim();
        } else if (locator instanceof By.ByClassName) {
            return "Class: " + ((By.ByClassName) locator).toString().split(":")[1].trim();
        } else if (locator instanceof By.ByXPath) {
            return "XPath: " + ((By.ByXPath) locator).toString().split(":")[1].trim();
        } else if (locator instanceof By.ByCssSelector) {
            return "CSS Selector: " + ((By.ByCssSelector) locator).toString().split(":")[1].trim();
        } else {
            return "Unknown Element";
        }
    }
}
