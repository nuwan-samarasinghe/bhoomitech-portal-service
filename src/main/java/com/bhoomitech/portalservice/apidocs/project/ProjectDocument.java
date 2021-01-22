package com.bhoomitech.portalservice.apidocs.project;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectDocument {
    private Long projectId;
    private String projectName;
    private String projectStartTimestamp;
    private String projectEndTimestamp;
    private String userHref;
    private Boolean agreementStatus;
    private String price;
    private List<ProjectFileInfoDocument> projectFileInfoDocuments = new ArrayList<>();
    private String createdTimestamp;
    private String userName;
    private String organization;
}
