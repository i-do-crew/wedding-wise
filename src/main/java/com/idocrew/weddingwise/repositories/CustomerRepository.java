package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findById(long id);
}