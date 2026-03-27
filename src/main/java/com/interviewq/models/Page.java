package com.interviewq.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Represents the pagination metadata returned by every STAPI list/search endpoint.
 *
 * Example JSON fragment:
 * <pre>
 * "page": {
 *   "pageNumber": 0,
 *   "pageSize": 10,
 *   "numberOfElements": 10,
 *   "totalElements": 542,
 *   "totalPages": 55,
 *   "firstPage": true,
 *   "lastPage": false
 * }
 * </pre>
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Page {
    private int pageNumber;
    private int pageSize;
    private int numberOfElements;
    private int totalElements;
    private int totalPages;
    private boolean firstPage;
    private boolean lastPage;
}

