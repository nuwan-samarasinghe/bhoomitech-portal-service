package com.supplier.supplierservice.controller;

import com.supplier.supplierservice.apidocs.supplier.SupplierDocument;
import com.supplier.supplierservice.model.Supplier;
import com.supplier.supplierservice.service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/supplier", name = "Supplier Management")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PreAuthorize(value = "hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @GetMapping(value = "/all", name = "Get All Suppliers")
    public ResponseEntity<List<SupplierDocument>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @PreAuthorize(value = "hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @GetMapping(value = "/names", name = "Get All Supplier Names")
    public ResponseEntity<List<SupplierDocument>> getAllSupplierNames() {
        return ResponseEntity.ok(supplierService.getAllSupplierNames());
    }

    @PreAuthorize(value = "hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @GetMapping(value = "/name/{supplier-name}", name = "Get Suppliers By Name")
    public ResponseEntity<List<SupplierDocument>> getSupplierByName(@PathVariable("supplier-name") String supplierName) {
        return ResponseEntity.ok(supplierService.getSupplierByName("%" + supplierName + "%"));
    }

    @PreAuthorize(value = "hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @GetMapping(value = "/id/{supplier-id}", name = "Get Suppliers By Id")
    public ResponseEntity<Supplier> getSupplierByName(@PathVariable("supplier-id") Integer id) {
        return supplierService.getSupplierById(id)
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PreAuthorize(value = "hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/create", name = "Create New Supplier")
    public ResponseEntity<Supplier> createNewSupplier(@RequestBody Supplier supplier) {
        return ResponseEntity.ok(supplierService.createOrUpdateSupplier(supplier));
    }

    @PreAuthorize(value = "hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PutMapping(value = "/update", name = "Update New Supplier")
    public ResponseEntity<Supplier> updateNewSupplier(@RequestBody Supplier supplier) {
        return ResponseEntity.ok(supplierService.createOrUpdateSupplier(supplier));
    }
}
