Feature: Manage Requests Service Logic

  Background:
    Given there are no requests

  Scenario: Create and Read a valid request
    When I create a request with title "Contract Review"
    Then the created request should have status "PENDING"
    And the request list should contain 1 request

  Scenario: Create a request without a title
    When I try to create a request with an empty title
    Then I should receive an error message "Request title cannot be empty"

  Scenario: Update a request status
    Given I create a request with title "Status Test"
    When I update the request status to "APPROVED"
    Then the request should have status "APPROVED"

  Scenario: Delete a request
    Given I create a request with title "Delete Me"
    When I delete the request
    Then the request should no longer exist