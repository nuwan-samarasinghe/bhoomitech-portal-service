package com.supplier.supplierservice.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "supplier_order_misc_cost")
public class SupplierOrderMiscCost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Double miscAmount;

    @ManyToOne
    @JoinColumn(name = "supplier_order_id")
    private SupplierOrder supplierOrder;

    @ManyToOne
    @JoinColumn(name = "miscellaneous_cost_type_id")
    private MiscellaneousCostType miscellaneousCostType;
}
