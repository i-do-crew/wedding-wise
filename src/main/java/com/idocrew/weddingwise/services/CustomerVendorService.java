package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.CustomerVendor;
import com.idocrew.weddingwise.entity.Vendor;

import java.util.Optional;
import java.util.Set;

public interface CustomerVendorService {
    Set<CustomerVendor> findByCustomer(Customer customer);
    CustomerVendor save(CustomerVendor customerVendor);
    CustomerVendor findById(Long id);
    Optional<CustomerVendor> findByCustomerAndVendor(Customer customer, Vendor vendor);
    Vendor findByVendor(Vendor vendor);
}
