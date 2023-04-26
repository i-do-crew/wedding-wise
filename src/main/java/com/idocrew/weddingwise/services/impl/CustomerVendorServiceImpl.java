package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.CustomerVendor;
import com.idocrew.weddingwise.entity.Vendor;
import com.idocrew.weddingwise.repositories.CustomerVendorRepository;
import com.idocrew.weddingwise.services.CustomerVendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
@RequiredArgsConstructor
@Service("customerVendorsService")
public class CustomerVendorServiceImpl implements CustomerVendorService {
    private final CustomerVendorRepository customerVendorRepository;
    @Override
    public Set<CustomerVendor> findByCustomer(Customer customer) {
        return customerVendorRepository.findCustomerVendorByCustomer(customer);
    }

    @Override
    public CustomerVendor save(CustomerVendor customerVendor) {
        return customerVendorRepository.save(customerVendor);
    }

    @Override
    public CustomerVendor findById(Long id) {
        return customerVendorRepository.findById(id).get();
    }

    @Override
    public Optional<CustomerVendor> findByCustomerAndVendor(Customer customer, Vendor vendor) {
        return customerVendorRepository.findCustomerVendorByCustomerAndVendor(customer, vendor);
    }
}
