package com.supplier.supplierservice.apidocs.supplier;

import lombok.Data;
import lombok.NonNull;

@Data
public class SupplierOrderBillImageDocument {
    private Integer billImageId;
    @NonNull
    private String imageName;
    @NonNull
    private String image;
}
