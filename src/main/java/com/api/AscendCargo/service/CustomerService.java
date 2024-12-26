package com.api.AscendCargo.service;

import com.api.AscendCargo.exceptions.ForeignKeyException;
import com.api.AscendCargo.exceptions.InvalidFormatException;
import com.api.AscendCargo.exceptions.NotFoundException;
import com.api.AscendCargo.model.Customer;
import com.api.AscendCargo.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepo customerRepo;

    @Autowired
    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Customer getCustomerById(Long id)
    {
        return customerRepo.findById(id).orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    public List<Customer> getAllCustomer()
    {
        return customerRepo.findAll();
    }

    public Customer createCustomer(Customer customer)
    {
        Optional<Customer> customerByPhone = customerRepo.findByPhone(customer.getPhone());

        if(customerByPhone.isPresent())
        {
            throw new NotFoundException("Customer already exists");
        }

        try
        {
            return customerRepo.save(customer);
        } catch (DataIntegrityViolationException e)
        {
            throw new ForeignKeyException("Foreign Key violation: " + e.getMessage());
        }

    }
}
