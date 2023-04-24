package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.VendorComposite;
import com.idocrew.weddingwise.exception.InvalidTokenException;

public interface UserRegistrationService {
    void register(Customer customer);
    void register(VendorComposite vendorComposite);
    boolean verifyUser(final String token) throws InvalidTokenException;
}
