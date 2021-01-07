package com.supplier.supplierservice.service;

import com.supplier.supplierservice.apidocs.supplier.*;
import com.supplier.supplierservice.model.*;
import com.supplier.supplierservice.repository.*;
import com.supplier.supplierservice.util.SupplierConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SupplierOrderService {


    private final SupplierRepository supplierRepository;

    private final SupplierConverter supplierConverter;

    private final SupplierOrderRepository supplierOrderRepository;

    private final SupplierOrderImageRepository supplierOrderImageRepository;

    private final MiscellaneousCostTypeRepository miscellaneousCostTypeRepository;

    private final SupplierOrderMiscCostRepository supplierOrderMiscCostRepository;

    private final SupplierOrderRawMaterialsRepository supplierOrderRawMaterialsRepository;

    public SupplierOrderService(SupplierOrderRepository supplierOrderRepository, SupplierRepository supplierRepository, SupplierConverter supplierConverter, SupplierOrderImageRepository supplierOrderImageRepository, MiscellaneousCostTypeRepository miscellaneousCostTypeRepository, SupplierOrderMiscCostRepository supplierOrderMiscCostRepository, SupplierOrderRawMaterialsRepository supplierOrderRawMaterialsRepository) {
        this.supplierOrderRepository = supplierOrderRepository;
        this.supplierRepository = supplierRepository;
        this.supplierConverter = supplierConverter;
        this.supplierOrderImageRepository = supplierOrderImageRepository;
        this.miscellaneousCostTypeRepository = miscellaneousCostTypeRepository;
        this.supplierOrderMiscCostRepository = supplierOrderMiscCostRepository;
        this.supplierOrderRawMaterialsRepository = supplierOrderRawMaterialsRepository;
    }

    public List<SupplierOrder> getAllSupplierOrders() {
        return this.supplierOrderRepository.findAll();
    }

    public List<SupplierOrder> getOrderQuery(SupplierOrderQueryDocument queryDocument) {
        return this.supplierOrderRepository.findAllByOrderDateBetweenOrOrderStatusOrSupplier_SupplierNameLike(
                queryDocument.getOrderDateStart(),
                queryDocument.getOrderDateEnd(),
                queryDocument.getOrderStatus(),
                queryDocument.getSupplierName());
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity updateOrderStatus(List<SupplierOrderStatusUpdateDocument> updateDocuments) {
        for (SupplierOrderStatusUpdateDocument orderDocument : updateDocuments) {
            if (orderDocument.getOrderId() != null && orderDocument.getSupplierOrderStatusType() != null) {
                Optional<SupplierOrder> supplierOrder = this.supplierOrderRepository.findById(orderDocument.getOrderId());
                if (supplierOrder.isPresent()) {
                    SupplierOrder order = supplierOrder.get();
                    order.setOrderStatus(orderDocument.getSupplierOrderStatusType());
                    SupplierOrder saveSupplierOrder = this.supplierOrderRepository.save(order);
                    order.setModifiedUserHref(orderDocument.getModifiedUserHref());
                    log.info("SUPPLIER-ORDER : successfully updated the order with {}", saveSupplierOrder.getOrderStatus());
                }
            }
        }
        return ResponseEntity.ok().build();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<String> createNewOrder(SupplierOrderDocument orderDocument) {
        Optional<Supplier> supplier = this.supplierRepository
                .findById(orderDocument.getSupplierId());
        if (supplier.isPresent()) {
            // create supplier order
            SupplierOrder supplierOrder = this.supplierConverter
                    .getGenerateNewSupplierOrderDocumentSupplierOrderFunction()
                    .apply(orderDocument);
            supplierOrder.setSupplier(supplier.get());
            SupplierOrder saveOrder = this.supplierOrderRepository.save(supplierOrder);
            log.info("SUPPLIER-ORDER : supplier order created order id is {}", saveOrder.getId());
            // create supplier order bills
            List<SupplierOrderImage> supplierOrderImages = orderDocument
                    .getSupplierOrderBillImageDocuments()
                    .stream()
                    .map(this.supplierConverter.getNewSupplierOrderDocumentSupplierOrderImageFunction())
                    .peek(supplierOrderImage -> supplierOrderImage.setSupplierOrder(saveOrder))
                    .collect(Collectors.toList());
            List<SupplierOrderImage> savedOrderImages = this.supplierOrderImageRepository.saveAll(supplierOrderImages);
            log.info("SUPPLIER-ORDER : supplier order bill entries created id's are {}",
                    savedOrderImages.stream().map(SupplierOrderImage::getId).collect(Collectors.toList()));
            // get all misc cost types map
            Map<Integer, String> miscCostTypes = this.miscellaneousCostTypeRepository.findAll()
                    .stream()
                    .collect(Collectors.toMap(MiscellaneousCostType::getId, MiscellaneousCostType::getMiscType));
            // create misc cost for the supplier
            List<SupplierOrderMiscCost> supplierOrderMiscCosts = new ArrayList<>();
            orderDocument.getMiscSupplierOrderDocuments()
                    .forEach(miscSupplierOrderDocument -> supplierOrderMiscCosts
                            .add(this.supplierConverter.getMiscSupplierOrderDocumentSupplierOrderMiscCostFunction()
                                    .apply(miscSupplierOrderDocument, miscCostTypes)));
            List<SupplierOrderMiscCost> savedOrderMiscCosts = this.supplierOrderMiscCostRepository.saveAll(supplierOrderMiscCosts
                    .stream()
                    .peek(supplierOrderMiscCost -> supplierOrderMiscCost.setSupplierOrder(supplierOrder))
                    .collect(Collectors.toList()));
            log.info("SUPPLIER-ORDER : supplier order misc entries created id's are {}",
                    savedOrderMiscCosts.stream().map(SupplierOrderMiscCost::getId).collect(Collectors.toList()));
            // adding raw materials
            List<SupplierOrderRawMaterials> savedSupplierOrderRawMaterials = this.supplierOrderRawMaterialsRepository.saveAll(orderDocument.getOrderRawMaterialDocuments()
                    .stream()
                    .map(this.supplierConverter.getSupplierOrderRawMaterialDocumentSupplierOrderRawMaterialsFunction())
                    .peek(supplierOrderRawMaterials -> supplierOrderRawMaterials.setSupplierOrder(supplierOrder))
                    .collect(Collectors.toList()));
            log.info("SUPPLIER-ORDER : supplier order raw materials entries created id's are {}",
                    savedSupplierOrderRawMaterials.stream().map(SupplierOrderRawMaterials::getId).collect(Collectors.toList()));
            return ResponseEntity.ok("Order successfully placed");
        } else {
            return ResponseEntity.badRequest()
                    .body("Selected supplier not available.");
        }
    }

    public ResponseEntity updateSupplierOrder(SupplierOrderDocument orderDocument) {
        Boolean isValidDocument = validateSupplierOrderDocument(orderDocument);
        if (isValidDocument) {
            Optional<SupplierOrder> supplierOrder = this.supplierOrderRepository.findById(orderDocument.getOrderId());
            if (supplierOrder.isPresent()) {

                // update raw materials
                List<SupplierOrderRawMaterials> updatedOrderRawMaterials = null;
                if (orderDocument.getOrderRawMaterialDocuments() != null) {
                    List<SupplierOrderRawMaterials> orderRawMaterials = supplierOrderRawMaterialsRepository.findAllById(orderDocument.getOrderRawMaterialDocuments()
                            .stream()
                            .map(SupplierOrderRawMaterialDocument::getRawMaterialId).collect(Collectors.toList()));
                    updatedOrderRawMaterials = supplierConverter.updateRawMaterials(orderRawMaterials, orderDocument.getOrderRawMaterialDocuments());
                    this.supplierOrderRawMaterialsRepository.saveAll(updatedOrderRawMaterials);
                    log.info("SUPPLIER-ORDER : raw materials updated.");
                }

                // update order bill images
                List<SupplierOrderImage> updatedSupplierOrderImages;
                if (orderDocument.getSupplierOrderBillImageDocuments() != null) {
                    List<SupplierOrderImage> supplierOrderImages = supplierOrderImageRepository.findAllById(orderDocument.getSupplierOrderBillImageDocuments()
                            .stream().map(SupplierOrderBillImageDocument::getBillImageId)
                            .collect(Collectors.toList()));
                    updatedSupplierOrderImages = supplierConverter.updateSupplierOrderImage(supplierOrderImages, orderDocument.getSupplierOrderBillImageDocuments());
                    supplierOrderImageRepository.saveAll(updatedSupplierOrderImages);
                    log.info("SUPPLIER-ORDER : supplier order bill images updated.");
                }

                // supplier order misc cost update
                List<SupplierOrderMiscCost> updateMiscellaneousCostTypes;
                if (orderDocument.getMiscSupplierOrderDocuments() != null) {
                    List<SupplierOrderMiscCost> miscellaneousCostTypes = supplierOrderMiscCostRepository.findAllById(orderDocument.getMiscSupplierOrderDocuments()
                            .stream()
                            .map(MiscSupplierOrderDocument::getMiscId).collect(Collectors.toList()));
                    updateMiscellaneousCostTypes = supplierConverter.updateMiscellaneousCostTypes(
                            miscellaneousCostTypes,
                            orderDocument.getMiscSupplierOrderDocuments(),
                            miscellaneousCostTypeRepository);
                    supplierOrderMiscCostRepository.saveAll(updateMiscellaneousCostTypes);
                    log.info("SUPPLIER-ORDER : supplier misc updated.");
                }

                SupplierOrder updateSupplierOrder = supplierConverter
                        .updateSupplierOrder(supplierOrder.get(), orderDocument, updatedOrderRawMaterials);
                supplierOrderRepository.save(updateSupplierOrder);
                log.info("SUPPLIER-ORDER : order updated.");
            } else {
                return ResponseEntity.badRequest().body("Invalid content provided to update");
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid content provided to update");
        }
        return ResponseEntity.ok().build();
    }

    private Boolean validateSupplierOrderDocument(SupplierOrderDocument orderDocument) {
        if (orderDocument.getOrderId() == null) {
            return Boolean.FALSE;
        }
        if (orderDocument.getSupplierOrderBillImageDocuments() != null &&
                !orderDocument.getSupplierOrderBillImageDocuments().isEmpty()) {
            for (SupplierOrderBillImageDocument supplierOrderBillImageDocument : orderDocument.getSupplierOrderBillImageDocuments()) {
                if (supplierOrderBillImageDocument.getBillImageId() == null) {
                    return Boolean.FALSE;
                }
            }
        }
        if (orderDocument.getMiscSupplierOrderDocuments() != null &&
                !orderDocument.getMiscSupplierOrderDocuments().isEmpty()) {
            for (MiscSupplierOrderDocument miscSupplierOrderDocument : orderDocument.getMiscSupplierOrderDocuments()) {
                if (miscSupplierOrderDocument.getMiscId() == null) {
                    return Boolean.FALSE;
                }
            }
        }
        if (orderDocument.getOrderRawMaterialDocuments() != null &&
                !orderDocument.getOrderRawMaterialDocuments().isEmpty()) {
            for (SupplierOrderRawMaterialDocument orderRawMaterialDocument : orderDocument.getOrderRawMaterialDocuments()) {
                if (orderRawMaterialDocument.getRawMaterialId() == null) {
                    return Boolean.FALSE;
                }
            }
        }
        return Boolean.TRUE;
    }
}
