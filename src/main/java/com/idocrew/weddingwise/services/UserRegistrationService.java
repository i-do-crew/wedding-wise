package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entities.Customer;
import com.idocrew.weddingwise.entities.VendorComposite;

public interface UserRegistrationService {
    void register(Customer customer);
    void register(VendorComposite vendorComposite);
}
