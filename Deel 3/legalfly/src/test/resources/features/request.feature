Feature: Request beheer

  Scenario: Een nieuwe request aanmaken met geldige gegevens
  Gegeven een jurist met id 1 en een wet met id 2
  Wanneer ik een request aanmaak met de inhoud "Test request"
  Dan wordt de request succesvol aangemaakt

  Scenario: Alle requests ophalen
  Wanneer ik alle requests opvraag
  Dan ontvang ik een lijst van requests

