package com.idocrew.weddingwise;

import com.idocrew.weddingwise.entity.VendorComposite;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WeddingWiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeddingWiseApplication.class, args);
	}

	@Bean
	public VendorComposite vendorComposite() {
		return new VendorComposite();
	}
}
