package utility;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.logger.LoggerUtil;

import java.time.Duration;

public class WebDriverInstanceManager {
    private static ThreadLocal<WebDriver> threadWebDriver = new ThreadLocal<>();
    private static ThreadLocal<WebDriverWait> threadWait = new ThreadLocal<>();
    private static String webDriverType;
    private static int timeoutDuration = -1;
    private static boolean isDriverTypeSetup = false;

    private WebDriverInstanceManager() {

    }

    public static int getTimeoutDuration() {
        if (timeoutDuration < 0) {
            try {
                timeoutDuration = Integer.parseInt(ConfigurationManager.getProperty("timeout"));
            } catch (Exception e) {
                LoggerUtil.logInfo("Cannot read timeout from properties file, use default value '60'");
                timeoutDuration = 60;
            }
        }
        return timeoutDuration;
    }

    public static void setDriverType() {
        if (isDriverTypeSetup) {
            return;
        }
        webDriverType = ConfigurationManager.getProperty("browser").toLowerCase();
        switch (webDriverType) {
            case "edge":
                LoggerUtil.logInfo("Set Edge as the testing browser");
                WebDriverManager.edgedriver().setup();
                break;
            case "firefox":
                LoggerUtil.logInfo("Set Firefox as the testing browser");
                WebDriverManager.firefoxdriver().setup();
                break;
            case "chrome":
            default:
                LoggerUtil.logInfo("Set Chrome as the testing browser");
                WebDriverManager.chromedriver().setup();
                break;
        }
        isDriverTypeSetup = true;
    }

    public static WebDriver getDriver() {
        if (threadWebDriver.get() == null) {
            setDriverType();
            String headlessOption = "--headless";
            boolean headless = ConfigurationManager.getProperty("headless") != null &&
                    !ConfigurationManager.getProperty("headless").equalsIgnoreCase("false");
            switch (webDriverType) {
                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if(headless)edgeOptions.addArguments(headlessOption);
                    threadWebDriver.set(new EdgeDriver(edgeOptions));
                    break;
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if(headless)firefoxOptions.addArguments(headlessOption);
                    threadWebDriver.set(new FirefoxDriver(firefoxOptions));
                    break;
                case "chrome":
                default:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if(headless)chromeOptions.addArguments(headlessOption);
                    threadWebDriver.set(new ChromeDriver(chromeOptions));
                    break;
            }
        }
        threadWebDriver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(getTimeoutDuration()));
        return threadWebDriver.get();
    }

    public static WebDriverWait getWait() {
        if (threadWait.get() == null) {
            threadWait.set(new WebDriverWait(getDriver(), Duration.ofSeconds(getTimeoutDuration())));
        }
        return threadWait.get();
    }

    public static void quitDriver() {
        if (threadWebDriver.get() != null) {
            threadWebDriver.get().quit();
            threadWebDriver.remove();
            threadWait.remove();
        }
    }
}
