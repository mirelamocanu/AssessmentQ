package com.interviewq.stepdefs;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.interviewq.config.ApiConfig;
import com.interviewq.context.ScenarioContext;
import com.interviewq.models.Character;
import com.interviewq.models.CharacterSearchResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CharacterSteps {

    private final ScenarioContext scenarioContext;

    public CharacterSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    // ------------------------------------------------------------------
    // GIVEN
    // ------------------------------------------------------------------

    // ------------------------------------------------------------------
    // WHEN
    // ------------------------------------------------------------------

    @When("I search for characters")
    public void iSearchForCharacters() {
        Response response = given()
                .queryParam("pageNumber", ApiConfig.DEFAULT_PAGE_NUMBER)
                .queryParam("pageSize", ApiConfig.DEFAULT_PAGE_SIZE)
                .when()
                .get(ApiConfig.BASE_URL + ApiConfig.CHARACTER_SEARCH);

        scenarioContext.setResponse(response);
    }

    private List<Character> getAllPages(Response response) {

        List<Character> allCharacters = new ArrayList<>();
        int totalPages = response.getBody().as(CharacterSearchResponse.class).getPage().getTotalPages();
        for (int page = 0; page < totalPages; page++) {
            Response pageResponse = given()
                    .queryParam("pageNumber", page)
                    .queryParam("pageSize", ApiConfig.DEFAULT_PAGE_SIZE)
                    .when()
                    .get(ApiConfig.BASE_URL + ApiConfig.CHARACTER_SEARCH);

            allCharacters.addAll(pageResponse.getBody().as(CharacterSearchResponse.class).getCharacters());
        }
        return allCharacters;
    }

    @When("I search for characters with page size {int}")
    public void iSearchForCharactersWithPageSize(int pageSize) {
        Response response = given()
                .queryParam("pageNumber", ApiConfig.DEFAULT_PAGE_NUMBER)
                .queryParam("pageSize", pageSize)
                .when()
                .get(ApiConfig.BASE_URL + ApiConfig.CHARACTER_SEARCH);

        scenarioContext.setResponse(response);
    }

    @When("I retrieve the first character from the results")
    public void iRetrieveTheFirstCharacterFromTheResults() {
        // Grab the UID of the first character returned by the previous search
        String uid = scenarioContext.getResponse()
                .jsonPath()
                .getString("characters[0].uid");

        assertThat(uid)
                .as("Expected a UID in the first search result")
                .isNotBlank();

        scenarioContext.setCapturedUid(uid);

        Response response = given()
                .queryParam("uid", uid)
                .when()
                .get(ApiConfig.BASE_URL + ApiConfig.CHARACTER);

        scenarioContext.setResponse(response);
    }

    @When("I retrieve the character with UID {string}")
    public void iRetrieveTheCharacterWithUID(String uid) {
        Response response = given()
                .queryParam("uid", uid)
                .when()
                .get(ApiConfig.BASE_URL + ApiConfig.CHARACTER);

        scenarioContext.setResponse(response);
    }

    // ------------------------------------------------------------------
    // THEN
    // ------------------------------------------------------------------

    @Then("the response status code is {int}")
    public void theResponseStatusCodeIs(int expectedStatus) {
        assertThat(scenarioContext.getResponse().getStatusCode())
                .as("HTTP status code")
                .isEqualTo(expectedStatus);
    }

    @Then("the response contains a list of characters")
    public void theResponseContainsAListOfCharacters() {
        CharacterSearchResponse body = scenarioContext.getResponse()
                .as(CharacterSearchResponse.class);

        assertThat(body.getCharacters())
                .as("characters list")
                .isNotEmpty();
    }

    @Then("each character has a non-blank name and UID")
    public void eachCharacterHasANonBlankNameAndUID() {
        CharacterSearchResponse body = scenarioContext.getResponse()
                .as(CharacterSearchResponse.class);

        body.getCharacters().forEach(c -> {
            assertThat(c.getUid()).as("uid for character %s", c.getName()).isNotBlank();
            assertThat(c.getName()).as("name for character uid %s", c.getUid()).isNotBlank();
        });
    }

    @Then("the result page contains at most {int} characters")
    public void theResultPageContainsAtMostCharacters(int max) {
        int actual = scenarioContext.getResponse().jsonPath().getList("characters").size();
        assertThat(actual)
                .as("number of characters in the page")
                .isLessThanOrEqualTo(max);
    }

    @Then("the character response contains a name field")
    public void theCharacterResponseContainsANameField() {
        String name = scenarioContext.getResponse().jsonPath().getString("character.name");
        assertThat(name)
                .as("character.name")
                .isNotBlank();
    }

    @Then("the character UID in the response matches {string}")
    public void theCharacterUIDInTheResponseMatches(String expectedUid) {
        String actualUid = scenarioContext.getResponse().jsonPath().getString("character.uid");
        assertThat(actualUid)
                .as("character.uid")
                .isEqualTo(expectedUid);
    }



    @And("the response contains a list terrans")
    public void theResponseContainsAListTerrans() {
        CharacterSearchResponse body = scenarioContext.getResponse()
                .as(CharacterSearchResponse.class);

        assertThat(body.getCharacters())
                .as("characters list")
                .isNotEmpty();

        body.getCharacters().forEach(c -> {
            assertThat(c.getPlaceOfBirth())
                    .as("placeOfBirth for character %s", c.getName())
                    .containsIgnoringCase("Earth");
            log.info("No. of characters born on Earth: " + body.getCharacters().size());
        });
    }


}

