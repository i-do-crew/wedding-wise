package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.CustomerVendor;

import java.util.Set;

public interface CustomerVendorService {
    Set<CustomerVendor> findByCustomer(Customer customer);
}
