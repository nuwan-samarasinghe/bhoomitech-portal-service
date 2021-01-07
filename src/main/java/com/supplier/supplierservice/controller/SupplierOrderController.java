package com.supplier.supplierservice.controller;

import com.supplier.supplierservice.apidocs.supplier.SupplierOrderDocument;
import com.supplier.supplierservice.apidocs.supplier.SupplierOrderQueryDocument;
import com.supplier.supplierservice.apidocs.supplier.SupplierOrderStatusUpdateDocument;
import com.supplier.supplierservice.model.SupplierOrder;
import com.supplier.supplierservice.service.SupplierOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/supplier/order")
public class SupplierOrderController {

    private final SupplierOrderService supplierOrderService;

    public SupplierOrderController(SupplierOrderService supplierOrderService) {
        this.supplierOrderService = supplierOrderService;
    }

    @PreAuthorize(value = "hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @GetMapping(value = "/all")
    public List<SupplierOrder> getAllSupplierOrders() {
        return this.supplierOrderService.getAllSupplierOrders();
    }

    @PreAuthorize(value = "hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @GetMapping(value = "/query")
    public List<SupplierOrder> getAllSupplierOrders(@RequestBody SupplierOrderQueryDocument queryDocument) {
        return this.supplierOrderService.getOrderQuery(queryDocument);
    }

    @PreAuthorize(value = "hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/create")
    public ResponseEntity<String> createSupplierOrders(@RequestBody SupplierOrderDocument orderDocument) {
        return this.supplierOrderService.createNewOrder(orderDocument);
    }

    @PreAuthorize(value = "hasRole('ROLE_admin')")
    @PutMapping(value = "/status/update")
    public ResponseEntity updateSupplierOrderStatus(@RequestBody List<SupplierOrderStatusUpdateDocument> orderDocuments) {
        return this.supplierOrderService.updateOrderStatus(orderDocuments);
    }

    @PreAuthorize(value = "hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PutMapping(value = "/update")
    public ResponseEntity updateSupplierOrders(@RequestBody SupplierOrderDocument orderDocument) {
        return this.supplierOrderService.updateSupplierOrder(orderDocument);
    }

}
