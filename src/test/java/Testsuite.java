import core.BaseClass;
import org.junit.*;
import org.junit.rules.TestName;
import org.testng.annotations.Parameters;
import testcases.BankingOnline;
import tools.Report;
import tools.SeleniumDriver;

import java.io.IOException;

public class Testsuite extends BaseClass {

    @Rule
    public TestName name = new TestName();

    @Before
    public void initialize() {
        //First close Excel before launching driver to avoid file in used error when excel is open
        try {
            Runtime.getRuntime().exec("taskkill /F /IM EXCEL.EXE");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //launch browser and create report
        TestName = name.getMethodName();
        Report.createTest();
        SeleniumDriverInstance = new SeleniumDriver(SeleniumDriver.BrowserType.CHROME);
        Assert.assertTrue("Driver successfully found", SeleniumDriverInstance.getDriver() != null);
        Report.info(SeleniumDriverInstance.getCurrentBrowser() + " Successfully Launched");
    }


    @Test
    @Parameters ({"fruit"})
    public void absaPayments() throws IOException {
        String result = BankingOnline.ApplyNow();
    }

    @After
    public void cleanUp() {
        SeleniumDriverInstance.shutDown();
    }

}
