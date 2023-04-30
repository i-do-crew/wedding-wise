package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.BudgetEntry;
import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.Vendor;
import com.idocrew.weddingwise.repositories.BudgetEntryRepository;
import com.idocrew.weddingwise.services.BudgetEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service("budgetEntryService")
public class BudgetEntryServiceImpl implements BudgetEntryService {
    private final BudgetEntryRepository budgetEntryRepository;
    @Override
    public List<BudgetEntry> findBudgetEntriesByCustomer(Customer customer) {
        return budgetEntryRepository.findAllByCustomer(customer);
    }

    @Override
    public void save(List<BudgetEntry> budgetEntries) {
        budgetEntryRepository.saveAll(budgetEntries);
    }

    @Override
    public void delete(BudgetEntry budgetEntry) {
        budgetEntryRepository.delete(budgetEntry);
    }

    @Override
    public BudgetEntry findBudgetEntryByCustomerAndVendor(Customer customer, Vendor vendor) {
        return budgetEntryRepository.findBudgetEntryByCustomerAndVendor(customer, vendor);
    }

}
