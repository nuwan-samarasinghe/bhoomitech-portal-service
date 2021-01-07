package com.bhoomitech.portalservice.repository;

import com.bhoomitech.portalservice.model.UserDetailInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDetailInfoRepository extends JpaRepository<UserDetailInfo, Long> {
    Optional<UserDetailInfo> findByUserHref(String userHref);

    List<UserDetailInfo> findAllByUserHrefIn(List<String> userHrefs);
}
