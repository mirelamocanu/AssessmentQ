Feature: Locations endpoint
  Tests for the STAPI /location endpoints.
  GET  https://stapi.co/api/v1/rest/location/search  (list + pagination)
  POST https://stapi.co/api/v2/rest/location/search  (filter by field)

  Scenario: Retrieve a list of locations
    When I retrieve the list of locations
    Then the response status code is 200
    And the response list is not empty

  Scenario: Search for Earth locations
#    default pagination
# transform to scenario outline with different page sizes and page numbers
    When I search for locations on Earth
    Then the response status code is 200
    And the response contains 32 earth locations

  Scenario: Retrieve a list of locations
    When I retrieve the list of locations
    Then the response status code is 200
    And the response list is not empty

  Scenario: Every location in the search result has a name and UID
    When I retrieve the list of locations
    Then the response status code is 200
    And each location has a non-blank name and UID

  Scenario: Pagination metadata is present and consistent
    When I retrieve the list of locations
    Then the response status code is 200
    And the page metadata is valid

  Scenario Outline: Page size is respected in search results
    When I retrieve the list of locations with page size <pageSize>
    Then the response status code is 200
    And the result page contains at most <pageSize> locations

    Examples:
      | pageSize |
      | 5        |
      | 10       |
      | 50       |

  Scenario Outline: Retrieve a specific page of locations
    When I retrieve page <pageNumber> of locations with page size <pageSize>
    Then the response status code is 200
    And the response list is not empty
    Examples:
      | pageNumber | pageSize |
      | 0          | 10       |
      | 1          | 10       |
      | 2          | 5        |

  Scenario: Retrieve a single location by UID
    When I retrieve the list of locations
    And I retrieve the first location from the results
    Then the response status code is 200
    And the location response contains a name field

  Scenario: Retrieve a location with an invalid UID returns an error
    When I retrieve the location with UID "LOMA0000000000"
    Then the response status code is 200
    And the response body is null

#    POST


  # -----------------------------------------------------------------------
  # list / pagination  (GET)
  # -----------------------------------------------------------------------

  @smoke
  Scenario: Retrieve a list of locations returns HTTP 200
    When I retrieve the list of locations
    Then the response status code is 200
    And  the response list is not empty

  Scenario: Every location in the search result has a name and UID
    When I retrieve the list of locations
    Then the response status code is 200
    And  each location has a non-blank name and UID

  Scenario: Pagination metadata is present and consistent
    When I retrieve the list of locations
    Then the response status code is 200
    And  the page metadata is valid

  Scenario Outline: Page size is respected in search results
    When I retrieve the list of locations with page size <pageSize>
    Then the response status code is 200
    And  the result page contains at most <pageSize> locations

    Examples:
      | pageSize |
      | 5        |
      | 10       |
      | 50       |

  Scenario Outline: Retrieve a specific page of locations
    When I retrieve page <pageNumber> of locations with page size <pageSize>
    Then the response status code is 200
    And  the response list is not empty

    Examples:
      | pageNumber | pageSize |
      | 0          | 10       |
      | 1          | 10       |
      | 2          | 5        |

  @smoke
  Scenario: Retrieve a single location by UID
    When I retrieve the list of locations
    And  I retrieve the first location from the results
    Then the response status code is 200
    And  the location response contains a name field

  # -----------------------------------------------------------------------
  # name filter  (POST)
  # -----------------------------------------------------------------------

  Scenario: Search by name returns only locations whose name contains the term
    When I search for locations with name "Earth"
    Then the response status code is 200
    And  the response list is not empty
    And  every location name contains "Earth"

  @wip
  Scenario: Search for a location name that does not exist returns an empty list
    When I search for locations with name "ZZZNOTEXIST999"
    Then the response status code is 200
    And  the locations list is empty

  # -----------------------------------------------------------------------
  # boolean filters — all 26 flags via Scenario Outline  (POST v2)
  # -----------------------------------------------------------------------

  Scenario Outline: Search by boolean location flag returns only matching locations
    When I search for locations with <filterParam> set to "true"
    Then the response status code is 200
    And  the response list is not empty
    And  every location in the response has <filterParam> set to true

    Examples:
      | filterParam           |
      | earthlyLocation       |
      | qonosLocation         |
      | fictionalLocation     |
      | mythologicalLocation  |
      | religiousLocation     |
      | geographicalLocation  |
      | bodyOfWater           |
      | country               |
      | subnationalEntity     |
      | settlement            |
      | usSettlement          |
      | bajoranSettlement     |
      | colony                |
      | landform              |
      | road                  |
      | structure             |
      | shipyard              |
      | buildingInterior      |
      | establishment         |
      | medicalEstablishment  |
      | ds9Establishment      |
      | school                |
      | restaurant            |
      | residence             |
      | mirror                |
      | alternateReality      |

  # -----------------------------------------------------------------------
  # Earth locations — legacy GET-based step kept for backward compatibility
  # -----------------------------------------------------------------------

  Scenario: Search for Earth locations returns earthly-flagged locations
    When I search for locations on Earth
    Then the response status code is 200
    And  the response contains 32 earth locations

  # -----------------------------------------------------------------------
  # combined filters  (POST v2)
  # -----------------------------------------------------------------------

  Scenario: Earthly settlements are flagged as both earthly and settlement
    When I search for locations with earthlyLocation set to "true" and settlement set to "true"
    Then the response status code is 200
    And  the response list is not empty
    And  every location in the response has earthlyLocation set to true
    And  every location in the response has settlement set to true

  Scenario: DS9 medical establishments are flagged as both DS9 and medical
    When I search for locations with ds9Establishment set to "true" and medicalEstablishment set to "true"
    Then the response status code is 200
    And  the response list is not empty
    And  every location in the response has ds9Establishment set to true
    And  every location in the response has medicalEstablishment set to true

  Scenario: Mirror fictional locations are flagged as both mirror and fictional
    When I search for locations with fictionalLocation set to "true" and mirror set to "true"
    Then the response status code is 200
    And  the response list is not empty
    And  every location in the response has fictionalLocation set to true
    And  every location in the response has mirror set to true

  # -----------------------------------------------------------------------
  # negative test
  # -----------------------------------------------------------------------

  @wip
  Scenario: Retrieve a location with an invalid UID returns an empty body
    When I retrieve the location with UID "LOMA0000000000"
    Then the response status code is 200
    And  the response body is null