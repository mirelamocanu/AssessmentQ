package com.interviewq.context;

import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ScenarioContext {

    /** The last HTTP response received during the current scenario. */
    private Response response;

    /** A UID captured during a step for use in a later step. */
    private String capturedUid;

}

