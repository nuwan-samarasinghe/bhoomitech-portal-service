package com.bhoomitech.portalservice.repository;

import com.bhoomitech.portalservice.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByProjectName(String projectName);

    List<Project> findAllByUserHref(String userHref);
}
