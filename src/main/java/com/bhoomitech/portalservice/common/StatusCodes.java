package com.bhoomitech.portalservice.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCodes {

    PROJECT_NAME_AVAILABLE_OK(2000, "project name available"),
    PROJECT_DETAILS_AVAILABLE_OK(2001, "project details available"),
    PROJECT_CREATION_OK(2002, "project successfully created"),
    PROJECT_UPDATE_OK(2003, "project successfully created"),
    PROJECT_INFO_CREATION_OK(2004, "project info successfully created"),
    PROJECT_CREATION_COMPLETED_OK(2005, "project creation update success"),

    PROJECT_NAME_NOT_AVAILABLE_ERROR(4000, "project name not available"),
    PROJECT_DETAILS_NOT_AVAILABLE_ERROR(4001, "An error occurred while getting project details"),
    USER_DETAILS_NOT_AVAILABLE_ERROR(4002, "cannot get user details"),
    PROJECT_CREATION_ERROR(4003, "cannot create project"),
    PROJECT_UPDATE_ERROR(4004, "cannot update project"),
    PROJECT_INFO_CREATION_ERROR(4005, "cannot create project info"),
    PROJECT_CREATION_COMPLETED_ERROR(4006, "project creation update error"),

    PROJECT_CREATION_VALIDATION_ERROR(5001, "No gps coordinates provided for the known files"),
    DATA_VALIDATION_ERROR(5002, "invalid data provided");

    private final Integer statusCode;
    private final String message;
}
