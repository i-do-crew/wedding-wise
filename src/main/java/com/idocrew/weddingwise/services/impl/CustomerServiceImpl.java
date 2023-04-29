package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.User;
import com.idocrew.weddingwise.repositories.CustomerRepository;
import com.idocrew.weddingwise.services.CustomerService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final EntityManager em;

    @Override
    public Customer findCustomerById(long id) {
        return customerRepository.getReferenceById(id);
    }

    @Override
    public Customer findCustomerByUser(User user) {
        String query = String.format("select c.* from customers c where c.user_id=%d;", user.getId());
        return (Customer) em.createNativeQuery(query, Customer.class).getSingleResult();
        //return customerRepository.findCustomerByUser(user);
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
