package com.bhoomitech.portalservice.apidocs.project;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectDocument {
    private Long projectId;
    @NotBlank(message = "project name is a mandatory field")
    @NotEmpty(message = "project name is a mandatory field")
    private String projectName;
    @NotBlank(message = "project start date time is a mandatory field")
    @NotEmpty(message = "project start date time is a mandatory field")
    private String projectStartTimestamp;
    @NotBlank(message = "project end date time is a mandatory field")
    @NotEmpty(message = "project end date time is a mandatory field")
    private String projectEndTimestamp;
    @NotBlank(message = "user href is a mandatory field")
    @NotEmpty(message = "user href is a mandatory field")
    private String userHref;
    @NotBlank(message = "agreement status is a mandatory field")
    @NotEmpty(message = "agreement status is a mandatory field")
    private Boolean agreementStatus;
    @NotBlank(message = "price is a mandatory field")
    @NotEmpty(message = "price is a mandatory field")
    private String price;
    private List<ProjectFileInfoDocument> projectFileInfoDocuments = new ArrayList<>();
    private String createdTimestamp;
}
