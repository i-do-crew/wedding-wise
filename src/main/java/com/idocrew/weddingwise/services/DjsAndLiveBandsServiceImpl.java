package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.DjsAndLiveBand;
import com.idocrew.weddingwise.repositories.DjsAndLiveBandsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("djsAndLiveBandsService")
public class DjsAndLiveBandsServiceImpl implements DjsAndLiveBandsService {
    private final DjsAndLiveBandsRepository djsAndLiveBandsRepository;
    @Override
    public DjsAndLiveBand saveDjsAndLiveBands(DjsAndLiveBand djsAndLiveBand) {
        return djsAndLiveBandsRepository.save(djsAndLiveBand);
    }
}
