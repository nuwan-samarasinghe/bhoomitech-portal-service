package com.supplier.supplierservice.service;

import com.supplier.supplierservice.model.MiscellaneousCostType;
import com.supplier.supplierservice.repository.MiscellaneousCostTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiscCostTypeService {

    private final MiscellaneousCostTypeRepository miscellaneousCostTypeRepository;

    public MiscCostTypeService(MiscellaneousCostTypeRepository miscellaneousCostTypeRepository) {
        this.miscellaneousCostTypeRepository = miscellaneousCostTypeRepository;
    }

    public List<MiscellaneousCostType> getAllMiscellaneousCostTypes() {
        return this.miscellaneousCostTypeRepository.findAll();
    }

    public MiscellaneousCostType createMiscType(String miscType) {
        MiscellaneousCostType miscellaneousCostType = new MiscellaneousCostType();
        miscellaneousCostType.setMiscType(miscType);
        return this.miscellaneousCostTypeRepository.save(miscellaneousCostType);
    }

    public MiscellaneousCostType updateMiscType(MiscellaneousCostType miscType) {
        return this.miscellaneousCostTypeRepository.save(miscType);
    }
}
