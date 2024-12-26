package com.api.AscendCargo.service;

import com.api.AscendCargo.exceptions.ForeignKeyException;
import com.api.AscendCargo.exceptions.NotFoundException;
import com.api.AscendCargo.model.Supplier;
import com.api.AscendCargo.repository.SupplierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
   private final SupplierRepo supplierRepo;

   @Autowired
    public SupplierService(SupplierRepo supplierRepo) {
       this.supplierRepo = supplierRepo;
   }

    public List<Supplier> getSuppliers()
    {
        return supplierRepo.findAll();
    }

    public Supplier getSupplierById(Long id)
    {
        return supplierRepo.findById(id).orElseThrow(() -> new RuntimeException("Supplier details not found"));
    }

    public Supplier createSupplier(Supplier supplier)
    {
        Optional<Supplier> supplierByEmail = supplierRepo.findByEmail(supplier.getEmail());
        Optional<Supplier> supplierByPhone = supplierRepo.findByPhone(supplier.getPhone());

        if(supplierByEmail.isPresent() || supplierByPhone.isPresent())
        {
            throw new NotFoundException("Supplier already exists");
        }

        try
        {
            return supplierRepo.save(supplier);
        } catch (DataIntegrityViolationException e)
        {
            throw new ForeignKeyException("Foreign Key violation.");
        }
    }
}
