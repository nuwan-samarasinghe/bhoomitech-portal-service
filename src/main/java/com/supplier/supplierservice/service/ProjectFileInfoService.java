package com.supplier.supplierservice.service;

import com.supplier.supplierservice.model.ProjectFileInfo;
import com.supplier.supplierservice.repository.ProjectFileInfoRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProjectFileInfoService {

    private final ProjectFileInfoRepository projectFileInfoRepository;

    public ProjectFileInfoService(ProjectFileInfoRepository projectFileInfoRepository) {
        this.projectFileInfoRepository = projectFileInfoRepository;
    }

    public List<ProjectFileInfo> getProjectInfos() {
        List<ProjectFileInfo> fileInfos = projectFileInfoRepository.findAll();
        log.info("getting all the project information {}", fileInfos.size());
        return fileInfos;
    }

    public List<ProjectFileInfo> getProjectInfosByUserHref(@NonNull String userHref) {
        List<ProjectFileInfo> fileInfos = projectFileInfoRepository.findAllByProject_UserHref(userHref);
        log.info("getting project information for user href {} and {}", userHref, fileInfos.size());
        return fileInfos;
    }

    public Optional<ProjectFileInfo> getProjectFileInfoById(@NonNull Long projectFileInfoId) {
        Optional<ProjectFileInfo> fileInfoRepositoryById = projectFileInfoRepository.findById(projectFileInfoId);
        log.info("getting project information for {}", fileInfoRepositoryById);
        return fileInfoRepositoryById;
    }

    public Optional<ProjectFileInfo> getProjectFileInfoByProjectId(@NonNull Long projectId) {
        Optional<ProjectFileInfo> projectFileInfoByProjectId = projectFileInfoRepository.findByProject_Id(projectId);
        log.info("getting project information for {}", projectFileInfoByProjectId);
        return projectFileInfoByProjectId;
    }

    public Optional<ProjectFileInfo> getProjectFileInfoByProjectId(@NonNull String projectName) {
        Optional<ProjectFileInfo> projectFileInfoByProjectProjectName = projectFileInfoRepository.findByProject_ProjectName(projectName);
        log.info("getting project information for {}", projectFileInfoByProjectProjectName);
        return projectFileInfoByProjectProjectName;
    }

    @Transactional(rollbackFor = Exception.class)
    public ProjectFileInfo saveOrUpdateProjectFileInfo(@NonNull ProjectFileInfo projectFileInfo) {
        log.info("saving the project {}", projectFileInfo);
        return projectFileInfoRepository.save(projectFileInfo);
    }
}
