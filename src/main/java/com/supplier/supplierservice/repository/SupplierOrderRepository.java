package com.supplier.supplierservice.repository;

import com.supplier.supplierservice.model.SupplierOrder;
import com.supplier.supplierservice.model.SupplierOrderStatusType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface SupplierOrderRepository extends JpaRepository<SupplierOrder, Integer> {
    List<SupplierOrder> findAllByOrderDateBetweenOrOrderStatusOrSupplier_SupplierNameLike(
            Date start,
            Date end,
            SupplierOrderStatusType supplierOrderStatusType,
            String supplierName);

    List<SupplierOrder> findAllBySupplier_Id(Integer supplierId);
}
