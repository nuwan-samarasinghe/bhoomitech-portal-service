package com.supplier.supplierservice.controller;

import com.supplier.supplierservice.model.MiscellaneousCostType;
import com.supplier.supplierservice.service.MiscCostTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/supplier/misc/type", name = "Miscellaneous Cost Type Management")
public class MiscCostTypeController {

    private final MiscCostTypeService miscCostTypeService;

    public MiscCostTypeController(MiscCostTypeService miscCostTypeService) {
        this.miscCostTypeService = miscCostTypeService;
    }

    @PreAuthorize(value = "hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @GetMapping(value = "/all", name = "Get Misc Types")
    public ResponseEntity<List<MiscellaneousCostType>> getAllMiscTypes() {
        return ResponseEntity.ok(miscCostTypeService.getAllMiscellaneousCostTypes());
    }

    @PreAuthorize(value = "hasRole('ROLE_admin')")
    @PostMapping(value = "/create", name = "Create Misc Types")
    public ResponseEntity<MiscellaneousCostType> createMiscType(@RequestBody String miscType) {
        return ResponseEntity.ok(miscCostTypeService.createMiscType(miscType));
    }

    @PreAuthorize(value = "hasRole('ROLE_admin')")
    @PutMapping(value = "/update", name = "Create Misc Types")
    public ResponseEntity<MiscellaneousCostType> createMiscType(@RequestBody MiscellaneousCostType miscType) {
        return ResponseEntity.ok(miscCostTypeService.updateMiscType(miscType));
    }
}
