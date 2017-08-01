package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import webpages.LoginPage;


import java.util.List;

/**
 * Created by Vijayakumar_G on 31-07-2017.
 */
public class WebUtils
{

    private static String APP_URL = new ReadProperties().readProperties("app_url");

    public static LoginPage navigateToLoginPage(WebDriver driver)
    {
        getURL(driver, APP_URL);
        return PageFactory.initElements(driver, LoginPage.class);
    }

    public static void getURL(WebDriver driver, String URL)
    {
        driver.get(URL);
    }

    public static byte[] saveScreenshotAsByte(WebDriver driver) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        byte[] screenshotAs = ts.getScreenshotAs(OutputType.BYTES);
        return  screenshotAs;
    }

    public static void waitForElementVisible(WebDriver driver, By by, int time)
    {
        WebDriverWait wait = new WebDriverWait(driver, time);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static void waitForElementInVisible(WebDriver driver, By by, int time)
    {
        WebDriverWait wait = new WebDriverWait(driver, time);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public static void click(WebDriver driver, By by)
    {
        waitForElementVisible(driver, by, 20);
        driver.findElement(by).click();
    }

    public static void click(WebDriver driver, By parent, By child)
    {
        WebElement element =  driver.findElement(parent);
        element.findElement(child).click();
    }

    public static void clearAndSendKeys(WebDriver driver, By by, String value)
    {
        driver.findElement(by).clear();
        driver.findElement(by).sendKeys(value);
    }

    public static List<WebElement> getWebElementList(WebDriver driver, By parent)
    {
        return driver.findElements(parent);

    }

    public static WebElement getWebElement(WebDriver driver, By by)
    {
        return driver.findElement(by);
    }

    public static void selectCheckBox(WebDriver driver, By by)
    {
        if(!driver.findElement(by).isSelected())
        {
            driver.findElement(by).click();
        }
    }

    public static void selectCheckBox(WebDriver driver, By parent, By child)
    {
        WebElement element = getWebElement(driver, parent);
        if(!element.findElement(child).isSelected())
        {
            element.click();
        }
    }

    public static void unSelectCheckBox(WebDriver driver, By by)
    {
        if(driver.findElement(by).isSelected())
        {
            driver.findElement(by).click();
        }
    }

    public static void unSelectCheckBox(WebDriver driver, By parent, By child)
    {
        WebElement element = getWebElement(driver, parent);
        if(element.findElement(child).isSelected())
        {
            element.click();
        }
    }

    public static void selectFromDropDown(WebDriver driver, By parent, String value)
    {
        WebElement element = getWebElement(driver, parent);
        Select oSelect = new Select(element);
        oSelect.selectByVisibleText(value);
    }

    public static boolean isElementDisplayed(WebDriver driver, By by)
    {
        return driver.findElement(by).isDisplayed();
    }

    public static boolean isElementSelected(WebDriver driver, By by)
    {
        return driver.findElement(by).isSelected();
    }

    public static boolean isElementEnabled(WebDriver driver, By by)
    {
        return driver.findElement(by).isEnabled();
    }

    public static boolean isAlertPresent(WebDriver driver, By by)
    {
        try
        {
            driver.switchTo().alert();
            return true;
        }
        catch (NoAlertPresentException Ex)
        {
            return false;
        }

    }

    public static String getElementText(WebDriver driver, By by)
    {
        return driver.findElement(by).getText();
    }

    public static void switchToFrame(WebDriver driver, int frameIndex)
    {
        driver.switchTo().frame(frameIndex);
    }

    public static int getElementSize(WebDriver driver, By by)
    {
        return driver.findElements(by).size();
    }
}
