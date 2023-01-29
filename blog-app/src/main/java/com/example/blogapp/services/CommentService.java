package com.example.blogapp.services;

import com.example.blogapp.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto comment,Integer postId);
	
	void deleteComment(Integer commentId);
}
