@Login
Feature: login to web applicatuion

  As a user
  I want to automate My App
  So that I can reduce time and effort

  @Test01
  Scenario: login
    Given user is on facebook login page
    When user enter username "testuser" and pass "password"
    And click on login button
    Then facebook home page should be displayed