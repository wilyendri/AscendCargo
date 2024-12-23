package com.api.AscendCargo.repository;

import com.api.AscendCargo.model.Orders;
import com.api.AscendCargo.model.ShippingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShippingDetailsRepo extends JpaRepository<ShippingDetails, Long> {

    Optional<ShippingDetails> findByPhone(String phone);
}
