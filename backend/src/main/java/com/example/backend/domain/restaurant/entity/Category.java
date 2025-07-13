package com.example.backend.domain.restaurant.entity;

import com.example.backend.domain.restaurant.exception.CategoryDepthExceededException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;
    private int depth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Category> children = new ArrayList<>();

    private Category(String name, Category parent, int depth) {
        this.name = name;
        this.parent = parent;
        this.depth = depth;
    }

    public static Category of(String name, Category parent) {
        int depth = (parent != null) ? parent.getDepth() + 1 : 1;

        if (depth < 1 || depth > 3) {
            throw new CategoryDepthExceededException(depth);
        }

        return new Category(name, parent, depth);
    }
}