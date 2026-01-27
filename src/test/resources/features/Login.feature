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

  @ui @negative
  Scenario Outline: Login eșuat cu credențiale invalide
    Given I am on the login page
    When I attempt to login with "<email>" and "<password>"
    Then I should see the error message "<errorMessage>"

    Examples:
      | email                      | password      | errorMessage                       |
      | user_inexistent@y.com      | pass123       | Email or password was incorrect    |
      | student_qa@y.com           | gresita       | Email or password was incorrect    |
