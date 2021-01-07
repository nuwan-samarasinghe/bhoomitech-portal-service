package com.supplier.supplierservice.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "supplier_order_image")
public class SupplierOrderImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String fileName;
    @Lob
    private String image;

    @ManyToOne
    @JoinColumn(name = "supplier_order_id")
    private SupplierOrder supplierOrder;
}
