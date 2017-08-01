package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

/**
 * Created by Vijayakumar_G on 31-07-2017.
 */
public class BrowserFactory
{
    private WebDriver driver;
    static String browser;

    public WebDriver getDriver()
    {
        browser =  new ReadProperties().readProperties("browser");
         switch (browser)
         {
             case "SAFARI":

                 driver = getSafariDriver();

             case "IE":

                 driver = getIeDriver();

             case "FIREFOX":

                 driver = getFirefoxDriver();

             default:

                 driver = getChromeDriver();
         }
         return driver;
    }

    private  WebDriver getChromeDriver()
    {
        System.out.println("launching chrome browser");
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY , "src/test/resources/Browsers/chromedriver.exe");
        DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
        chromeCapabilities.setCapability("nativeEvents", false);
        chromeCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
        chromeCapabilities.setCapability("ignoreProtectedModeSettings", true);
        chromeCapabilities.setCapability("ignoreZoomSetting", true);
        chromeCapabilities.setCapability("disable-popup-blocking", true);
        chromeCapabilities.setCapability("enablePersistentHover", true);
        chromeCapabilities.setCapability("browserstack.debug", true);
        chromeCapabilities.setCapability("browserstack.video", false);
        driver = new ChromeDriver(chromeCapabilities);
        driver.manage().window().maximize();
        return driver;
    }

    private  WebDriver getIeDriver()
    {
        System.out.println("launching IE browser");
        System.setProperty(InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY , "src/test/resources/Browsers/IEDriverServer.exe");
        System.setProperty(InternetExplorerDriverService.IE_DRIVER_LOGLEVEL_PROPERTY, "DEBUG");
        System.setProperty(InternetExplorerDriverService.IE_DRIVER_LOGFILE_PROPERTY, "build/reports/ie-driver-"+System.currentTimeMillis()+".log");
        DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
        ieCapabilities.setCapability("nativeEvents", false);
        ieCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
        ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
        ieCapabilities.setCapability("ignoreZoomSetting", true);
        ieCapabilities.setCapability("disable-popup-blocking", true);
        ieCapabilities.setCapability("enablePersistentHover", true);
        driver = new InternetExplorerDriver(ieCapabilities);
        driver.manage().window().fullscreen();
        return driver;
    }

    private  WebDriver getFirefoxDriver()
    {
        System.out.println("launching chrome browser");
        System.setProperty("webdriver.chrome.driver", "src/test/resources/Browsers");
        driver = new FirefoxDriver();
        return driver;
    }

    private  WebDriver getSafariDriver()
    {
        System.out.println("launching chrome browser");
        System.setProperty("webdriver.chrome.driver", "src/test/resources/Browsers");
        driver = new SafariDriver();
        return driver;
    }
}
