package com.api.AscendCargo.controller;

import com.api.AscendCargo.exceptions.ForeignKeyException;
import com.api.AscendCargo.exceptions.InvalidFormatException;
import com.api.AscendCargo.exceptions.NotFoundException;
import com.api.AscendCargo.model.Supplier;
import com.api.AscendCargo.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/supplier")
public class SupplierController {
    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping()
    public ResponseEntity<List<Supplier>> getSupplier() {
        List<Supplier> suppliers = supplierService.getSuppliers();
        return ResponseEntity.ok().body(suppliers);
    }

    @GetMapping("/getsupplier/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
        Supplier supplier = supplierService.getSupplierById(id);
        return ResponseEntity.ok().body(supplier);
    }

    @PostMapping()
    public ResponseEntity<Supplier> createShippingDetails(@RequestBody Supplier supplier){
        Supplier newSupplier = supplierService.createSupplier(supplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSupplier);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<?> handleInvalidFormatException(InvalidFormatException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ForeignKeyException.class)
    public ResponseEntity<?> handleForeignKeyException(ForeignKeyException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}
