package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.Vendor;
import com.idocrew.weddingwise.entity.VendorComposite;

public interface UserRegistrationService {
    void register(Customer customer);
    void register(VendorComposite vendorComposite);
}
