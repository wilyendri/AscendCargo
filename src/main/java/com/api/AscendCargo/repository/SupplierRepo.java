package com.api.AscendCargo.repository;

import com.api.AscendCargo.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepo extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByEmail(String email);

    Optional<Supplier> findByPhone(String phone);
}
