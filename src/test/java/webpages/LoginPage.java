package webpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utils.WebUtils;

/**
 * Created by Vijayakumar_G on 31-07-2017.
 */
public class LoginPage
{
    public void fillInUsernameAndPass(WebDriver driver, String usernmae, String pass)
    {
        WebUtils.waitForElementVisible(driver, By.name("j_username_"), 20);
        WebUtils.clearAndSendKeys(driver, By.name("j_username_"), usernmae);
        WebUtils.clearAndSendKeys(driver, By.name("j_password"), pass);
    }

    public AdminPage clickOnLoginButton(WebDriver driver)
    {
        WebUtils.waitForElementVisible(driver, By.id("submit-login"), 20);
        WebUtils.click(driver, By.id("submit-login"));
        if(WebUtils.getElementSize(driver, By.cssSelector(".form-alert.login-error")) == 0)
        {
            return PageFactory.initElements(driver, AdminPage.class);
        }
        return null;
    }
}
