package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import utils.ExtentReportManager;

public class Hooks {

    @Before
    public void setup() {
        // Start reporting
        ExtentReportManager.initReport("TrendyolTestReport.html");
        ExtentReportManager.createTest("Trendyol Test Case");
    }

    @After
    public void teardown() {
        // Reporting termination
        ExtentReportManager.flush();
    }
}
