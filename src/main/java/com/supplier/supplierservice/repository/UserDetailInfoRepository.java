package com.supplier.supplierservice.repository;

import com.supplier.supplierservice.model.UserDetailInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDetailInfoRepository extends JpaRepository<UserDetailInfo, Long> {
    Optional<UserDetailInfo> findByUserHref(String userHref);

    List<UserDetailInfo> findAllByUserHrefIn(List<String> userHrefs);
}
