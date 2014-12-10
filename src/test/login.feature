Feature: Login

  Scenario: Login user
    Given  login to app
    When login with e-mail "sdasdas" and api-key "asdasdasd"
    Then A user loginned