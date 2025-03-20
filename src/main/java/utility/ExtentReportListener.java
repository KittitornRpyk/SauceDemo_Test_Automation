package utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import dataclass.LoginData;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportListener implements ITestListener {
    private static ExtentReports extent;
    private static String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

    private static ExtentReports getExtentInstance() {
        if (extent == null) {
            String reportPath = String.format("logs/report_%s/testResultReport_%s.html", timestamp, timestamp);

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setReportName("Automation Test Report");
            sparkReporter.config().setDocumentTitle("Test Execution Report");
            sparkReporter.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
        }
        return extent;
    }

    public static ExtentTest test;

    @Override
    public void onTestStart(ITestResult result) {
        Object[] parameters = result.getParameters();
        String subject = result.getMethod().getMethodName();
        if (parameters.length > 0 && parameters[0] instanceof LoginData) {
            LoginData loginData = (LoginData) parameters[0];
            subject = String.format("%s(Username: %s)", subject, loginData.getUsername());
        }
        test = getExtentInstance().createTest(subject);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            WebDriver driver = (WebDriver) result.getTestContext().getAttribute("driver");
            String screenshotPath = captureScreenshot(driver).replace("logs/report_" + timestamp + "/", "");
            test.fail("Test Failed: " + result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (Exception e) {
            test.fail("Test Failed" + result.getThrowable());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.skip("Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        getExtentInstance().flush();
    }

    private String captureScreenshot(WebDriver driver) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destination = String.format("logs/report_%s/screenshot_%s.png", timestamp, System.currentTimeMillis());
        try {
            FileHandler.copy(source, new File(destination));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destination;
    }
}
