package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.User;
import com.idocrew.weddingwise.entity.Vendor;

public interface UserRegistrationService {
    void register(Customer customer);
    void register(Vendor vendor);
}
