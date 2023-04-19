package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findById(long id);
    List<Category> findAll();
}
