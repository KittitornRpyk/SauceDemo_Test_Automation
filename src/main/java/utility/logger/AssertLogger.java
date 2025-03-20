package utility.logger;

import org.testng.Assert;

public class AssertLogger {

    private static final String ASSERT_PASSED_MESSAGE = "Assert Result: Passed";
    private static final String ASSERT_FAILED_MESSAGE = "Assert Result: Failed";

    private AssertLogger() {

    }

    public static void assertEquals(Object actual, Object expected) {
        LoggerUtil.logInfo(String.format("Asserting Equals%nActual: %s%nExpected: %s", actual, expected));
        if (actual.equals(expected)) {
            LoggerUtil.logAssertPassed(ASSERT_PASSED_MESSAGE);
        } else {
            LoggerUtil.logAssertFailed(ASSERT_FAILED_MESSAGE);
        }
        Assert.assertEquals(actual, expected);
    }

    public static void assertTrue(boolean condition, String message) {
        assertTrue(condition, message, true);
    }

    public static void assertTrue(boolean condition, String message, boolean isLogReportHtml) {
        LoggerUtil.logInfo("Asserting True: " + message, isLogReportHtml);
        if (condition) {
            LoggerUtil.logAssertPassed(ASSERT_PASSED_MESSAGE, isLogReportHtml);
        } else {
            LoggerUtil.logAssertFailed(ASSERT_FAILED_MESSAGE, isLogReportHtml);
        }
        Assert.assertTrue(condition, message);
    }

    public static void assertFalse(boolean condition, String message) {
        LoggerUtil.logInfo("Asserting False: " + message);
        if (!condition) {
            LoggerUtil.logAssertPassed(ASSERT_PASSED_MESSAGE);
        } else {
            LoggerUtil.logAssertFailed(ASSERT_FAILED_MESSAGE);
        }
        Assert.assertFalse(condition, message);
    }
}