package baseclass;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import page.LoginPage;
import utility.ConfigurationManager;
import utility.WebDriverInstanceManager;

public class UIBaseTest extends BaseTest {
    public LoginPage loginPage;

    @BeforeMethod
    public void beforeUIMethod(ITestContext context) {
        WebDriverInstanceManager.getDriver().get(ConfigurationManager.getProperty("sauceDemo.endpoint.fe"));
        context.setAttribute("driver", WebDriverInstanceManager.getDriver());
        WebDriverInstanceManager.getDriver().manage().window().maximize();
        loginPage = new LoginPage(WebDriverInstanceManager.getDriver(), false);
    }

    @AfterMethod
    public void afterUIMethod() {
        WebDriverInstanceManager.quitDriver();
    }

}
