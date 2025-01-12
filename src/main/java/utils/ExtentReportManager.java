package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.io.File;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ExtentTest test;


    public static void initReport(String reportName) {
        String path = System.getProperty("user.dir") + File.separator + "target" + File.separator + "ExtentReports" + File.separator + reportName;
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(path); // Saves the report to the specified location
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    public static void createTest(String testName) {
        test = extent.createTest(testName);
    }

    public static ExtentTest getTest() {
        return test;
    }

    public static void flush() {
        if (extent != null) {
            extent.flush(); // Report Save
        }
    }
}
