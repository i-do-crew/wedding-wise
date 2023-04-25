package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.Venue;
import com.idocrew.weddingwise.repositories.VenueRepository;
import com.idocrew.weddingwise.services.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Service("VenueService")
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;

    @Override
    public Venue findVenueById(long id) {
        return venueRepository.getReferenceById(id);
    }

    @Override
    public Collection<Venue> findVenuesByCityAndState(String city, String state) {
        return venueRepository.findVenuesByCityAndState(city, state);
    }

    @Override
    public Venue saveVenue(Venue venue) {
        return venueRepository.save(venue);
    }

    @Override
    public void deleteVenue(Venue venue) {
        venueRepository.delete(venue);
    }
}
