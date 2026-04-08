package com.interviewq.stepdefs;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.interviewq.config.ApiConfig;
import com.interviewq.context.ScenarioContext;
import com.interviewq.model.Location;
import com.interviewq.model.LocationSearchResponse;
import com.interviewq.model.Page;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;

@Slf4j
public class LocationStepDefs {

    private final ScenarioContext scenarioContext;

    public LocationStepDefs(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    // -----------------------------------------------------------------------
    // WHEN – list / pagination  (GET v1)
    // -----------------------------------------------------------------------

    @When("I retrieve the list of locations")
    public void iRetrieveTheListOfLocations() {
        Response response = given()
                .queryParam("pageNumber", ApiConfig.DEFAULT_PAGE_NUMBER)
                .queryParam("pageSize", ApiConfig.DEFAULT_PAGE_SIZE)
                .when()
                .get(ApiConfig.BASE_URL + ApiConfig.LOCATION_SEARCH);
        scenarioContext.setResponse(response);
    }

    @When("I retrieve the list of locations with page size {int}")
    public void iRetrieveTheListOfLocationsWithPageSize(int pageSize) {
        Response response = given()
                .queryParam("pageNumber", ApiConfig.DEFAULT_PAGE_NUMBER)
                .queryParam("pageSize", pageSize)
                .when()
                .get(ApiConfig.BASE_URL + ApiConfig.LOCATION_SEARCH);
        scenarioContext.setResponse(response);
    }

    @When("I retrieve page {int} of locations with page size {int}")
    public void iRetrievePageOfLocationsWithPageSize(int pageNumber, int pageSize) {
        Response response = given()
                .queryParam("pageNumber", pageNumber)
                .queryParam("pageSize", pageSize)
                .when()
                .get(ApiConfig.BASE_URL + ApiConfig.LOCATION_SEARCH);
        scenarioContext.setResponse(response);
    }

    @When("I retrieve the first location from the results")
    public void iRetrieveTheFirstLocationFromTheResults() {
        String uid = scenarioContext.getResponse()
                .jsonPath()
                .getString("locations[0].uid");
        assertThat(uid).as("Expected a UID in the first search result").isNotBlank();
        scenarioContext.setCapturedUid(uid);

        Response response = given()
                .queryParam("uid", uid)
                .when()
                .get(ApiConfig.BASE_URL + ApiConfig.LOCATION);
        scenarioContext.setResponse(response);
    }

    @When("I retrieve the location with UID {string}")
    public void iRetrieveTheLocationWithUID(String uid) {
        Response response = given()
                .queryParam("uid", uid)
                .when()
                .get(ApiConfig.BASE_URL + ApiConfig.LOCATION);
        scenarioContext.setResponse(response);
    }

    // -----------------------------------------------------------------------
    // WHEN – legacy Earth search  (GET v1, kept for backward compatibility)
    // -----------------------------------------------------------------------

    @When("I search for locations on Earth")
    public void iSearchForLocationsOnEarth() {
        Response response = given()
                .queryParam("name", "Earth")
                .when()
                .get(ApiConfig.BASE_URL + ApiConfig.LOCATION_SEARCH);
        scenarioContext.setResponse(response);
    }

    // -----------------------------------------------------------------------
    // WHEN – name filter  (POST v2)
    // -----------------------------------------------------------------------

    @When("I search for locations with name {string}")
    public void iSearchForLocationsWithName(String name) {
        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", name)
                .queryParam("pageNumber", ApiConfig.DEFAULT_PAGE_NUMBER)
                .queryParam("pageSize", ApiConfig.DEFAULT_PAGE_SIZE)
                .when()
                .post(ApiConfig.BASE_URL_V2 + ApiConfig.LOCATION_SEARCH);
        scenarioContext.setResponse(response);
    }

    // -----------------------------------------------------------------------
    // WHEN – single boolean filter  (POST v2)
    // Handles all 26 boolean flags from the Scenario Outline
    // -----------------------------------------------------------------------

    @When("I search for locations with {word} set to {string}")
    public void iSearchForLocationsWithFilterSetTo(String filterParam, String value) {
        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam(filterParam, value)
                .queryParam("pageNumber", ApiConfig.DEFAULT_PAGE_NUMBER)
                .queryParam("pageSize", ApiConfig.DEFAULT_PAGE_SIZE)
                .when()
                .post(ApiConfig.BASE_URL_V2 + ApiConfig.LOCATION_SEARCH);
        scenarioContext.setResponse(response);
    }

    // -----------------------------------------------------------------------
    // WHEN – two combined boolean filters  (POST v2)
    // -----------------------------------------------------------------------

    @When("I search for locations with {word} set to {string} and {word} set to {string}")
    public void iSearchForLocationsWithTwoFilters(
            String filter1, String value1, String filter2, String value2) {
        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam(filter1, value1)
                .formParam(filter2, value2)
                .queryParam("pageNumber", ApiConfig.DEFAULT_PAGE_NUMBER)
                .queryParam("pageSize", ApiConfig.DEFAULT_PAGE_SIZE)
                .when()
                .post(ApiConfig.BASE_URL_V2 + ApiConfig.LOCATION_SEARCH);
        scenarioContext.setResponse(response);
    }

    // -----------------------------------------------------------------------
    // THEN – list / data integrity
    // -----------------------------------------------------------------------

    @And("the response list is not empty")
    public void theResponseListIsNotEmpty() {
        LocationSearchResponse body = scenarioContext.getResponse().as(LocationSearchResponse.class);
        assertThat(body.getLocations()).as("locations list").isNotEmpty();
    }

    @And("each location has a non-blank name and UID")
    public void eachLocationHasANonBlankNameAndUID() {
        LocationSearchResponse body = scenarioContext.getResponse().as(LocationSearchResponse.class);
        body.getLocations().forEach(loc -> {
            assertThat(loc.getUid()).as("uid for location '%s'", loc.getName()).isNotBlank();
            assertThat(loc.getName()).as("name for location uid %s", loc.getUid()).isNotBlank();
        });
    }

    @And("the page metadata is valid")
    public void thePageMetadataIsValid() {
        LocationSearchResponse body = scenarioContext.getResponse().as(LocationSearchResponse.class);
        SoftAssertions.assertSoftly(
                softly -> {
                    softly.assertThat(body.getPage()).as("page object").isNotNull();
                    softly.assertThat(body.getPage().getTotalElements()).as("totalElements").isGreaterThan(0);
                    softly.assertThat(body.getPage().getTotalPages()).as("totalPages").isGreaterThan(0);
                    softly.assertThat(body.getPage().getNumberOfElements()).as("numberOfElements")
                            .isLessThanOrEqualTo(body.getPage().getPageSize());

                });
    }

    @And("the result page contains at most {int} locations")
    public void theResultPageContainsAtMostLocations(int max) {
        int actual = scenarioContext.getResponse().jsonPath().getList("locations").size();
        assertThat(actual).as("locations on page").isLessThanOrEqualTo(max);
    }

    @And("the location response contains a name field")
    public void theLocationResponseContainsANameField() {
        String name = scenarioContext.getResponse().jsonPath().getString("location.name");
        assertThat(name).as("location.name").isNotBlank();
    }

    @And("the response body is null")
    public void theResponseBodyIsNull() {
        String location = scenarioContext.getResponse().jsonPath().getString("location");
        assertThat(location).as("location body").isNull();
    }

    // -----------------------------------------------------------------------
    // THEN – legacy Earth locations count
    // -----------------------------------------------------------------------

    @And("the response contains {int} earth locations")
    public void theResponseContainsEarthLocations(int expectedCount) {
        long count = scenarioContext.getResponse()
                .body()
                .as(LocationSearchResponse.class)
                .getLocations()
                .stream()
                .filter(loc -> Boolean.TRUE.equals(loc.getEarthlyLocation()))
                .count();
        assertThat(count).isEqualTo(expectedCount);
        log.info("Number of earthly locations found: {}", count);
    }

    // -----------------------------------------------------------------------
    // THEN – name filter assertion
    // -----------------------------------------------------------------------

    @Then("every location name contains {string}")
    public void everyLocationNameContains(String namePart) {
        LocationSearchResponse body = scenarioContext.getResponse().as(LocationSearchResponse.class);
        body.getLocations().forEach(loc ->
                assertThat(loc.getName())
                        .as("name for location uid %s", loc.getUid())
                        .containsIgnoringCase(namePart)
        );
    }

    @Then("the locations list is empty")
    public void theLocationsListIsEmpty() {
        LocationSearchResponse body = scenarioContext.getResponse().as(LocationSearchResponse.class);
        assertThat(body.getLocations()).as("locations list").isEmpty();
    }

    // -----------------------------------------------------------------------
    // THEN – generic boolean field assertion
    // One step handles all 26 boolean flags — no reflection, fully type-safe
    // -----------------------------------------------------------------------

    @Then("every location in the response has {word} set to {word}")
    public void everyLocationInTheResponseHasFieldSetTo(String fieldName, String value) {
        boolean expected = Boolean.parseBoolean(value);
        LocationSearchResponse body = scenarioContext.getResponse().as(LocationSearchResponse.class);
        body.getLocations().forEach(loc ->
                assertThat(getBooleanField(loc, fieldName))
                        .as("%s for location '%s'", fieldName, loc.getName())
                        .isEqualTo(expected)
        );
    }

    private Boolean getBooleanField(Location loc, String fieldName) {
        return switch (fieldName) {
            case "earthlyLocation" -> loc.getEarthlyLocation();
            case "qonosLocation" -> loc.getQonosLocation();
            case "fictionalLocation" -> loc.getFictionalLocation();
            case "mythologicalLocation" -> loc.getMythologicalLocation();
            case "religiousLocation" -> loc.getReligiousLocation();
            case "geographicalLocation" -> loc.getGeographicalLocation();
            case "bodyOfWater" -> loc.getBodyOfWater();
            case "country" -> loc.getCountry();
            case "subnationalEntity" -> loc.getSubnationalEntity();
            case "settlement" -> loc.getSettlement();
            case "usSettlement" -> loc.getUsSettlement();
            case "bajoranSettlement" -> loc.getBajoranSettlement();
            case "colony" -> loc.getColony();
            case "landform" -> loc.getLandform();
            case "road" -> loc.getRoad();
            case "structure" -> loc.getStructure();
            case "shipyard" -> loc.getShipyard();
            case "buildingInterior" -> loc.getBuildingInterior();
            case "establishment" -> loc.getEstablishment();
            case "medicalEstablishment" -> loc.getMedicalEstablishment();
            case "ds9Establishment" -> loc.getDs9Establishment();
            case "school" -> loc.getSchool();
            case "restaurant" -> loc.getRestaurant();
            case "residence" -> loc.getResidence();
            case "mirror" -> loc.getMirror();
            case "alternateReality" -> loc.getAlternateReality();
            default -> throw new IllegalArgumentException("Unknown Location field: " + fieldName);
        };
    }

    @And("there are {int} locations in the response")
    public void thereAreLocationsInTheResponse(int noOfLocations) {
        assertThat(scenarioContext.getResponse().as(LocationSearchResponse.class)
                .getLocations().size())
                .as("Number of locations in response is different.")
                .isEqualTo(noOfLocations);
    }

    @And("the number of locations is equal to {int}")
    public void theNumberOfLocationsIsEqualToNoOfLocations(int noOfLocations) {
        assertThat(scenarioContext.getResponse().as(LocationSearchResponse.class)
                .getPage().getTotalElements()).as("Number of total(elements) locations in response is different.")
                .isEqualTo(noOfLocations);
    }

    @And("the page metadata indicates page number {int} and page size {int}")
    public void thePageMetadataIndicatesPageNumberPageNumberAndPageSizePageSize(int expectedPageNumber,
            int expectedPageSize) {
        Page page = scenarioContext.getResponse().as(LocationSearchResponse.class)
                .getPage();
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(page.getPageNumber()).as("Page number").isEqualTo(expectedPageNumber);
            softly.assertThat(page.getPageSize()).as("Page size").isEqualTo(expectedPageSize);
        });
    }

    @And("the page metadata has the following values:")
    public void thePageMetadataHasTheFollowingValues(DataTable dataTable) {
        Page actual = scenarioContext.getResponse().as(LocationSearchResponse.class).getPage();
        Page expectedPage = new Page();
        List<Map<String, String>> maps = dataTable.asMaps();
        maps.forEach(row -> {
            expectedPage.setPageNumber(Integer.parseInt(row.get("pageNumber")));
            expectedPage.setPageSize(Integer.parseInt(row.get("pageSize")));
            expectedPage.setNumberOfElements(Integer.parseInt(row.get("pageSize")));
            expectedPage.setTotalElements(Integer.parseInt(row.get("totalElements")));
            expectedPage.setTotalPages(Integer.parseInt(row.get("totalPages")));
            expectedPage.setFirstPage(Boolean.parseBoolean(row.get("firstPage")));
            expectedPage.setLastPage(Boolean.parseBoolean(row.get("lastPage")));
        });

        assertThat(actual).as("Page metadata").isEqualTo(expectedPage);
    }

    @And("only {int} are earthly locations")
    public void onlyAreEarthlyLocations(int earthlyLocationsCount) {
        long count = scenarioContext.getResponse().as(LocationSearchResponse.class)
                .getLocations().stream().filter(Location::getEarthlyLocation).count();

        assertThat(count)
                .as("Number of earthly locations")
                .isEqualTo(earthlyLocationsCount);
    }
}