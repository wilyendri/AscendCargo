package com.api.AscendCargo.service;

import com.api.AscendCargo.exceptions.InvalidFormatException;
import com.api.AscendCargo.exceptions.NotFoundException;
import com.api.AscendCargo.model.Orders;
import com.api.AscendCargo.repository.OrderRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private static final String ORDER_NUM_BEGIN = "POOD";

    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public List<Orders> getAllOrders() {
        return orderRepo.findAll();
    }

    public Orders getOrderById(Long id) {
        return orderRepo.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
    }

    public Orders getOrderByOrderNumber(String orderNumber) {
        if(!isOrderNumberValid(orderNumber)) {
            throw new InvalidFormatException("Order number format is invalid");
        }

        return orderRepo.findByOrderNumber(orderNumber).orElseThrow(() -> new NotFoundException("Order not found"));
    }

    public Orders createOrder(Orders order) {

        if(!isOrderNumberValid(order.getOrderNumber())) {
            throw new InvalidFormatException("Order number format is invalid");
        }

        Optional<Orders> orderNumber = orderRepo.findByOrderNumber(order.getOrderNumber());
        if(orderNumber.isPresent()) {
            throw new NotFoundException("Order number already exists");
        }

        return orderRepo.save(order);
    }

    private boolean isOrderNumberValid(String orderNumber) {
        //POOD8134
        if (orderNumber.startsWith(ORDER_NUM_BEGIN) && orderNumber.length() == 8)
        {
            String numbers = orderNumber.substring(4);

            for(char c: numbers.toCharArray())
            {
                if(!Character.isDigit(c))
                {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

}
