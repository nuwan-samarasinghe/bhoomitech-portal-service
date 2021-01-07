package com.supplier.supplierservice.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "supplier_order_raw_materials")
public class SupplierOrderRawMaterials {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String rawMaterialHref;
    private String serialNumber;
    private Double quantity;
    private Double itemCost;
    private Double sellingPrice;
    @ManyToOne
    @JoinColumn(name = "supplier_order_id")
    private SupplierOrder supplierOrder;
}
