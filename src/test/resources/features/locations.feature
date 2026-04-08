Feature: Locations endpoint
  Tests for the STAPI /location endpoints.
  GET  https://stapi.co/api/v1/rest/location/search  (list + pagination)
  POST https://stapi.co/api/v2/rest/location/search  (filter by field)

  # -----------------------------------------------------------------------
  # list / pagination  (GET)
  # -----------------------------------------------------------------------

  Scenario: Retrieve a list of locations returns HTTP 200
    When I retrieve the list of locations
    Then the response status code is 200
    And  the response list is not empty
    And  each location has a non-blank name and UID

  Scenario: Pagination metadata is present and consistent
    When I retrieve the list of locations
    Then the response status code is 200
    And  the page metadata is valid

#  Scenario Outline: Page size is respected in search results
#    When I retrieve the list of locations with page size <pageSize>
#    Then the response status code is 200
#    And  the result page contains at most <pageSize> locations
#
#    Examples:
#      | pageSize |
#      | 5        |
#      | 10       |
#      | 50       |

  Scenario Outline: Retrieve a specific page of locations
    When I retrieve page <pageNumber> of locations with page size <pageSize>
    Then the response status code is 200
    And the response list is not empty
    And the page metadata has the following values:
      | pageNumber   | pageSize   | totalElements   | totalPages   | firstPage   | lastPage   |
      | <pageNumber> | <pageSize> | <totalElements> | <totalPages> | <firstPage> | <lastPage> |

    Examples:
      | pageNumber | pageSize | totalElements | totalPages | firstPage | lastPage |
      | 0          | 10       | 2502          | 251        | true      | false    |
      | 1          | 10       | 2502          | 251        | false     | false    |
      | 2          | 5        | 2502          | 501        | false     | false    |
#      | 50         | 50       | 2502          | 501        | false     | true     |

  @smoke
  Scenario: Retrieve a single location by UID
    When I retrieve the list of locations
    And  I retrieve the first location from the results
    Then the response status code is 200
    And  the location response contains a name field

  Scenario: Retrieve a location with an invalid UID returns an error
    When I retrieve the location with UID "LOMA0000000000"
    Then the response status code is 200
    And the response body is null


  # -----------------------------------------------------------------------
  # name filter  (POST)
  # -----------------------------------------------------------------------

  Scenario: Search by name returns only locations whose name contains the term
    When I search for locations with name "Earth"
    Then the response status code is 200
    And  the response list is not empty
    And  every location name contains "Earth"
    And there are 8 locations in the response
    And only 5 are earthly locations

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
    And the number of locations is equal to <noOfLocations>

    Examples:
      | filterParam          | noOfLocations |
      | earthlyLocation      | 1102          |
#      | qonosLocation        | 43            |
#      | fictionalLocation    | 70            |
#      | geographicalLocation | 959           |
#      | country              | 70            |
#      | alternateReality     | 84            |

  # -----------------------------------------------------------------------
  # Earth locations — legacy GET-based step kept for backward compatibility
  # -----------------------------------------------------------------------

  Scenario: Search for Earth locations returns earthly-flagged locations
    When I search for locations on Earth
    Then the response status code is 200
    And  the response contains 32 earth locations
#    And every location in the response has earthlyLocation set to true

  # -----------------------------------------------------------------------
  # combined filters  (POST v2)
  # -----------------------------------------------------------------------

  Scenario: Earthly settlements are flagged as both earthly and settlement
    When I search for locations with earthlyLocation set to "true" and settlement set to "true"
    Then the response status code is 200
    And  the response list is not empty
    And  every location in the response has earthlyLocation set to true
    And  every location in the response has settlement set to true

  Scenario:On Earth, settlements are all earthly locations
    When I search for locations with earthlyLocation set to "true" and settlement set to "true"
    Then the response status code is 200
    And  the response list is not empty
    And  every location in the response has earthlyLocation set to true
    And  every location in the response has settlement set to true

  Scenario:Medical establishments are flagged as both establishments and medical
    When I search for locations with establishment set to "true" and medicalEstablishment set to "true"
    Then the response status code is 200
    And  the response list is not empty
    And  every location in the response has establishment set to true
    And  every location in the response has medicalEstablishment set to true

  Scenario: On Earth, road locations are all strutures
    When I search for locations with road set to "true" and earthlyLocation set to "true"
    Then the response status code is 200
    And  the response list is not empty
    And  every location in the response has earthlyLocation set to true
    And  every location in the response has road set to true
    And  every location in the response has structure set to true
    And the number of locations is equal to 60

  # -----------------------------------------------------------------------
  # negative test
  # -----------------------------------------------------------------------

  @wip
  Scenario: Retrieve a location with an invalid UID returns an empty body
    When I retrieve the location with UID "LOMA0000000000"
    Then the response status code is 200
    And  the response body is null