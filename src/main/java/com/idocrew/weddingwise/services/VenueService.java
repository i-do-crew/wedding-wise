package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.Vendor;
import com.idocrew.weddingwise.entity.Venue;

import java.util.Collection;
import java.util.List;

public interface VenueService {
    Venue findVenueById(long id);
    Collection<Venue> findVenuesByCityAndState(String city, String state);
    Venue saveVenue(Venue Venue);
    void deleteVenue(Venue Venue);
    List<Venue> findByVendor(Vendor vendor);
}
