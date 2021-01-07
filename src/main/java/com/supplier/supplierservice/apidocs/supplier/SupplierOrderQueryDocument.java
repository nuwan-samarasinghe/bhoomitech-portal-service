package com.supplier.supplierservice.apidocs.supplier;

import com.supplier.supplierservice.model.SupplierOrderStatusType;
import lombok.Data;

import java.sql.Date;

@Data
public class SupplierOrderQueryDocument {
    private String supplierName;
    private SupplierOrderStatusType orderStatus;
    private Date orderDateStart;
    private Date orderDateEnd;
}
