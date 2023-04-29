package com.idocrew.weddingwise.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Scope("session")
public class VendorComposite {
    private Vendor vendor;
    private Venue venue;
    private Set<MusicGenre> musicGenres;
    private PhotoFormat photoFormat;
    private MusicVendorCategory musicVendorCategory;
    public User getUser(){
        return vendor.getUser();
    }
}
