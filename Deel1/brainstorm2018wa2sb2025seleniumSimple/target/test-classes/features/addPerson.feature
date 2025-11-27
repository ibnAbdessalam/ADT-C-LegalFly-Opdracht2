Feature: Add Person

As a CRM-responsible I want to add a person to the customer base
Just after adding the person, the details of the person should be shown
After the list is shown again, the person first name and last name should be visible

Scenario: Add one person - see her details
Given I am on the page where I can introduce a new person
When I enter "Jennifer" in the voornaam field
And I enter "Heylen" in the familienaam field
And I enter "jennifer@streamz.com" in the emailadres field
And I enter "northernlights" in the paswoord field
And I press on the Submit button
Then I should see the following on the screen
  | label        | data           |
  | Voornaam:    | Jennifer           |
  | Familienaam: | Heylen           |
  | E-mailadres: | jennifer@streamz.com |
  | Paswoord:    | northernlights          |
And I close the browser

Scenario: Add one person - see her in the list
  Given I am on the page where I can introduce a new person
  When I enter "Elisa" in the voornaam field
  And I enter "Mertens" in the familienaam field
  And I enter "elisa@mertens.be" in the emailadres field
  And I enter "wta-finals" in the paswoord field
  And I press on the Submit button
  And I click the Lijst van alle personen Link
  Then I should see a list containing "Elisa Mertens"
  And I close the browser