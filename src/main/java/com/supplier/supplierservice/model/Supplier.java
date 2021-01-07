package com.supplier.supplierservice.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "supplier_name")
    private String supplierName;
    @Column(name = "supplier_address")
    private String supplierAddress;
    @Column(name = "supplier_phone")
    private String supplierPhone;
    @Column(name = "supplier_email")
    private String supplierEmail;
}
