package com.supplier.supplierservice.service;

import com.supplier.supplierservice.apidocs.supplier.SupplierDocument;
import com.supplier.supplierservice.model.Supplier;
import com.supplier.supplierservice.model.SupplierOrder;
import com.supplier.supplierservice.repository.SupplierOrderRepository;
import com.supplier.supplierservice.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    private final SupplierOrderRepository supplierOrderRepository;

    public SupplierService(SupplierRepository supplierRepository,
                           SupplierOrderRepository supplierOrderRepository) {
        this.supplierRepository = supplierRepository;
        this.supplierOrderRepository = supplierOrderRepository;
    }

    public List<SupplierDocument> getAllSupplierNames() {
        List<SupplierDocument> supplierDocuments = new ArrayList<>();
        List<Supplier> all = supplierRepository.findAll();
        all.forEach(supplier -> {
            SupplierDocument supplierDocument = new SupplierDocument();
            supplierDocument.setId(supplier.getId());
            supplierDocument.setSupplierName(supplier.getSupplierName());
            supplierDocuments.add(supplierDocument);
        });
        return supplierDocuments;
    }

    public List<SupplierDocument> getAllSuppliers() {
        List<SupplierDocument> supplierDocuments = new ArrayList<>();
        List<Supplier> all = supplierRepository.findAll();
        all.forEach(supplier -> {
            SupplierDocument supplierDocument = new SupplierDocument();
            OptionalDouble average = supplierOrderRepository.findAllBySupplier_Id(supplier.getId())
                    .stream().mapToInt(SupplierOrder::getOrderRating)
                    .average();
            supplierDocument.setId(supplier.getId());
            supplierDocument.setSupplierAddress(supplier.getSupplierAddress());
            supplierDocument.setSupplierEmail(supplier.getSupplierEmail());
            supplierDocument.setSupplierName(supplier.getSupplierName());
            supplierDocument.setSupplierPhone(supplier.getSupplierPhone());
            if (average.isPresent()) {
                supplierDocument.setRating((int) average.getAsDouble());
            }
            supplierDocuments.add(supplierDocument);
        });
        return supplierDocuments;
    }

    public List<SupplierDocument> getSupplierByName(String supplierName) {
        List<SupplierDocument> supplierDocuments = new ArrayList<>();
        if (Objects.nonNull(supplierName)) {
            supplierRepository.findBySupplierNameLike(supplierName).forEach(supplier -> {
                SupplierDocument supplierDocument = new SupplierDocument();
                OptionalDouble average = supplierOrderRepository.findAllBySupplier_Id(supplier.getId())
                        .stream().mapToInt(SupplierOrder::getOrderRating)
                        .average();
                supplierDocument.setId(supplier.getId());
                supplierDocument.setSupplierAddress(supplier.getSupplierAddress());
                supplierDocument.setSupplierEmail(supplier.getSupplierEmail());
                supplierDocument.setSupplierName(supplier.getSupplierName());
                supplierDocument.setSupplierPhone(supplier.getSupplierPhone());
                if (average.isPresent()) {
                    supplierDocument.setRating((int) average.getAsDouble());
                }
                supplierDocuments.add(supplierDocument);
            });
            return supplierDocuments;
        }
        return supplierDocuments;
    }

    public Optional<Supplier> getSupplierById(Integer id) {
        return supplierRepository.findById(id);
    }

    public Supplier createOrUpdateSupplier(Supplier supplier) {
        return supplierRepository.saveAndFlush(supplier);
    }
}
