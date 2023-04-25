package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.PhotoFormat;
import com.idocrew.weddingwise.repositories.PhotoFormatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Service("photoFormatService")
public class PhotoFormatServiceImpl implements PhotoFormatService {
    private final PhotoFormatRepository photoFormatRepository;
    @Override
    public Collection<PhotoFormat> findAll() {
        return photoFormatRepository.findAll();
    }
}
