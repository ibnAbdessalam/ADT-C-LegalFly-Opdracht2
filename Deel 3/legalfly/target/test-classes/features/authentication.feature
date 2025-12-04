# language: nl

Functionaliteit: Inloggen

  Scenario: Inloggen met geldige admin gegevens
  Gegeven een admin met email "admin@example.com" en wachtwoord "Admin123!"
  Wanneer ik inlog als admin
  Dan ben ik succesvol ingelogd als admin

  Scenario: Inloggen met geldige jurist gegevens
  Gegeven een jurist met email "john.doe@example.com" en wachtwoord "John123!"
  Wanneer ik inlog als jurist
  Dan ben ik succesvol ingelogd als jurist