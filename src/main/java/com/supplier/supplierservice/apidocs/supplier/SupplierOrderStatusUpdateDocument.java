package com.supplier.supplierservice.apidocs.supplier;

import com.supplier.supplierservice.model.SupplierOrderStatusType;
import lombok.Data;
import lombok.NonNull;

@Data
public class SupplierOrderStatusUpdateDocument {
    @NonNull
    private Integer orderId;
    @NonNull
    private SupplierOrderStatusType supplierOrderStatusType;
    @NonNull
    private String modifiedUserHref;
}
