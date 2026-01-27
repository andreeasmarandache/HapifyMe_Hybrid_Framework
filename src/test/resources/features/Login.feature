Feature: Autentificarea utilizatorului în HapifyMe

  Background:
    Given I open the browser

  @ui @slow
  Scenario: Login standard prin Interfața Grafică
    Given I am on the login page
    When I enter valid credentials
    Then I should be redirected to the homepage

  @hybrid @fast
  Scenario: Login rapid folosind API (Hybrid Approach)
    Given I am logged in via API authentication
    Then I should be redirected to the homepage
