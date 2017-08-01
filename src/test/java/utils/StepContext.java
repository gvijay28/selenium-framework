package utils;

import org.openqa.selenium.WebDriver;

/**
 * Created by Vijayakumar_G on 31-07-2017.
 */
public class StepContext
{
    static public WebDriver driver;

    /*public static WebDriver getDriver()
    {
        try
        {
           return new BrowserFactory().getDriver();
        }
        catch (WebDriverException e)
        {
            e.printStackTrace();
        }
        return null;
    }*/



   /* public WebDriver getDriver()
    {
        return driver;
    }*/

    /*@After
    public void closeBrowser(Scenario scenario)
    {
        if (scenario.isFailed())
        {
            byte[] saveScreenshotAsByte = WebUtils.saveScreenshotAsByte(driver);
            scenario.embed(saveScreenshotAsByte, "image/png");

            *//*final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png"); //stick it in the report*//*
        }
        driver.close();

    }*/
}
