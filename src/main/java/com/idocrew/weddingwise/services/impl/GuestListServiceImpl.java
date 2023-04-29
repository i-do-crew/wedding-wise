package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.Guest;
import com.idocrew.weddingwise.repositories.GuestListRepository;
import com.idocrew.weddingwise.services.GuestListService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GuestListServiceImpl implements GuestListService {

    private final EntityManager em;
    private final GuestListRepository guestListRepository;

    @Override
    public List<Guest> findByCustomer(Customer customer) {
        String query = String.format("select l.* from guest_lists l where l.customer_id=%d;", customer.getId());
        return (List<Guest>) em.createNativeQuery(query, Guest.class).getResultList();
    }

    @Override
    public Guest findById(long id) {
        String query = String.format("select l.* from guest_lists l where l.id=%d;", id);
        return (Guest) em.createNativeQuery(query, Guest.class).getSingleResult();
    }

    @Override
    public void save(Guest guest) {
        guestListRepository.save(guest);
    }

    @Override
    public void delete(Guest guest) {
        guestListRepository.delete(guest);
    }
}
