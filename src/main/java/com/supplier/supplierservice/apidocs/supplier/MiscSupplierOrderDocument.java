package com.supplier.supplierservice.apidocs.supplier;

import lombok.Data;

@Data
public class MiscSupplierOrderDocument {
    private Integer miscId;
    private Integer miscTypeId;
    private Double amount;
}
