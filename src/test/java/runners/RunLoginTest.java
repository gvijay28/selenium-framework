package runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by Vijayakumar_G on 31-07-2017.
 */

@RunWith(Cucumber.class) @CucumberOptions(format = { "pretty", "html:build/reports/cucumber/Login",
        "json:build/reports/cucumber/cucumber.json" },
        tags = { "@Login" }, glue = {
        "stepdefinitions/cucumber",
        "util" }, features = "classpath:Tests/Cucumber/")

public class RunLoginTest {
}
