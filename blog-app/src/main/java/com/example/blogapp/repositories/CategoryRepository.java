package com.example.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogapp.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

	
}
