@starapi
Feature: Character API
  Tests for the STAPI /character endpoints.
  https://stapi.co/api/v2/rest

  @smoke
  Scenario: Search characters returns HTTP 200
    When I search for characters
    Then the response status code is 200

  @smoke
  Scenario: Search characters returns a non-empty list
    When I search for characters
    Then the response status code is 200
    And  the response contains a list of characters

  Scenario: Every character in the search result has a name and UID
    When I search for characters
    Then the response status code is 200
    And  each character has a non-blank name and UID

  Scenario: Page size is respected in search results
    When I search for characters with page size 5
    Then the response status code is 200
    And  the result page contains at most 5 characters

  @smoke
  Scenario: Retrieve a single character by UID
    When I search for characters
    And  I retrieve the first character from the results
    Then the response status code is 200
    And  the character response contains a name field

  @wip
  Scenario: Search with page size 0 returns a bad request or empty list
    # TODO: decide what the API actually returns and write the assertion
    When I search for characters with page size 0
    Then the response status code is 200

  @wip
  Scenario: Retrieve a character with an invalid UID returns an error
    When I retrieve the character with UID "CHMA0000000000"
    Then the response status code is 404

  Scenario Outline: Search by gender returns only characters of that gender
    When I search for characters with gender "<gender>"
    Then the response status code is 200
    And  every character in the response has gender "<gender>"
    Examples:
      | gender |
      | M      |
      | F      |

  Scenario: Search for alternate reality characters returns only alternate reality characters
    When I search for characters with alternateReality "true"
    Then the response status code is 200
    And  every character in the response has alternateReality set to true

  # -----------------------------------------------------------------------
  # name filter  (POST form-param)
  # -----------------------------------------------------------------------
  Scenario: Search by name returns only characters whose name contains the term
    When I search for characters with name "Spock"
    Then the response status code is 200
    And  every character name contains "Spock"

  @wip
  Scenario: Search by a name that does not exist returns an empty list
    When I search for characters with name "ZZZNOTEXIST999"
    Then the response status code is 200
    And  the characters list is empty

  # -----------------------------------------------------------------------
  # gender filter
  # -----------------------------------------------------------------------
  Scenario Outline: Search by gender returns only characters of that gender
    When I search for characters with gender "<gender>"
    Then the response status code is 200
    And  every character in the response has gender "<gender>"

    Examples:
      | gender |
      | M      |
      | F      |

  # -----------------------------------------------------------------------
  # deceased filter
  # -----------------------------------------------------------------------
  Scenario: Search for deceased characters returns only deceased characters
    When I search for characters with deceased "true"
    Then the response status code is 200
    And  every character in the response has deceased set to true

  Scenario: Search for living characters returns only living characters
    When I search for characters with deceased "false"
    Then the response status code is 200
    And  every character in the response has deceased set to false

  # -----------------------------------------------------------------------
  # hologram filter
  # -----------------------------------------------------------------------
  Scenario: Search for hologram characters returns only holograms
    When I search for characters with hologram "true"
    Then the response status code is 200
    And  every character in the response has hologram set to true

  # -----------------------------------------------------------------------
  # fictionalCharacter filter
  # -----------------------------------------------------------------------
  Scenario: Search for fictional characters returns only fictional characters
    When I search for characters with fictionalCharacter "true"
    Then the response status code is 200
    And  every character in the response has fictionalCharacter set to true

  # -----------------------------------------------------------------------
  # mirror filter
  # -----------------------------------------------------------------------
  Scenario: Search for mirror universe characters returns only mirror characters
    When I search for characters with mirror "true"
    Then the response status code is 200
    And  every character in the response has mirror set to true

  # -----------------------------------------------------------------------
  # alternateReality filter
  # -----------------------------------------------------------------------
  Scenario: Search for alternate reality characters returns only alternate reality characters
    When I search for characters with alternateReality "true"
    Then the response status code is 200
    And  every character in the response has alternateReality set to true

  # -----------------------------------------------------------------------
  # combined filters
  # -----------------------------------------------------------------------
  Scenario: Search for female deceased characters matches both filters
    When I search for characters with gender "F" and deceased "true"
    Then the response status code is 200
    And  every character in the response has gender "F"
    And  every character in the response has deceased set to true

  Scenario: Search for mirror + alternate reality characters matches both filters
    When I search for characters with mirror "true" and alternateReality "true"
    Then the response status code is 200
    And  every character in the response has mirror set to true
    And  every character in the response has alternateReality set to true
