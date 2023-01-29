package com.example.blogapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogapp.entities.Category;
import com.example.blogapp.entities.Post;
import com.example.blogapp.entities.User;

public interface PostRepository extends JpaRepository<Post, Integer>{
	
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String keyword);
	
}
