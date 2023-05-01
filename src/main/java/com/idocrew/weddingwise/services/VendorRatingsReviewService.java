package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.Vendor;
import com.idocrew.weddingwise.entity.VendorRatingsReview;

import java.util.List;

public interface VendorRatingsReviewService {

    List<VendorRatingsReview> findByVendor(Vendor vendor);
    List<VendorRatingsReview> findByCustomer(Customer customer);
}
