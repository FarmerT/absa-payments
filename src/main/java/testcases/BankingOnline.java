package testcases;

import core.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import tools.ExcelReader;
import tools.Report;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BankingOnline extends BaseClass {

    public static String ApplyNow() throws IOException {

        //test data storage for later utilization
        String[] testData = {"01234567891123", "Ben", "Obama", "briand@absa.africa", "0654282383"};
        ExcelReader.writeExcel("Sheet1", testData);

        boolean isTitleDisplayed, isError;
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


        //Navigate to Url
        SeleniumDriverInstance.navigate(pageObjects.AbsaPageObject.url());
        Report.info("Navigation to " + pageObjects.AbsaPageObject.url());

        //Validate Page
        SeleniumDriverInstance.validatePage("Absa | Banking for individuals and businesses");

        //Pop Handler
        SeleniumDriverInstance.popUpHandler();

        Report.stepPassedWithScreenshot("Absa Page");

        //Wait
        SeleniumDriverInstance.waitForElement(pageObjects.AbsaPageObject.searchIcon());

        //click search icon
        SeleniumDriverInstance.clickElement(pageObjects.AbsaPageObject.searchIcon());

        //Search for online banking
        SeleniumDriverInstance.enterText(pageObjects.AbsaPageObject.txtSearch(), "online banking");
        SeleniumDriverInstance.clickElement(pageObjects.AbsaPageObject.btnSearch());

        //Click Products checkbox
        SeleniumDriverInstance.waitForElement(pageObjects.AbsaPageObject.chkProduct());
        SeleniumDriverInstance.clickElement(pageObjects.AbsaPageObject.chkProduct());

        //Validate Checkbox click
        SeleniumDriverInstance.isSelected(pageObjects.AbsaPageObject.chkProduct());

        JavascriptExecutor js = (JavascriptExecutor) SeleniumDriverInstance.getDriver();
        js.executeScript("window.scrollBy(0,700)");

        //Click link
        SeleniumDriverInstance.clickElement(pageObjects.AbsaPageObject.lnk_IslamicPremiumBanking());

        //Switch Tab
        SeleniumDriverInstance.switchToTabOrWindow();

        //Validate Page Title
        isTitleDisplayed = SeleniumDriverInstance.getDriver().findElement(By.xpath(pageObjects.AbsaPageObject.lbl_IslamicBankingTitle())).isDisplayed();
        if (!isTitleDisplayed) {
            Report.testFailed("Islamic Premium Banking title does not exist");
        } else {
            Report.info("Islamic Premium Banking title exists");
        }

        //Click Apply
        SeleniumDriverInstance.scrollToElement(pageObjects.AbsaPageObject.div_element());
        SeleniumDriverInstance.clickElement(pageObjects.AbsaPageObject.btnApplyNow());

        //Switch Tab
        SeleniumDriverInstance.switchToTabOrWindow();

        //Type ID Number
        SeleniumDriverInstance.waitForElement(pageObjects.AbsaPageObject.txtIdNumber());
        SeleniumDriverInstance.enterText(pageObjects.AbsaPageObject.txtIdNumber(), testData[0]);

        //Type First Name
        SeleniumDriverInstance.enterText(pageObjects.AbsaPageObject.txtFirstName(), testData[1]);

        //Type Surname
        SeleniumDriverInstance.enterText(pageObjects.AbsaPageObject.txtSurname(), testData[2]);

        //Type Email
        SeleniumDriverInstance.enterText(pageObjects.AbsaPageObject.txtEmail(), testData[3]);

        //Validate Email using Regular Expression function
        String Email = "briand@absa.africa";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(testData[3]);

        if (matcher.find()) {
            Report.info("Email Address " + testData[3] + " successfully validated");

        } else {
            Report.testFailed("Invalid Email Address - " + testData[3]);
        }


        //Type Cell Number
        SeleniumDriverInstance.enterText(pageObjects.AbsaPageObject.txtCellNumber(), testData[4]);

        //Pause - test execution is too fast
        SeleniumDriverInstance.pause(3000);

        //Click Let's Continue button
        SeleniumDriverInstance.clickElement(pageObjects.AbsaPageObject.btnContinue());

        //Validate ID error message
        SeleniumDriverInstance.validateElementText(pageObjects.AbsaPageObject.lbl_ErrorMessage(), "ID number invalid");

        return Report.finaliseTest();
    }
}
