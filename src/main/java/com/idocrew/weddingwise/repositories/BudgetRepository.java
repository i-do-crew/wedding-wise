package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.BudgetEntry;
import com.idocrew.weddingwise.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepository  extends JpaRepository<BudgetEntry, Long> {
    BudgetEntry findById(long id);

    List<BudgetEntry> findAllByCustomer(Customer customer);

}
