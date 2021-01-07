package com.supplier.supplierservice.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "supplier_order")
public class SupplierOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Date orderDate;
    private Date deliveryDate;
    private SupplierOrderStatusType orderStatus;
    private Integer orderRating;
    @Column(name = "order_comment")
    private String orderComment;
    private Double orderPrice;
    private Double orderDiscount;
    private String createUserHref;
    private String modifiedUserHref;
    @CreationTimestamp
    private Timestamp createdTimeStamp;
    @UpdateTimestamp
    private Timestamp modifiedTimeStamp;
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
}
