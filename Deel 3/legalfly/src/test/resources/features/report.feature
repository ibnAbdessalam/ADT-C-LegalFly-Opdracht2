Feature: Report beheer

  Scenario: Een nieuw report aanmaken met geldige gegevens
    Given Ik een niewe report wil aanmaken
    And ik vul "1" in voor het veld "requestId"
    And ik vul "1" voor het veld "adminId"
    And ik vul "Test rapport" in voor het veld "content"
    And ik vul "Compliance" in voor het veld "type"
    And ik voeg een infraction toe met:
      | lawId            | 1                                       |
      | type             | Onrechtmatige clausule                  |
      | details          | Artikel 4.4 is strijdig met wetgeving   |
      | correctiveMeasure| Aanpassen of schrappen van claussule    |
    When ik verstuur het formulier om een nieuw report aan te maken
    Then wordt het report succesvol aangemaakt

  Scenario: Alle rapporten ophalen
    Given ik alle reports wilt ophalen
    When ik vraag alle reports op
    Then ontvang ik een lijst van reports

