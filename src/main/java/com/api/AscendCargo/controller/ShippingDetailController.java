package com.api.AscendCargo.controller;

import com.api.AscendCargo.exceptions.InvalidFormatException;
import com.api.AscendCargo.exceptions.NotFoundException;
import com.api.AscendCargo.model.Orders;
import com.api.AscendCargo.model.ShippingDetails;
import com.api.AscendCargo.service.OrderService;
import com.api.AscendCargo.service.ShippingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/shipping")
public class ShippingDetailController {
    private final ShippingDetailsService shippingDetailsService;

    @Autowired
    public ShippingDetailController(ShippingDetailsService shippingDetailsService) {
        this.shippingDetailsService = shippingDetailsService;
    }

    @GetMapping()
    public ResponseEntity<List<ShippingDetails>> getShippingDetails() {
        List<ShippingDetails> shippingDetails = shippingDetailsService.getShippingDetails();
        return ResponseEntity.ok().body(shippingDetails);
    }

    @GetMapping("/getshipping/{id}")
    public ResponseEntity<ShippingDetails> getShippingDetailById(@PathVariable Long id) {
        ShippingDetails shippingDetails = shippingDetailsService.getShippingDetailById(id);
        return ResponseEntity.ok().body(shippingDetails);
    }

    @PostMapping()
    public ResponseEntity<ShippingDetails> createShippingDetails(@RequestBody ShippingDetails shippingDetails){
        ShippingDetails newShipping = shippingDetailsService.createShippingDetail(shippingDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(newShipping);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<?> handleInvalidFormatException(InvalidFormatException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}
