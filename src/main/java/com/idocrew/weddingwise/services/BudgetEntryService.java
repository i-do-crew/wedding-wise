package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.BudgetEntry;
import com.idocrew.weddingwise.entity.Customer;

import java.util.List;

public interface BudgetEntryService {
    List<BudgetEntry> findBudgetEntriesByCustomer(Customer customer);
}
