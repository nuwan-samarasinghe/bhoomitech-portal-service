package com.bhoomitech.portalservice.repository;

import com.bhoomitech.portalservice.model.ProjectFileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectFileInfoRepository extends JpaRepository<ProjectFileInfo, Long> {
    Optional<ProjectFileInfo> findByProject_ProjectName(String projectName);

    Optional<ProjectFileInfo> findByProject_Id(Long projectId);

    List<ProjectFileInfo> findAllByProject_UserHref(String userHref);
}
