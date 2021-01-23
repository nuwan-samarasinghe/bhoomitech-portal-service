package com.bhoomitech.portalservice.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCodes {
    PROJECT_NAME_AVAILABLE(2000, "project name available"),
    PROJECT_NAME_NOT_AVAILABLE(4000, "project name not available");

    private final Integer statusCode;
    private final String message;
}
