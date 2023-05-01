package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.Vendor;
import com.idocrew.weddingwise.entity.VendorRatingsReview;
import com.idocrew.weddingwise.repositories.VendorRatingsReviewRepository;
import com.idocrew.weddingwise.services.VendorRatingsReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VendorRatingsReviewServiceImpl implements VendorRatingsReviewService {

    private final VendorRatingsReviewRepository vendorRatingsReviewRepository;

    @Override
    public List<VendorRatingsReview> findByVendor(Vendor vendor) {
        return vendorRatingsReviewRepository.findByVendor(vendor);
    }

    @Override
    public List<VendorRatingsReview> findByCustomer(Customer customer) {
        return vendorRatingsReviewRepository.findByCustomer(customer);
    }
}
