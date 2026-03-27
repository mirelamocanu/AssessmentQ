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


