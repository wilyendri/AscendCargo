package com.api.AscendCargo.repository;

import com.api.AscendCargo.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {

    // By SKU
    Optional<Item> findBySku(String sku);
}
