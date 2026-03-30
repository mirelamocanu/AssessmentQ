package com.interviewq.stepdefs;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.interviewq.config.ApiConfig;
import com.interviewq.context.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CharacterSteps {

    private final ScenarioContext scenarioContext;

    public CharacterSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }


    // GIVEN // ------------------------------------------------------------------

    // WHEN // ------------------------------------------------------------------

    @When("I search for characters")
    public void iSearchForCharacters() {
        Response response = given()
                .queryParam("pageNumber", ApiConfig.DEFAULT_PAGE_NUMBER)
                .queryParam("pageSize", ApiConfig.DEFAULT_PAGE_SIZE)
                .when()
                .get(ApiConfig.BASE_URL + ApiConfig.CHARACTER_SEARCH);

        scenarioContext.setResponse(response);
    }


    // THEN // ------------------------------------------------------------------

    @Then("the response status code is {int}")
    public void theResponseStatusCodeIs(int expectedStatus) {
        assertThat(scenarioContext.getResponse().getStatusCode())
                .as("HTTP status code")
                .isEqualTo(expectedStatus);
    }

  }

