package com.bhoomitech.portalservice.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "project_name", unique = true)
    private String projectName;
    @Column(name = "project_start_timestamp")
    private Timestamp projectStartTimestamp;
    @Column(name = "project_end_timestamp")
    private Timestamp projectEndTimestamp;
    @Column(name = "user_href")
    private String userHref;
    @Column(name = "agreement_status")
    private Boolean agreementStatus;
    @Column(name = "price")
    private String price;
    @Column(name = "created_timestamp")
    private Timestamp createdTimestamp;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private List<ProjectFileInfo> fileInfos;
}
