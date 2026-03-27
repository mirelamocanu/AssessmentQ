Feature: Star Trek  Locations

  Scenario: Retrieve a list of locations on Earth
    When I search for locations on Earth
    Then the response status code is 200
    And  the response contains of earth locations