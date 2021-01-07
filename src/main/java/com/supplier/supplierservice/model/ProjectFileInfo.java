package com.supplier.supplierservice.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "project_file_info")
public class ProjectFileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name ="project_file_type")
    private ProjectFileType projectFileType;
    @Column(name ="base_point_id")
    private String basePointId;
    @Column(name ="file_name")
    private String fileName;
    @Column(name ="file_location")
    private String fileLocation;
    @Column(name ="antenna_height")
    private Long antennaHeight;
    @Column(name ="antenna_brand")
    private String antennaBrand;
    @Column(name ="antenna_model")
    private String antennaModel;
    @Column(name ="gps_coordinates_lat")
    private String gpsCoordinatesLat;
    @Column(name ="gps_coordinates_lon")
    private String gpsCoordinatesLon;
    @Column(name ="gps_coordinates_z")
    private String gpsCoordinatesZ;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
