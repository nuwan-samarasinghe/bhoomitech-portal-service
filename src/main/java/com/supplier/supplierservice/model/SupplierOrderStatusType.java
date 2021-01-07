package com.supplier.supplierservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SupplierOrderStatusType {
    DRAFT("draft", 0),
    SUBMITTED("submitted", 1),
    APPROVED("approved", 2);

    @Getter
    private final String orderStatus;

    @Getter
    private final Integer statusId;
}
