Feature: Documentanonymisatie van klantgegevens

  Scenario: Security verantwoordelijke anonimiseert contract van klant Lisa De Vries

    Given de klant "Imran Rossi" heeft haar contract voor juridisch advies gedeeld met de teammanager

    And de klant heeft haar persoonlijke informatie gedeeld met de LegalFly security verantwoordelijke

    And de LegalFly security verantwoordelijke heeft mock data laten genereren met valse maar geldige waarden

    When de security verantwoordelijke de persoonlijke data in het document laat vervangen door de gegenereerde mock data

    Then bevat het document geen echte persoonsgegevens van "Imran Rossi"

    And bevat het document in de plaats mock gegevens zoals:
      | Veld        | Originele waarde               | Mock waarde                   |
      | Naam        | Imran Rossi                    | Emma Janssens                 |
      | Adres       | alamaisonoslm                  | Kerkstraat 45, Leuven         |
      | E-mailadres | jsp@email.com                  | emma.janssens@mockmail.com    |
      | Telefoon    | 0497 22 44 11                  | 0486 55 88 99                 |