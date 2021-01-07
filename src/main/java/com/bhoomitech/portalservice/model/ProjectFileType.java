package com.bhoomitech.portalservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ProjectFileType {
    UNKNOWN_FILE("UNKNOWN_FILE",0),
    KNOWN_FILE("KNOWN_FILE",1);

    @Getter
    private final String fileType;

    @Getter
    private final Integer fileTypeId;
}
