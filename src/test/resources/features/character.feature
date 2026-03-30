@starapi
Feature: Character API
  Tests for the STAPI /character endpoints.
  https://stapi.co/api/v2/rest

  @smoke
  Scenario: Search characters returns HTTP 200
    When I search for characters
    Then the response status code is 200