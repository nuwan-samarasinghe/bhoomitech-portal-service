package com.supplier.supplierservice.controller;

import com.supplier.supplierservice.model.UserDetailInfo;
import com.supplier.supplierservice.service.UserDetailInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/user", name = "User Details Controller")
public class UserDetailsController {

    private final UserDetailInfoService userDetailInfoService;

    public UserDetailsController(UserDetailInfoService userDetailInfoService) {
        this.userDetailInfoService = userDetailInfoService;
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @PostMapping(value = "/list", name = "Get User Details For Given User Hrefs")
    private @ResponseBody
    List<UserDetailInfo> getAllUserDetailInfosForUserHrefs(@RequestBody List<String> userHrefs) {
        return userDetailInfoService.getAllUserDetailsForUserHrefs(userHrefs);
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/create", name = "Create User Details For Given User Hrefs")
    private @ResponseBody
    UserDetailInfo createUserDetailInfo(@RequestBody UserDetailInfo userDetailInfo) {
        return userDetailInfoService.saveOrUpdateUserDetailInfo(userDetailInfo);
    }

}
