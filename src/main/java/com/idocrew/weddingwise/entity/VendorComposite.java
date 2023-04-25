package com.idocrew.weddingwise.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VendorComposite {
    private Vendor vendor;
    private Venue venue;
    private Set<MusicGenre> musicGenres;
    private PhotoFormat photoFormat;
    private DjsAndLiveBandsCategory djsAndLiveBandsCategory;
    public User getUser(){
        return vendor.getUser();
    }
}
