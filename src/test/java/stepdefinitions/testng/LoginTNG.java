package stepdefinitions.testng;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.BrowserFactory;
import utils.WebUtils;
import webpages.AdminPage;
import webpages.LoginPage;

/**
 * Created by Vijayakumar_G on 31-07-2017.
 */
public class LoginTNG
{
    private WebDriver driver;
    private LoginPage loginPage;
    private AdminPage adminPage;

    @BeforeClass
    public void getDriver()
    {
        try
        {
            driver =  new BrowserFactory().getDriver();
        }
        catch (WebDriverException e)
        {
            e.printStackTrace();
        }
    }

    @Test(description = "0001" )
    public void user_is_on_facebook_login_page() throws Throwable
    {
        loginPage = WebUtils.navigateToLoginPage(driver);
        Assert.assertTrue("user should be on facebook login page", loginPage !=  null);
    }

    @Test(priority = 2)
    public void user_enter_username_and_pass() throws Throwable
    {
        loginPage.fillInUsernameAndPass(driver, "testuser", "password");
    }

    @Test(priority = 3)
    public void click_on_login_button() throws Throwable
    {
        adminPage = loginPage.clickOnLoginButton(driver);
    }

    @Test(priority = 4)
    public void facebook_home_page_should_be_displayed() throws Throwable
    {
        Assert.assertTrue("facebook home page should be displayed", adminPage != null);
    }

    @AfterTest
    public void closeBrowser()
    {
        driver.quit();
    }
}
