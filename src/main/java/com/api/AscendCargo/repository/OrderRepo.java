package com.api.AscendCargo.repository;

import com.api.AscendCargo.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepo extends JpaRepository<Orders, Long> {
    Optional<Orders> findByOrderNumber(String orderNumber);

}
