$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("Tests/Cucumber/Login.feature");
formatter.feature({
  "line": 2,
  "name": "login to web applicatuion",
  "description": "\r\nAs a user\r\nI want to automate My App\r\nSo that I can reduce time and effort",
  "id": "login-to-web-applicatuion",
  "keyword": "Feature",
  "tags": [
    {
      "line": 1,
      "name": "@Login"
    }
  ]
});
formatter.before({
  "duration": 4145325353,
  "status": "passed"
});
formatter.scenario({
  "line": 9,
  "name": "login",
  "description": "",
  "id": "login-to-web-applicatuion;login",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 8,
      "name": "@Test01"
    }
  ]
});
formatter.step({
  "line": 10,
  "name": "user is on LILA login page",
  "keyword": "Given "
});
formatter.step({
  "line": 11,
  "name": "user enter username \"physicianusera3\" and pass \"password@1234\"",
  "keyword": "When "
});
formatter.step({
  "line": 12,
  "name": "click on login button",
  "keyword": "And "
});
formatter.step({
  "line": 13,
  "name": "LILA home page should be displayed",
  "keyword": "Then "
});
formatter.match({
  "location": "LoginSteps.user_is_on_LILA_login_page()"
});
formatter.result({
  "duration": 6822362909,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "physicianusera3",
      "offset": 21
    },
    {
      "val": "password@1234",
      "offset": 48
    }
  ],
  "location": "LoginSteps.user_enter_username_and_pass(String,String)"
});
formatter.result({
  "duration": 322583604,
  "status": "passed"
});
formatter.match({
  "location": "LoginSteps.click_on_login_button()"
});
formatter.result({
  "duration": 2926748533,
  "status": "passed"
});
formatter.match({
  "location": "LoginSteps.lila_home_page_should_be_displayed()"
});
formatter.result({
  "duration": 331044,
  "error_message": "java.lang.AssertionError: LILA home page should be displayed\r\n\tat org.junit.Assert.fail(Assert.java:88)\r\n\tat org.junit.Assert.assertTrue(Assert.java:41)\r\n\tat stepdefinitions.cucumber.LoginSteps.lila_home_page_should_be_displayed(LoginSteps.java:62)\r\n\tat ✽.Then LILA home page should be displayed(Tests/Cucumber/Login.feature:13)\r\n",
  "status": "failed"
});
formatter.embedding("image/png", "embedded0.png");
formatter.after({
  "duration": 180717531,
  "status": "passed"
});
});