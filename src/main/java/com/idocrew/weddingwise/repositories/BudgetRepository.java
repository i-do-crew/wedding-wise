package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.Budget;
import com.idocrew.weddingwise.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository  extends JpaRepository<Budget, Long> {
    Budget findById(long id);

    Budget findBudgetByCustomer(Customer customer);
}
