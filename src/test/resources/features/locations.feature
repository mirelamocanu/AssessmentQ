Feature: Locations endpoint

  @UUID-12345678-1234-5678-1234-567812345678
  Scenario: Retrieve a list of locations
    When I retrieve the list of locations
    Then the response status code is 200
    And the response list is not empty

  Scenario: Search for Earth locations
#    default pagination
    When I search for locations on Earth
    Then the response status code is 200
    And the response contains 32 earth locations