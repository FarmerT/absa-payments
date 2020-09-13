package tools;

import core.BaseClass;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//All Global methods are stored here
public class SeleniumDriver extends BaseClass {

    private WebDriver driver;
    private static int screenshotCounter = 0;

    //Browser Types to use
    public enum BrowserType {CHROME, IE, FIREFOX}

    private BrowserType currentBrowser;

    public SeleniumDriver(BrowserType browser) {
        this.currentBrowser = browser;
        launchDriver();
    }


    //Start Driver using WebDriver Manager Reference https://github.com/bonigarcia/webdrivermanager
    public boolean launchDriver() {
        switch (this.currentBrowser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                this.driver = new ChromeDriver();
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                this.driver = new FirefoxDriver();
                break;
            default:
                break;
        }

        this.driver.manage().window().maximize();
        return true;
    }

    //Get Driver
    public WebDriver getDriver() {
        return driver;
    }

    //Get current browser
    public BrowserType getCurrentBrowser() {
        return currentBrowser;
    }

    //close driver
    public boolean shutDown() {
        try {
            this.driver.close();
            this.driver.quit();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Navigate Function
    public boolean navigate(String url) {
        try {
            this.driver.navigate().to(url);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Pop up function
    public boolean popUpHandler() {
        try {

            Alert alert = driver.switchTo().alert();
            alert.getText();
            alert.accept();

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return false;
        }
    }

    //Switch Tab or Window function
    public boolean switchToTabOrWindow() {
        try {
            String winHandleBefore = SeleniumDriverInstance.driver.getWindowHandle();
            for (String winHandle : SeleniumDriverInstance.driver.getWindowHandles()) {
                SeleniumDriverInstance.driver.switchTo().window(winHandle);
            }
        } catch (Exception e) {
            Report.testFailed("Could not switch to new tab " + e.getMessage());

            return false;
        }
        Report.info("Switched to window " + driver.getTitle());
        return true;
    }

    //Scroll to Element Function
    public boolean scrollToElement(By elementXpath) {
        try {
            WebElement element = driver.findElement(elementXpath);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            return true;
        } catch (Exception e) {
            Report.testFailed("Error scrolling to element - " + elementXpath + " - " + e.getStackTrace());
            return false;
        }

    }

    //Validate page function
    public void validatePage(String title) {
        try {
            if (driver.getTitle().equalsIgnoreCase(title)) {
                Report.info("Page Successfully Validated");
            } else {
                Report.testFailed("Failed to Validate Page");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    //Click Function
    public boolean clickElement(By selector) {
        try {
            waitForElement(selector);
            WebDriverWait wait = new WebDriverWait(this.driver, 1);
            wait.until(ExpectedConditions.elementToBeClickable(selector));
            WebElement elementToClick = this.driver.findElement(selector);
            elementToClick.click();

            return true;
        } catch (Exception e) {
            Report.testFailed("Failed to click " + selector + " " + e.getMessage());
            return false;
        }
    }

    //Type Function
    public boolean enterText(By selector, String textToEnter) {
        try {
            waitForElement(selector);
            WebDriverWait wait = new WebDriverWait(this.driver, 1);
            wait.until(ExpectedConditions.elementToBeClickable(selector));
            WebElement elementToClick = this.driver.findElement(selector);
            elementToClick.clear();
            elementToClick.sendKeys(textToEnter);

            return true;
        } catch (Exception e) {
            Report.testFailed("Failed to Type " + textToEnter + " " + e.getMessage());
            return false;
        }
    }

    //Clear Text Function
    public boolean clearText(By selector) {
        try {
            waitForElement(selector);
            WebDriverWait wait = new WebDriverWait(this.driver, 1);
            wait.until(ExpectedConditions.elementToBeClickable(selector));
            WebElement elementToClick = this.driver.findElement(selector);
            elementToClick.clear();

            return true;
        } catch (Exception e) {
            Report.testFailed("Failed to Clear text" + e.getMessage());
            return false;
        }
    }

    //Retrieve Text Function
    public String getTextFromElement(By selector) {
        try {
            waitForElement(selector);
            WebDriverWait wait = new WebDriverWait(this.driver, 1);
            wait.until(ExpectedConditions.elementToBeClickable(selector));
            WebElement element = this.driver.findElement(selector);
            return element.getText();
        } catch (Exception e) {
            return Report.testFailed("Failed to get text " + e.getMessage());
        }
    }

    //Validate Text Function
    public boolean validateElementText(By selector, String textToValidate) {
        try {
            waitForElement(selector);
            WebDriverWait wait = new WebDriverWait(this.driver, 1);
            wait.until(ExpectedConditions.elementToBeClickable(selector));
            WebElement element = this.driver.findElement(selector);
            Report.stepPassed(element.getText() + " successfully validated against " + textToValidate);
            return element.getText().equals(textToValidate);
        } catch (Exception e) {
            Report.testFailed("Failed to Validate" + textToValidate + " " + e.getMessage());
            return false;
        }
    }

    //Checkbox validation check Function
    public boolean isSelected(By selector) {
        try {
            waitForElement(selector);
            WebDriverWait wait = new WebDriverWait(this.driver, 1);
            wait.until(ExpectedConditions.elementToBeClickable(selector));
            WebElement checkBox = this.driver.findElement(selector);
            if (checkBox.isSelected()) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            Report.testFailed("Checkbox validation failed " + e.getMessage());

            return false;
        }
    }

    //Validate Email using regular expression function
    public boolean ValidateEmailAddress(By selector, String inputEmail) {
        try {

            String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(inputEmail);

            if (!matcher.find()) {

                Report.testFailed("Invalid Email Address - " + inputEmail);
                return false;
            } else {
                Report.info(inputEmail + " - Email Address successfully validated");
                return true;
            }

        } catch (Exception e) {
            Report.testFailed("Invalid Email Address - " + inputEmail);

            return false;
        }
    }

    //Wait Function
    public boolean waitForElement(By selector) {
        boolean found = false;
        int counter = 0;
        try {
            while (!found && counter < 30) {
                try {
                    WebDriverWait wait = new WebDriverWait(this.driver, 1);
                    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(selector));
                    found = true;
                } catch (Exception e) {
                    counter++;
                    pause(1000);
                }
            }
            return found;
        } catch (Exception e) {
            Report.testFailed("Failed to Wait for " + selector + " " + e.getMessage());
            return false;
        }
    }

    //Pause Function
    public void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //Screenshot Function
    public String takeScreenshot(boolean testStatus) {
        screenshotCounter++;
        StringBuilder imagePathBuilder = new StringBuilder();
        StringBuilder relativePathBuilder = new StringBuilder();

        try {
            imagePathBuilder.append(getReportDirectory());
            relativePathBuilder.append("Screenshots\\");
            new File(imagePathBuilder.toString() + (relativePathBuilder).toString()).mkdirs();

            relativePathBuilder.append(screenshotCounter + "_");
            if (testStatus) {
                relativePathBuilder.append("PASSED");
            } else {
                relativePathBuilder.append("FAILED");
            }
            relativePathBuilder.append(".png");

            File screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(imagePathBuilder.append(relativePathBuilder).toString()));

            return "./" + relativePathBuilder.toString();
        } catch (Exception e) {
            return null;
        }
    }

}


