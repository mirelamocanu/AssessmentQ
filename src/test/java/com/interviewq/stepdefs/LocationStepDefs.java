package com.interviewq.stepdefs;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.interviewq.config.ApiConfig;
import com.interviewq.context.ScenarioContext;
import com.interviewq.models.Location;
import com.interviewq.models.LocationSearchResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocationStepDefs {

    private final ScenarioContext scenarioContext;

    public LocationStepDefs(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @When("I search for locations on Earth")
    public void iSearchForLocationsOnEarth() {
        Response response = given()
                .queryParam("placeOfBirth", "Earth")
                .when()
                .get(ApiConfig.BASE_URL + ApiConfig.LOCATION_SEARCH);

        scenarioContext.setResponse(response);
    }

    @And("the response contains of earth locations")
    public void theResponseContainsOfEarthLocations() {

        List<Location> locationsList = scenarioContext.getResponse()
                .body()
                .as(LocationSearchResponse.class)
                .getLocations()
                .stream()
                .filter(loc -> loc.getEarthlyLocation().equals(true))
                .toList();

        assertThat(locationsList)
                .as("locations list")
                .isNotEmpty();

        log.info("Number of locations found: " + locationsList.size());
    }
}
