package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.CustomerVendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CustomerVendorRepository extends JpaRepository<CustomerVendor, Long> {
    Set<CustomerVendor> findCustomerVendorByCustomer(Customer customer);
}
