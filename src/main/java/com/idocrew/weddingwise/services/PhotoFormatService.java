package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.PhotoFormat;
import com.idocrew.weddingwise.entity.Vendor;

import java.util.Collection;

public interface PhotoFormatService {

    Collection<PhotoFormat> findAll();
}
