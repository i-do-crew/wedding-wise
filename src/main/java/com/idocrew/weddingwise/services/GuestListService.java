package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.Guest;

import java.util.List;

public interface GuestListService {
    List<Guest> findByCustomer(Customer customer);
    Guest findById(long id);
    void save(Guest guest);
    void delete(Guest guest);
}
