package com.interviewq.models;

/**
 * Represents a Location returned by the STAPI location endpoints.
 *
 * Maps to the Location schema from STAPI API.
 * Null attributes are excluded from JSON serialization via @JsonInclude(NON_NULL).
 * Unknown fields are ignored so the model stays stable even if the API adds new properties.
 *
 * Required fields: uid, name
 *
 * @see <a href="https://stapi.co/api/v1/rest/swagger-ui.html">STAPI Swagger UI</a>
 */

public class Location {
    // ---------------------------------------------------------------
    // Required Fields
    // ---------------------------------------------------------------
    private String uid;
    private String name;

    // ---------------------------------------------------------------
    // Location Type Classification
    // ---------------------------------------------------------------
    private Boolean earthlyLocation;
    private Boolean fictionalLocation;
    private Boolean religiousLocation;
    private Boolean geographicalLocation;
    private Boolean bodyOfWater;
    private Boolean country;
    private Boolean subnationalEntity;
    private Boolean settlement;
    private Boolean usSettlement;
    private Boolean bajoranSettlement;
    private Boolean colony;
    private Boolean landform;
    private Boolean landmark;
    private Boolean road;
    private Boolean structure;
    private Boolean shipyard;
    private Boolean buildingInterior;
    private Boolean establishment;
    private Boolean medicalEstablishment;
    private Boolean ds9Establishment;
    private Boolean school;

    // ---------------------------------------------------------------
    // Universe Classification
    // ---------------------------------------------------------------
    private Boolean mirror;
    private Boolean alternateReality;
}

