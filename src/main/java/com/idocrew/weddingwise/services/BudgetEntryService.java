package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.BudgetEntry;
import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.Vendor;

import java.util.List;

public interface BudgetEntryService {

    List<BudgetEntry> findBudgetEntriesByCustomer(Customer customer);

    void save(BudgetEntry budgetEntry);

    void save(List<BudgetEntry> budgetEntries);

    void delete(BudgetEntry budgetEntry);

    BudgetEntry findBudgetEntryByCustomerAndVendor(Customer customer, Vendor vendor);

    BudgetEntry createBudgetEntry(Customer customer, Vendor vendor);
}
