package com.supplier.supplierservice.apidocs.supplier;

import com.supplier.supplierservice.model.Supplier;
import lombok.Data;

@Data
public class SupplierDocument extends Supplier {
    private Integer rating;
}
