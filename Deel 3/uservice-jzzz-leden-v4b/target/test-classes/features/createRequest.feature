Feature: New request

  @UI
  Scenario: The user want to add a new request
    Given User is on the Request page


    When The user enters a title for the request
    And  The user enters a description for the request
    And  The user enters the mail of the client
    And  Enters the create button

    Then The request is shown in the list of requests
    And The request is shown in the list of requests
