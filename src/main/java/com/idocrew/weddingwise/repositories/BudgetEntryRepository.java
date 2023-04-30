package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.BudgetEntry;
import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetEntryRepository extends JpaRepository<BudgetEntry, Long> {

    List<BudgetEntry> findAllByCustomer(Customer customer);
    BudgetEntry findBudgetEntryByCustomerAndVendor(Customer customer, Vendor vendor);
}
