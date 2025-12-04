Feature: Manage Requests via API

  Scenario: Add a new request via the API
    Given I use the API endpoint "/api/requests"
    When I send a POST request with the following data:
      | title       | Contract Review        |
      | description | Check NDA agreement    |
      | clientEmail | client@example.com     |
    Then the response status should be 201
    And the response should contain "id"
    And the response should contain "Contract Review"

  Scenario: Retrieve all requests
    Given I use the API endpoint "/api/requests"
    When I send a GET request
    Then the response status should be 200
    And the response should contain a list of requests