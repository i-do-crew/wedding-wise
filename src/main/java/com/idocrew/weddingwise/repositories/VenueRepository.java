package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.Vendor;
import com.idocrew.weddingwise.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, Long> {
    List<Venue> findAll();
    List<Venue> findByVendor(Vendor vendor);
    List<Venue> findByCityAndState(String city, String state);
}
