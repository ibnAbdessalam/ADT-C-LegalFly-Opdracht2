Feature: Manage Requests API Endpoint

  Scenario: Create a new request (POST)
    Given I use the API endpoint "/api/requests"
    When I send a POST request with the following data:
    """
    {
      "title": "API Contract",
      "description": "Created via API",
      "clientEmail": "api@test.com"
    }
    """
    Then the response status should be 201
    And the response should contain "API Contract"
    And the response should contain "PENDING"

  Scenario: Retrieve all requests (GET)
    Given I use the API endpoint "/api/requests"
    When I send a GET request
    Then the response status should be 200
    And the response should contain a list of requests

  Scenario: Update a request (PUT)
    Given I use the API endpoint "/api/requests"
    And I have a request with title "To Update"
    When I send a PUT request to update title to "Updated Title"
    Then the response status should be 200
    And the response should contain "Updated Title"

  Scenario: Delete a request (DELETE)
    Given I use the API endpoint "/api/requests"
    And I have a request with title "To Delete"
    When I send a DELETE request
    Then the response status should be 204

  Scenario: Try to create invalid request (Sad Path)
    Given I use the API endpoint "/api/requests"
    When I send a POST request with the following data:
    """
    {
      "title": "",
      "clientEmail": "test@test.com"
    }
    """

    Then the response status should be 400