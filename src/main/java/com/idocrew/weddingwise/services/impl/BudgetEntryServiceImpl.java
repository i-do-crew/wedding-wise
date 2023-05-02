package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.*;
import com.idocrew.weddingwise.repositories.BudgetEntryRepository;
import com.idocrew.weddingwise.repositories.SimpleBudgetEntryRepository;
import com.idocrew.weddingwise.services.BudgetEntryService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service("budgetEntryService")
public class BudgetEntryServiceImpl implements BudgetEntryService {
    private final BudgetEntryRepository budgetEntryRepository;
    private final SimpleBudgetEntryRepository simpleBudgetEntryRepository;
    private final EntityManager em;


    @Override
    public List<SimpleBudgetEntry> findSimpleBudgetEntriesByCustomer(Customer customer) {
        String query = String.format("select * from budget_entries where customer_id = %d;", customer.getId());
        return (List<SimpleBudgetEntry>) em.createNativeQuery(query, SimpleBudgetEntry.class).getResultList();

    }

    @Override
    public List<BudgetEntry> findBudgetEntriesByCustomer(Customer customer) {
        String query = String.format("select * from budget_entries where customer_id = %d;", customer.getId());
        return (List<BudgetEntry>) em.createNativeQuery(query, BudgetEntry.class).getResultList();
        //return budgetEntryRepository.findAllByCustomer(customer);
    }

    @Override
    public void save(BudgetEntry budgetEntry) {
        budgetEntryRepository.save(budgetEntry);
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

    @Override
    public BudgetEntry createBudgetEntry(Customer customer, Vendor vendor) {
        BudgetEntry budgetEntry = new BudgetEntry();
        budgetEntry.setCustomer(customer);
        budgetEntry.setVendor(vendor);
        budgetEntry.setAmount(BigDecimal.valueOf(0));
        budgetEntryRepository.save(budgetEntry);
        return budgetEntry;
    }

}
