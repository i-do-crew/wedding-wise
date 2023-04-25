package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.User;

public interface CustomerService {
    Customer findCustomerById(long id);
    Customer findCustomerByUser(User user);
    void saveCustomer(Customer Customer);
    void deleteCustomer(Customer Customer);
}
