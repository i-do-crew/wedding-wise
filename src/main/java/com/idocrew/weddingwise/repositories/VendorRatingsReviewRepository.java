package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.Vendor;
import com.idocrew.weddingwise.entity.VendorRatingsReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorRatingsReviewRepository  extends JpaRepository<VendorRatingsReview, Long> {
    List<VendorRatingsReview> findByVendor(Vendor vendor);
    List<VendorRatingsReview> findByCustomer(Customer customer);
}
