package com.supplier.supplierservice.repository;

import com.supplier.supplierservice.model.ProjectFileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectFileInfoRepository extends JpaRepository<ProjectFileInfo, Long> {
    Optional<ProjectFileInfo> findByProject_ProjectName(String projectName);

    Optional<ProjectFileInfo> findByProject_Id(Long projectId);

    List<ProjectFileInfo> findAllByProject_UserHref(String userHref);
}
