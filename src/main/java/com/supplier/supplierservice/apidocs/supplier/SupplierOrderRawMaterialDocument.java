package com.supplier.supplierservice.apidocs.supplier;

import lombok.Data;
import lombok.NonNull;

@Data
public class SupplierOrderRawMaterialDocument {
    private Integer rawMaterialId;
    @NonNull
    private String rawMaterialHref;
    private String serialNumber;
    @NonNull
    private Double qty;
    @NonNull
    private Double itemCost;
    private Double sellingPrice;
}
