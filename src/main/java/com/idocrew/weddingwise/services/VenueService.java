package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.Venue;

import java.util.Collection;
import java.util.List;

public interface VenueService {
    public Venue findVenueById(long id);
    public Collection<Venue> findVenuesByCityAndState(String city, String state);
    public Venue saveVenue(Venue Venue);
    public void deleteVenue(Venue Venue);
    
}
