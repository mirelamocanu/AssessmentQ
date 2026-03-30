package com.interviewq.models;

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

public class Page {
    private int pageNumber;
    private int pageSize;
    private int numberOfElements;
    private int totalElements;
    private int totalPages;
    private boolean firstPage;
    private boolean lastPage;
}

