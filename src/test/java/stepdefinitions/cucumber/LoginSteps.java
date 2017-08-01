package stepdefinitions.cucumber;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import utils.BrowserFactory;
import utils.WebUtils;
import webpages.AdminPage;
import webpages.LoginPage;

/**
 * Created by Vijayakumar_G on 31-07-2017.
 */
public class LoginSteps
{
    private WebDriver driver;
    private LoginPage loginPage;
    private AdminPage adminPage;

    @Before
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

    @Given("^user is on facebook login page$")
    public void user_is_on_facebook_login_page() throws Throwable
    {
        loginPage = WebUtils.navigateToLoginPage(driver);
        Assert.assertTrue("user should be onLILA login page", loginPage !=  null);
    }

    @When("^user enter username \"([^\"]*)\" and pass \"([^\"]*)\"$")
    public void user_enter_username_and_pass(String username, String pass) throws Throwable
    {
        loginPage.fillInUsernameAndPass(driver, username, pass);
    }

    @When("^click on login button$")
    public void click_on_login_button() throws Throwable
    {
        adminPage = loginPage.clickOnLoginButton(driver);
    }

    @Then("^facebook home page should be displayed$")
    public void facebook_home_page_should_be_displayed() throws Throwable
    {
        Assert.assertTrue("facebook home page should be displayed", adminPage == null);
    }

    @After
    public void closeBrowser(Scenario scenario)
    {
        if (scenario.isFailed())
        {
            byte[] saveScreenshotAsByte = WebUtils.saveScreenshotAsByte(driver);
            scenario.embed(saveScreenshotAsByte, "image/png");
        }
        driver.close();

    }
}
