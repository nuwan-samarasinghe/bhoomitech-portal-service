package com.supplier.supplierservice.util;

import com.supplier.supplierservice.apidocs.supplier.MiscSupplierOrderDocument;
import com.supplier.supplierservice.apidocs.supplier.SupplierOrderBillImageDocument;
import com.supplier.supplierservice.apidocs.supplier.SupplierOrderDocument;
import com.supplier.supplierservice.apidocs.supplier.SupplierOrderRawMaterialDocument;
import com.supplier.supplierservice.model.*;
import com.supplier.supplierservice.repository.MiscellaneousCostTypeRepository;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

@Component
public class SupplierConverter {

    @Getter
    Function<SupplierOrderDocument, SupplierOrder> generateNewSupplierOrderDocumentSupplierOrderFunction = orderDocument -> {
        SupplierOrder supplierOrder = new SupplierOrder();
        if (orderDocument.getOrderDate() != null) {
            supplierOrder.setOrderDate(orderDocument.getOrderDate());
        }
        if (orderDocument.getDeliveryDate() != null) {
            supplierOrder.setDeliveryDate(orderDocument.getDeliveryDate());
        }
        if (orderDocument.getSupplierOrderStatusType() != null) {
            supplierOrder.setOrderStatus(orderDocument.getSupplierOrderStatusType());
        }
        if (orderDocument.getRating() != null) {
            supplierOrder.setOrderRating(orderDocument.getRating());
        }
        if (orderDocument.getComment() != null) {
            supplierOrder.setOrderComment(orderDocument.getComment());
        }
        Double orderTotalPrice = 0.0;
        for (SupplierOrderRawMaterialDocument supplierOrderRawMaterialDocument : orderDocument.getOrderRawMaterialDocuments()) {
            orderTotalPrice = orderTotalPrice + supplierOrderRawMaterialDocument.getQty() * supplierOrderRawMaterialDocument.getItemCost();
        }
        supplierOrder.setOrderPrice(orderTotalPrice);
        if (orderDocument.getDiscount() != null) {
            supplierOrder.setOrderDiscount(orderDocument.getDiscount());
        }
        if (orderDocument.getCreatedUserHref() != null) {
            supplierOrder.setCreateUserHref(orderDocument.getCreatedUserHref());
        }
        supplierOrder.setCreatedTimeStamp(Timestamp.from(new Date().toInstant()));
        return supplierOrder;
    };

    @Getter
    Function<SupplierOrderBillImageDocument, SupplierOrderImage> newSupplierOrderDocumentSupplierOrderImageFunction = supplierOrderBillImageDocument -> {
        SupplierOrderImage supplierOrderImage = new SupplierOrderImage();
        supplierOrderImage.setFileName(supplierOrderBillImageDocument.getImageName());
        supplierOrderImage.setImage(supplierOrderBillImageDocument.getImage());
        return supplierOrderImage;
    };

    @Getter
    BiFunction<MiscSupplierOrderDocument, Map<Integer, String>, SupplierOrderMiscCost> miscSupplierOrderDocumentSupplierOrderMiscCostFunction = (miscSupplierOrderDocument, miscellaneousCostType) -> {
        SupplierOrderMiscCost supplierOrderMiscCost = new SupplierOrderMiscCost();
        supplierOrderMiscCost.setMiscAmount(miscSupplierOrderDocument.getAmount());
        supplierOrderMiscCost.setMiscellaneousCostType(new MiscellaneousCostType(miscSupplierOrderDocument.getMiscTypeId(), miscellaneousCostType.get(miscSupplierOrderDocument.getMiscTypeId())));
        return supplierOrderMiscCost;
    };

    @Getter
    Function<SupplierOrderRawMaterialDocument, SupplierOrderRawMaterials> supplierOrderRawMaterialDocumentSupplierOrderRawMaterialsFunction = supplierOrderRawMaterialDocument -> {
        SupplierOrderRawMaterials supplierOrderRawMaterials = new SupplierOrderRawMaterials();
        supplierOrderRawMaterials.setRawMaterialHref(supplierOrderRawMaterialDocument.getRawMaterialHref());
        if (supplierOrderRawMaterialDocument.getSerialNumber() != null) {
            supplierOrderRawMaterials.setSerialNumber(supplierOrderRawMaterialDocument.getSerialNumber());
        }
        supplierOrderRawMaterials.setQuantity(supplierOrderRawMaterialDocument.getQty());
        supplierOrderRawMaterials.setItemCost(supplierOrderRawMaterialDocument.getItemCost());
        supplierOrderRawMaterials.setSellingPrice(supplierOrderRawMaterialDocument.getSellingPrice());
        return supplierOrderRawMaterials;
    };

    public SupplierOrder updateSupplierOrder(
            SupplierOrder supplierOrder,
            SupplierOrderDocument orderDocument,
            List<SupplierOrderRawMaterials> updatedOrderRawMaterials) {
        // updates the non dynamic parts
        if (orderDocument.getOrderDate() != null) {
            supplierOrder.setOrderDate(orderDocument.getOrderDate());
        }
        if (orderDocument.getDeliveryDate() != null) {
            supplierOrder.setDeliveryDate(orderDocument.getDeliveryDate());
        }
        if (orderDocument.getSupplierOrderStatusType() != null) {
            supplierOrder.setOrderStatus(orderDocument.getSupplierOrderStatusType());
        }
        if (orderDocument.getRating() != null) {
            supplierOrder.setOrderRating(orderDocument.getRating());
        }
        if (orderDocument.getComment() != null) {
            supplierOrder.setOrderComment(orderDocument.getComment());
        }
        if (orderDocument.getDiscount() != null) {
            supplierOrder.setOrderDiscount(orderDocument.getDiscount());
        }
        if (orderDocument.getModifiedUserHref() != null) {
            supplierOrder.setModifiedUserHref(orderDocument.getModifiedUserHref());
        }
        double orderTotalPrice = 0.0;
        for (SupplierOrderRawMaterials supplierOrderRawMaterials : updatedOrderRawMaterials) {
            orderTotalPrice = orderTotalPrice + (supplierOrderRawMaterials.getQuantity() * supplierOrderRawMaterials.getItemCost());
        }
        supplierOrder.setOrderPrice(orderTotalPrice);
        return supplierOrder;
    }

    public List<SupplierOrderRawMaterials> updateRawMaterials(List<SupplierOrderRawMaterials> orderRawMaterials, List<SupplierOrderRawMaterialDocument> orderRawMaterialDocuments) {
        orderRawMaterialDocuments
                .forEach(supplierOrderRawMaterialDocument -> orderRawMaterials
                        .stream()
                        .filter(supplierOrderRawMaterials -> supplierOrderRawMaterials.getId().equals(supplierOrderRawMaterialDocument.getRawMaterialId()))
                        .findAny().ifPresent(supplierOrderRawMaterials -> {
                            if (supplierOrderRawMaterialDocument.getItemCost() != null) {
                                supplierOrderRawMaterials.setItemCost(supplierOrderRawMaterialDocument.getItemCost());
                            }
                            if (supplierOrderRawMaterialDocument.getQty() != null) {
                                supplierOrderRawMaterials.setQuantity(supplierOrderRawMaterialDocument.getQty());
                            }
                            if (supplierOrderRawMaterialDocument.getSellingPrice() != null) {
                                supplierOrderRawMaterials.setSellingPrice(supplierOrderRawMaterialDocument.getSellingPrice());
                            }
                            if (supplierOrderRawMaterialDocument.getSerialNumber() != null) {
                                supplierOrderRawMaterials.setSerialNumber(supplierOrderRawMaterialDocument.getSerialNumber());
                            }
                        }));
        return orderRawMaterials;
    }

    public List<SupplierOrderImage> updateSupplierOrderImage(
            List<SupplierOrderImage> supplierOrderImages,
            List<SupplierOrderBillImageDocument> supplierOrderBillImageDocuments) {
        supplierOrderImages.forEach(supplierOrderImage -> supplierOrderBillImageDocuments
                .stream()
                .filter(supplierOrderBillImageDocument -> supplierOrderBillImageDocument.getBillImageId()
                        .equals(supplierOrderImage.getId()))
                .findAny().ifPresent(supplierOrderBillImageDocument -> {
                    if (supplierOrderBillImageDocument.getImage() != null) {
                        supplierOrderImage.setImage(supplierOrderBillImageDocument.getImage());
                    }
                    if (supplierOrderBillImageDocument.getImageName() != null) {
                        supplierOrderImage.setFileName(supplierOrderBillImageDocument.getImageName());
                    }
                }));
        return supplierOrderImages;
    }

    public List<SupplierOrderMiscCost> updateMiscellaneousCostTypes(List<SupplierOrderMiscCost> miscellaneousCostTypes,
                                                                    List<MiscSupplierOrderDocument> miscSupplierOrderDocuments,
                                                                    MiscellaneousCostTypeRepository miscellaneousCostTypeRepository) {
        miscellaneousCostTypes.forEach(miscellaneousCostType -> miscSupplierOrderDocuments
                .stream()
                .filter(miscSupplierOrderDocument -> miscSupplierOrderDocument.getMiscId().equals(miscellaneousCostType.getId()))
                .findAny().ifPresent(miscSupplierOrderDocument -> {
                    if (miscSupplierOrderDocument.getAmount() != null) {
                        miscellaneousCostType.setMiscAmount(miscSupplierOrderDocument.getAmount());
                    }
                    if (miscSupplierOrderDocument.getMiscTypeId() != null) {
                        Optional<MiscellaneousCostType> miscellaneousCostTypeOptional = miscellaneousCostTypeRepository.findById(miscSupplierOrderDocument.getMiscTypeId());
                        if (miscellaneousCostTypeOptional.isPresent()) {
                            miscellaneousCostType.setMiscellaneousCostType(miscellaneousCostTypeOptional.get());
                        } else {
                            throw new RuntimeException("not a valid misc cost type");
                        }
                    }
                }));
        return miscellaneousCostTypes;
    }
}
