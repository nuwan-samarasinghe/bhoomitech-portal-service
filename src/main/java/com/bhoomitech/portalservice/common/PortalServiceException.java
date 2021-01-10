package com.bhoomitech.portalservice.common;

public class PortalServiceException extends RuntimeException {
    public PortalServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
