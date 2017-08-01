@Login
Feature: login to web applicatuion

  As a user
  I want to automate My App
  So that I can reduce time and effort

  @Test01
  Scenario: login
    Given user is on LILA login page
    When user enter username "physicianusera3" and pass "password@1234"
    And click on login button
    Then LILA home page should be displayed