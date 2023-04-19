package com.idocrew.weddingwise.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class VendorsMusicGenreId implements Serializable {
    private static final long serialVersionUID = 1902715407273714167L;
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "vendor_id", nullable = false)
    private Long vendorId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        VendorsMusicGenreId entity = (VendorsMusicGenreId) o;
        return Objects.equals(this.vendorId, entity.vendorId) &&
                Objects.equals(this.id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendorId, id);
    }

}