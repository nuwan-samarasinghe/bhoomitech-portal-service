package com.supplier.supplierservice.apidocs.supplier;

import com.supplier.supplierservice.model.SupplierOrderStatusType;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
import java.util.List;

@Data
public class SupplierOrderDocument {

    private Integer orderId;

    @NonNull
    private Integer supplierId;
    @NonNull
    private SupplierOrderStatusType supplierOrderStatusType;
    private Double discount;
    @NonNull
    private Date orderDate;
    private Date deliveryDate;
    @NonNull
    private Integer rating;
    @NonNull
    private String comment;
    @NonNull
    private String createdUserHref;
    @NonNull
    private String modifiedUserHref;

    @NonNull
    private List<SupplierOrderBillImageDocument> supplierOrderBillImageDocuments;

    private List<MiscSupplierOrderDocument> miscSupplierOrderDocuments;

    @NonNull
    private List<SupplierOrderRawMaterialDocument> orderRawMaterialDocuments;

}
