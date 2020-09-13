package tools;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import core.BaseClass;
import java.io.File;

public class Report extends BaseClass {

    private static ExtentReports report;
    private static ExtentTest currentTest;
    private static String reportFileName;
    private static String reportPath;

    private static void setUp() {


        reportPath = System.getProperty("user.dir") + "\\Reports\\" + TestName + "__" + getCurrentDateTime() + "\\";
        setReportDirectory(reportPath);

        reportFileName = (getReportDirectory() + "extentReport.html");
        new File(getReportDirectory()).mkdirs();

        report = new ExtentReports();
        ExtentHtmlReporter html = new ExtentHtmlReporter(reportFileName);

        report.attachReporter(html);
        //report.setSystemInfo("Host Name", "Selenium Testing Tool");
        report.setSystemInfo("User Name", System.getProperty("user.name"));

        html.config().setDocumentTitle(reportFileName);
        html.config().setReportName(TestName);
        html.config().setTheme(Theme.STANDARD);

        report.setAnalysisStrategy(AnalysisStrategy.TEST);
    }

    public static void createTest() {
        try {
            if (report == null) setUp();
            if (currentTest == null || !currentTest.getModel().getName().equals(TestName)) {
                currentTest = report.createTest(TestName);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void stepPassed(String message) {
        if (currentTest == null) createTest();

        currentTest.log(Status.PASS, MarkupHelper.createLabel(currentTest.getModel().getName() + " - Test Case PASSED", ExtentColor.GREEN));
        System.out.println("[SUCCESS] - " + message);

        report.flush();
    }

    public static void info(String message) {
        if (currentTest == null) createTest();
        currentTest.pass(message);
        System.out.println("[INFO] - " + message);

        report.flush();
    }

    public static void warning(String message) {
        if (currentTest == null) createTest();
        currentTest.pass(message);
        System.out.println("[WARNING] - " + message);

        report.flush();
    }

    public static String testFailed(String message) {
        try {
            currentTest.fail(message, MediaEntityBuilder.createScreenCaptureFromPath(SeleniumDriverInstance.takeScreenshot(true)).build());
        } catch (Exception e) {
            currentTest.pass(message + "- screenshot capture failure");
        }
        return message;
    }


    public static void stepPassedWithScreenshot(String message) {
        if (currentTest == null) createTest();
        try {
            currentTest.pass(message, MediaEntityBuilder.createScreenCaptureFromPath(SeleniumDriverInstance.takeScreenshot(true)).build());

        } catch (Exception e) {
            currentTest.pass(message + "- screenshot capture failure");
        }

        System.out.println(message);
        report.flush();
    }

    public static String finaliseTest() {
        if (currentTest == null) createTest();
        try {
            currentTest.pass("Test Complete!", MediaEntityBuilder.createScreenCaptureFromPath(SeleniumDriverInstance.takeScreenshot(true)).build());


        } catch (Exception e) {
            currentTest.pass("Test Completed!");
        }
        System.out.println("[COMPLETE] - Test Complete");

        report.flush();

        return null;
    }
}
