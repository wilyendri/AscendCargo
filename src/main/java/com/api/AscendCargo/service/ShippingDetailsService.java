package com.api.AscendCargo.service;

import com.api.AscendCargo.exceptions.NotFoundException;
import com.api.AscendCargo.model.ShippingDetails;
import com.api.AscendCargo.model.Supplier;
import com.api.AscendCargo.repository.ShippingDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShippingDetailsService {
    private final ShippingDetailsRepo shippingDetailsRepo;

    @Autowired
    public ShippingDetailsService(ShippingDetailsRepo shippingDetailsRepo) {
        this.shippingDetailsRepo = shippingDetailsRepo;
    }

    public List<ShippingDetails> getShippingDetails()
    {
        return shippingDetailsRepo.findAll();
    }

    public ShippingDetails getShippingDetailById(Long id)
    {
        return shippingDetailsRepo.findById(id).orElseThrow(() -> new NotFoundException("Shipping details not found"));
    }

    public ShippingDetails createShippingDetail(ShippingDetails shippingDetails)
    {
        Optional<ShippingDetails> shippingDetailByPhone = shippingDetailsRepo.findByPhone(shippingDetails.getPhone());

        if(shippingDetailByPhone.isPresent())
        {
            throw new NotFoundException("Supplier already exists");
        }

        return shippingDetailsRepo.save(shippingDetails);
    }
}
