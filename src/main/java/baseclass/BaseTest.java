package baseclass;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utility.logger.LoggerUtil;

import java.lang.reflect.Method;

public class BaseTest {

    @BeforeMethod()
    public void beforeMethod(ITestResult result) {
        LoggerUtil.logInfo("============================ Starting test case ============================",false);
        LoggerUtil.logInfo(String.format("Test Name: %s", getTestName(result.getMethod().getConstructorOrMethod().getMethod())),false);
        LoggerUtil.logInfo(String.format("Description: %s", result.getMethod().getDescription()),false);
        LoggerUtil.logInfo(String.format("Method Name: %s", result.getMethod().getMethodName()),false);
    }

    @AfterMethod()
    public void afterMethod() {
        LoggerUtil.logInfo("============================ End test case ============================");
    }

    private String getTestName(Method method) {
        if (method.isAnnotationPresent(Test.class)) {
            Test testAnnotation = method.getAnnotation(Test.class);
            return testAnnotation.testName().isEmpty() ? null : testAnnotation.testName();
        }
        return null;
    }
}
