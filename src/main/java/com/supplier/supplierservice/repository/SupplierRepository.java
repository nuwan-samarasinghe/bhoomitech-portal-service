package com.supplier.supplierservice.repository;

import com.supplier.supplierservice.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    List<Supplier> findBySupplierNameLike(String supplierName);
}
