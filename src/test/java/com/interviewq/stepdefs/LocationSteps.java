package com.interviewq.stepdefs;

import static io.restassured.RestAssured.given;

import com.interviewq.config.ApiConfig;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class LocationSteps {

    @When("I retrieve the list of locations")
    public void iRetrieveTheListOfLocations() {
        Response response = given()
                .queryParam("pageNumber", ApiConfig.DEFAULT_PAGE_NUMBER)
                .queryParam("pageSize", ApiConfig.DEFAULT_PAGE_SIZE)
                .when()
                .get(ApiConfig.BASE_URL + ApiConfig.LOCATION_SEARCH);
    }
}
