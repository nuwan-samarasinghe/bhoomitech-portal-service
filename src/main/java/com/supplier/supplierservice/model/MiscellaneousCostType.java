package com.supplier.supplierservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "miscellaneous_cost_type")
@AllArgsConstructor
@NoArgsConstructor
public class MiscellaneousCostType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "misc_type")
    private String miscType;
}
