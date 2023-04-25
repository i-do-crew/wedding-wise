package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.Vendor;
import com.idocrew.weddingwise.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, Long> {
    List<Venue> findVenuesByVendor(Vendor vendor);
    List<Venue> findVenuesByCityAndState(String city, String state);
}
