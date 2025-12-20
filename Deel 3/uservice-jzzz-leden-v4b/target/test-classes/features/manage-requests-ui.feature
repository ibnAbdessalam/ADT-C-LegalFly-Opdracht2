@UI
Feature: Manage Requests via UI

  Scenario: User creates and then deletes a request
    Given I am on the LegalFly application

    # Create
    When I enter "Visual Test Request" in the title field
    And I enter "ui@test.com" in the email field
    And I click the Create button
    Then I should see "Visual Test Request" in the list of requests

    # Delete
    When I click the Delete button for the request "Visual Test Request"
    And I accept the confirmation dialog
    Then I should NOT see "Visual Test Request" in the list of requests

  Scenario: User tries to create empty request (Sad Path)
    Given I am on the LegalFly application
    When I click the Create button
    Then I should see an alert with message "Veuillez remplir au moins le titre et l'email"