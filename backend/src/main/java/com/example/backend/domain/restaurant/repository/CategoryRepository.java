package com.example.backend.domain.restaurant.repository;

import com.example.backend.domain.restaurant.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select distinct c from Category c left join fetch c.children")
    List<Category> findAllCategories();

    @Query("select distinct c from Category c left join fetch c.children where c.id = :id")
    Optional<Category> findByIdWithChildren(@Param("id") Long id);
}
