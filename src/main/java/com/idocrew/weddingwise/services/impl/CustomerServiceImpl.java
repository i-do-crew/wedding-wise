package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.User;
import com.idocrew.weddingwise.repositories.CustomerRepository;
import com.idocrew.weddingwise.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    @Override
    public Customer findCustomerById(long id) {
        return customerRepository.getReferenceById(id);
    }

    @Override
    public Customer findCustomerByUser(User user) {
        return customerRepository.findCustomerByUser(user);
    }

    @Override
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }
}
