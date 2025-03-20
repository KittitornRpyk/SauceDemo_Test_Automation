package utility.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static utility.ExtentReportListener.test;

public class LoggerUtil {

    private static final Logger logger = LogManager.getLogger();

    private LoggerUtil() {

    }

    public static void logInfo(String message) {
        logInfo(message, true);
    }

    public static void logInfo(String message, boolean logReportHtml) {
        String formattedMessage = message.replace("\n", "<br>");
        if (test != null && logReportHtml) {
            test.info(formattedMessage.contains("Verify") || formattedMessage.contains("Assert") ?
                    String.format("<div style=\"color: #fcb103\">%s</div>", formattedMessage) :
                    formattedMessage);
        }
        logger.info(message);
    }

    public static void logAssertPassed(String message) {
        logAssertPassed(message, true);
    }

    public static void logAssertPassed(String message, boolean isLogReportHtml) {
        String formattedMessage = message.replace("\n", "<br>");
        if (test != null && isLogReportHtml) {
            test.info(String.format("<div style=\"color: #15ad22\">%s</div>", formattedMessage));
        }
        logger.info(message);
    }

    public static void logAssertFailed(String message) {
        logAssertFailed(message, true);
    }

    public static void logAssertFailed(String message, boolean isLogReportHtml) {
        String formattedMessage = message.replace("\n", "<br>");
        if (test != null && isLogReportHtml) {
            test.info(String.format("<div style=\"color: #ff0000\">%s</div>", formattedMessage));
        }
        logger.info(message);
    }

    public static void logWarning(String message) {
        String formattedMessage = message.replace("\n", "<br>");
        if (test != null) {
            test.warning(formattedMessage);
        }
        logger.warn(message);
    }

    public static void logError(String message) {
        String formattedMessage = message.replace("\n", "<br>");
        if (test != null) {
            test.fail(formattedMessage);
        }
        logger.error(message);
    }

    public static void logDebug(String message) {
        String formattedMessage = message.replace("\n", "<br>");
        if (test != null) {
            test.info("DEBUG: " + formattedMessage);
        }
        logger.debug(message);
    }

    public static void logFatal(String message) {
        String formattedMessage = message.replace("\n", "<br>");
        if (test != null) {
            test.fail("FATAL: " + formattedMessage);
        }
        logger.error("FATAL: {}", message);
    }
}