package com.supplier.supplierservice.service;

import com.supplier.supplierservice.model.UserDetailInfo;
import com.supplier.supplierservice.repository.UserDetailInfoRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserDetailInfoService {
    private final UserDetailInfoRepository userDetailInfoRepository;

    public UserDetailInfoService(UserDetailInfoRepository userDetailInfoRepository) {
        this.userDetailInfoRepository = userDetailInfoRepository;
    }

    public List<UserDetailInfo> getAllUserDetailInfos() {
        List<UserDetailInfo> userDetailInfos = userDetailInfoRepository.findAll();
        log.info("getting all the user infos {}", userDetailInfos.size());
        return userDetailInfos;
    }

    public Optional<UserDetailInfo> getAllUserDetailInfoByUserHref(@NonNull String userHref) {
        Optional<UserDetailInfo> userDetailInfos = userDetailInfoRepository.findByUserHref(userHref);
        log.info("getting all the user infos {}", userDetailInfos);
        return userDetailInfos;
    }

    @Transactional(rollbackFor = Exception.class)
    public UserDetailInfo saveOrUpdateUserDetailInfo(@NonNull UserDetailInfo userDetailInfo) {
        log.info("saving the user details info {}", userDetailInfo);
        return userDetailInfoRepository.save(userDetailInfo);
    }

    public List<UserDetailInfo> getAllUserDetailsForUserHrefs(List<String> userHrefs) {
        log.info("get user details for {}", userHrefs);
        return userDetailInfoRepository.findAllByUserHrefIn(userHrefs);
    }
}
