package com.example.blogapp.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.blogapp.entities.Category;
import com.example.blogapp.entities.Post;
import com.example.blogapp.entities.User;
import com.example.blogapp.exceptions.ResourceNotFoundException;
import com.example.blogapp.payloads.CategoryDto;
import com.example.blogapp.payloads.PostDto;
import com.example.blogapp.payloads.PostResponse;
import com.example.blogapp.repositories.CategoryRepository;
import com.example.blogapp.repositories.PostRepository;
import com.example.blogapp.repositories.UserRepository;
import com.example.blogapp.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
		
		Category cat = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(cat);
		
		Post savedPost = this.postRepository.save(post);
		
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto post, Integer postId) {
		Post p = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		Category cat = this.categoryRepository.findById(p.getCategory().getCategory_id()).orElseThrow(() -> new ResourceNotFoundException("Category", "category id", post.getCategory().getCategory_id()));
		p.setCategory(cat);
		p.setContent(post.getContent());
		p.setImageName(post.getImageName());
		p.setTitle(post.getTitle());
		Post updatedPost = this.postRepository.save(p);
		return this.modelMapper.map(updatedPost, PostDto.class);
		
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}	

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc"))
			sort = Sort.by(sortBy).ascending();
		else
			sort = Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort); // Pageable is abstract interface for pagination information
		Page<Post> pagePost = this.postRepository.findAll(pageable);
		List<Post> allPosts = pagePost.getContent();
		List<PostDto> posts = allPosts.stream().map(p ->this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(posts);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		this.postRepository.delete(post);
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
		List<Post> postsByUser = this.postRepository.findByUser(user);
		List<PostDto> posts = postsByUser.stream().map(p ->this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
		return posts;
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category cat = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		List<Post> postsByCategory = this.postRepository.findByCategory(cat);
		List<PostDto> posts = postsByCategory.stream().map(p ->this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
		return posts;
	}

	

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> posts = this.postRepository.findByTitleContaining(keyword);
		List<PostDto> postdto = posts.stream().map(p ->this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
		return postdto;
	}
	
	/*
	 * @Override
	public List<Post> findByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> findByCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}
	 * */
	

}
