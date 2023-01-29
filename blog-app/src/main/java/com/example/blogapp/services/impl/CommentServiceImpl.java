package com.example.blogapp.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blogapp.entities.Comment;
import com.example.blogapp.entities.Post;
import com.example.blogapp.exceptions.ResourceNotFoundException;
import com.example.blogapp.payloads.CommentDto;
import com.example.blogapp.repositories.CommentRepository;
import com.example.blogapp.repositories.PostRepository;
import com.example.blogapp.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto comment, Integer postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment id", postId));
		Comment cmt = this.modelMapper.map(comment,Comment.class);
		cmt.setPost(post);
		Comment savedComment = this.commentRepository.save(cmt);
		return this.modelMapper.map(savedComment,CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment cmt = this.commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment id", commentId));
		this.commentRepository.delete(cmt);
	}

}
