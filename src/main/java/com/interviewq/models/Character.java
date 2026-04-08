package com.interviewq.models;

import java.util.List;

/**
 * Represents a full Character returned by the STAPI character endpoints.
 * <p>
 * Maps to the CharacterFull schema from STAPI API. Null attributes are excluded from JSON serialization via
 * @JsonInclude(NON_NULL). Unknown fields are ignored so the model stays stable even if the API adds new properties.
 * <p>
 * Required fields: uid, name
 *
 * @see <a href="https://stapi.co/api/v1/rest/swagger-ui.html">STAPI Swagger UI</a>
 */

public class Character {

    // ---------------------------------------------------------------
    // Required Fields
    // ---------------------------------------------------------------
    private String uid;
    private String name;

    // ---------------------------------------------------------------
    // Birth Information
    // ---------------------------------------------------------------
    private String gender;
    private Integer yearOfBirth;
    private Integer monthOfBirth;
    private Integer dayOfBirth;
    private String placeOfBirth;

    // ---------------------------------------------------------------
    // Death Information
    // ---------------------------------------------------------------
    private Integer yearOfDeath;
    private Integer monthOfDeath;
    private Integer dayOfDeath;
    private String placeOfDeath;
    private Boolean deceased;

    // ---------------------------------------------------------------
    // Physical Attributes
    // ---------------------------------------------------------------
    private Integer height;          // in centimeters
    private Integer weight;          // in kilograms
    private String bloodType;

    // ---------------------------------------------------------------
    // Character Status
    // ---------------------------------------------------------------
    private String maritalStatus;
    private String serialNumber;
    private Boolean hologram;
    private String hologramActivationDate;
    private String hologramStatus;
    private String hologramDateStatus;
    private Boolean fictionalCharacter;
    private Boolean mirror;
    private Boolean alternateReality;

    // ---------------------------------------------------------------
    // Relations (Nested Objects/Arrays)
    // ---------------------------------------------------------------
    private List<?> performers;
    private List<?> episodes;
    private List<?> movies;
    private List<?> characterSpecies;
    private List<?> characterRelations;
    private List<?> titles;
    private List<?> occupations;
    private List<?> organizations;
}



