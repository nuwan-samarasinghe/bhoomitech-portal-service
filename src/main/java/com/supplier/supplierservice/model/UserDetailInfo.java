package com.supplier.supplierservice.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_detail_info")
public class UserDetailInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "company_number")
    private String companyNumber;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "user_address")
    private String userAddress;

    @Column(name = "user_href")
    private String userHref;
}
