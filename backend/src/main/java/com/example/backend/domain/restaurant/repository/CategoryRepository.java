package com.example.backend.domain.restaurant.repository;

import com.example.backend.domain.restaurant.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select distinct c from Category c " +
            "left join fetch c.children c1 " +
            "left join fetch c1.children " +
            "where c.parent is null")
    List<Category> findAllCategories();
}
