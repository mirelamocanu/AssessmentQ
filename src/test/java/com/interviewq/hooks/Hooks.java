package com.interviewq.hooks;

import com.interviewq.config.ApiConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        // Point REST Assured at the configured base URL
        RestAssured.baseURI = ApiConfig.BASE_URL;

        // Accept self-signed / untrusted TLS certificates
        RestAssured.useRelaxedHTTPSValidation();

        // Log every request and response to the console.
         RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        log.info("▶  Starting scenario: {}", scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        log.info("◀  Finished scenario: " + scenario.getName()
                + " [" + scenario.getStatus() + "]");

        RestAssured.reset();
    }
}

