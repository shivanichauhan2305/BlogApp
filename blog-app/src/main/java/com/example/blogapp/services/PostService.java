package com.example.blogapp.services;

import java.util.List;

import com.example.blogapp.entities.Category;
import com.example.blogapp.entities.Post;
import com.example.blogapp.entities.User;
import com.example.blogapp.payloads.PostDto;
import com.example.blogapp.payloads.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto post, Integer userId, Integer categoryId);	
	PostDto updatePost(PostDto post, Integer postId);
	PostDto getPostById(Integer postId);
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
	void deletePost(Integer postId);
	
	List<PostDto> getPostsByUser(Integer userId);
	
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	//List<PostDto> findByUser(User user);
	
	//List<PostDto> findByCategory(Category category);
	
	List<PostDto> searchPost(String keyword);
	

}
